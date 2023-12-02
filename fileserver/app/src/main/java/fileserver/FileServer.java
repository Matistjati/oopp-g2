package fileserver;

import filehandling.FsHandler;
import fileserver.routes.FileListRoute;
import oopp.cli.Cli;
import oopp.cli.command.Command;
import oopp.route.Router;
import oopp.serialize.Jackson;
import oopp.server.Server;
import oopp.server.ServerInfo;
import oopp.server.FileInfo;
import routes.UploadFileRoute;

import java.io.IOException;
import java.nio.file.Path;


public class FileServer extends Server {
    private final FsHandler fsHandler = new FsHandler(Path.of("./storage"));
    private final FileServerClient client;
    private final Cli cli = new Cli(this);
    private final String name;
    private boolean connected = false;

    public FileServer(FileServerConfig config) throws IOException {
        super(config.socketAddress());
        this.client = new FileServerClient(Jackson.OBJECT_MAPPER, config.webSocketAddress());
        this.name = config.name();
        Router router = new Router(
                new FileListRoute(this.fsHandler),
                new UploadFileRoute()
        );
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
        if (this.connected) {
            System.out.println("ERROR: Already connected to web server.");
            return;
        }
        client.newRequest("/api/fileServers")
                .post(new ServerInfo(this.name, this.getAddress()))
                .send()
                .handle(() -> {
                    System.out.println("Successfully connected to web server.");
                    this.connected = true;
                }, 200)
                .handle(String.class, msg -> {
                    System.out.printf("ERROR: Could not connect to web server: %s\n", msg);
                }, 409)
                .handle((() -> {
                    throw new RuntimeException();
                }));
    }

    @Command
    private void disconnect() {
        if (!this.connected) {
            System.out.println("ERROR: Not connected to web server.");
            return;
        }
        client.newRequest("/api/fileServers/" + this.name)
                .delete()
                .send()
                .handle(() -> {
                    System.out.println("Successfully disconnected from web server.");
                    this.connected = false;
                }, 200)
                .handle(String.class, msg -> {
                    System.out.printf("ERROR: Could not disconnect from web server: %s\n", msg);
                }, 409)
                .handle(() -> {
                    throw new RuntimeException();
                });
    }
}
