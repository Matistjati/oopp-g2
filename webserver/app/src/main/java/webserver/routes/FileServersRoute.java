package webserver.routes;

import com.sun.net.httpserver.HttpExchange;
import oopp.route.Route;
import oopp.serialize.Jackson;
import webserver.WebServer;

public class FileServersRoute extends Route {
    final WebServer webServer;

    public FileServersRoute(WebServer webServer) {
        super("/api/fileServers", Jackson.OBJECT_MAPPER);
        this.webServer = webServer;
    }
}
