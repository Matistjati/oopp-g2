package fileserver;

import oopp.cli.Cli;
import oopp.cli.command.Command;
import oopp.route.Router;
import oopp.serialize.Jackson;
import oopp.server.Server;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;


public class FileServer extends Server {
    private final FileServerClient client = new FileServerClient(Jackson.OBJECT_MAPPER);
    private final Cli cli = new Cli(this);
    private final String name;
    private boolean connected = false;

    public FileServer(FileServerConfig config) throws IOException {
        super(config.socketAddress());
        this.name = config.name();
        Router router = new Router(List.of(

        ));
        this.mount(router);
    }

    @Override
    public void start() {
        super.start();
        System.out.printf("File server started on port %d.\n", this.getAddress().getPort());
        cli.start();
    }

    @Command
    @Override
    public void stop() {
        cli.stop();
        super.stop();
        System.out.printf("File server stopped on port %d.\n", this.getAddress().getPort());
    }

    @Command
    private void connect() {
        
    }

    @Command
    private void disconnect() {

    }
}
