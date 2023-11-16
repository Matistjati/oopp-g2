package webserver;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.*;

import oopp.routing.Router;
import oopp.server.Server;

public class WebServer extends Server {
    private final FileServerRegistry fileServerRegistry;

    WebServer(String path, int port) throws IOException {
        super(port);
        fileServerRegistry = new FileServerRegistry();
        Router router = new Router(List.of(
                new RegistryRoute(this),
                new StaticRoute(Path.of("./web/")),
                new FileListRoute()
        ));
        router.mount(this.httpServer);
    }

    @Override
    public void start() {
        super.start();
        System.out.printf("Web server started on port %d.\n", this.httpServer.getAddress().getPort());
    }

    @Override
    public void stop() {
        super.stop();
        System.out.printf("Web server stopped on port %d.\n", this.httpServer.getAddress().getPort());
    }

    public FileServerRegistry getFileServerRegistry() {
        return fileServerRegistry;
    }
}
