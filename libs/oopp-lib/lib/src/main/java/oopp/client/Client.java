package oopp.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import oopp.serialize.DeserializingBodyHandler;
import oopp.serialize.SerializingBodyPublisher;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class Client {
    private final HttpClient backingClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;

    public Client(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public RequestBuilder newRequest(InetSocketAddress socketAddress, final String endpoint) {
        return new RequestBuilder(this, socketAddress, endpoint);
    }

    private FluidResponse send(final HttpRequest request) {
        try {
            final HttpResponse<byte[]> response = backingClient.send(request, BodyHandlers.ofByteArray());
            return new FluidResponse(this.objectMapper, response);
        }
        catch(IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private URI createUri(InetSocketAddress socketAddress, final String endpoint) {
        try {
            return new URI("http", null, socketAddress.getHostName(), socketAddress.getPort(), endpoint, null, null);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static final class RequestBuilder {
        private final Client client;
        private final HttpRequest.Builder delegate = HttpRequest.newBuilder();

        private RequestBuilder(final Client client, InetSocketAddress socketAddress, final String endpoint) {
            this.client = client;
            delegate.uri(client.createUri(socketAddress, endpoint));
        }

        public RequestBuilder post(Object object) {
            delegate.POST(new SerializingBodyPublisher(client.objectMapper, object));
            return this;
        }

        public RequestBuilder put(Object object) {
            delegate.PUT(new SerializingBodyPublisher(client.objectMapper, object));
            return this;
        }

        public RequestBuilder get() {
            delegate.GET();
            return this;
        }

        public RequestBuilder delete() {
            delegate.DELETE();
            return this;
        }

        public FluidResponse send() {
            return client.send(delegate.build());
        }
    }
}