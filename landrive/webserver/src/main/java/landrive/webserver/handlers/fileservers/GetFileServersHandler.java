package landrive.webserver.handlers.fileservers;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.observability.HttpResponse;
import io.vertx.ext.web.RoutingContext;
import landrive.webserver.registry.Registry;

public class GetFileServersHandler implements Handler<RoutingContext> {
    private final Registry registry;

    public GetFileServersHandler(final Registry registry) {
        this.registry = registry;
    }

    @Override
    public void handle(RoutingContext ctx) {
        final Buffer responseBuffer = JsonObject.mapFrom(this.registry.getFileServerList()).toBuffer();
        ctx.response()
                .setChunked(true)
                .setStatusCode(200)
                .end(responseBuffer);
    }
}
