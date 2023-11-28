package filehandling;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FsEntryAsdsdsdsds {
    private final Lock lock = new ReentrantLock();
    private final AtomicBoolean deleted = new AtomicBoolean(false);
    private final Map<String, FsEntryAsdsdsdsds> children = new ConcurrentHashMap<>();

    public FsEntryAsdsdsdsds(final Path path) {
        final File file = path.toFile();
        if (file.isDirectory()) {
            for (final String name : file.list()) {
                if (!children.containsKey(name)){

                }
                FsEntryAsdsdsdsds newEntry = new FsEntryAsdsdsdsds(Path.of(path.toString(), name));
            }
        }
    }

    public boolean acquire() {
        this.lock.lock();
        if (this.deleted.get()){
            this.lock.unlock();
            return false;
        }
        this.children.values().forEach(FsEntryAsdsdsdsds::acquire);
        return true;
    }

    public Optional<FsEntryAsdsdsdsds> getChild(List<String> path) {
        if (path.size() == 0) {
            return Optional.of(this);
        }
        final String name = path.remove(0);
        final FsEntryAsdsdsdsds child = this.children.get(name);
        if (child == null || child.isDeleted()) {
            return Optional.empty();
        }
        return child.getChild(path);
    }

    public void release() {
        this.children.values().forEach(FsEntryAsdsdsdsds::release);
        this.lock.unlock();
    }

    public boolean isDeleted() {
        return this.deleted.get();
    }
}
