package landrive.fileserver.handler.fileupload;

import io.vertx.ext.web.Router;
import landrive.fileserver.filesystem.FsService;
import landrive.lib.route.MountingHandlers;

public class FileUploadHandlers implements MountingHandlers {
    private final FsService fsService;

    public FileUploadHandlers(final FsService fsService) {
        this.fsService = fsService;
    }

    @Override
    public void mount(Router router) {
        router.postWithRegex("\\/api\\/uploadFile\\/(?<dir>.*)").handler(new FileUploadHandler(fsService));
        router.options("/api/uploadFile/*").handler(new FileUploadOptionsHandler());
    }
}
