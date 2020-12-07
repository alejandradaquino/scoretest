package scores.http.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class LoginHandler extends BaseHandler {

    public LoginHandler() {
        super();
    }

    public LoginHandler(BaseHandler handler) {
        super(handler);
    }

    @Override
    protected String doHandle(HttpExchange exchange) {
        return "Login ok";
    }

    @Override
    public boolean canHandle(HttpExchange exchange) {
        List<String> paths = getPaths(exchange);
        String requestMethod =  exchange.getRequestMethod();
        return requestMethod.equals("GET") && paths.size() == 2 && paths.get(0).matches("\\d.*")&& paths.get(1).equals("login");
    }
}
