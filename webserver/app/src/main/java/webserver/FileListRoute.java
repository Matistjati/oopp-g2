package webserver;

import com.sun.net.httpserver.HttpExchange;

import oopp.routing.Route;

public class FileListRoute extends Route {
    FileListRoute() {
        super("/api/fileList");
    }
}
