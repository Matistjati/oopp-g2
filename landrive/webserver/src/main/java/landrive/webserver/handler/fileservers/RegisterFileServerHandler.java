package landrive.webserver.handler.fileservers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import landrive.lib.server.ServerInfo;
import landrive.webserver.registry.Registry;

public class RegisterFileServerHandler implements Handler<RoutingContext> {
    private final Registry registry;

    public RegisterFileServerHandler(final Registry registry) {
        this.registry = registry;
    }

    @Override
    public void handle(RoutingContext ctx) {
        final ServerInfo info = ctx.body().asPojo(ServerInfo.class);

        registry.register(info);

        System.out.println("Registered file server with name '" + info.name() + "'.");

        ctx.response().setStatusCode(200).end();
    }
}
