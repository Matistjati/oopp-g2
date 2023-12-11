package landrive.webserver.handler.dashboard;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class IndexRedirection implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        ctx.redirect("/dashboard");
    }
}