package webserver.routes;

import com.sun.net.httpserver.HttpExchange;
import oopp.route.Route;
import oopp.serialize.Jackson;
import oopp.server.FileInfo;
import webserver.FileServerRegistry;
import webserver.WebServer;

import java.net.InetSocketAddress;

public class FileListRoute extends ProxyRoute {
    public FileListRoute(FileServerRegistry fileServerRegistry) {
        super("/api/fileList", fileServerRegistry);
    }
}
