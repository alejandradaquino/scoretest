package scores.repositories;

import java.time.Instant;
import java.util.TimerTask;

public class SessionCleanerTask extends TimerTask {
   private SessionRepository sessionRepository ;

    public SessionCleanerTask(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }


    @Override
    public void run() {
        sessionRepository.cleanExpired();
        System.out.println("Expired cleaned at: "+ Instant.now());
    }

}
