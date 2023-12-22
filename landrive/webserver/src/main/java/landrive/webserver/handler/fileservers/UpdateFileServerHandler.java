package landrive.webserver.handler.fileservers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import landrive.lib.server.ServerInfo;
import landrive.webserver.registry.Registry;

public class UpdateFileServerHandler implements Handler<RoutingContext> {
    private final Registry registry;

    public UpdateFileServerHandler(final Registry registry) {
        this.registry = registry;
    }

    @Override
    public void handle(RoutingContext ctx) {
        final String name = ctx.pathParam("name");
        final ServerInfo info = ctx.body().asPojo(ServerInfo.class);

        this.registry.rename(name, info);
        
        System.out.println("Renamed file server with name '" + name + "' to '" + info.name() + "'.");

        ctx.response().setStatusCode(200).end();
    }
}
