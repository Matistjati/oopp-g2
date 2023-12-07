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
        String fileName = ctx.request().getParam("fileName");
        String directory = ctx.request().getParam("directory");
        System.out.println("FileDownloadHandler: " + fileName + " " + directory);
        if (directory == null) {
            directory = "";
        }

        HttpServerResponse response = ctx.response();

        fsService.downloadFile(fileName, directory, response).onFailure(ctx::fail);
    }
}