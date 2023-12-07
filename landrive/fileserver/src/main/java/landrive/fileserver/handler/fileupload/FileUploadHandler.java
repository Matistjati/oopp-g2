package landrive.fileserver.handler.fileupload;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import landrive.fileserver.filesystem.FsService;

public class FileUploadHandler implements Handler<RoutingContext> {
    private final FsService fsService;

    public FileUploadHandler(FsService fsService) {
        this.fsService = fsService;
    }

    @Override
    public void handle(RoutingContext ctx) {
        final HttpServerRequest request = ctx.request();
        final HttpServerResponse response = ctx.response();
        final String dir = ctx.pathParam("dir");
        request.setExpectMultipart(true);
        request.uploadHandler(fileUpload -> {
            fsService.uploadFile(fileUpload, dir)
                    .onFailure(ctx::fail);
        }).endHandler(unused -> {
            response
                    .putHeader("Access-Control-Allow-Origin", "*")
                    .setStatusCode(200)
                    .end();
        });
    }
}