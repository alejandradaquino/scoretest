package scores.services;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import scores.http.exceptions.InvalidSessionException;
import scores.repositories.SessionRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionServiceTest {

    private final long userId = 1223L;
    private SessionRepository repository = new SessionRepository();
    SessionService service = new SessionService(6000L, repository);

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

    @Test
    public void getUserFrom_withValidSessionKey_returnsUserId() {
        String sessionKey = service.doLogin(2002L);
        assertThat(service.getUserFrom(sessionKey)).isEqualTo(2002L);
    }

    @Test
    public void getUserFrom_withExpiredSessionKey_throwsException() throws InterruptedException {
        SessionService service = new SessionService(0L, repository);
        String sessionKey = service.doLogin(userId);
        Thread.sleep(1L);
        Assertions.assertThatThrownBy(() -> service.getUserFrom(sessionKey)).isInstanceOf(InvalidSessionException.class);
    }
}