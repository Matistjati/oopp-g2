package landrive.lib.route;

import io.vertx.core.Handler;
import io.vertx.ext.web.Router;

public interface IMountable {

    public void mount(Router router);
}