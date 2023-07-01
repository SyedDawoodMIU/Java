package game;

public class Application {
	public static void main(String[] args) {
		ILevel initialLevel = new Level1();
		Game game = new Game(initialLevel);
		game.play();
		game.play();
		game.play();
		game.play();
		game.play();
	}
}
