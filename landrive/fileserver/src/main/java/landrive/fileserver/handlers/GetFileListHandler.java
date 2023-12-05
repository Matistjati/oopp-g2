package landrive.fileserver.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import landrive.fileserver.filesystem.FsDirectoryList;
import landrive.fileserver.filesystem.FsHandler;

public class GetFileListHandler implements Handler<RoutingContext> {
    private final FsHandler fsHandler;

    public GetFileListHandler(final FsHandler fsHandler) {
        this.fsHandler = fsHandler;
    }

    @Override
    public void handle(RoutingContext ctx) {
        final String dir = ctx.pathParam("dir");
        final FsDirectoryList response = fsHandler.getFileList(dir);
        ctx.response()
                .putHeader("Access-Control-Allow-Origin", "*")
                .setChunked(true)
                .setStatusCode(200)
                .end(Json.encode(response));
    }
}
