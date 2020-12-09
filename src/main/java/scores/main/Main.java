package scores.main;

import com.sun.net.httpserver.HttpServer;
import scores.http.handlers.HighscoreListHandler;
import scores.http.handlers.LoginHandler;
import scores.http.handlers.ScoreHandler;
import scores.http.readers.QueryParamsReader;
import scores.initialization.Handlers;
import scores.initialization.Repositories;
import scores.initialization.Server;
import scores.initialization.Services;
import scores.repositories.SessionCleanerTask;
import scores.repositories.SessionRepository;
import scores.services.ScoreService;
import scores.services.SessionService;

import java.net.InetSocketAddress;
import java.util.Timer;

public class Main {

    public static void main(String[] args) throws Exception {
        Repositories.init();
        Services.init();
        Handlers.init();
        Server.init();
    }
}
