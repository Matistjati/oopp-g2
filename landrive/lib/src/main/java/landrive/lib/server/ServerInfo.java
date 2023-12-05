package landrive.lib.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.vertx.core.net.SocketAddress;

@JsonDeserialize
public record ServerInfo(String name, SocketAddress socketAddress) {
    public ServerInfo(
            @JsonProperty String name,
            @JsonProperty SocketAddress socketAddress
    ) {
        this.name = name;
        this.socketAddress = socketAddress;
    }
}
