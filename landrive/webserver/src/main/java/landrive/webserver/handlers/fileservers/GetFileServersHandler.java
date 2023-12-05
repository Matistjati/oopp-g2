package landrive.webserver.handlers.fileservers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class GetFileServersHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        ctx.response().setStatusCode(200).end();
    }
}
