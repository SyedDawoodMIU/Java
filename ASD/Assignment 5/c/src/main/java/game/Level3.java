package game;

public class Level3 implements ILevel {

    @Override
    public int calculatePoints(int points) {
        return 3 * points;
    }

    @Override
    public ILevel checkLevelUpgrade(int totalPoints) {
        return this;
    }

    @Override
    public double getLevelNumber() {
        return 3;
    }
}
