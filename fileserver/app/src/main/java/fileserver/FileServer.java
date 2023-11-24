package fileserver;

import oopp.cli.Cli;
import oopp.cli.command.Command;
import oopp.routing.Router;
import oopp.server.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;


public class FileServer extends Server {
    private final String name;
    private final InetSocketAddress webSocketAddress;
    private boolean connected = false;
    private final Cli cli = new Cli(this);

    public FileServer(FileServerConfig config) throws IOException {
        super(config.socketAddress());
        this.name = config.name();
        this.webSocketAddress = config.webSocketAddress();
        Router router = new Router(List.of(

        ));
        router.mount(this.backingServer);
    }

    @Override
    public void start() {
        System.out.printf("File server starting on port %d.\n", this.backingServer.getAddress().getPort());
        super.start();
        cli.start();
    }

    @Override
    public void stop() {
        System.out.printf("File server starting on port %d.\n", this.backingServer.getAddress().getPort());
        super.stop();
    }

    @Command(name = "connect")
    private void connectCommand() {
        System.out.println("Connecting...");
        // Actually connect here.
    }

    @Command(name = "stop")
    private void stopCommand() {
        cli.stop();
        this.stop();
    }
}
