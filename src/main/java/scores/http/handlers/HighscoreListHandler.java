package scores.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

public class HighscoreListHandler extends BaseHandler{

    @Override
    protected String doHandle(HttpExchange exchange) {
        return"Highscorelist ok";
    }

    @Override
    public boolean canHandle(HttpExchange exchange) {
        List<String> paths = getPaths(exchange);
        String requestMethod =  exchange.getRequestMethod();
        return requestMethod.equals("GET") && paths.size() == 2 && paths.get(0).matches("\\d.*")&& paths.get(1).equals("highscorelist");
    }
}
