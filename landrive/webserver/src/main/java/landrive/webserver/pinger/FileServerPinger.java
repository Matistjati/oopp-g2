package landrive.webserver.pinger;

import landrive.lib.server.ServerInfo;
import landrive.webserver.registry.Registry;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;

public class FileServerPinger implements Handler<Long> {
    private final Registry registry;
    private final WebClient client;

    public FileServerPinger(Registry registry, Vertx vertx){
        this.registry = registry;
        this.client = WebClient.create(vertx);
    }

    @Override
    public void handle(Long sec) {
        for (ServerInfo info : registry.getFileServerList()){
            this.client
                .get(info.socketAddress().port(), info.socketAddress().host(), "/ping")
                .send()
                .onFailure(res -> {
                    System.out.println("Lost connection to '" + info.name() + "'");
                    this.registry.unregister(info.name());
                });
        }
    }

}
