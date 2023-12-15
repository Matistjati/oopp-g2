import landrive.fileserver.filesystem.FsFileLocker;
import org.junit.jupiter.api.Test;


import java.nio.file.Path;

public class FsFileLockerTest {
    private final Path path = Path.of("./");
    @Test
    public void tryWriterBlocksReader(){
        FsFileLocker locker = new FsFileLocker();
        assert locker.tryWriterLock(path);
        assert !locker.tryAddReader(path);
    }

    @Test
    public void tryReaderBlocksWriter(){
        FsFileLocker locker = new FsFileLocker();
        assert locker.tryAddReader(path);
        assert !locker.tryWriterLock(path);
    }

    @Test
    public void tryWWritersBlockEachOther(){
        FsFileLocker locker = new FsFileLocker();
        assert locker.tryWriterLock(path);
        assert !locker.tryWriterLock(path);
    }

    @Test
    public void tryReaderDontBlocksEachOther(){
        FsFileLocker locker = new FsFileLocker();
        assert locker.tryAddReader(path);
        assert locker.tryAddReader(path);
    }

    @Test
    public void tryReleaseReader(){
        FsFileLocker locker = new FsFileLocker();
        assert locker.tryAddReader(path);
        locker.releaseReader(path);
        assert locker.tryWriterLock(path);
    }

    @Test
    public void tryReleaseWriter(){
        FsFileLocker locker = new FsFileLocker();
        assert locker.tryWriterLock(path);
        locker.writerUnlock(path);
        assert locker.tryWriterLock(path);
    }

    @Test
    public void tryAddMultipleReaders(){
        FsFileLocker locker = new FsFileLocker();
        assert locker.tryAddReader(path);
        assert locker.tryAddReader(path);
        locker.releaseReader(path);
        assert !locker.tryWriterLock(path);
    }
}
