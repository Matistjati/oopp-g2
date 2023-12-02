package oopp.route;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class SerializingRoute extends Route {
    private final ObjectMapper objectMapper;

    public SerializingRoute(String endpoint, ObjectMapper objectMapper) {
        super(endpoint);
        this.objectMapper = objectMapper;
    }

    protected void serializeAndWrite(HttpExchange exchange, int rCode, byte[] bytes) {
        try {
            exchange.sendResponseHeaders(rCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void serializeAndWrite(HttpExchange exchange, int rCode, Object dto) {
        try {
            serializeAndWrite(exchange, rCode, this.objectMapper.writeValueAsBytes(dto));
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected <T> T readAndDeserialize(HttpExchange exchange, Class<T> dtoType) {
        try {
            return this.objectMapper.readValue(exchange.getRequestBody().readAllBytes(), dtoType);
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
