package landrive.fileserver.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.vertx.core.net.SocketAddress;

@JsonDeserialize
public record Config(String name, SocketAddress socketAddress, SocketAddress webServerSocketAddress) {
    public Config(
            @JsonProperty String name,
            @JsonProperty SocketAddress socketAddress,
            @JsonProperty SocketAddress webServerSocketAddress
    ) {
        this.name = name;
        this.socketAddress = socketAddress;
        this.webServerSocketAddress = webServerSocketAddress;
    }
}
