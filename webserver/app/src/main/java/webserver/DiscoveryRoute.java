package webserver;

import com.sun.net.httpserver.HttpExchange;
import oopp.routing.Route;
import oopp.server.ServerInfo;

import java.io.IOException;

public class DiscoveryRoute extends Route {
    final WebServer webServer;

    public DiscoveryRoute(WebServer webServer) {
        super("/api/discovery");
        this.webServer = webServer;
    }

    @Override
    public void postRequest(HttpExchange exchange) {
        final ServerInfo serverInfo;
        try {
            byte[] bytes = exchange.getRequestBody().readAllBytes();
            serverInfo = ServerInfo.ofByteArray(bytes);
            exchange.sendResponseHeaders(200, 0);
            exchange.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        webServer.registerFileServer(serverInfo);
    }
}
