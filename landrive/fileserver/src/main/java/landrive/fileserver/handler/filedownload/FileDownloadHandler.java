package landrive.fileserver.handler.filedownload;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import landrive.fileserver.filesystem.FsService;

public class FileDownloadHandler implements Handler<RoutingContext> {
    private final FsService fsService;

    public FileDownloadHandler(FsService fsService) {
        this.fsService = fsService;
    }

    @Override
    public void handle(RoutingContext ctx) {
        String fileName = ctx.request().getParam("fileName");
        //final String dir = ctx.pathParam("dir");

        System.out.println("Downloading file: " + fileName);
        //System.out.println("From directory: " + dir);


        HttpServerResponse response = ctx.response();

        response.putHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        response.putHeader(HttpHeaders.CONTENT_TYPE, "application/octet-stream");

        ctx.response().sendFile("./storage/" + fileName,
                ar -> {
                    if (ar.succeeded()) {
                        System.out.println("File downloaded successfully");
                        ctx.response().setStatusCode(200).end();
                    } else {
                        System.out.println("File download failed");
                        ar.cause().printStackTrace();
                        ctx.response().setStatusCode(500).end("Internal Server Error");
                    }
                });
    }
}