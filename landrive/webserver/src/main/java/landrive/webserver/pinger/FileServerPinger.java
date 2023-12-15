package landrive.webserver.pinger;

import landrive.lib.server.ServerInfo;
import landrive.webserver.registry.Registry;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import io.vertx.core.Handler;

public class FileServerPinger implements Handler<Long> {
    private final Registry registry;
    private final HttpClient client;
    private final HttpRequest.Builder builder;

    public FileServerPinger(Registry registry){
        this.registry = registry;
        client = HttpClient.newBuilder().build();
        builder = HttpRequest.newBuilder().timeout(Duration.ofSeconds(5)).GET();
    }

    @Override
    public void handle(Long sec) {
        for (ServerInfo info : registry.getFileServerList()){
            try {
                String uri = "http://" + info.socketAddress().toString() + "/ping";
                HttpRequest request = builder.uri(new URI(uri)).build();
                HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
                if (response.statusCode() != 200) {
                    registry.unregister(info.name());
                }
                
            } catch (ConnectException e) {
                registry.unregister(info.name());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            };
        }
    }

}
