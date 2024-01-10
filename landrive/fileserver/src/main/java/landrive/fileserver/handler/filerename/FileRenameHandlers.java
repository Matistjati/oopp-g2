package landrive.fileserver.handler.filerename;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import landrive.fileserver.filesystem.FsService;
import landrive.fileserver.handler.defaultoptions.OptionsHandler;
import landrive.lib.route.MountingHandlers;

public class FileRenameHandlers implements MountingHandlers {
    private final FsService fsService;

    public FileRenameHandlers(final FsService fsService) {
        this.fsService = fsService;
    }

    @Override
    public void mount(Router router) {
        router.postWithRegex("\\/api\\/renameFile\\/(?<path>.*)")
                .handler(BodyHandler.create())
                .handler(new FileRenameHandler(fsService));
        router.options("/api/renameFile/*").handler(new OptionsHandler());
    }
}
