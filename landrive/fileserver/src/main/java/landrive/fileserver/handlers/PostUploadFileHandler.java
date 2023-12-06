package landrive.fileserver.handlers;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import landrive.fileserver.filesystem.FsService;
import landrive.lib.route.IMountable;

public class PostUploadFileHandler implements IMountable, Handler<RoutingContext> {
    private final FsService fsService;

    public PostUploadFileHandler(final FsService fsService) {
        this.fsService = fsService;
    }

    @Override
    public void handle(RoutingContext ctx) {
        final HttpServerRequest request = ctx.request();
        final HttpServerResponse response = ctx.response();
        request.setExpectMultipart(true);
        request.uploadHandler(upload -> {
            response.putHeader("Access-Control-Allow-Origin", "*");
            this.fsService.uploadFile(upload)
                    .onSuccess(unused -> {
                        response.setStatusCode(200).end();
                    })
                    .onFailure(ctx::fail);
        });
    }

    @Override
    public void mount(Router router) {
        router.postWithRegex("\\/api\\/uploadFile\\/(?<dir>.*)")
                .handler(this);
    }
}
