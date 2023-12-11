package landrive.webserver.handler.dashboard;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import landrive.lib.route.MountingHandlers;

public class StaticHandlers implements MountingHandlers {
    @Override
    public void mount(Router router) {
        router.route("/dashboard/*").handler(StaticHandler.create("webfiles"));
        router.route("/").handler(new IndexRedirection());
    }
}