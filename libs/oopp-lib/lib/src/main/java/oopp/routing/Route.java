package oopp.routing;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public abstract class Route implements HttpHandler {
    final protected String endpoint;

    public Route(String path) {
        this.endpoint = path;
    }

    public String getEndpoint() {
        return endpoint;
    }

    private void handleUnsupportedMethod(HttpExchange exchange) {
        sendErrorResponse(exchange, 405);
    }

    protected void sendErrorResponse(final HttpExchange exchange, final int responseCode) {
        try {
            exchange.sendResponseHeaders(responseCode, 0);
            exchange.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void getRequest(HttpExchange exchange) {
        handleUnsupportedMethod(exchange);
    }

    protected void putRequest(HttpExchange exchange) {
        handleUnsupportedMethod(exchange);
    }

    protected void postRequest(HttpExchange exchange) {
        handleUnsupportedMethod(exchange);
    }

    @Override
    public void handle(HttpExchange exchange) {
        switch (exchange.getRequestMethod()) {
            case "GET" -> getRequest(exchange);
            case "PUT" -> putRequest(exchange);
            case "POST" -> postRequest(exchange);
            default -> handleUnsupportedMethod(exchange);
        }
    }
}
