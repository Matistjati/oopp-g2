package webserver;

import oopp.util.ArgsParser;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        final int port;
        try {
            port = ArgsParser.parse(args, "--port", Integer::parseInt, 8000);
        }
        catch (IllegalArgumentException e) {
            System.out.println("ERROR: Parsing arguments.");
            return;
        }
        WebServer server = new WebServer("./html", port);
        server.start();
    }
}
