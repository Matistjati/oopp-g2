package oopp.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.net.InetSocketAddress;

@JsonDeserialize
public record FileInfo(String name, String size, String date) {
    public FileInfo(
            @JsonProperty("name") String name,
            @JsonProperty("size") String size,
            @JsonProperty("date") String date) {
        this.name = name;
        this.size = size;
        this.date = date;
    }
}
