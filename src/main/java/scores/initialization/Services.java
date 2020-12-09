package scores.initialization;

import scores.services.ScoreService;
import scores.services.SessionService;

public class Services {
    private static final int AMOUNT_OF_HIGHEST = 10;
    public static SessionService sessionService;
    public static ScoreService scoreService;

    public static void init() {
        sessionService = new SessionService(Repositories.EXPIRATION_TIME, Repositories.sessionRepository);
        scoreService = new ScoreService(AMOUNT_OF_HIGHEST);

    }

}
