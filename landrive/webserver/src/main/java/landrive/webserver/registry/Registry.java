package landrive.webserver.registry;

import landrive.lib.server.ServerInfo;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Registry {
    private final Vertx vertx;
    private final Map<String, ServerInfo> registered;

    public Registry(Vertx vertx) {
        registered = new ConcurrentHashMap<>();
        this.vertx = vertx;
    }

    public void register(final ServerInfo serverInfo) {
        registered.put(serverInfo.name(), serverInfo);
        vertx.eventBus().publish(
            "fileserver.register", 
            new JsonObject()
                    .put("name", serverInfo.name())
                    .put("socketAddress", serverInfo.socketAddress())
                    .toString()
        );
    }
    
    public void unregister(final String name) {
        ServerInfo serverInfo = registered.remove(name);
        vertx.eventBus().publish(
            "fileserver.unregister", 
            new JsonObject()
                    .put("name", serverInfo.name())
                    .toString()
        );
    }
    
    public void rename(final String name, final ServerInfo serverInfo) {
        registered.remove(name);
        registered.put(serverInfo.name(), serverInfo);
        vertx.eventBus().publish(
            "fileserver.rename", 
            new JsonObject()
                    .put("oldName", name)
                    .put("newName", serverInfo.name())
                    .toString()
        );
    }

    public List<ServerInfo> getFileServerList() {
        return this.registered.values().stream().sorted().toList();
    }
}