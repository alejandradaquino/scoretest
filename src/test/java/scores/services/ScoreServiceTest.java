package scores.services;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import scores.model.Score;

import java.util.List;

import static java.util.Arrays.stream;

public class ScoreServiceTest {
    ScoreService service;

    @Before
    public void setUp() {
        service = new ScoreService(5);
    }

    @Test
    public void getHighestScores_noScoresRegistered_returnsEmptyList() {
        List<Score> highestByLevel = service.getHighestByLevel(1L);
        Assertions.assertThat(highestByLevel).isEmpty();
    }

    @Test
    public void getHighestScores_lessThanTenRegistered_returnsAllScoresRegistered() {
        long levelId = 233L;
        Score score1 = new Score(122L, levelId, 1L);
        Score score2 = new Score(2L, levelId, 124L);
        Score score3 = new Score(722L, levelId, 123L);
        Score score4 = new Score(1988L, levelId, 13L);
        service.registerScore(score1);
        service.registerScore(score2);
        service.registerScore(score3);
        service.registerScore(score4);

        List<Score> highestByLevel = service.getHighestByLevel(levelId);
        Assertions.assertThat(highestByLevel).containsExactly(score1, score2, score3, score4);
    }

    @Test
    public void getHighestScores_moreThanTenRegistered_returnsTenHighestScoresRegistered() {
        long levelId = 233L;
        Long[] scores = new Long[]{10L, 22L, 11334556L, 11L, 4356L, 1L, 23423235L, 1000L, 27L, 10555551L, 1156L, 343656L, 101567L};

        stream(scores).map(s -> new Score(1L, levelId, s)).forEach(service::registerScore);

        Assertions.assertThat(service.getHighestByLevel(levelId).stream().map(Score::getScore)).containsOnly(11334556L, 101567L, 343656L, 23423235L, 10555551L);
    }

    @Test
    public void getHighestScores_differentLevelsId_areNotMixed() {
        long levelId1 = 233L;
        long levelId2 = 231L;
        Score score1Level1 = new Score(122L, levelId1, 1L);
        Score score2level1 = new Score(2L, levelId1, 124L);
        Long[] scoresLevel2 = new Long[]{10L, 22L, 11334556L, 11L, 4356L, 1L, 23423235L, 1000L, 27L, 10555551L, 1156L, 343656L, 101567L};

        stream(scoresLevel2).map(s -> new Score(1L, levelId2, s)).forEach(service::registerScore);
        service.registerScore(score1Level1);
        service.registerScore(score2level1);

        Assertions.assertThat(service.getHighestByLevel(levelId2).stream().map(Score::getScore)).containsOnly(11334556L, 101567L, 343656L, 23423235L, 10555551L);
        Assertions.assertThat(service.getHighestByLevel(levelId1)).containsExactly(score1Level1, score2level1);
    }
}