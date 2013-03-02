package badassstickman;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class GameScreen implements Screen {
	
	private Texture background;
	private SpriteBatch batch;
	private BadassStickman game;
	private Texture stickman;
	private TextureRegion healthBar;
	private Player player;
	
	static final int TILE_SIZE = 32; 
	
	public GameScreen(BadassStickman game) {
		this.game = game;
	}
	
	public void checkPlayerControls() {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player.setFlipped(true);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			player.setFlipped(false);
		}
	}

	@Override
	public void dispose() {
		background.dispose();
		batch.dispose();
		stickman.dispose();
		player.dispose();
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void render(float delta) {
		update();
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(healthBar, TILE_SIZE, Gdx.graphics.getHeight() - TILE_SIZE * 2);
		player.render(batch);
		batch.end();
		
		player.debug();
	}

	@Override
	public void resize(int arg0, int arg1) {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void show() {
		background = new Texture(Gdx.files.internal("assets/basm_background.png"));
		stickman = new Texture(Gdx.files.internal("assets/stickman.png"));
		batch = new SpriteBatch();
		healthBar = new TextureRegion(stickman, 0, TILE_SIZE * 28, TILE_SIZE * 8, TILE_SIZE);
		player = new Player(stickman);
	}
	
	public void update() {
		checkPlayerControls();
	}
}
