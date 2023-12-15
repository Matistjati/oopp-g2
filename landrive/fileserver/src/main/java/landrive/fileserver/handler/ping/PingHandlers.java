package landrive.fileserver.handler.ping;

import io.vertx.ext.web.Router;
import landrive.lib.route.MountingHandlers;

public class PingHandlers implements MountingHandlers {
    @Override
    public void mount(Router router) {
        router.get("/ping").handler(new PingHandler());
    }
}
