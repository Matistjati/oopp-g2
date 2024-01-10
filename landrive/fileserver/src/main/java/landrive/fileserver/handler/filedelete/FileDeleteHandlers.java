package landrive.fileserver.handler.filedelete;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import landrive.fileserver.filesystem.FsService;
import landrive.fileserver.handler.defaultoptions.OptionsHandler;
import landrive.lib.route.MountingHandlers;

public class FileDeleteHandlers implements MountingHandlers {
    private final FsService fsService;

    public FileDeleteHandlers(final FsService fsService) {
        this.fsService = fsService;
    }

    @Override
    public void mount(Router router) {
        router.deleteWithRegex("\\/api\\/deleteFile\\/(?<path>.*)")
                .handler(BodyHandler.create())
                .handler(new FileDeleteHandler(fsService));
        router.options("/api/deleteFile/*").handler(new OptionsHandler());
    }
}