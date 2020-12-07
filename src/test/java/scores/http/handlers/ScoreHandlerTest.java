package scores.http.handlers;


import org.junit.Test;
import org.mockito.Mockito;
import scores.http.readers.QueryParamsReader;
import scores.services.ScoreService;
import scores.services.SessionService;

import static org.assertj.core.api.Assertions.assertThat;

public class ScoreHandlerTest extends BaseHandlerTest {

    private SessionService sessionService = Mockito.mock(SessionService.class);
    private ScoreService scoreService = Mockito.mock(ScoreService.class);
    private final ScoreHandler handler = new ScoreHandler(new QueryParamsReader(), sessionService, scoreService);

    @Test
    public void canHandle_urlContainsScore_returnsTrue() {
        assertThat(handler.canHandle(exchangeFor("2332/score", "POST"))).isTrue();
    }

    @Test
    public void canHandle_disorderedPath_returnsFalse() {
        assertThat(handler.canHandle(exchangeFor("score/2332", "POST"))).isFalse();
    }

    @Test
    public void canHandle_urlContainsScoreOtherMethodThanPost_returnsFalse() {
        assertThat(handler.canHandle(exchangeFor("2332/score", "GET"))).isFalse();
    }

    @Test
    public void canHandle_urlDoesnotContainsScore_returnsFalse() {
        assertThat(handler.canHandle(exchangeFor("2332/otherstuff", "POST"))).isFalse();
    }

}