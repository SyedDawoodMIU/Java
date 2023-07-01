package game;

public class Level2_5 implements ILevel {
    private static final int POINTS_THRESHOLD = 20;

    @Override
    public int calculatePoints(int points) {
        return points + 1;
    }

    @Override
    public ILevel checkLevelUpgrade(int totalPoints) {
        if (totalPoints > POINTS_THRESHOLD) {
            return new Level3();
        }
        return this;
    }

    @Override
    public double getLevelNumber() {
        return 2.5;
    }
}
