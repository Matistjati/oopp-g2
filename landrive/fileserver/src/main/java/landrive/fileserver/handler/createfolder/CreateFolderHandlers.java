package landrive.fileserver.handler.createfolder;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import landrive.fileserver.filesystem.FsService;
import landrive.fileserver.handler.rename.RenameHandler;
import landrive.fileserver.handler.rename.RenameOptionsHandler;
import landrive.lib.route.MountingHandlers;

public class CreateFolderHandlers implements MountingHandlers {
    private final FsService fsService;

    public CreateFolderHandlers(final FsService fsService) {
        this.fsService = fsService;
    }

    @Override
    public void mount(Router router) {
        router.postWithRegex("\\/api\\/createFolder\\/(?<path>.*)")
                .handler(BodyHandler.create())
                .handler(new CreateFolderHandler(fsService));
        router.options("/api/createFolder/*").handler(new CreateFolderOptionsHandler());
    }

}
