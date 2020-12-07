package scores.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import scores.services.SessionService;

import java.util.List;

public class LoginHandler extends BaseHandler {

    private SessionService sessionService;


    public LoginHandler(SessionService sessionService) {
        super();
        this.sessionService = sessionService;
    }

    public LoginHandler(BaseHandler handler, SessionService sessionService) {
        super(handler);
        this.sessionService = sessionService;
    }

    @Override
    protected String doHandle(HttpExchange exchange) {
        List<String> paths = getPaths(exchange);
        Long userId = Long.valueOf(paths.get(0));
        return sessionService.doLogin(userId);
    }

    @Override
    public boolean canHandle(HttpExchange exchange) {
        List<String> paths = getPaths(exchange);
        String requestMethod = exchange.getRequestMethod();
        return requestMethod.equals("GET") && paths.size() == 2 && paths.get(0).matches("\\d.*") && paths.get(1).equals("login");
    }
}
