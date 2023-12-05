package landrive.fileserver.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

public class FsHandler {
    private final Path storageRoot;
    private final Map<Path, Lock> locks = new ConcurrentHashMap<>();

    public FsHandler(final String storageRoot) {
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

    public FsDirectoryList getFileList(String path) {
        final Path dirPath = storageRoot.resolve(path);
        if (!dirPath.toAbsolutePath().startsWith(this.storageRoot.toAbsolutePath())) {
            return new FsDirectoryList();
        }
        final File dir = dirPath.toFile();
        if (!dir.exists()) {
            throw new RuntimeException();
        }
        return new FsDirectoryList(dir);
    }
}
