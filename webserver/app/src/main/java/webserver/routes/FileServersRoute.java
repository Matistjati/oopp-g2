package webserver.routes;

import com.sun.net.httpserver.HttpExchange;
import oopp.route.Route;
import oopp.serialize.Jackson;
import oopp.server.ServerInfo;
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
        final ServerInfo serverInfo = this.readAndDeserialize(exchange, ServerInfo.class);
        final InetSocketAddress socketAddress = exchange.getRemoteAddress();
        if (webServer.registerFileServer(serverInfo)) {
            this.sendEmptyResponse(exchange, 200);
        }
        else {
            this.serializeAndWrite(exchange, 409, String.format("name \"%s\" is already in use", serverInfo.name()));
        }
    }

    @Override
    protected void delete(HttpExchange exchange) {
        final String name = this.stripUri(exchange.getRequestURI());
        webServer.unregisterFileServer(name);
        this.sendEmptyResponse(exchange, 200);
    }
}
