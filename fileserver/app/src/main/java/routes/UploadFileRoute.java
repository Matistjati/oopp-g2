package routes;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpHandler;
import oopp.route.Route;
import oopp.serialize.Jackson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class UploadFileRoute extends Route {

    public UploadFileRoute() {
        super("/api/uploadfile", Jackson.OBJECT_MAPPER);
    }

    @Override
    protected void post(HttpExchange exchange) {
        InputStream requestBody = exchange.getRequestBody();

        // TODO: save to file system
        saveToFile(requestBody, System.getProperty("java.io.tmpdir")+"test2.txt");

        // Respond with a success message
        this.serializeAndWrite(exchange, 200, "File uploaded successfully");

    }

    private void saveToFile(InputStream inputStream, String filePath) {
        Path path = Path.of(filePath);

        try
        {
            // Use Files.copy to copy the input stream to the file
            Files.copy(inputStream, path);
            System.out.println("Uploaded!"+filePath);
        }
        catch (IOException e)
        {
            System.out.println("Fail!");
            e.printStackTrace();
        }

    }
}
