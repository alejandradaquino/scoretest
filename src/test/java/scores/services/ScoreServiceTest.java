package scores.services;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import scores.model.Score;

import java.util.List;

import static org.junit.Assert.*;

public class ScoreServiceTest {
    ScoreService service = new ScoreService();

    @Test
    public void getHighestScores_noScoresRegistered_returnsEmptyList(){
        List<Score> highestByLevel = service.getHighestByLevel(1L);
        Assertions.assertThat(highestByLevel).isEmpty();
    }

}