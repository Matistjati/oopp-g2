package landrive.fileserver.handler.filedownload;

import io.vertx.ext.web.Router;
import landrive.fileserver.filesystem.FsService;
import landrive.lib.route.MountingHandlers;

public class FileDownloadHandlers implements MountingHandlers {
    private final FsService fsService;

    public FileDownloadHandlers(final FsService fsService) {
        this.fsService = fsService;
    }

    @Override
    public void mount(Router router) {
        router.get("/api/download/:fileName").handler(new FileDownloadHandler(fsService));
    }
}