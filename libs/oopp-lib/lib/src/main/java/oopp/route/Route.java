package oopp.route;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("SameParameterValue")
public abstract class Route implements HttpHandler {
    final private String endpoint;
    final private Map<String, Route> subRoutes = new HashMap<>();

    public Route(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }

    protected void get(HttpExchange exchange) {
        throw new RuntimeException();
    }

    protected void put(HttpExchange exchange) {
        throw new RuntimeException();
    }

    protected void post(HttpExchange exchange) {
        throw new RuntimeException();
    }

    protected void delete(HttpExchange exchange) {
        throw new RuntimeException();
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            switch (exchange.getRequestMethod()) {
                case "GET" -> get(exchange);
                case "PUT" -> put(exchange);
                case "POST" -> post(exchange);
                case "DELETE" -> delete(exchange);
                default -> throw new RuntimeException();
            }
        }
        catch(Throwable e) {
<<<<<<< HEAD
            Route.sendEmptyResponse(exchange, 200);
            e.printStackTrace();
=======
            System.out.println("allt sprängdes");
            serializeAndWrite(exchange,500, e.getMessage());
>>>>>>> bc34fed (Worked on file upload dashboard)
        }
        finally {
            exchange.close();
        }
    }

    protected static void sendEmptyResponse(HttpExchange exchange, int rCode) {
        try {
            exchange.sendResponseHeaders(rCode, -1);
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected String stripUri(URI uri) {
        try {
            return uri.getPath().substring(this.endpoint.length() + 1);
        }
        catch (IndexOutOfBoundsException e) {
            return "";
        }
    }
}