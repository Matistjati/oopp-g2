package landrive.fileserver.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.vertx.core.Vertx;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.core.net.SocketAddress;
import landrive.lib.cli.Cli;
import landrive.lib.config.ConfigLoader;
import landrive.fileserver.FileServer;
import landrive.fileserver.config.Config;
import landrive.lib.serialize.socket.SocketAddressDeserializer;
import landrive.lib.serialize.socket.SocketAddressSerializeModule;
import landrive.lib.serialize.socket.SocketAddressSerializer;

import java.nio.file.Path;

public final class App {
    public static void main(String[] args) {
        DatabindCodec.mapper().registerModule(new SocketAddressSerializeModule());
        final Config config = ConfigLoader.load(
                Config.class,
                Path.of("config.json"),
                "default-config.json"
        );
        final FileServer fileServer = new FileServer(config);
        final Cli cli = new Cli(fileServer);
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(fileServer);
        vertx.deployVerticle(cli);
    }
}