package landrive.webserver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import landrive.lib.cli.command.Command;
import landrive.lib.route.MountingHandlers;
import landrive.webserver.config.Config;
import landrive.webserver.handler.dashboard.StaticHandlers;
import landrive.webserver.handler.fileservers.FileServersHandlers;
import landrive.webserver.registry.Registry;

public final class WebServer extends AbstractVerticle {
    private final SocketAddress socketAddress;
    private final Registry registry = new Registry();

    public WebServer(final Config config) {
        this.socketAddress = config.socketAddress();
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
        httpServer.listen(this.socketAddress);
        System.out.println("Web server listening on " + this.socketAddress + ".");
    }

    @Command(name = "stop")
    public void cmdStop() {
        this.vertx.close();
    }
}