import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class StaticRoute extends Route {
    final Path defaultHtmlPath;

    public StaticRoute(final Path defaultHtmlPath) {
        super("/");
        this.defaultHtmlPath = defaultHtmlPath;
    }

    @Override
    protected void getRequest(final HttpExchange exchange) {
        String requestURI = exchange.getRequestURI().getPath().substring(1);
        Path filePath = this.defaultHtmlPath.resolve(
                requestURI.isBlank() ? "index.html" : requestURI
        );
        if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
            sendErrorResponse(exchange, 404);
        }
        String contentType = FileTypeResolver.resolveFileType(filePath);

        // debug

        if (contentType.equals("application/octet-stream")) {
            System.out.printf("DEBUG: %s has an unresolvable file type.\n", requestURI);
        }

        // debug

        try {
            byte[] fileBytes = Files.readAllBytes(filePath); // Throw IOException.
            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, fileBytes.length); // Throws IOException.
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(fileBytes); // Throws IOException.
            }
            exchange.close();
        }
        catch (IOException e) { // Error occurred when reading from the stream.
            e.printStackTrace();
            sendErrorResponse(exchange, 500);
        }
    }
}
