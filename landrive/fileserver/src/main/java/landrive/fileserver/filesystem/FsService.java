package landrive.fileserver.filesystem;

import io.vertx.core.Future;
import io.vertx.core.file.FileSystem;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerFileUpload;
import io.vertx.core.http.HttpServerResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FsService {
    private static class SimpleLock {
        private boolean locked = false;

        public SimpleLock(final boolean locked) {
            this.locked = locked;
        }

        private boolean isLocked() {
            return this.locked;
        }

        private void setLocked(final boolean locked) {
            this.locked = locked;
        }
    }

    private final FileSystem fs;
    private final Path storageRoot;
    private final Map<String, Integer> activeReaders = new HashMap<>();
    private final Map<String, SimpleLock> writerLocks = new HashMap<>();

    public FsService(final FileSystem fs, final String storageRoot) {
        this.fs = fs;
        this.storageRoot = Path.of(storageRoot);
        if (!this.storageRoot.toFile().exists()) {
            try {
                Files.createDirectories(this.storageRoot);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean validPath(final Path path) {
        return (path.toAbsolutePath().startsWith(this.storageRoot.toAbsolutePath()));
    }

    private synchronized boolean tryAddReader(Path path) {
        final SimpleLock lock = writerLocks.getOrDefault(path, new SimpleLock(false));
        if (lock.isLocked()) {
            return false;
        }
        activeReaders.put(path.toString(), activeReaders.getOrDefault(path, 0) + 1);
        return true;
    }

    private synchronized void writerUnlock(final Path path) {
        this.writerUnlock(path.toFile());
    }

    private synchronized void writerUnlock(final File file) {
        final File[] children = file.listFiles();
        if (children == null) {
            writerLocks.getOrDefault(file.getPath(), new SimpleLock(false)).setLocked(false);
            return;
        }
        for (File child : children) {
            this.writerUnlock(child);
        }
    }

    private synchronized boolean tryWriterLock(Path path) {
        return tryWriterLock(path.toFile());
    }

    private synchronized boolean tryWriterLock(final File file) {
        final SimpleLock lock = writerLocks.getOrDefault(file.getPath(), new SimpleLock(false));
        if (!lock.isLocked() && activeReaders.getOrDefault(file.getPath(), 0) == 0) {
            final File[] children = file.listFiles();
            if (children == null) {
                lock.setLocked(true);
                return true;
            }
            final List<File> lockedChildren = new ArrayList<>();
            for (final File child : children) {
                if (!this.tryWriterLock(child)) {
                    for (final File lockedChild : lockedChildren) {
                        this.writerUnlock(lockedChild);
                    }
                    return false;
                }
                lockedChildren.add(child);
            }
            lock.setLocked(true);
            return true;
        }
        return false;
    }

    public FsDirectoryList getFileList(String path) {
        final Path dirPath = this.storageRoot.resolve(path);
        if (!validPath(dirPath)) {
            return new FsDirectoryList();
        }
        final File dir = dirPath.toFile();
        if (!dir.exists()) {
            throw new RuntimeException();
        }
        return new FsDirectoryList(dir);
    }

    public Future<Void> downloadFile(final String fileName, final String subDirectory, HttpServerResponse response) {
        final Path filePath = this.storageRoot.resolve(subDirectory).resolve(fileName);
        final String filePathString = filePath.toString();
        System.out.println("Downloading file: " + fileName + " from directory: " + filePathString);
        if (!validPath(filePath)) {
            return Future.failedFuture(new IllegalAccessException("Path is not valid."));
        }
        response.putHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        response.putHeader(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        response.sendFile(filePathString, ar -> {
            if (ar.succeeded()) {
                System.out.println("File downloaded successfully");
            } else {
                System.out.println("File download failed");
                ar.cause().printStackTrace();
            }
        });
        return Future.succeededFuture();
    }

    public Future<Void> uploadFile(final HttpServerFileUpload fileUpload, String dir) {
        final Path filePath = this.storageRoot.resolve(dir).resolve(fileUpload.filename());
        if (!validPath(filePath)) {
            return Future.failedFuture(new IllegalAccessException("Path is not valid."));
        }
        return fileUpload.streamToFileSystem(filePath.toString());
    }

    public Future<Void> renameFile(String path, String newName) {
        final Path filePath = this.storageRoot.resolve(path);
        if (!validPath(filePath)) {
            return Future.failedFuture(new IllegalAccessException("Path is not valid."));
        }
        try {
            final Path newPath = filePath.resolveSibling(newName);
            Files.move(filePath, newPath);
        }
        catch (Throwable e) {
            return Future.failedFuture(e);
        }
        return Future.succeededFuture();
    }

    public Future<Void> createFolder(String path, String folderName) {
        // Check if the path is valid
        if (folderName==null) return Future.failedFuture(new IllegalArgumentException("folderName is null"));
        final Path filePath = this.storageRoot.resolve(path);
        if (!validPath(filePath)) {
            return Future.failedFuture(new IllegalAccessException("Path is not valid."));
        }

        // Check if the folder already exists
        Path newFolderPath = filePath.resolve(folderName);
        if (Files.exists(newFolderPath)) {
            return Future.succeededFuture(); // Folder already exists, no need to create
        }

        try {
            // Create the folder
            Files.createDirectories(newFolderPath);
        } catch (Throwable e) {
            return Future.failedFuture(e);
        }

        return Future.succeededFuture();
    }
}
