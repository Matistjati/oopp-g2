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

public class FsService {
    private final FileSystem fs;
    private final Path storageRoot;

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
            System.out.println("Path is not valid.");
            return Future.failedFuture(new IllegalAccessException("Path is not valid."));
        }

        String filePathString = filePath.toString();

        // If file already exist add number after name
        if (Files.exists(filePath)) {
            int to_add = 1;
            String filePathWithoutExtension = filePathString.substring(0, filePathString.lastIndexOf('.'));
            String fileExtension = filePathString.substring(filePathString.lastIndexOf('.'));
            String to_try = filePathWithoutExtension + " (" + Integer.toString(to_add) + ")" + fileExtension;
            while (Files.exists(Path.of(to_try))) {
                to_add++;
                to_try = filePathWithoutExtension + " (" + Integer.toString(to_add) + ")" + fileExtension;
            }
            filePathString = to_try;
        }

        return fileUpload.streamToFileSystem(filePathString);
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
