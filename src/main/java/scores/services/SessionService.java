package scores.services;

import scores.repositories.SessionRepository;

import java.util.UUID;

public class SessionService {
    private long expirationTime;
    SessionRepository repository;

    public SessionService(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String doLogin(Long userId) {
        expirationTime++;
        return UUID.randomUUID().toString();
    }

    public Boolean isValidSession(String sessionKey){
        return true;
    }

    public Long getUserFrom(String sessionKey){
        return 0L;
    }
}
