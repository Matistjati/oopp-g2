package landrive.fileserver.handler.ping;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class PingHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        ctx.response()
                .setStatusCode(200)
                .end();
    }
}
