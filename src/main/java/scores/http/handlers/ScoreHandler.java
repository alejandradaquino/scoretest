package scores.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import scores.http.readers.QueryParamsReader;
import scores.http.exceptions.InvalidSessionException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.stream;

public class ScoreHandler extends BaseHandler {
    private QueryParamsReader queryParamsReader;
    public ScoreHandler(QueryParamsReader queryParamsReader) {
        super();
        this.queryParamsReader = queryParamsReader;
    }

    public ScoreHandler(HttpHandler handler, QueryParamsReader queryParamsReader) {
        super(handler);
        this.queryParamsReader = queryParamsReader;
    }

    @Override
    protected String doHandle(HttpExchange exchange) {
        List<String> paths = getPaths(exchange);
        Long levelId = Long.valueOf(paths.get(0));
        Map<String, String> params = queryParamsReader.readParameters(exchange.getRequestURI().getQuery());
        String sessionkey = Optional.ofNullable(params.get("sessionkey")).orElseThrow(InvalidSessionException::new);
        return "LevelId "+ levelId +" key: "+ sessionkey;
    }

    @Override
    public boolean canHandle(HttpExchange exchange) {
        List<String> paths = getPaths(exchange);
        String requestMethod =  exchange.getRequestMethod();
        return requestMethod.equals("POST") && paths.size() == 2 && paths.get(0).matches("\\d.*")&& paths.get(1).equals("score");
    }

}
