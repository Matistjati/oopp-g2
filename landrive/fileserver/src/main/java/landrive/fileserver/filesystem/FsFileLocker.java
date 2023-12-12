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
        final SimpleLock lock = writerLocks.getOrDefault(path, new SimpleLock(false));
        if (lock.isLocked()) {
            return false;
        }
        activeReaders.put(path.toString(), activeReaders.getOrDefault(path, 0) + 1);
        return true;
    }


    private synchronized boolean tryAddReader(final File file) {

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
