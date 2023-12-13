package landrive.fileserver.filesystem;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FsFileLocker {
    private static class SimpleLock {
        private boolean locked;

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

    private final Map<String, Integer> activeReaders = new HashMap<>();
    private final Map<String, SimpleLock> writerLocks = new HashMap<>();

    private synchronized void releaseReader(final Path path) {
        this.releaseReader(path.toFile());
    }

    private synchronized void releaseReader(final File file) {
        decrementReaderCount(file.getPath());
        final File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                this.releaseReader(child);
            }
        }
    }

    private synchronized boolean tryAddReader(Path path) {
        return this.tryAddReader(path.toFile());
    }

    private synchronized boolean tryAddReader(final File file) {
        if (!this.hasWriter(file.getPath())) {
            final File[] children = file.listFiles();
            if (children == null) {
                this.increaseReaderCount(file.getPath());
                return true;
            }
            final List<File> lockedChildren = new ArrayList<>();
            for (final File child : children) {
                if (!this.tryAddReader(child)) {
                    for (final File lockedChild : lockedChildren) {
                        this.decrementReaderCount(lockedChild.getPath());
                    }
                    return false;
                }
                lockedChildren.add(child);
            }
            this.increaseReaderCount(file.getPath());
            return true;
        }
        return false;
    }

    private synchronized void increaseReaderCount(final String path) {
        activeReaders.put(path, activeReaders.getOrDefault(path, 0) + 1);
    }

    private synchronized void decrementReaderCount(final String path) {
        activeReaders.put(path, activeReaders.getOrDefault(path, 0) - 1);
    }

    private synchronized boolean hasWriter(final String path){
        return writerLocks.getOrDefault(path, new SimpleLock(false)).isLocked();
    }

    private synchronized void writerUnlock(final Path path) {
        this.writerUnlock(path.toFile());
    }

    private synchronized void writerUnlock(final File file) {
        writerLocks.getOrDefault(file.getPath(), new SimpleLock(false)).setLocked(false);
        final File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                this.releaseReader(child);
            }
        }
    }

    private synchronized boolean tryWriterLock(final Path path) {
        return this.tryWriterLock(path.toFile());
    }

    private synchronized boolean tryWriterLock(final File file) {
        this.writerLocks.putIfAbsent(file.getPath(), new SimpleLock(false));
        final SimpleLock lock = this.writerLocks.get(file.getPath());
        final int activeReaderCount = this.activeReaders.getOrDefault(file.getPath(), 0);
        if (!lock.isLocked() && activeReaderCount == 0) {
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
}
