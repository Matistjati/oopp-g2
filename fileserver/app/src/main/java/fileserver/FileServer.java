package fileserver;

import com.sun.net.httpserver.HttpServer;
import oopp.routing.Router;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

public class FileServer {
    private final HttpServer httpServer;

    FileServer(int port) {
        this.httpServer = create(port);
        Router router = new Router(List.of(

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
        System.out.printf("File server started on port %d.\n", this.httpServer.getAddress().getPort());
    }

    public void stop() {
        this.httpServer.stop(0);
        System.out.printf("File server stopped on port %d.\n", this.httpServer.getAddress().getPort());
    }
}
