package landrive.fileserver.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import landrive.fileserver.filesystem.FsDirectoryList;
import landrive.fileserver.filesystem.FsService;
import landrive.lib.route.MountingRoute;

public class GetFileListHandler implements MountingRoute, Handler<RoutingContext> {
    private final FsService fsService;

    public GetFileListHandler(final FsService fsService) {
        this.fsService = fsService;
    }

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

    @Override
    public void mount(Router router) {
        router.getWithRegex("\\/api\\/fileList\\/(?<dir>.*)")
                .handler(this);
    }
}
