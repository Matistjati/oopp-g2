package fileserver.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import oopp.route.Route;
import oopp.serialize.Jackson;

public class FileListRoute extends Route {
    public FileListRoute() {
        super("/api/fileList", Jackson.OBJECT_MAPPER);
    }

    @Override
    public void get(HttpExchange exchange) {
        System.out.println("in file server");
        this.sendEmptyResponse(exchange, 200);
    }
}
