package oopp.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public abstract class Server {
    protected final HttpServer httpServer;

    protected Server(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    protected Server(String hostname, int port) throws IOException {
        this(createDefault(hostname, port));
    }

    protected Server(int port) throws IOException {
        this("localhost", port);
    }

    public void start() {
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(0);
    }

    private static HttpServer createDefault(String hostName, int port) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(hostName, port), 0);
        httpServer.setExecutor(null);
        return httpServer;
    }
}
