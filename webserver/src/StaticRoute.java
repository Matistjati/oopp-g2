import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class StaticRoute extends Route {
    final String defaultHtmlPath;
    public StaticRoute(final String defaultHtmlPath) {
        super("/");
        this.defaultHtmlPath = defaultHtmlPath;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestURI = exchange.getRequestURI().toString();
        if (requestURI.equals("/")) {
            requestURI = "/index.html";
        }
        Path filePath = FileSystems.getDefault().getPath(defaultHtmlPath, requestURI);
        byte[] fileBytes = Files.readAllBytes(filePath);
        String contentType = FileTypeResolver.resolveFileType(filePath);

        // debug

        if (contentType.equals("application/octet-stream")) {
            System.out.printf("DEBUG: %s has an unresolvable file type.\n", requestURI);
        }

        // debug

        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(200, fileBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(fileBytes);
        }
    }
}
