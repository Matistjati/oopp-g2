package oopp.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.http.HttpClient;

import oopp.cli.CLIArgs;
import oopp.cli.CLIOption;
import oopp.cli.GenericCLI;


public abstract class Server {
    protected final HttpServer httpServer;
    protected final HttpClient httpClient;
    protected final GenericCLI cli = new GenericCLI();

    protected Server(HttpServer httpServer) {
        this.httpServer = httpServer;
        this.httpClient = HttpClient.newHttpClient();
        this.setupCli();
    }

    protected Server(String hostname, int port) throws IOException {
        this(createDefault(hostname, port));
    }

    protected Server(int port) throws IOException {
        this("localhost", port);
    }

    public void start() {
        httpServer.start();
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    }

    public void stop() {
        httpServer.stop(0);
    }

    private static HttpServer createDefault(String hostName, int port) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(hostName, port), 0);
        httpServer.setExecutor(null);
        return httpServer;
    }

    protected String cliStop(CLIArgs args)
    {
        stop();
        cli.stop();
        return "";
    }

    private void setupCli()
    {
        cli.addCliOption(new CLIOption(this::cliStop, "Stop server"));
    }
}
