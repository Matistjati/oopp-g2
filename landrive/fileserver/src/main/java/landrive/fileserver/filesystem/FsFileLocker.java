package landrive.fileserver.filesystem;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FsFileLocker {
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

    private static class SimpleCounter {
        private int count = 0;

        public SimpleCounter(final int count) {
            this.count = count;
        }

        public SimpleCounter() {}

        public int getCount() {
            return count;
        }

        public void increment() {
            this.count ++;
        }

        public void decrement() {
            this.count --;
        }
    }

    private final Map<String, Integer> activeReaders = new HashMap<>();
    private final Map<String, SimpleLock> writerLocks = new HashMap<>();

    private synchronized boolean tryAddReader(Path path) {
        return tryAddReader(path.toFile());
    }

    private synchronized void releaseReader(final Path path) {
        releaseReader(path.toFile());
    }

    private synchronized void releaseReader(final File file) {
        decrementReaderCount(file.toPath());
        final File[] children = file.listFiles();
        for (File child : children) {
            this.releaseReader(child);
        }
    }

    private synchronized boolean tryAddReader(final File file) {
        if (hasWriter(file.toPath())) {
            final File[] children = file.listFiles();
            if (children == null) {
                increaseReaderCount(file.toPath());
                return true;
            }
            final List<File> lockedChildren = new ArrayList<>();
            for (final File child : children) {
                if (!this.tryAddReader(child)) {
                    for (final File lockedChild : lockedChildren) {
                        this.decrementReaderCount(lockedChild.toPath());
                    }
                    return false;
                }
                lockedChildren.add(child);
            }
            increaseReaderCount(file.toPath());
            return true;
        }
        return false;
    }

    private synchronized boolean hasWriter(final Path path){
        return writerLocks.getOrDefault(path, new SimpleLock(false)).isLocked();
    }

    private synchronized void increaseReaderCount(final Path path){
        activeReaders.put(path.toString(), activeReaders.getOrDefault(path, 0) + 1);
    }

    private synchronized void decrementReaderCount(final Path path) {
        activeReaders.put(path.toString(), activeReaders.getOrDefault(path, 0) - 1);
    }
    private synchronized void writerUnlock(final Path path) {
        this.writerUnlock(path.toFile());
    }

    private synchronized void writerUnlock(final File file) {
        writerLocks.getOrDefault(file.getPath(), new SimpleLock(false)).setLocked(false);
        final File[] children = file.listFiles();
        for (File child : children) {
            this.releaseReader(child);
        }
    }

    private synchronized boolean tryWriterLock(final Path path) {
        return tryWriterLock(path.toFile());
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
