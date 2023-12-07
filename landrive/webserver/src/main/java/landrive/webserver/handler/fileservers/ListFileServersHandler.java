package landrive.webserver.handler.fileservers;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import landrive.lib.server.ServerInfo;
import landrive.webserver.registry.Registry;

import java.util.List;

public class ListFileServersHandler implements Handler<RoutingContext> {
    private final Registry registry;

    public ListFileServersHandler(final Registry registry) {
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
}
