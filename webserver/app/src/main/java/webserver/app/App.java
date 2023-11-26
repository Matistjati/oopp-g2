package webserver.app;

import oopp.config.ConfigLoader;
import webserver.WebServer;
import webserver.WebServerConfig;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) {
        final WebServerConfig config = ConfigLoader.load(WebServerConfig.class, Path.of("config.json"), "defaultConfig.json");
        final WebServer webServer;
        try {
            webServer = new WebServer(config);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        webServer.start();
    }
}
