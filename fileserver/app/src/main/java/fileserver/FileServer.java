package fileserver;

import oopp.routing.Router;
import oopp.server.Server;
import oopp.util.URIUtil;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class FileServer extends Server {
    final int webPort;
    final String name;

    FileServer(int port, int webPort, String name) throws IOException {
        super(port);
        this.webPort = webPort;
        this.name = name;
        Router router = new Router(List.of(

        ));
        router.mount(this.httpServer);
    }

    private void sendDiscoveryRequest() {
        URI uri = URIUtil.of("localhost", webPort, "/api/discovery");
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString("<TODO>");
        HttpRequest req =
                HttpRequest.newBuilder(uri).
                timeout(Duration.ofMillis(1000)).
                POST(body).
                build();

        CompletableFuture<HttpResponse<String>> futureResponse = this.httpClient.sendAsync(req, HttpResponse.BodyHandlers.ofString());
        futureResponse.whenComplete((response, throwable) -> {
            if (throwable != null) {
                if (throwable instanceof HttpTimeoutException) {
                    System.out.printf("ERROR: Request to web server on port %d timed out.\n", this.webPort);
                }
                else if (throwable instanceof CompletionException) {
                    System.out.printf("ERROR: No process on port %d.\n", this.webPort);
                }
                else {
                    // debug

                    System.out.printf("ERROR: Exception: %s\n", throwable.getClass().getSimpleName());

                    //debug
                }
                return;
            }
            // TODO: implement
        });
    }

    @Override
    public void start() {
        super.start();
        sendDiscoveryRequest();
        System.out.printf("File server started on port %d.\n", this.httpServer.getAddress().getPort());
        cli.run(name);
    }

    @Override
    public void stop() {
        super.stop();
        System.out.printf("File server stopped on port %d.\n", this.httpServer.getAddress().getPort());
    }
}
