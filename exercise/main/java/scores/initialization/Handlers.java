package scores.initialization;

import com.sun.net.httpserver.HttpHandler;
import scores.http.handlers.HighscoreListHandler;
import scores.http.handlers.LoginHandler;
import scores.http.handlers.ScoreHandler;
import scores.http.readers.QueryParamsReader;

public class Handlers {

    private static HighscoreListHandler highscoreListHandler;
    private static ScoreHandler scoreHandler;
    private static LoginHandler loginHandler;

    public static void init() {
        highscoreListHandler = new HighscoreListHandler(Services.scoreService);
        scoreHandler = new ScoreHandler(highscoreListHandler, new QueryParamsReader(), Services.sessionService,Services.scoreService);
        loginHandler = new LoginHandler(scoreHandler, Services.sessionService);
    }

    public static HttpHandler getChain(){
        return loginHandler;
    }
}
