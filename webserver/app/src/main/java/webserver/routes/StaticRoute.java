package webserver.routes;

import com.sun.net.httpserver.HttpExchange;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import oopp.route.Route;
import oopp.serialize.Jackson;
import webserver.FileTypeResolver;

public class StaticRoute extends Route {
    final Path defaultHtmlPath;

    public StaticRoute(final Path defaultHtmlPath) {
        super("/", Jackson.OBJECT_MAPPER);
        this.defaultHtmlPath = defaultHtmlPath;
    }

    @Override
    protected void get(final HttpExchange exchange) {
        String requestURI = exchange.getRequestURI().getPath().substring(1);
        Path filePath = this.defaultHtmlPath.resolve(
                requestURI.isBlank() ? "index.html" : requestURI
        );
        if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
            throw new RuntimeException(String.format("File does not exist: %s.", filePath));
        }
        String contentType = FileTypeResolver.resolveFileType(filePath);
        if (contentType.equals("application/octet-stream")) {
            System.out.printf("DEBUG: %s has an unresolvable file type.\n", requestURI);
        }
        exchange.getResponseHeaders().set("Content-Type", contentType);
        try {
            this.serializeAndWrite(exchange, 200, Files.readAllBytes(filePath));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
