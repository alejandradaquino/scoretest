package scores.services;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionServiceTest {

    private final long userId = 1223L;
    SessionService service = new SessionService(10L);

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
        SessionService service = new SessionService(0L);
        String sessionKey = service.doLogin(userId);
        Thread.sleep(1L);
        assertThat(service.isValidSession(sessionKey)).isFalse();
    }
}