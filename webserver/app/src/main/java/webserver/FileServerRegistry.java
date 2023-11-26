package webserver;

import oopp.server.ServerInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileServerRegistry {
    private final Map<String, ServerInfo> registered = new ConcurrentHashMap<>();

    public FileServerRegistry() {

    }

    /*public void register(ServerInfo serverInfo) {
        registered.put(serverInfo.getNameIdentifier(), serverInfo);
    }*/
}
