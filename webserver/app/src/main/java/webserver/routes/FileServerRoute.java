package webserver.routes;

import com.sun.net.httpserver.HttpExchange;
import oopp.route.Route;
import oopp.serialize.Jackson;
import webserver.WebServer;

import java.net.InetSocketAddress;

public class FileServerRoute extends Route {
    private final WebServer webServer;

    public FileServerRoute(WebServer webServer) {
        super("/api/fileServer", Jackson.OBJECT_MAPPER);
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
}
