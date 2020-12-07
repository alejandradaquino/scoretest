package scores.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import scores.services.ScoreService;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Long.valueOf;
import static java.util.Arrays.stream;

public class HighscoreListHandler extends BaseHandler{

    private ScoreService scoreService;

    public HighscoreListHandler(ScoreService scoreService){
        this.scoreService = scoreService;
    }

    public HighscoreListHandler(HttpHandler next, ScoreService scoreService) {
        super(next);
        this.scoreService = scoreService;
    }

    @Override
    protected String doHandle(HttpExchange exchange) {

        Long levelId = valueOf(getPaths(exchange).get(0));
        return scoreService.getHighestByLevel(levelId).stream()
                .map(s->s.getUserId() + "=" + s.getScore())
                .collect(Collectors.joining(","));
    }

    @Override
    public boolean canHandle(HttpExchange exchange) {
        List<String> paths = getPaths(exchange);
        String requestMethod =  exchange.getRequestMethod();
        return requestMethod.equals("GET") && paths.size() == 2 && paths.get(0).matches("\\d.*")&& paths.get(1).equals("highscorelist");
    }
}
