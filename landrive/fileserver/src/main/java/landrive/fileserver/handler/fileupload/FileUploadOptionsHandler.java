package landrive.fileserver.handler.fileupload;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class FileUploadOptionsHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        ctx.response()
                .putHeader("Access-Control-Allow-Origin", "*")
                .setStatusCode(200)
                .end();
    }
}