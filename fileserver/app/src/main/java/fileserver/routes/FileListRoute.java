package fileserver.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import filehandling.FsDirectoryList;
import filehandling.FsHandler;
import oopp.route.Route;
import oopp.route.SerializingRoute;
import oopp.serialize.Jackson;

import java.io.IOException;
import java.nio.file.Path;

public class FileListRoute extends SerializingRoute {
    private final FsHandler fsHandler;

    public FileListRoute(FsHandler fsHandler) {
        super("/api/fileList", Jackson.OBJECT_MAPPER);
        this.fsHandler = fsHandler;
    }

    @Override
    public void get(HttpExchange exchange) {
        final Path path = Path.of(this.stripUri(exchange.getRequestURI()));
        final FsDirectoryList directoryList = this.fsHandler.getFileList(path);
        this.serializeAndWrite(exchange, 200, directoryList);
    }
}
