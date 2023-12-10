package landrive.fileserver.handler.rename;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class RenameOptionsHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        ctx.response()
                .putHeader("Access-Control-Allow-Origin", "*")
                .putHeader("Access-Control-Allow-Headers", "*")
                .setStatusCode(200)
                .end();
    }
}