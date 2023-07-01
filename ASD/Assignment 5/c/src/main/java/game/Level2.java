package game;

public class Level2 implements ILevel {
    private static final int POINTS_THRESHOLD = 15;

    @Override
    public int calculatePoints(int points) {
        return 2 * points;
    }

    @Override
    public ILevel checkLevelUpgrade(int totalPoints) {
        if (totalPoints > POINTS_THRESHOLD) {
            return new Level2_5();
        }
        return this;
    }

    @Override
    public double getLevelNumber() {
        return 2;
    }
}
