package landrive.fileserver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.uritemplate.UriTemplate;
import io.vertx.uritemplate.Variables;
import landrive.fileserver.config.Config;
import landrive.fileserver.filesystem.FsHandler;
import landrive.fileserver.handlers.GetFileListHandler;
import landrive.lib.cli.command.Command;
import landrive.lib.server.ServerInfo;

import java.nio.file.Path;

public final class FileServer extends AbstractVerticle {
    private final FsHandler fsHandler = new FsHandler("storage");
    private final String name;
    private final SocketAddress socketAddress;
    private final SocketAddress webServerSocketAddress;
    private HttpServer httpServer;
    private WebClient client;

    public FileServer(final Config config) {
        this.name = config.name();
        this.socketAddress = config.socketAddress();
        this.webServerSocketAddress = config.webServerSocketAddress();
    }

    @Override
    public void start() {
        final Router router = Router.router(this.vertx);
        router.route().handler(BodyHandler.create());
        router.getWithRegex("\\/api\\/fileList\\/(?<dir>.*)")
                .handler(new GetFileListHandler(this.fsHandler));

        this.httpServer = this.vertx.createHttpServer().requestHandler(router);
        WebClientOptions clientOptions = new WebClientOptions()
                .setDefaultHost(webServerSocketAddress.host())
                .setDefaultPort(webServerSocketAddress.port());

        this.client = WebClient.create(this.vertx, clientOptions);
        httpServer.listen(this.socketAddress);
    }

    @Command(name = "stop")
    public void cmdStop() {
        vertx.close();
    }

    @Command(name = "connect")
    public void cmdConnect() {
        this.client
                .post("/api/fileServers")
                .sendJson(new ServerInfo(this.name, this.socketAddress))
                .onSuccess(res -> {
                    System.out.println("Connected to web server successfully.");
                });
    }

    @Command(name = "disconnect")
    public void cmdDisconnect() {
        final UriTemplate uritemplate = UriTemplate.of("/api/fileServers/{name}");
        final String requestUri = uritemplate.expandToString(Variables.variables().set("name", this.name));
        this.client
                .delete(requestUri)
                .send()
                .onSuccess(res -> {
                    System.out.println("Disconnected from web server successfully.");
                });
    }
}