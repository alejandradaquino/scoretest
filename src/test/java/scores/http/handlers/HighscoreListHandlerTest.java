package scores.http.handlers;


import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.mockito.Mockito;
import scores.model.Score;
import scores.services.ScoreService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

public class HighscoreListHandlerTest extends BaseHandlerTest {

    private ScoreService scoreService = Mockito.mock(ScoreService.class);
    private final HighscoreListHandler handler = new HighscoreListHandler(scoreService);

    @Test
    public void canHandle_urlContainsHighscoreList_returnsTrue() {
        assertThat(handler.canHandle(exchangeFor("2332/highscorelist", "GET"))).isTrue();
    }

    @Test
    public void canHandle_disorderedPath_returnsFalse() {
        assertThat(handler.canHandle(exchangeFor("highscorelist/2332", "GET"))).isFalse();
    }

    @Test
    public void canHandle_urlContainsHighscoreListOtherMethodThanGet_returnsFalse() {
        assertThat(handler.canHandle(exchangeFor("2332/highscorelist", "POST"))).isFalse();
    }

    @Test
    public void canHandle_urlDoesnotContainsHighscoreList_returnsFalse() {
        assertThat(handler.canHandle(exchangeFor("2332/otherstuff", "GET"))).isFalse();
    }

    @Test
    public void doHandle_levelWithNoScores_returnsEmptyString(){
        when(scoreService.getHighestByLevel(anyLong())).thenReturn(newArrayList());
        String response =handler.doHandle(exchangeFor("2332/highscorelist", "GET"));
        assertThat(response).isEmpty();
    }

    @Test
    public void doHandle_levelWithTwoScores_returnsScoresMapedToCSV(){
        Score score1 = new Score(122L, 2332L, 150L);
        Score score2 = new Score(2L, 2332L, 124L);
        when(scoreService.getHighestByLevel(anyLong())).thenReturn(newArrayList(score1,score2));
        String response =handler.doHandle(exchangeFor("2332/highscorelist", "GET"));
        assertThat(response).isEqualTo("122=150,2=124");
    }

}