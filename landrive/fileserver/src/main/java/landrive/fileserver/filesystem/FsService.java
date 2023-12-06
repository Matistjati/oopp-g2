package landrive.fileserver.filesystem;

import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.http.HttpServerFileUpload;
import io.vertx.core.impl.ContextInternal;
import io.vertx.core.streams.Pipe;
import io.vertx.core.streams.impl.PipeImpl;

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
    private final Map<Path, Lock> locks = new ConcurrentHashMap<>();

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

    public Future<Void> uploadFile(HttpServerFileUpload fileUpload) {
        final Pipe<Buffer> pipe = new PipeImpl<>(fileUpload);
        final String filename = fileUpload.filename();
        pipe.endOnComplete(true);
        Future<AsyncFile> fut = fs.open(this.storageRoot.resolve(filename).toString(), new OpenOptions());
        fut.onFailure(err -> {
            pipe.close();
        });
        return fut.compose(f -> {
            Future<Void> to = pipe.to(f);
            return to.compose(v -> {
                synchronized (FsService.this) {
                    return ContextInternal.current().succeededFuture();
                }
            }, err -> {
                fs.delete(filename);
                return ContextInternal.current().failedFuture(err);
            });
        });
    }
}
