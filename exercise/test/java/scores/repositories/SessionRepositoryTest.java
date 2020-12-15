package scores.repositories;

import org.junit.Test;
import scores.model.Session;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionRepositoryTest {
    SessionRepository repository = new SessionRepository();

    @Test
    public void save_thenFindSession_returnsTheSession() {
        Session session = new Session(233L, "SOMEKEY", Instant.now().plus(10, ChronoUnit.SECONDS).toEpochMilli());
        repository.save(session);

        Optional<Session> maybeSession = repository.find("SOMEKEY");

        assertThat(maybeSession).isPresent();
        assertThat(maybeSession.get()).isEqualTo(session);

    }

    @Test
    public void save_sessionIsExpired_doesNotReturnsSession() {
        Session session = new Session(233L, "SOMEKEY", Instant.now().plus(-1, ChronoUnit.SECONDS).toEpochMilli());
        repository.save(session);

        Optional<Session> maybeSession = repository.find("SOMEKEY");

        assertThat(maybeSession).isNotPresent();
    }

}