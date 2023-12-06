package landrive.lib.route;

import io.vertx.ext.web.Router;

public interface MountingRoute {

    public void mount(Router router);
}