package filehandling;

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

    public FsHandler(Path storageRoot) {
        this.storageRoot = storageRoot;
        if (!storageRoot.toFile().exists()) {
            try {
                Files.createDirectories(storageRoot);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public FsDirectoryList getFileList(Path path) {
        final File dir = storageRoot.resolve(path).toFile();
        if (!dir.exists()) {
            throw new RuntimeException();
        }
        return new FsDirectoryList(dir);
    }
}
