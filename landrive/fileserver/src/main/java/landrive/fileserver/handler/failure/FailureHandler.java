package landrive.fileserver.handler.failure;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public final class FailureHandler implements Handler<RoutingContext> {
    @Override
    public void handle(final RoutingContext ctx) {
        ctx.response()
                .end(ctx.failure().getMessage());
    }
}