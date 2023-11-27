package oopp.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.function.Consumer;

public class FluidResponse {
    private final ObjectMapper objectMapper;
    private final HttpResponse<byte[]> response;
    private boolean handled;

    FluidResponse(ObjectMapper objectMapper, HttpResponse<byte[]> response) {
        this.objectMapper = objectMapper;
        this.response = response;
    }

    private <T> T deserialize(byte[] bytes, Class<T> dtoClass) {
        try {
            return objectMapper.readValue(bytes, dtoClass);
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private boolean statusEqualsAny(Integer[] rCodes) {
        return (rCodes.length == 0 || Arrays.asList(rCodes).contains(this.response.statusCode()));
    }

    public FluidResponse handle(Runnable action, Integer... rCodes) {
        if (!this.handled && statusEqualsAny(rCodes)) {
            action.run();
            this.handled = true;
        }
        return this;
    }

    public <T> FluidResponse handle(Class<T> dtoClass, Consumer<T> action, Integer... rCodes) {
        if (!this.handled && statusEqualsAny(rCodes)) {
            final byte[] bytes = response.body();
            action.accept(deserialize(bytes, dtoClass));
            this.handled = true;
        }
        return this;
    }
}
