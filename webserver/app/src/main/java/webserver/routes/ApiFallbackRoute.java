package webserver.routes;

import oopp.route.Route;
import oopp.serialize.Jackson;

public class ApiFallbackRoute extends Route {
    public ApiFallbackRoute() {
        super("/api");
    }
}
