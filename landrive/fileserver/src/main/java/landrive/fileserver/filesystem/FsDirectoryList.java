package landrive.fileserver.filesystem;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@JsonSerialize
public class FsDirectoryList {
    @JsonProperty("dirs")
    private final List<FsEntryInfo> dirs = new ArrayList<>();
    @JsonProperty("files")
    private final List<FsEntryInfo> files = new ArrayList<>();

    public FsDirectoryList() {}

    public FsDirectoryList(File directory) {
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException();
        }
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Stream.of(Objects.requireNonNull(directory.listFiles())).forEach(entry -> {
            final long lastModifiedTime = entry.lastModified();
            final Instant instant = Instant.ofEpochMilli(lastModifiedTime);
            final LocalDateTime lastModifiedDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            FsEntryInfo entryInfo = new FsEntryInfo(
                    entry.getName(),
                    lastModifiedDateTime.format(formatter),
                    entry.length()
            );
            if (entry.isDirectory()) {
                dirs.add(entryInfo);
                return;
            }
            files.add(entryInfo);
        });
    }
}
