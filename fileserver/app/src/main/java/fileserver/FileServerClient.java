package fileserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import oopp.client.Client;

import java.net.InetSocketAddress;

public class FileServerClient extends Client {
    private InetSocketAddress webServerSocketAddress;

    public FileServerClient(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    public <T> RequestBuilder<T> newRequest(final String endpoint, final Class<T> responseType) {
        return this.newRequest(this.webServerSocketAddress, endpoint, responseType);
    }
}
