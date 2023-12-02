package webserver.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import oopp.route.Route;
import webserver.FileServerRegistry;

import java.net.URI;

public abstract class ProxyRoute extends Route {
    private final FileServerRegistry fileServerRegistry;

    protected ProxyRoute(FileServerRegistry fileServerRegistry, String endpoint, ObjectMapper objectMapper) {
        super(endpoint, objectMapper);
        this.fileServerRegistry = fileServerRegistry;
    }

   @Override
   public void handle(HttpExchange exchange) {
        String strippedUri = this.stripUri(exchange.getRequestURI());
        String fileServerName = striped
   }
}
