package landrive.lib.serialize.socket;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.vertx.core.net.SocketAddress;

public class SocketAddressSerializeModule extends SimpleModule {
    public SocketAddressSerializeModule() {
        this.addSerializer(SocketAddress.class, new SocketAddressSerializer());
        this.addDeserializer(SocketAddress.class, new SocketAddressDeserializer());
    }
}
