package webserver;

import oopp.util.ArgsParser;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        Integer port = ArgsParser.parse(args, "--port", Integer::parseInt, 8000);
        WebServer server = new WebServer("./html", port);
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
    }
}
