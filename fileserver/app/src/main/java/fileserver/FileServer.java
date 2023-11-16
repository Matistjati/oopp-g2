package fileserver;

import oopp.routing.Router;
import oopp.server.Server;

import java.io.IOException;
import java.util.List;

public class FileServer extends Server {
    FileServer(int port) throws IOException {
        super(port);
        Router router = new Router(List.of(

        ));
        router.mount(this.httpServer);
    }

    @Override
    public void start() {
        super.start();
        System.out.printf("File server started on port %d.\n", this.httpServer.getAddress().getPort());
    }

    @Override
    public void stop() {
        super.stop();
        System.out.printf("File server stopped on port %d.\n", this.httpServer.getAddress().getPort());
    }
}
