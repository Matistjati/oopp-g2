package fileserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import oopp.client.Client;

import java.net.InetSocketAddress;

public class FileServerClient extends Client {
    private final InetSocketAddress webSocketAddress;

    public FileServerClient(ObjectMapper objectMapper, InetSocketAddress webSocketAddress) {
        super(objectMapper);
        this.webSocketAddress = webSocketAddress;
    }

    public RequestBuilder newRequest(final String endpoint) {
        return this.newRequest(this.webSocketAddress, endpoint);
    }
}
