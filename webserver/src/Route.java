import com.sun.net.httpserver.HttpHandler;

abstract class Route implements HttpHandler {
    final protected String path;

    Route(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
