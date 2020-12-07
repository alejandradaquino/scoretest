package scores.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import scores.http.exceptions.InvalidSessionException;
import scores.http.readers.QueryParamsReader;
import scores.services.SessionService;

import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

public class ScoreHandler extends BaseHandler {
    private QueryParamsReader queryParamsReader;
    private SessionService sessionService;

    public ScoreHandler(QueryParamsReader queryParamsReader, SessionService sessionService) {
        super();
        this.queryParamsReader = queryParamsReader;
        this.sessionService = sessionService;
    }

    public ScoreHandler(HttpHandler handler, QueryParamsReader queryParamsReader, SessionService sessionService) {
        super(handler);
        this.queryParamsReader = queryParamsReader;
        this.sessionService = sessionService;
    }

    @Override
    protected String doHandle(HttpExchange exchange) {
        List<String> paths = getPaths(exchange);
        Long levelId = Long.valueOf(paths.get(0));
        Map<String, String> params = queryParamsReader.readParameters(exchange.getRequestURI().getQuery());
        Long userId = ofNullable(params.get("sessionkey"))
                .map(key -> sessionService.getUserFrom(key))
                .orElseThrow(InvalidSessionException::new);
        return "LevelId " + levelId + " userId: " + userId + " session "+ params.get("sessionkey");
    }

    @Override
    public boolean canHandle(HttpExchange exchange) {
        List<String> paths = getPaths(exchange);
        String requestMethod = exchange.getRequestMethod();
        return requestMethod.equals("POST") && paths.size() == 2 && paths.get(0).matches("\\d.*") && paths.get(1).equals("score");
    }

}
