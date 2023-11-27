package webserver.routes;

import com.sun.net.httpserver.HttpExchange;
import oopp.route.Route;
import oopp.serialize.Jackson;
import webserver.WebServer;

import java.net.InetSocketAddress;

public class FileServersRoute extends Route {
    private final WebServer webServer;

    public FileServersRoute(WebServer webServer) {
        super("/api/fileServers", Jackson.OBJECT_MAPPER);
        this.webServer = webServer;
    }

    @Override
    protected void post(HttpExchange exchange) {
        final String name = this.readAndDeserialize(exchange, String.class);
        final InetSocketAddress socketAddress = exchange.getRemoteAddress();
        if (webServer.registerFileServer(name, socketAddress)) {
            this.sendEmptyResponse(exchange, 200);
        }
        else {
            this.serializeAndWrite(exchange, 409, String.format("name \"%s\" is already in use", name));
        }
    }

    @Override
    protected void delete(HttpExchange exchange) {
        System.out.println("in delete");
        this.sendEmptyResponse(exchange, 200);
    }
}
