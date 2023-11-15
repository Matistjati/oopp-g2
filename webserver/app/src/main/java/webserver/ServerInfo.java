package webserver;

import java.net.URI;

public class ServerInfo {
    final String name;
    final URI serverURI;

    public ServerInfo(String name, URI serverURI) {
        this.name = name;
        this.serverURI = serverURI;
    }
}
