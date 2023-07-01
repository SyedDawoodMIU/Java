package game;

public interface ILevel {
    int calculatePoints(int points);

    ILevel checkLevelUpgrade(int totalPoints);

    double getLevelNumber();
}
