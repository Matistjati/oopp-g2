package fileserver;

import java.io.File;
import java.io.IOException;

import org.ini4j.Ini;
import org.ini4j.Wini;

public class App {
    public static void main(String[] args) throws IOException {
        File iniFile = new File("./settings.ini");
        if (iniFile.createNewFile()) {
            System.out.println("No settings file was found, creating.");
            Wini defaultIni = new Wini();
            defaultIni.add("network", "port", 8080);
            defaultIni.add("network", "webPort", 8000);
            defaultIni.add("network", "name", "file server");
            defaultIni.store(iniFile);
            return;
        }
        final Ini ini = new Ini(iniFile);
        final int port;
        final int webPort;
        final String name;
        try {
            port = ini.get("network", "port", Integer.class);
            webPort = ini.get("network", "webPort", Integer.class);
            name = ini.get("network", "name");
        }
        catch (IllegalArgumentException e) {
            System.out.println("ERROR: Parsing ini file.");
            return;
        }
        FileServer fileServer = new FileServer(port, webPort, name);
        fileServer.start();
    }
}
