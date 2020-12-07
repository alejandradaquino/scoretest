package scores.http.handlers;


import org.assertj.core.api.Assertions;
import org.junit.Test;

public class HighscoreListHandlerTest extends BaseHandlerTest {

    private final HighscoreListHandler handler = new HighscoreListHandler();

    @Test
    public void canHandle_urlContainsHighscoreList_returnsTrue() {
        Assertions.assertThat(handler.canHandle(exchangeFor("2332/highscorelist", "GET"))).isTrue();
    }

    @Test
    public void canHandle_disorderedPath_returnsFalse() {
        Assertions.assertThat(handler.canHandle(exchangeFor("highscorelist/2332", "GET"))).isFalse();
    }

    @Test
    public void canHandle_urlContainsHighscoreListOtherMethodThanGet_returnsFalse() {
        Assertions.assertThat(handler.canHandle(exchangeFor("2332/highscorelist", "POST"))).isFalse();
    }

    @Test
    public void canHandle_urlDoesnotContainsHighscoreList_returnsFalse() {
        Assertions.assertThat(handler.canHandle(exchangeFor("2332/otherstuff", "GET"))).isFalse();
    }

}