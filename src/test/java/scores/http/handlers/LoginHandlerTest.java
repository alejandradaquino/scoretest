package scores.http.handlers;


import org.junit.Test;
import org.mockito.Mockito;
import scores.services.SessionService;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginHandlerTest extends BaseHandlerTest {

    private SessionService sessionService = Mockito.mock(SessionService.class);
    private final LoginHandler handler = new LoginHandler(sessionService);

    @Test
    public void canHandle_urlContainsLogin_returnsTrue() {
        assertThat(handler.canHandle(exchangeFor("2332/login", "GET"))).isTrue();
    }

    @Test
    public void canHandle_disorderedPath_returnsFalse() {
        assertThat(handler.canHandle(exchangeFor("login/2332", "GET"))).isFalse();
    }

    @Test
    public void canHandle_urlContainsLoginOtherMethodThanGet_returnsFalse() {
        assertThat(handler.canHandle(exchangeFor("2332/login", "POST"))).isFalse();
    }

    @Test
    public void canHandle_urlDoesnotContainsLogin_returnsFalse() {
        assertThat(handler.canHandle(exchangeFor("2332/otherstuff", "GET"))).isFalse();
    }

}