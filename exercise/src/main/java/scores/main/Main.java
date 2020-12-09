package scores.main;

import com.sun.net.httpserver.HttpServer;
import scores.http.handlers.HighscoreListHandler;
import scores.http.handlers.LoginHandler;
import scores.http.handlers.ScoreHandler;
import scores.http.readers.QueryParamsReader;
import scores.repositories.SessionRepository;
import scores.services.ScoreService;
import scores.services.SessionService;

import java.net.InetSocketAddress;

public class Main {
    private static final String CONTEXT = "/";
    private static final int PORT = 8081;
    private static final long EXPIRATION_TIME = 1000 * 60 * 10L;
    private static final int AMOUNT_OF_HIGHEST = 10;

    public static void main(String[] args) throws Exception {
        //Repositories init
        SessionRepository sessionRepository = new SessionRepository();

        //Services init
        SessionService sessionService = new SessionService(EXPIRATION_TIME, sessionRepository);
        ScoreService scoreService = new ScoreService(AMOUNT_OF_HIGHEST);

        //Handlers init
        HighscoreListHandler highscoreListHandler = new HighscoreListHandler(scoreService);
        ScoreHandler scoreHandler = new ScoreHandler(highscoreListHandler, new QueryParamsReader(), sessionService,scoreService);
        LoginHandler chain = new LoginHandler(scoreHandler, sessionService);

        //HTTP server start
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext(CONTEXT, chain);
        httpServer.setExecutor(null);

        httpServer.start();
        System.out.println("Server is started and listening on port " + PORT);
    }
}
