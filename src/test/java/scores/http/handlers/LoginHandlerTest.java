package scores.http.handlers;


import com.sun.net.httpserver.HttpExchange;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.net.URISyntaxException;

public class LoginHandlerTest {

    private final LoginHandler loginHandler = new LoginHandler();

    @Test
    public void canHandle_urlContainsLogin_returnsTrue() {

        Assertions.assertThat(loginHandler.canHandle(exchangeForUriAndMethod("2332/login", "GET"))).isTrue();
    }

    @Test
    public void canHandle_urlContainsLoginOtherMethodThanGet_returnsFalse() {
        Assertions.assertThat(loginHandler.canHandle(exchangeForUriAndMethod("2332/login", "POST"))).isFalse();
    }

    @Test
    public void canHandle_urlDoesnotContainsLogin_returnsFalse() {
        Assertions.assertThat(loginHandler.canHandle(exchangeForUriAndMethod("2332/otherstuff", "GET"))).isFalse();
    }

    private HttpExchange exchangeForUriAndMethod(String uri, String method) {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);

        URI requestURI = null;
        try {
            requestURI = new URI(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Mockito.when(exchange.getRequestURI()).thenReturn(requestURI);
        Mockito.when(exchange.getRequestMethod()).thenReturn(method);
        return exchange;
    }

}