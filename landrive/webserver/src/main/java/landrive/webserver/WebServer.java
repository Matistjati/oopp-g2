package landrive.webserver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import landrive.lib.cli.command.Command;
import landrive.lib.route.MountingHandlers;
import landrive.webserver.config.Config;
import landrive.webserver.handler.dashboard.StaticHandlers;
import landrive.webserver.handler.fileservers.FileServersHandlers;
import landrive.webserver.registry.Registry;

public final class WebServer extends AbstractVerticle {
    private final SocketAddress socketAddress;
    public final Registry registry;

    public WebServer(final Config config, Vertx vertx) {
        this.socketAddress = config.socketAddress();
        registry = new Registry(vertx);
    }

    private void webSocketSetup(HttpServer httpServer) {
        httpServer.webSocketHandler(webSocket -> {
            vertx.eventBus().consumer("fileserver.register").handler((message) -> {
                webSocketEventHandler(webSocket);
            });
            vertx.eventBus().consumer("fileserver.unregister").handler((message) -> {
                webSocketEventHandler(webSocket);
            });
            vertx.eventBus().consumer("fileserver.rename").handler((message) -> {
                webSocketEventHandler(webSocket);
            });
        });
    }

    private void webSocketEventHandler(ServerWebSocket webSocket) {
        String eventData = new JsonObject().put("event", "fileservers.update").put("data", "").toString();
        webSocket.writeTextMessage(eventData);
    }

    @Override
    public void start() {
        final Router router = Router.router(this.vertx);
        router.route().handler(BodyHandler.create());
        MountingHandlers.mountAll(router,
                new FileServersHandlers(this.registry),
                new StaticHandlers()
        );
        final HttpServer httpServer = this.vertx.createHttpServer().requestHandler(router);
        webSocketSetup(httpServer);
        httpServer.listen(this.socketAddress);
        System.out.println("Web server listening on " + this.socketAddress + ".");
    }

    @Command(name = "stop")
    public void cmdStop() {
        this.vertx.close();
    }
}