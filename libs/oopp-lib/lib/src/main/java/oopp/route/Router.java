package oopp.route;

import com.sun.net.httpserver.HttpServer;

import java.util.Arrays;
import java.util.List;

public class Router {
    private final List<Route> routes;

    public Router(Route... routes) {
        this.routes = Arrays.asList(routes);
    }

    public void mount(final HttpServer server) {
        for (final Route route : this.routes) {
            server.createContext(route.getEndpoint(), route);
        }
    }
}
