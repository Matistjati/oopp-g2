package landrive.webserver.handlers.fileservers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import landrive.lib.server.ServerInfo;
import landrive.webserver.registry.Registry;

public class DeleteFileServersHandler implements Handler<RoutingContext> {
    private final Registry registry;

    public DeleteFileServersHandler(final Registry registry) {
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
