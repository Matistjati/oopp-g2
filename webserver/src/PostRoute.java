import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class PostRoute extends Route {
    PostRoute() {
        super("/api/post");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
