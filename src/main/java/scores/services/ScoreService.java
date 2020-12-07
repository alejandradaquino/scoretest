package scores.services;

import scores.model.Score;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ScoreService {

    private Map<Long, List<Score>> highestScoresByLevel = new ConcurrentHashMap<>();
    private Integer amountOfHigest;

    public ScoreService(Integer amountOfHigest) {
        this.amountOfHigest = amountOfHigest;
    }

    public void registerScore(Score score) {
        if (!highestScoresByLevel.containsKey(score.getLevelId())) {
            highestScoresByLevel.put(score.getLevelId(), new ArrayList<>());
        }
        List<Score> clonedList = new ArrayList(highestScoresByLevel.get(score.getLevelId()));

        Boolean changeMade;
        if (clonedList.size() < amountOfHigest) {
            clonedList.add(score);
            changeMade = true;
        } else {
            changeMade = makeReplacements(score, clonedList);
        }
        if (changeMade) {
            List<Score> sorted = clonedList.stream().sorted((s1, s2) -> s2.getScore().compareTo(s1.getScore())).collect(Collectors.toList());
            highestScoresByLevel.put(score.getLevelId(), sorted);
        }
    }

    public List<Score> getHighestByLevel(Long levelId) {
        return highestScoresByLevel.containsKey(levelId) ? highestScoresByLevel.get(levelId) : new ArrayList<>();
    }

    private Boolean makeReplacements(Score score, List<Score> clonedList) {
        Boolean changeMade = false;
        Score lowerScore = score;
        for (int i = 0; i < clonedList.size(); i++) {
            if (clonedList.get(i).getScore() < lowerScore.getScore()) {
                Score temp = clonedList.get(i);
                clonedList.remove(i);
                clonedList.add(i, lowerScore);
                lowerScore = temp;
                changeMade = true;
            }
        }
        return changeMade;
    }
}
