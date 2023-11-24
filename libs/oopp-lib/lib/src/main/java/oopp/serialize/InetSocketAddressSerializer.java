package oopp.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class InetSocketAddressSerializer extends StdSerializer<InetSocketAddress> {
    public InetSocketAddressSerializer() {
        super(InetSocketAddress.class);
    }

    @Override
    public void serialize(InetSocketAddress value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("hostname", value.getHostName());
        gen.writeNumberField("port", value.getPort());
        gen.writeEndObject();
    }
}
