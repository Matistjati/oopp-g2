package webserver;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oopp.routing.Router;

public class WebServer {
    private final Map<Integer, HttpURLConnection> registeredFileServers;
    private final HttpServer server;

    WebServer(String path, int port) {
        registeredFileServers = new HashMap<>();
        this.server = create(path, port);
    }

    private static HttpServer create(String path, int port) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", port), 0);
            Router router = new Router(List.of(
                    new StaticRoute(Path.of("./web/")),
                    new FileListRoute()
            ));
            router.mount(server);
            server.setExecutor(null);
            return server;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void start() {
        this.server.start();
        System.out.printf("Web server started on port %d.\n", this.server.getAddress().getPort());
    }

    public void stop() {
        this.server.stop(0);
        System.out.printf("Web server stopped on port %d.\n", this.server.getAddress().getPort());
    }
}
