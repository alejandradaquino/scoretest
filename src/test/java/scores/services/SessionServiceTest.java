package scores.services;

import org.junit.Test;
import scores.repositories.SessionRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionServiceTest {

    private final long userId = 1223L;
    private SessionRepository repository = new SessionRepository();
    SessionService service = new SessionService(10L, repository);

    @Test
    public void doLogin_returnsANewSessionKey() {
        assertThat(service.doLogin(userId)).isNotBlank();
    }

    @Test
    public void isValidSession_withValidSessionKey_returnsTrue() {
        String sessionKey = service.doLogin(userId);
        assertThat(service.isValidSession(sessionKey)).isTrue();
    }

    @Test
    public void isValidSession_withExpiredSessionKey_returnsFalse() throws InterruptedException {
        SessionService service = new SessionService(0L, repository);
        String sessionKey = service.doLogin(userId);
        Thread.sleep(1L);
        assertThat(service.isValidSession(sessionKey)).isFalse();
    }
}