package oopp.util;

import java.net.URI;
import java.net.URISyntaxException;

public class URIUtil {
    public static URI of(String hostname, int port, String endpoint) {
        try {
            return new URI("http", null, hostname, port, endpoint, null, null);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
