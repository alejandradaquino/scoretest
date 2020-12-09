package scores.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

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

    protected HttpExchange exchangeFor(String uri, String method, String body) {
        HttpExchange exchange = exchangeFor(uri,method);
        Mockito.when(exchange.getRequestBody())
                .thenReturn(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)));
        return exchange;
    }
}
