package badassstickman;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class BadassStickman extends Game {
	
	private EndScreen endScreen;
	private GameScreen gameScreen;
	private StartScreen startScreen;

	@Override
	public void create() {
		endScreen = new EndScreen();
		gameScreen = new GameScreen(this);
		startScreen = new StartScreen(this);
		setScreen(startScreen);
	}
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = 1024;
		cfg.height = 768;
		cfg.useGL20 = true;
		cfg.title = "Badass Stickman";
		new LwjglApplication(new BadassStickman(), cfg);
	}
	
	public EndScreen getEndScreen() {
		return endScreen;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}

}
