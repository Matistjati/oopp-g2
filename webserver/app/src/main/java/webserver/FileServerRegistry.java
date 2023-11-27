package webserver;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileServerRegistry {
    private final Map<String, InetSocketAddress> registered = new ConcurrentHashMap<>();

    public FileServerRegistry() {

    }

    public boolean register(String name, InetSocketAddress socketAddress) {
        InetSocketAddress prev = registered.putIfAbsent(name, socketAddress);
        if (prev == null) {
            System.out.printf("Successfully registered file server on %s.\n", socketAddress);
            return true;
        }
        System.out.printf("File server on %s tried to register on name already in use: \"%s\"\n", socketAddress, name);
        return false;
    }
}
