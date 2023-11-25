package oopp.client;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    public <T> RequestBuilder<T> newRequest(InetSocketAddress socketAddress, final String endpoint, final Class<T> responseType) {
        return new RequestBuilder<>(this, socketAddress, endpoint, responseType);
    }

    private <T> HttpResponse<T> send(final HttpRequest request, final Class<T> responseType) throws IOException, InterruptedException {
        if (responseType == byte[].class) {
            //noinspection unchecked
            return (HttpResponse<T>) backingClient.send(request, BodyHandlers.ofByteArray());
        } else if (responseType == Void.class) {
            //noinspection unchecked
            return (HttpResponse<T>) backingClient.send(request, BodyHandlers.discarding());
        }
        return backingClient.send(request, new DeserializingBodyHandler<>(objectMapper, responseType));
    }
    private URI createUri(InetSocketAddress socketAddress, final String endpoint) {
        try {
            return new URI("http", null, socketAddress.getHostName(), socketAddress.getPort(), endpoint, null, null);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static final class RequestBuilder<T> {
        private final Client client;
        private final Class<T> responseType;
        private final HttpRequest.Builder delegate = HttpRequest.newBuilder();

        private RequestBuilder(final Client client, InetSocketAddress socketAddress, final String endpoint, final Class<T> responseType) {
            this.client = client;
            this.responseType = responseType;
            delegate.uri(client.createUri(socketAddress, endpoint));
        }

        public RequestBuilder<T> post(Object object) {
            delegate.POST(new SerializingBodyPublisher(client.objectMapper, object));
            return this;
        }

        public RequestBuilder<T> put(Object object) {
            delegate.PUT(new SerializingBodyPublisher(client.objectMapper, object));
            return this;
        }

        public RequestBuilder<T> get() {
            delegate.GET();
            return this;
        }

        public RequestBuilder<T> delete() {
            delegate.DELETE();
            return this;
        }

        public HttpResponse<T> send() throws IOException, InterruptedException {
            return client.send(delegate.build(), responseType);
        }
    }
}