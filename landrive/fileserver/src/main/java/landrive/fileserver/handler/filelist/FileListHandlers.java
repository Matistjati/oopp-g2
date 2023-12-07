package landrive.fileserver.handler.filelist;

import io.vertx.ext.web.Router;
import landrive.fileserver.filesystem.FsService;
import landrive.lib.route.MountingHandlers;

public class FileListHandlers implements MountingHandlers {
    private final FsService fsService;

    public FileListHandlers(final FsService fsService) {
        this.fsService = fsService;
    }

    @Override
    public void mount(Router router) {
        router.getWithRegex("\\/api\\/fileList\\/(?<dir>.*)").handler(new FileListHandler(fsService));
    }
}
