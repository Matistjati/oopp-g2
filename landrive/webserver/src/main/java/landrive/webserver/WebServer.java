package landrive.webserver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import landrive.lib.cli.command.Command;
import landrive.webserver.config.Config;
import landrive.webserver.handlers.fileservers.DeleteFileServersHandler;
import landrive.webserver.handlers.fileservers.GetFileServersHandler;
import landrive.webserver.handlers.fileservers.PostFileServersHandler;
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


        GetFileServersHandler getFileServersHandler =
                new GetFileServersHandler(this.registry);
        getFileServersHandler.mount(router);

        PostFileServersHandler postFileServersHandler =
                new PostFileServersHandler(this.registry);
        postFileServersHandler.mount(router);

        DeleteFileServersHandler deleteFileServersHandler =
                new DeleteFileServersHandler(this.registry);
        deleteFileServersHandler.mount(router);


        router.route("/dashboard/*")
                .handler(StaticHandler.create("webfiles"));

        final HttpServer httpServer = this.vertx.createHttpServer().requestHandler(router);
        httpServer.listen(this.socketAddress);
        System.out.println("Web server listening on " + this.socketAddress + ".");
    }

    @Command(name = "stop")
    public void cmdStop() {
        this.vertx.close();
    }
}