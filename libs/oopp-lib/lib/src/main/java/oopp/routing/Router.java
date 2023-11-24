package oopp.routing;

import com.sun.net.httpserver.HttpServer;

import java.util.List;

public class Router {
    private final List<Route> routes;

    public Router(final List<Route> routes) {
        this.routes = routes;
    }

    public void mount(final HttpServer server) {
        for (final Route route : this.routes) {
            server.createContext(route.getEndpoint(), route);
        }
    }
}
