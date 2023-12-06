package landrive.webserver.handlers.fileservers;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import landrive.lib.route.MountingRoute;
import landrive.lib.server.ServerInfo;
import landrive.webserver.registry.Registry;

import java.util.List;

public class GetFileServersHandler implements MountingRoute, Handler<RoutingContext> {
    private final Registry registry;

    public GetFileServersHandler(final Registry registry) {
        this.registry = registry;
    }

    @Override
    public void handle(RoutingContext ctx) {
        final List<ServerInfo> response = this.registry.getFileServerList();
        ctx.response()
                .setChunked(true)
                .setStatusCode(200)
                .end(Json.encode(response));
    }

    @Override
    public void mount(Router router) {
        router.get("/api/fileServers")
                .handler(this);
    }
}
