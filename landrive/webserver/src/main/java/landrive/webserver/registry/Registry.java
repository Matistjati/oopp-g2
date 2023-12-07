package landrive.webserver.registry;

import landrive.lib.server.ServerInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Registry {
    private final Map<String, ServerInfo> registered = new ConcurrentHashMap<>();

    public Registry() {

    }

    public void register(final ServerInfo serverInfo) {
        registered.put(serverInfo.name(), serverInfo);
        System.out.println("File server " + serverInfo.name() + " registered.");
    }

    public void unregister(final String name) {
        registered.remove(name);
        System.out.println("File server " + name + " unregistered.");
    }

    public List<ServerInfo> getFileServerList() {
        return this.registered.values().stream().sorted().toList();
    }
}