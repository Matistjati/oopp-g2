package fileserver;

import oopp.server.config.ConfigLoader;

import java.io.IOException;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) {
        final FileServerConfig config = ConfigLoader.load(FileServerConfig.class, Path.of("config.json"), "defaultConfig.json");
        final FileServer fileServer;
        try {
            fileServer = new FileServer(config);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileServer.start();
    }
}
