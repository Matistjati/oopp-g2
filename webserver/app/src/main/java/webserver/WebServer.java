package webserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.*;

import oopp.cli.Cli;
import oopp.cli.command.Command;
import oopp.route.Router;
import oopp.server.Server;
import webserver.routes.StaticRoute;

public class WebServer extends Server {
    private final Cli cli = new Cli(this);

    public WebServer(WebServerConfig config) throws IOException {
        super(config.socketAddress());
        Router router = new Router(List.of(
                new StaticRoute(Path.of("./web/"))
        ));
        this.mount(router);
    }

    @Override
    public void start() {
        super.start();
        System.out.printf("Web server started on port %d.\n", this.getAddress().getPort());
        cli.start();
    }

    @Command
    @Override
    public void stop() {
        super.stop();
        cli.stop();
        System.out.printf("Web server stopped on port %d.\n", this.getAddress().getPort());
    }
}
