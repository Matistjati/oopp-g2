import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class StaticHandler implements HttpHandler {
    private final String path;

    public StaticHandler(String path) {
        this.path = path;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestURI = exchange.getRequestURI().toString();
        if (requestURI.equals("/")) {
            requestURI = "/index.html";
        }
        Path filePath = FileSystems.getDefault().getPath(path, requestURI);
        byte[] fileBytes = Files.readAllBytes(filePath);
        String contentType = FileTypeResolver.resolveFileType(filePath);
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(200, fileBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(fileBytes);
        }
    }
}
