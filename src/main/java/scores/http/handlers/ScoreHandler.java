package scores.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class ScoreHandler extends BaseHandler {
    public ScoreHandler() {
        super();
    }

    public ScoreHandler(HttpHandler handler) {
        super(handler);
    }

    @Override
    protected String doHandle(HttpExchange exchange) {
        List<String> paths = getPaths(exchange);
        Long levelId = Long.valueOf(paths.get(0));
        return "Score ok "+ levelId;
    }

    @Override
    public boolean canHandle(HttpExchange exchange) {
        List<String> paths = getPaths(exchange);
        String requestMethod =  exchange.getRequestMethod();
        return requestMethod.equals("POST") && paths.size() == 2 && paths.get(0).matches("\\d.*")&& paths.get(1).equals("score");
    }

}
