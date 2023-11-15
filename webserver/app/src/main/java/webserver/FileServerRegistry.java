package webserver;

import java.util.HashMap;
import java.util.Map;

public class FileServerRegistry {
    private final Map<String, ServerInfo> registered;

    public FileServerRegistry() {
        this.registered = new HashMap<>();
    }
}
