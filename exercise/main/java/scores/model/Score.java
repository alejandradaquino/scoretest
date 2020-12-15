package scores.model;

public class Score {
    private Long userId;
    private Long levelId;
    private Long score;

    public Score(Long userId, Long levelId, Long score) {
        this.userId = userId;
        this.levelId = levelId;
        this.score = score;
    }

    public Long getScore() {
        return score;
    }

    public Long getLevelId() {
        return levelId;
    }

    public Long getUserId() {
        return userId;
    }
}
