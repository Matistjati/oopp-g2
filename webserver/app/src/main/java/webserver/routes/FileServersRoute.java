package webserver.routes;

import com.sun.net.httpserver.HttpExchange;
import oopp.route.Route;
import oopp.serialize.Jackson;
import oopp.server.ServerInfo;
import webserver.FileServerRegistry;
import webserver.WebServer;

import java.net.InetSocketAddress;

public class FileServersRoute extends Route {
    private final FileServerRegistry fileServerRegistry;

    public FileServersRoute(FileServerRegistry fileServerRegistry) {
        super("/api/fileServers", Jackson.OBJECT_MAPPER);
        this.fileServerRegistry = fileServerRegistry;
    }

    @Override
    protected void post(HttpExchange exchange) {
        final ServerInfo serverInfo = this.readAndDeserialize(exchange, ServerInfo.class);
        final InetSocketAddress socketAddress = exchange.getRemoteAddress();
        if (fileServerRegistry.register(serverInfo)) {
            this.sendEmptyResponse(exchange, 200);
        }
        else {
            this.serializeAndWrite(exchange, 409, String.format("name \"%s\" is already in use", serverInfo.name()));
        }
    }

    @Override
    protected void delete(HttpExchange exchange) {
        final String name = this.stripUri(exchange.getRequestURI());
        fileServerRegistry.unregister(name);
        this.sendEmptyResponse(exchange, 200);
    }

    @Override
    protected void get(HttpExchange exchange) {
        this.serializeAndWrite(exchange, 200, this.fileServerRegistry.getFileServerNameList());
    }
}
