package scores.repositories;

import scores.model.Session;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Optional.*;

public class SessionRepository {
    Map<String, Session> sessionsByKey = new ConcurrentHashMap<>();

    public void save(Session session) {
        this.sessionsByKey.put(session.getSessionKey(), session);
    }

    public Optional<Session> find(String key) {
        return ofNullable(sessionsByKey.get(key)).flatMap(session -> session.isExpired() ? empty() : of(session));
    }
}
