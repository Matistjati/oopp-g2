package webserver;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.*;

import oopp.routing.Router;

public class WebServer {
    private final HttpServer httpServer;
    private final FileServerRegistry fileServerRegistry;

    WebServer(String path, int port) {
        fileServerRegistry = new FileServerRegistry();
        this.httpServer = create(port);
        Router router = new Router(List.of(
                new RegistryRoute(this),
                new StaticRoute(Path.of("./web/")),
                new FileListRoute()
        ));
        router.mount(this.httpServer);
    }

    private static HttpServer create(int port) {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress("localhost", port), 0);
            httpServer.setExecutor(null);
            return httpServer;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void start() {
        this.httpServer.start();
        System.out.printf("Web server started on port %d.\n", this.httpServer.getAddress().getPort());
    }

    public void stop() {
        this.httpServer.stop(0);
        System.out.printf("Web server stopped on port %d.\n", this.httpServer.getAddress().getPort());
    }

    public FileServerRegistry getFileServerRegistry() {
        return fileServerRegistry;
    }
}
