package scores.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import org.mockito.Mockito;

import java.net.URI;
import java.net.URISyntaxException;

public class BaseHandlerTest {
    protected HttpExchange exchangeFor(String uri, String method) {
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
