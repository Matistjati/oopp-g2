package landrive.webserver.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.vertx.core.net.SocketAddress;

@JsonDeserialize
public record Config(SocketAddress socketAddress) {
    public Config(@JsonProperty SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }
}