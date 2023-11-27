package oopp.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.net.InetSocketAddress;

@JsonDeserialize
public record ServerInfo(String name, InetSocketAddress socketAddress) {
    public ServerInfo(
            @JsonProperty("name") String name,
            @JsonProperty("socketAddress") InetSocketAddress socketAddress) {
        this.name = name;
        this.socketAddress = socketAddress;
    }
}
