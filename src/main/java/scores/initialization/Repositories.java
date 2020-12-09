package scores.initialization;

import scores.repositories.SessionCleanerTask;
import scores.repositories.SessionRepository;

import java.util.Timer;

public class Repositories {
    public static final long EXPIRATION_TIME = 1000 * 60 * 10L;
    public static SessionRepository sessionRepository;

    public static void init(){
        sessionRepository = new SessionRepository();
        new Timer().schedule(new SessionCleanerTask(sessionRepository), EXPIRATION_TIME, EXPIRATION_TIME / 2);

    }
}
