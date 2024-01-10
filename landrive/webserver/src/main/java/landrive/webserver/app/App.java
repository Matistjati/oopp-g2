package landrive.webserver.app;

import io.vertx.core.Vertx;
import io.vertx.core.json.jackson.DatabindCodec;
import landrive.lib.cli.Cli;
import landrive.lib.config.ConfigLoader;
import landrive.lib.serialize.socket.SocketAddressSerializeModule;
import landrive.webserver.WebServer;
import landrive.webserver.config.Config;
import landrive.webserver.pinger.FileServerPinger;

import java.nio.file.Path;

public final class App {
    public static void main(String[] args) {
        DatabindCodec.mapper().registerModule(new SocketAddressSerializeModule());
        final Config config = ConfigLoader.load(
                Config.class,
                Path.of("config.json"),
                "default-config.json"
        );
        final Vertx vertx = Vertx.vertx();
        final WebServer webServer = new WebServer(config, vertx);
        final Cli cli = new Cli(webServer);
        final FileServerPinger pinger = new FileServerPinger(webServer.registry, vertx);
        vertx.deployVerticle(webServer);
        vertx.deployVerticle(cli);
        vertx.setPeriodic(5000, pinger);
    }
}