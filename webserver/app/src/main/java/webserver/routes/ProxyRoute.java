package webserver.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import oopp.route.Route;
import webserver.FileServerRegistry;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ProxyRoute extends Route {
    private final HttpClient client = HttpClient.newHttpClient();
    private final FileServerRegistry fileServerRegistry;
    private static final Set<String> restrictedHeaders = new HashSet<>(List.of(
            "Access-Control-Request-Headers",
            "Access-Control-Request-Method",
            "Connection",
            "Content-Length",
            "Content-Transfer-Encoding",
            "Host",
            "Keep-Alive",
            "Origin",
            "Trailer",
            "Transfer-Encoding",
            "Upgrade",
            "Via"
    ));

    protected ProxyRoute(
            String endpoint,
            FileServerRegistry fileServerRegistry
    ) {
        super(endpoint);
        this.fileServerRegistry = fileServerRegistry;
    }

    private static boolean isRestrictedHeader(String name) {
        return restrictedHeaders.contains(name);
    }

    @Override
    public void handle(HttpExchange exchange) {
        final String strippedUri = this.stripUri(exchange.getRequestURI());
        if (strippedUri.isEmpty()) {
            super.handle(exchange);
            return;
        }
        final String[] exploded = strippedUri.split("/", 2);
        final String fileServerName = exploded[0];
        final String endpoint = this.getEndpoint() + "/" + (exploded.length > 1 ? exploded[1] : "");
        final InetSocketAddress socketAddress = this.fileServerRegistry.getSocketAddress(fileServerName);
        final URI requestUri = exchange.getRequestURI();
        try {
            final URI uri = new URI(
                    "http",
                    requestUri.getUserInfo(),
                    socketAddress.getHostName(),
                    socketAddress.getPort(),
                    endpoint,
                    requestUri.getQuery(),
                    requestUri.getFragment()
                    );
            final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(uri);
            final String requestMethod = exchange.getRequestMethod();
            exchange.getRequestHeaders().forEach((name, values) -> {
                        if (isRestrictedHeader(name)) {
                            return;
                        }
                        values.forEach(value -> requestBuilder.header(name, value));
                    });
            switch (requestMethod) {
                case "GET", "DELETE" -> requestBuilder.method(requestMethod, HttpRequest.BodyPublishers.noBody());
                case "PUT", "POST" -> requestBuilder.method(requestMethod, HttpRequest.BodyPublishers.ofInputStream(exchange::getRequestBody));
                default -> throw new RuntimeException();
            }
            final HttpRequest proxyRequest = requestBuilder.build();
            HttpResponse<byte[]> proxyResponse = client.send(proxyRequest, HttpResponse.BodyHandlers.ofByteArray());
            if (proxyResponse.body().length == 0) {
                Route.sendEmptyResponse(exchange, proxyResponse.statusCode());
                return;
            }
            exchange.sendResponseHeaders(proxyResponse.statusCode(), proxyResponse.body().length);
            exchange.getResponseBody().write(proxyResponse.body());
        }
        catch(Throwable e) {
            Route.sendEmptyResponse(exchange, 500);
            e.printStackTrace();
        }
        finally {
            exchange.close();
        }
    }

}
