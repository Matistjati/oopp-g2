package oopp.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Jackson {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    static {
        SimpleModule module = new SimpleModule();
        module.addSerializer(InetSocketAddress.class, new InetSocketAddressSerializer());
        module.addDeserializer(InetSocketAddress.class, new InetSocketAddressDeserializer());
        OBJECT_MAPPER.registerModule(module);
    }
}
