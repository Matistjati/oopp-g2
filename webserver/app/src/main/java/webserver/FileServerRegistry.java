package webserver;

import oopp.server.ServerInfo;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileServerRegistry {
    private final Map<String, InetSocketAddress> registered = new ConcurrentHashMap<>();

    public FileServerRegistry() {

    }

    public synchronized boolean register(ServerInfo serverInfo) {
        InetSocketAddress prev = registered.putIfAbsent(serverInfo.name(), serverInfo.socketAddress());
        if (prev == null) {
            System.out.printf("Successfully registered file server on %s with name \"%s\".\n", serverInfo.socketAddress(), serverInfo.name());
            return true;
        }
        System.out.printf("File server on %s tried to register on name already in use: \"%s\"\n", serverInfo.socketAddress(), serverInfo.name());
        return false;
    }

    public synchronized void unregister(String name) {
        final SocketAddress socketAddress = registered.remove(name);
        if (socketAddress != null) {
            System.out.printf("Successfully unregistered file server on %s with name %s\n.", socketAddress, name);
            return;
        }
        System.out.printf("Client tried to unregister from unregistered name: \"%s\"\n", name);
    }
}
