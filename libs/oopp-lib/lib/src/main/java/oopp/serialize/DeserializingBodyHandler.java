package oopp.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.ResponseInfo;
import java.net.http.HttpResponse.BodySubscribers;

public final class DeserializingBodyHandler<T> implements HttpResponse.BodyHandler<T> {
    private final ObjectMapper objectMapper;
    private final Class<T> dtoClass;

    public DeserializingBodyHandler(final ObjectMapper objectMapper, final Class<T> dtoClass) {
        this.objectMapper = objectMapper;
        this.dtoClass = dtoClass;
    }

    @Override
    public HttpResponse.BodySubscriber<T> apply(final ResponseInfo responseInfo) {
        return BodySubscribers.mapping(BodySubscribers.ofInputStream(), upstream -> {
            try {
                return objectMapper.readValue(upstream, dtoClass);
            } catch (final IOException exception) {
                throw new RuntimeException(exception);
            }
        });
    }
}