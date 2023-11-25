package fileserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import oopp.cli.Cli;
import oopp.cli.command.Command;
import oopp.client.Client;
import oopp.routing.Router;
import oopp.serialize.Jackson;
import oopp.server.Server;
import oopp.server.ServerInfo;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;


public class FileServer extends Server {
    private final FileServerClient client = new FileServerClient(Jackson.OBJECT_MAPPER);
    private final String name;
    private boolean connected = false;
    private final Cli cli = new Cli(this);

    public FileServer(FileServerConfig config) throws IOException {
        super(config.socketAddress());
        this.name = config.name();
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

    @Command
    @Override
    public void stop() {
        System.out.printf("File server starting on port %d.\n", this.backingServer.getAddress().getPort());
        cli.stop();
        super.stop();
    }

    @Command
    private void connect() {
        try {
            HttpResponse<Void> response = client.newRequest("/api/discovery", Void.class)
                    .post(name)
                    .send();
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Command
    private void disconnect() {

    }
}
