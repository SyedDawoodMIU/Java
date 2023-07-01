package game;

import java.util.Random;

public class Game {
	private int totalPoints = 0;
	private ILevel currentLevel;

	public Game(ILevel initialLevel) {
		this.currentLevel = initialLevel;
	}

	public void play() {
		Random random = new Random();
		int points = random.nextInt(7);
		totalPoints += currentLevel.calculatePoints(points);
		currentLevel = currentLevel.checkLevelUpgrade(totalPoints);
		System.out.println("points=" + totalPoints + " level=" + currentLevel.getLevelNumber());
	}
}
