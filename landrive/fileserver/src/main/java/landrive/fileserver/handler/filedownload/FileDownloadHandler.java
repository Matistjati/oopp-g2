package landrive.fileserver.handler.filedownload;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import landrive.fileserver.filesystem.FsService;

public class FileDownloadHandler implements Handler<RoutingContext> {
    private final FsService fsService;

    public FileDownloadHandler(final FsService fsService) {
        this.fsService = fsService;
    }
    @Override
    public void handle(RoutingContext ctx) {
        final String path = ctx.request().getParam("path");
        // System.out.println("FileDownloadHandler: " + fileName + " " + directory);
        HttpServerResponse response = ctx.response();
        fsService.downloadFile(path, response).onFailure(ctx::fail);
    }
}