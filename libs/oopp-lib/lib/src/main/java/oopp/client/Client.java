package oopp.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public final class Client {
    private final InetSocketAddress socketAddress;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Client(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public <R> R get(InetSocketAddress socketAddress, String endpoint, Class<R> responseType) throws IOException, InterruptedException {
        final HttpRequest request = beginBuildingRequest(socketAddress, endpoint)
                .GET()
                .build();

        return sendAndDeserialize(request, responseType);
    }

    public <R> R get(String endpoint, Class<R> responseType) throws IOException, InterruptedException {
        return this.get(this.socketAddress, endpoint, responseType);
    }

    public <R> R post(InetSocketAddress socketAddress, String endpoint, Object object, Class<R> responseType) throws IOException, InterruptedException {
        final HttpRequest request = beginBuildingRequest(socketAddress, endpoint)
                .POST(createBodyPublisher(object))
                .build();

        return sendAndDeserialize(request, responseType);
    }

    public <R> R post(String endpoint, Object object, Class<R> responseType) throws IOException, InterruptedException {
        return this.post(this.socketAddress, endpoint, object, responseType);
    }

    public <R> R put(InetSocketAddress socketAddress, String endpoint, Object object, Class<R> responseType) throws IOException, InterruptedException {
        final HttpRequest request = beginBuildingRequest(socketAddress, endpoint)
                .PUT(createBodyPublisher(object))
                .build();

        return sendAndDeserialize(request, responseType);
    }

    public <R> R put(String endpoint, Object object, Class<R> responseType) throws IOException, InterruptedException {
        return this.put(this.socketAddress, endpoint, object, responseType);
    }

    private <R> R sendAndDeserialize(HttpRequest request, Class<R> responseType) throws IOException, InterruptedException {
        final HttpResponse<byte[]> response = httpClient.send(request, BodyHandlers.ofByteArray());
        return objectMapper.readValue(response.body(), responseType);
    }

    private static HttpRequest.Builder beginBuildingRequest(InetSocketAddress socketAddress, String endpoint) {
        return HttpRequest.newBuilder()
                .uri(createUri(socketAddress, endpoint));
    }

    private BodyPublisher createBodyPublisher(Object object) throws JsonProcessingException {
        return BodyPublishers.ofByteArray(objectMapper.writeValueAsBytes(object));
    }

    private static URI createUri(InetSocketAddress socketAddress, String endpoint) {
        try {
            return new URI("http", null, socketAddress.getHostName(), socketAddress.getPort(), endpoint, null, null);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}