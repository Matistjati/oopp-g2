import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.function.Function;

abstract class Route implements HttpHandler {
    final protected String path;

    Route(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
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

    protected void postRequest(HttpExchange exchange) {
        handleUnsupportedMethod(exchange);
    }

    @Override
    public void handle(HttpExchange exchange) {
        switch (exchange.getRequestMethod()) {
            case "GET" -> getRequest(exchange);
            case "PUT" -> postRequest(exchange);
            default -> handleUnsupportedMethod(exchange);
        }
    }
}
