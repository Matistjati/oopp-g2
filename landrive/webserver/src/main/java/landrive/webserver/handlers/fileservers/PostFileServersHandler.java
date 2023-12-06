package landrive.webserver.handlers.fileservers;

import io.vertx.core.Handler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import landrive.lib.route.IMountable;
import landrive.lib.server.ServerInfo;
import landrive.webserver.registry.Registry;

public class PostFileServersHandler implements IMountable, Handler<RoutingContext> {
    private final Registry registry;

    public PostFileServersHandler(final Registry registry) {
        this.registry = registry;
    }

    @Override
    public void handle(RoutingContext ctx) {
        final ServerInfo info = ctx.body().asPojo(ServerInfo.class);
        registry.register(info);
        ctx.response().setStatusCode(200).end();
        System.out.println("Registered file server with name \"" + info.name() + "\".");
    }

    @Override
    public void mount(Router router) {
        router.post("/api/fileServers")
                .handler(this);
    }
}
