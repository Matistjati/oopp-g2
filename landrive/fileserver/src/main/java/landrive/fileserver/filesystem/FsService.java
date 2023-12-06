package landrive.fileserver.filesystem;

import io.vertx.core.Future;
import io.vertx.core.file.FileSystem;
import io.vertx.core.http.HttpServerFileUpload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;


public class FsService {
    private final FileSystem fs;
    private final Path storageRoot;
    private final Map<Path, Lock> fileLocks = new ConcurrentHashMap<>();

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

    private boolean lock(Path path) {
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

    public Future<Void> uploadFile(final HttpServerFileUpload fileUpload) {
        final Path filePath = this.storageRoot.resolve(fileUpload.filename());
        if (!validPath(filePath)) {
            return Future.failedFuture(new IllegalAccessException("Path is not valid."));
        }
        return fileUpload.streamToFileSystem(filePath.toString());
    }
}
