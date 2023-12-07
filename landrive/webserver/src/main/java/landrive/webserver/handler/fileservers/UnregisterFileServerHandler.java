package landrive.webserver.handler.fileservers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import landrive.webserver.registry.Registry;

public class UnregisterFileServerHandler implements Handler<RoutingContext> {
    private final Registry registry;

    public UnregisterFileServerHandler(final Registry registry) {
        this.registry = registry;
    }

    @Override
    public void handle(RoutingContext ctx) {
        final String name = ctx.pathParam("name");
        this.registry.unregister(name);
        System.out.println("Unregistered file server with name \"" + name + "\".");
        ctx.response().setStatusCode(200).end();
    }
}
