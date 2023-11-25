package oopp.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.ByteBuffer;
import java.util.concurrent.Flow;

public final class SerializingBodyPublisher implements BodyPublisher {
    private final BodyPublisher delegate;

    public SerializingBodyPublisher(final ObjectMapper objectMapper, final Object object) {
        try {
            final byte[] serializedBody = objectMapper.writeValueAsBytes(object);
            delegate = BodyPublishers.ofByteArray(serializedBody);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long contentLength() {
        return delegate.contentLength();
    }

    @Override
    public void subscribe(Flow.Subscriber<? super ByteBuffer> subscriber) {
        delegate.subscribe(subscriber);
    }
}