package scores.http.handlers;


import org.junit.Test;
import scores.http.readers.QueryParamsReader;

import static org.assertj.core.api.Assertions.assertThat;

public class ScoreHandlerTest extends BaseHandlerTest {

    private final ScoreHandler handler = new ScoreHandler(new QueryParamsReader());

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