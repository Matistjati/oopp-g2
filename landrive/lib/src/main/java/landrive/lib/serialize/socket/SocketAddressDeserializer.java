package landrive.lib.serialize.socket;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.vertx.core.net.SocketAddress;

import java.io.IOException;

public class SocketAddressDeserializer extends StdDeserializer<SocketAddress> {
    public SocketAddressDeserializer() {
        super(SocketAddress.class);
    }

    @Override
    public SocketAddress deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException {
        final JsonNode node = p.getCodec().readTree(p);
        return SocketAddress.inetSocketAddress(
                node.get("port").asInt(),
                node.get("hostname").textValue()
        );
    }
}
