package scores.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import scores.http.exceptions.InvalidSessionException;
import scores.http.readers.QueryParamsReader;
import scores.model.Score;
import scores.services.ScoreService;
import scores.services.SessionService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import static java.lang.Long.valueOf;
import static java.util.Optional.ofNullable;

public class ScoreHandler extends BaseHandler {
    private QueryParamsReader queryParamsReader;
    private SessionService sessionService;
    private ScoreService scoreService;

    public ScoreHandler(QueryParamsReader queryParamsReader, SessionService sessionService, ScoreService scoreService) {
        super();
        this.queryParamsReader = queryParamsReader;
        this.sessionService = sessionService;
        this.scoreService = scoreService;
    }

    public ScoreHandler(HttpHandler handler, QueryParamsReader queryParamsReader, SessionService sessionService, ScoreService scoreService) {
        super(handler);
        this.queryParamsReader = queryParamsReader;
        this.sessionService = sessionService;
        this.scoreService = scoreService;
    }

    @Override
    protected String doHandle(HttpExchange exchange) {
        Long levelId = valueOf(getPaths(exchange).get(0));
        Long score = valueOf(readRequestBody(exchange));
        Long userId = getUserId(getParams(exchange).get("sessionkey"));

        scoreService.registerScore(new Score(userId,levelId,score));

        return "";
    }

    private Map<String, String> getParams(HttpExchange exchange) {
        return queryParamsReader.readParameters(exchange.getRequestURI().getQuery());
    }

    private Long getUserId(String sessionKey) {
        return ofNullable(sessionKey)
                .map(key -> sessionService.getUserFrom(key))
                .orElseThrow(InvalidSessionException::new);
    }

    private String readRequestBody(HttpExchange exchange){
        try(InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr)) {
            int b;
            StringBuilder buf = new StringBuilder(512);
            while ((b = br.read()) != -1) {
                buf.append((char) b);
            }
            return buf.toString();
        }catch (Exception e){
            return "";
        }
    }

    @Override
    public boolean canHandle(HttpExchange exchange) {
        List<String> paths = getPaths(exchange);
        String requestMethod = exchange.getRequestMethod();
        return requestMethod.equals("POST") && paths.size() == 2 && paths.get(0).matches("\\d.*") && paths.get(1).equals("score");
    }

}
