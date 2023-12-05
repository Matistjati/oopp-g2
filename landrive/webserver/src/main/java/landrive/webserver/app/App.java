package landrive.webserver.app;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.core.net.SocketAddress;
import landrive.lib.cli.Cli;
import landrive.lib.config.ConfigLoader;
import landrive.lib.serialize.socket.SocketAddressSerializeModule;
import landrive.lib.server.ServerInfo;
import landrive.webserver.WebServer;
import landrive.webserver.config.Config;

import java.net.Socket;
import java.nio.file.Path;

public final class App {
    public static void main(String[] args) {
        DatabindCodec.mapper().registerModule(new SocketAddressSerializeModule());
        final Config config = ConfigLoader.load(
                Config.class,
                Path.of("config.json"),
                "default-config.json"
        );
        final WebServer webServer = new WebServer(config);
        final Cli cli = new Cli(webServer);
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(webServer);
        vertx.deployVerticle(cli);
    }
}