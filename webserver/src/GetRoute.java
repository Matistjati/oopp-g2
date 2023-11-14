import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class GetRoute extends Route {

    GetRoute() {
        super("/api/get");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("GET");
    }
}
