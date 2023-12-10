package landrive.fileserver.handler.rename;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import landrive.fileserver.filesystem.FsService;

public class RenameHandler implements Handler<RoutingContext> {
    final private FsService fsService;

    public RenameHandler(FsService fsService) {
        this.fsService = fsService;
    }

    @Override
    public void handle(RoutingContext ctx) {
        final String newName = ctx.body().asPojo(String.class);
        final String path = ctx.pathParam("path");
        fsService.renameFile(path, newName)
                .onSuccess((unused) -> {
                    ctx.response()
                            .putHeader("Access-Control-Allow-Origin", "*")
                            .putHeader("Access-Control-Allow-Headers", "*")
                            .setStatusCode(200)
                            .end();
                })
                .onFailure(ctx::fail);
    }
}
