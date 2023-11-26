package webserver;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.net.InetSocketAddress;

@JsonDeserialize
public record WebServerConfig(InetSocketAddress socketAddress) {
    public WebServerConfig(
            @JsonProperty("socketAddress") InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }
}
