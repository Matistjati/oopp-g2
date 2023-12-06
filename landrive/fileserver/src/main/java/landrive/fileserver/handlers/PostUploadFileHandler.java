package landrive.fileserver.handlers;

import io.vertx.core.Handler;
import io.vertx.core.file.FileSystem;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import landrive.fileserver.filesystem.FsService;

public class PostUploadFileHandler implements Handler<RoutingContext> {
    private final FsService fsService;

    public PostUploadFileHandler(final FsService fsService) {
        this.fsService = fsService;
    }

    @Override
    public void handle(RoutingContext ctx) {
        final HttpServerRequest request = ctx.request();
        final HttpServerResponse response = ctx.response();
        request.setExpectMultipart(true);
        request.uploadHandler(fileUpload -> {
            this.fsService.uploadFile(fileUpload)
                    .onFailure(ctx::fail);
        }).endHandler(unused -> {
            response
                    .putHeader("Access-Control-Allow-Origin", "*")
                    .setStatusCode(200)
                    .end();
        });
    }
}
