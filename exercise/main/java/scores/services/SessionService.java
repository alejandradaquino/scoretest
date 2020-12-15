package scores.services;

import scores.http.exceptions.InvalidSessionException;
import scores.model.Session;
import scores.repositories.SessionRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class SessionService {
    private long expirationTime;
    private SessionRepository repository;

    public SessionService(Long expirationTime, SessionRepository repository) {
        this.expirationTime = expirationTime;
        this.repository = repository;
    }

    public String doLogin(Long userId) {
        String key = UUID.randomUUID().toString();
        Session session = new Session(userId, key, Instant.now().plus(expirationTime, ChronoUnit.MILLIS).toEpochMilli());
        repository.save(session);
        return key;
    }

    public Boolean isValidSession(String sessionKey){
        return repository.find(sessionKey).isPresent();
    }

    public Long getUserFrom(String sessionKey){
        return repository.find(sessionKey).map(Session::getUserId).orElseThrow(InvalidSessionException::new);
    }
}
