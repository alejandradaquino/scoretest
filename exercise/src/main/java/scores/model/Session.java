package scores.model;

import java.time.Instant;
import java.util.Date;

public class Session {
    private Long userId;
    private String sessionKey;
    private Long expirationTime;

    public Session(Long userId, String sessionKey, Long expirationTime) {
        this.userId = userId;
        this.sessionKey = sessionKey;
        this.expirationTime = expirationTime;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public Long getUserId() {
        return userId;
    }

    public boolean isExpired() {
        return  Instant.now().toEpochMilli() > expirationTime;
    }
}
