package webserver;

import com.sun.net.httpserver.HttpExchange;
import oopp.routing.Route;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class DiscoveryRoute extends Route {
    final WebServer webServer;

    public DiscoveryRoute(WebServer webServer) {
        super("/api/discovery");
        this.webServer = webServer;
    }

    @Override
    public void postRequest(HttpExchange exchange) {
        try {
            byte[] bytes = exchange.getRequestBody().readAllBytes();
            // Deserialize bytes as ServerInfo.
            exchange.sendResponseHeaders(200, 0);
            exchange.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        // Register the file server to the file server registry.
    }
}
