package landrive.webserver.handlers.fileservers;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import landrive.lib.route.MountingHandlers;
import landrive.lib.server.ServerInfo;
import landrive.webserver.registry.Registry;

import java.util.List;

public class FileServersHandlers implements MountingHandlers {
    private class GetFileServersHandler implements Handler<RoutingContext> {
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

    public class DeleteFileServersHandler implements MountingHandlers, Handler<RoutingContext> {
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

        @Override
        public void mount(Router router) {
            router.delete("/api/fileServers/:name")
                    .handler(this);
        }
    }


    @Override
    public void mount(Router router) {
        router.get("/api/fileServers")
                .handler(this);
    }
}
