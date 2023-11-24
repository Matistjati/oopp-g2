package fileserver;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.net.InetSocketAddress;

@JsonDeserialize
public record FileServerConfig(
        String name,
        InetSocketAddress socketAddress,
        InetSocketAddress webSocketAddress) {
    public FileServerConfig(
            @JsonProperty("name") String name,
            @JsonProperty("socketAddress") InetSocketAddress socketAddress,
            @JsonProperty("webSocketAddress") InetSocketAddress webSocketAddress) {
            this.name = name;
            this.socketAddress = socketAddress;
            this.webSocketAddress = webSocketAddress;
    }
}
