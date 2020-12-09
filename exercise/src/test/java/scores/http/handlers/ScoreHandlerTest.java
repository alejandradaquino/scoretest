package scores.http.handlers;


import com.sun.net.httpserver.HttpExchange;
import org.junit.Test;
import org.mockito.Mockito;
import scores.http.readers.QueryParamsReader;
import scores.model.Score;
import scores.services.ScoreService;
import scores.services.SessionService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class ScoreHandlerTest extends BaseHandlerTest {

    private SessionService sessionService = Mockito.mock(SessionService.class);
    private ScoreService scoreService = Mockito.mock(ScoreService.class);
    private final ScoreHandler handler = new ScoreHandler(new QueryParamsReader(), sessionService, scoreService);
    private Score score;

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

    @Test
    public void doHandle_registerScore() {
        when(sessionService.getUserFrom(anyString())).thenReturn(773L);
        HttpExchange exchange = exchangeFor("2332/highscorelist?sessionkey=sdfeefsdfarf", "GET", "1234");
        Mockito.doAnswer(i->{
            score = i.getArgumentAt(0, Score.class);
            return null;
        }).when(scoreService).registerScore(Mockito.any(Score.class));

        handler.doHandle(exchange);


        assertThat(score).isNotNull();
        assertThat(score.getScore()).isEqualTo(1234L);
        assertThat(score.getUserId()).isEqualTo(773L);
        assertThat(score.getLevelId()).isEqualTo(2332L);
     }

}