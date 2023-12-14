package landrive.webserver.handler.fileservers;

import io.vertx.ext.web.Router;
import landrive.lib.route.MountingHandlers;
import landrive.webserver.registry.Registry;

public class FileServersHandlers implements MountingHandlers {
    private final Registry registry;

    public FileServersHandlers(Registry registry) {
        this.registry = registry;
    }

    @Override
    public void mount(Router router) {
        router.get("/api/fileServers").handler(new ListFileServersHandler(this.registry));
        router.post("/api/fileServers").handler(new RegisterFileServerHandler(this.registry));
        router.put("/api/fileServers/:name").handler(new UpdateFileServerHandler(this.registry));
        router.delete("/api/fileServers/:name").handler(new UnregisterFileServerHandler(this.registry));
    }
}
