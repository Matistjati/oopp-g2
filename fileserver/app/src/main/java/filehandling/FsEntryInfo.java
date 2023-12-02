package filehandling;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.net.InetSocketAddress;

@JsonSerialize
public record FsEntryInfo(
        String name,
        String date,
        Long size) {
    public FsEntryInfo(
            @JsonProperty("name") String name,
            @JsonProperty("date") String date,
            @JsonProperty("size") Long size) {
        this.name = name;
        this.date = date;
        this.size = size;
    }
}