package game;

import java.util.Random;

public class Game {
	private int totalPoints = 0;
	private int level = 1;

	public void play() {
		Random random = new Random();
		addPoints(random.nextInt(7));
		System.out.println("points="+totalPoints+" level="+level);
	}

	public int addPoints(int newPoints) {
		if (level == 1) {
			totalPoints = totalPoints + newPoints;
			if (totalPoints > 10) { // move to level 2
				level = 2;
				totalPoints = totalPoints + 1;//add 1 bonus point
			}
		} else if (level == 2) {
			totalPoints = totalPoints + 2 * newPoints;
			if (totalPoints > 20) { // move to level 3
				level = 3;
				totalPoints = totalPoints + 2; //add 2 bonus points
			}
		} else if (level == 3) {
			totalPoints = totalPoints + 3 * newPoints;
		}

		return totalPoints;
	}

}
