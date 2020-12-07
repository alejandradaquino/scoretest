package scores.test;

import scores.http.QueryParamsReader;
import scores.http.SimpleHttpServer;
import scores.http.handlers.HighscoreListHandler;
import scores.http.handlers.LoginHandler;
import scores.http.handlers.ScoreHandler;

public class Main {
    private static final String CONTEXT = "/";
    private static final int PORT = 8081;

    public static void main(String[] args) throws Exception {

        HighscoreListHandler highscoreListHandler = new HighscoreListHandler();
        ScoreHandler scoreHandler = new ScoreHandler(highscoreListHandler);
        LoginHandler chain = new LoginHandler(scoreHandler);
        SimpleHttpServer simpleHttpServer = new SimpleHttpServer(PORT, CONTEXT, chain);

        simpleHttpServer.start();
        System.out.println("Server is started and listening on port "+ PORT);
    }
}
