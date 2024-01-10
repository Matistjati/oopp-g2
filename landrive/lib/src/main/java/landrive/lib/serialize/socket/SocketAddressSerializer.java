package landrive.lib.serialize.socket;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.vertx.core.net.SocketAddress;

import java.io.IOException;

public class SocketAddressSerializer extends StdSerializer<SocketAddress> {
    public SocketAddressSerializer() {
        super(SocketAddress.class);
    }

    @Override
    public void serialize(SocketAddress value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("host", value.host());
        gen.writeNumberField("port", value.port());
        gen.writeEndObject();
    }
}
