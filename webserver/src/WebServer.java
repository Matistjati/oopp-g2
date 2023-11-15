import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.List;

public class WebServer {
    public static HttpServer host(String path, int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        Router router = new Router(List.of(
            new StaticRoute(Path.of("./html/")),
            new FileListRoute()
        ));
        router.mount(server);
        server.setExecutor(null);
        server.start();
        System.out.printf("Web server started on port %d.\n", port);
        return server;
    }

    public static void stop(HttpServer server) {
        server.stop(0);
        System.out.printf("Web server stopped on port %d.\n", server.getAddress().getPort());
    }

    public static void main(String[] args) {
        Integer port = ArgsParser.parse(args, "-port", Integer::parseInt, 8000);
        try {
            HttpServer server = WebServer.host("./html", port);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> stop(server)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
