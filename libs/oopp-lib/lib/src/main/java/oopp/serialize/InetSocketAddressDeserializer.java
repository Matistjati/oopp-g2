package oopp.serialize;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class InetSocketAddressDeserializer extends StdDeserializer<InetSocketAddress> {
    public InetSocketAddressDeserializer() {
        super(InetSocketAddress.class);
    }

    @Override
    public InetSocketAddress deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        return new InetSocketAddress(
                node.get("hostname").textValue(),
                (int) node.get("port").numberValue()
        );
    }
}
