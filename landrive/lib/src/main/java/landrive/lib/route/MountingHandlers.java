package landrive.lib.route;

import io.vertx.ext.web.Router;

import java.util.List;

public interface MountingHandlers {
    void mount(Router router);

    static void mountAll(final Router router, final MountingHandlers... handlers) {
        List.of(handlers).forEach(h -> h.mount(router));
    }
}