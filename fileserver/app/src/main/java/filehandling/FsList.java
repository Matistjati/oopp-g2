package filehandling;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fileserver.FileServer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("DataFlowIssue")
@JsonSerialize
public class FsList {
    private final List<String> directories = new ArrayList<>();
    private final List<String> files = new ArrayList<>();

    public FsList(File directory) {
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException();
        }
        Stream.of(directory.listFiles()).forEach(entry -> {
            if (entry.isDirectory()) {
                directories.add(entry.getName());
            }
            else{
                files.add(entry.getName());
            }
        });
    }
}
