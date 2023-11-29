package webserver.routes;

import com.sun.net.httpserver.HttpExchange;
import oopp.route.Route;
import oopp.serialize.Jackson;
import oopp.server.FileInfo;
import webserver.WebServer;

import java.net.InetSocketAddress;

public class FileListRoute extends Route {

    public FileListRoute() {
        super("/api/filelist", Jackson.OBJECT_MAPPER);
    }


    @Override
    protected void get(HttpExchange exchange) {
        System.out.println("FileListRoute");
        this.serializeAndWrite(exchange, 200, new String[]{"test today 100mb", "test2 yesterday 5kb", "test3 yoday 1gb", "test4 today 1pb"});
    }
}
