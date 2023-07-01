package game;

public class Level1 implements ILevel {
    private static final int BONUS_POINTS = 1;
    private static final int POINTS_THRESHOLD = 10;

    @Override
    public int calculatePoints(int points) {
        return points + BONUS_POINTS;
    }

    @Override
    public ILevel checkLevelUpgrade(int totalPoints) {
        if (totalPoints > POINTS_THRESHOLD) {
            return new Level2();
        }
        return this;
    }

    @Override
    public double getLevelNumber() {
        return 1;
    }
}
