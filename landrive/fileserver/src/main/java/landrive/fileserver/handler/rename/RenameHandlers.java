package landrive.fileserver.handler.rename;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import landrive.fileserver.filesystem.FsService;
import landrive.lib.route.MountingHandlers;

public class RenameHandlers implements MountingHandlers {
    private final FsService fsService;

    public RenameHandlers(final FsService fsService) {
        this.fsService = fsService;
    }

    @Override
    public void mount(Router router) {
        router.postWithRegex("\\/api\\/renameFile\\/(?<path>.*)")
                .handler(BodyHandler.create())
                .handler(new RenameHandler(fsService));
        router.options("/api/renameFile/*").handler(new RenameOptionsHandler());
    }
}
