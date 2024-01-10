package landrive.fileserver.handler.filedelete;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import landrive.fileserver.filesystem.FsService;

public class FileDeleteHandler implements Handler<RoutingContext> {
    final private FsService fsService;

    public FileDeleteHandler(FsService fsService) {
        this.fsService = fsService;
    }

    @Override
    public void handle(RoutingContext ctx) {
        final String path = ctx.pathParam("path");
        fsService.deleteFile(path)
                .onSuccess((unused) -> {
                    ctx.response()
                            .putHeader("Access-Control-Allow-Methods", "*")
                            .putHeader("Access-Control-Allow-Origin", "*")
                            .putHeader("Access-Control-Allow-Headers", "*")
                            .setStatusCode(200)
                            .end();
                })
                .onFailure(ctx::fail);
    }
}