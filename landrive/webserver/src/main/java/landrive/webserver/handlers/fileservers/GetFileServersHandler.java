package landrive.webserver.handlers.fileservers;

import com.fasterxml.jackson.core.JsonEncoding;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.observability.HttpResponse;
import io.vertx.ext.web.RoutingContext;
import landrive.lib.server.ServerInfo;
import landrive.webserver.registry.Registry;

import java.util.List;

public class GetFileServersHandler implements Handler<RoutingContext> {
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
}
