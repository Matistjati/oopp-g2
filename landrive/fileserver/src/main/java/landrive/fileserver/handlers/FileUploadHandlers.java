package landrive.fileserver.handlers;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import landrive.fileserver.filesystem.FsService;
import landrive.lib.route.MountingHandlers;

public class FileUploadHandlers implements MountingHandlers {
    private final FsService fsService;

    public FileUploadHandlers(final FsService fsService) {
        this.fsService = fsService;
    }

    private class FileUploadHandler implements Handler<RoutingContext> {
        @Override
        public void handle(RoutingContext ctx) {
            final HttpServerRequest request = ctx.request();
            final HttpServerResponse response = ctx.response();
            request.setExpectMultipart(true);
            request.uploadHandler(fileUpload -> {
                fsService.uploadFile(fileUpload)
                        .onFailure(ctx::fail);
            }).endHandler(unused -> {
                response
                        .putHeader("Access-Control-Allow-Origin", "*")
                        .setStatusCode(200)
                        .end();
            });
        }
    }

    private class FileUploadOptionsHandler implements Handler<RoutingContext> {
        @Override
        public void handle(RoutingContext ctx) {
            ctx.response()
                    .putHeader("Access-Control-Allow-Origin", "*")
                    .setStatusCode(200)
                    .end();
        }
    }

    @Override
    public void mount(Router router) {
        router.postWithRegex("\\/api\\/uploadFile\\/(?<dir>.*)").handler(new FileUploadHandler());
        router.options("/api/uploadFile/*").handler(new FileUploadOptionsHandler());
    }
}
