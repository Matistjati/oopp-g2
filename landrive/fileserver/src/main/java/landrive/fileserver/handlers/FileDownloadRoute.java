package landrive.fileserver.handlers;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import landrive.fileserver.filesystem.FsService;
import landrive.lib.route.MountingHandlers;

public class FileDownloadRoute implements MountingHandlers {
    private final FsService fsService;

    public FileDownloadRoute(final FsService fsService) {
        this.fsService = fsService;
    }

    private class FileDownloadHandler implements Handler<RoutingContext> {
        @Override
        public void handle(RoutingContext ctx) {
            String fileName = ctx.request().getParam("fileName");
            String directory = ctx.request().getParam("directory");

            if (directory == null) {
                directory = "";
            }

            HttpServerResponse response = ctx.response();

            fsService.downloadFile(fileName, directory, response).onFailure(ctx::fail);
        }
    }

    @Override
    public void mount(Router router) {
        router.get("/api/download/:fileName")
                .handler(new FileDownloadHandler());
    }
}