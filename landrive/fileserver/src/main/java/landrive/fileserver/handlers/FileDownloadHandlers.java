package landrive.fileserver.handlers;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import landrive.fileserver.filesystem.FsService;
import landrive.lib.route.MountingHandlers;

public class FileDownloadHandlers implements MountingHandlers {
    private final FsService fsService;

    public FileDownloadHandlers(final FsService fsService) {
        this.fsService = fsService;
    }

    private class FileDownloadHandler implements Handler<RoutingContext> {
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

    @Override
    public void mount(Router router) {
        router.get("/api/download/:fileName").handler(new FileDownloadHandler());
    }
}