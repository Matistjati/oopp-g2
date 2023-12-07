package landrive.fileserver.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import landrive.fileserver.filesystem.FsDirectoryList;
import landrive.fileserver.filesystem.FsService;
import landrive.lib.route.MountingHandlers;

public class FileListHandlers implements MountingHandlers {
    private final FsService fsService;

    public FileListHandlers(final FsService fsService) {
        this.fsService = fsService;
    }

    private class FileListHandler implements Handler<RoutingContext> {
        @Override
        public void handle(RoutingContext ctx) {
            final String dir = ctx.pathParam("dir");
            final FsDirectoryList response = fsService.getFileList(dir);
            ctx.response()
                    .putHeader("Access-Control-Allow-Origin", "*")
                    .setChunked(true)
                    .setStatusCode(200)
                    .end(Json.encode(response));
        }
    }

    @Override
    public void mount(Router router) {
        router.getWithRegex("\\/api\\/fileList\\/(?<dir>.*)").handler(new FileListHandler());
    }
}
