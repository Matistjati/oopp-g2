package webserver.routes;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;
import oopp.route.Route;
import oopp.serialize.Jackson;
import webserver.FileServerRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.HttpURLConnection;

public class UploadFileRoute extends Route {
    private final FileServerRegistry fileServerRegistry;

    public UploadFileRoute(FileServerRegistry fileServerRegistry) {
        super("/api/uploadfile", Jackson.OBJECT_MAPPER);
        this.fileServerRegistry = fileServerRegistry;
    }

    @Override
    protected void post(HttpExchange exchange) {
        InputStream requestBody = exchange.getRequestBody();

        Headers requestHeaders = exchange.getRequestHeaders();
        String id = requestHeaders.getFirst("id");

        // Print the header value
        System.out.println("id: " + id);



        //this.forwardFile(requestBody);

        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // Change * to your allowed origin
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, id"); // Add other headers if needed
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");

        try {
            exchange.sendResponseHeaders(204, -1);
            System.out.println("pong back");
        }
        catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
        finally {
            exchange.close();
        }

    }

    protected void forwardFile(InputStream fileContent) {
        // TODO: request should specify which fileserver to upload to
        for (String hostname : fileServerRegistry.getFileServerNameList()) {
            InetSocketAddress address = fileServerRegistry.getSocketAddress(hostname);
            try {
                URL url = new URL("http", address.getHostName(), address.getPort(), "/api/uploadfile");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                // connection.setRequestProperty("HeaderName", "HeaderValue");

                // Send data over to file server. Never load whole file into memory
                try (OutputStream outputStream = connection.getOutputStream()) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = fileContent.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    System.out.println("sent file!: ");
                } else {
                    System.out.println("HTTP request failed with response code: " + responseCode);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
