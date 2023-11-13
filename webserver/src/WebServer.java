import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WebServer {
    public static void host(String path, int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        server.createContext("/", new StaticHandler(path));
        server.setExecutor(null);
        server.start();
    }

    public static void main(String[] args) {
        int port = 8000;
        try {
            WebServer.host("./html", port);
            System.out.printf("Web server started on port %d.\n", port);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
