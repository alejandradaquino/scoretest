package scores.repositories;

import scores.model.Session;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.Optional.*;

public class SessionRepository {
    private Map<String, Session> sessionsByKey = new ConcurrentHashMap<>();

    public void save(Session session) {
        this.sessionsByKey.put(session.getSessionKey(), session);
    }

    public void cleanExpired() {
        sessionsByKey = sessionsByKey.entrySet().stream()
                .filter(v -> !v.getValue().isExpired())
                .collect(Collectors.toConcurrentMap(v -> v.getKey(), v -> v.getValue()));
    }

    public Optional<Session> find(String key) {
        return ofNullable(sessionsByKey.get(key)).flatMap(session -> session.isExpired() ? empty() : of(session));
    }
}
