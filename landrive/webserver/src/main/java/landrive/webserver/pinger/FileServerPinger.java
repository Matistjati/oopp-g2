package landrive.webserver.pinger;

import landrive.lib.server.ServerInfo;
import landrive.webserver.WebServer;
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

public class FileServerPinger extends Thread {
    private final WebServer webserver;
    private final Registry register;

    public FileServerPinger(WebServer webserver){
        this.webserver = webserver;
        this.register = webserver.registry;
    }

    @Override
    public void start() {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                                                 .timeout(Duration.ofSeconds(5))
                                                 .GET();
        
        while (webserver.isRunning){
            for (ServerInfo info : register.getFileServerList()){
                try {
                    String uri = "http://" + info.socketAddress().toString() + "/ping";
                    HttpRequest request = builder.uri(new URI(uri)).build();
                    HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
                    if (response.statusCode() != 200) {
                        register.unregister(info.name());
                    }
                    
                } catch (ConnectException e) {
                    register.unregister(info.name());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                };
            }
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
