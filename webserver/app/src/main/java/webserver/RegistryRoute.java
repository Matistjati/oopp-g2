package webserver;

import com.sun.net.httpserver.HttpExchange;
import oopp.routing.Route;

public class RegistryRoute extends Route {
    final WebServer webServer;

    public RegistryRoute(WebServer webServer) {
        super("/api/registry");
        this.webServer = webServer;
    }

    @Override
    public void postRequest(HttpExchange exchange) {

    }
}
