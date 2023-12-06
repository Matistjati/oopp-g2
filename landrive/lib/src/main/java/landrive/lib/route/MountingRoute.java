package landrive.lib.route;

import io.vertx.ext.web.Router;

import java.util.List;

public interface MountingRoute {
    void mount(Router router);

    static void mountAll(final Router router, final MountingRoute... routes) {
        List.of(routes).forEach(route -> route.mount(router));
    }
}