package oopp.server;

import com.sun.net.httpserver.HttpServer;
import oopp.route.Router;

import java.io.IOException;
import java.net.InetSocketAddress;

public abstract class Server {
    private final HttpServer backingServer;

    protected Server(HttpServer backingServer) {
        this.backingServer = backingServer;
    }

    protected Server(InetSocketAddress socketAddress) throws IOException {
        this(createDefault(socketAddress));
    }

    public void start() {
        this.backingServer.start();
    }

    public void stop() { backingServer.stop(0); }

    private static HttpServer createDefault(InetSocketAddress socketAddress) throws IOException {
        HttpServer httpServer = HttpServer.create(socketAddress, 0);
        httpServer.setExecutor(null);
        return httpServer;
    }

    public InetSocketAddress getAddress() {
        return this.backingServer.getAddress();
    }

    public void mount(Router router) {
        router.mount(this.backingServer);
    }
}
