package filehandling;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FsHandler {
    private final Map<Path, Lock> locks = new ConcurrentHashMap<>();
    public FsHandler() {

    }

    public boolean acquire(Path path) {
        File file = path.toFile();
        if (!file.exists()) {
            return false;
        }
        this.lock(path);
        List.of(file.listFiles()).forEach(subFile -> this.acquire(Path.of(subFile.getPath())));
        return true;
    }

    public synchronized void lock(Path path) {
        final Lock fileLock = locks.get(path);
        if (fileLock == null) {
            Lock newFileLock = new ReentrantLock();
            newFileLock.lock();
            locks.put(path, newFileLock);
            return;
        }
        fileLock.lock();
    }

    public void release() {

    }

    public FsList getFileList(Path path) {
        return new FsList(path.toFile());
    }

    private abstract static class FsEntry{
        private String name;

    }
}
