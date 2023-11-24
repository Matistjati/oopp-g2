package oopp.client;

import java.net.URI;
import java.net.URISyntaxException;

public class Client {
    private final java.net.http.HttpClient backingClient;

    public Client() {
        backingClient = java.net.http.HttpClient.newHttpClient();
    }

    public void sendRequest(String hostname, int port /* ... */) {
        // TODO: Implement.
    }

    private static URI of(String hostname, int port, String endpoint) {
        try {
            return new URI("http", null, hostname, port, endpoint, null, null);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
