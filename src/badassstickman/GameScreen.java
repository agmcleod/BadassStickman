package badassstickman;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


public class GameScreen implements Screen {
	
	private Texture background;
	private SpriteBatch batch;
	private Boss boss;
	private Array<Enemy> enemies;
	private boolean flipped = false;
	private BadassStickman game;
	private float gameTimer = 0;
	private Player player;
	private ShapeRenderer renderer;
	private float spawnTimer = 0;
	private int spawnAmount = 0;
	private Texture stickman;
	
	static final int TILE_SIZE = 32; 
	
	public GameScreen(BadassStickman game) {
		this.game = game;
	}

	public void checkPlayerControls() {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
			if(player.isFacingRight()) {
				flipped = true;
				player.setFacingRight(false);
			}
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
			if(!player.isFacingRight()) {
				flipped = true;
				player.setFacingRight(true);
			}
		}
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			player.attack(enemies, boss);
		}
	}

	@Override
	public void dispose() {
		background.dispose();
		batch.dispose();
		stickman.dispose();
		player.dispose();
		Iterator<Enemy> it = enemies.iterator();
		renderer.dispose();
		while(it.hasNext()) {
			it.next().dispose();
		}
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
		player.render(batch, flipped);
		if(boss != null) {
			boss.render(batch, flipped);
		}
		flipped = false;
		
		Iterator<Enemy> it = enemies.iterator();
		while(it.hasNext()) {
			it.next().render(batch);
		}
		batch.end();
		
		renderer.begin(ShapeType.FilledRectangle);
		float percent = (player.getHealth() / player.getMaxHealth());
		if(percent >= 0.5) {
			renderer.setColor(Color.GREEN);
		}
		else if(percent < 0.5 && percent > 0.25) {
			renderer.setColor(Color.YELLOW);
		}
		else {
			renderer.setColor(Color.RED);
		}
		renderer.filledRect(TILE_SIZE, Gdx.graphics.getHeight() - TILE_SIZE * 2, 256f * percent, 32);
		renderer.end();
		
		it = enemies.iterator();
		while(it.hasNext()) {
			Enemy e = it.next();
			e.drawHealth();
		}
		if(boss != null) {
			boss.drawHealth();
		}
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
		player = new Player(stickman);
		enemies = new Array<Enemy>();
		enemies.add(Enemy.spawn(stickman));
		renderer = new ShapeRenderer();
		boss = null;
	}
	
	public void update() {
		checkPlayerControls();
		Iterator<Enemy> it = enemies.iterator();
		Rectangle r = player.getWorldBoundingBox();
		while(it.hasNext()) {
			Enemy e = it.next();
			if(e.getFlipped()) {
				e.update(r.x + player.getBoundingBox().width, r.y, player);
			}
			else {
				e.update(r.x - e.getBoundingBox().width, r.y, player);
			}
			if(e.getHealth() == 0) {
				it.remove();
			}
		}
		spawnTimer += Gdx.graphics.getDeltaTime();
		gameTimer += Gdx.graphics.getDeltaTime();
		if(spawnTimer > 3 && spawnAmount < 7) {
			spawnTimer = 0;
			enemies.add(Enemy.spawn(stickman));
			spawnAmount++;
		}
		else if(enemies.size == 0 && boss == null && spawnAmount >= 7) {
			boss = new Boss(stickman);
		}
		
		// player regen
		if(gameTimer % 2 == 0) {
			player.setHealth(player.getHealth() + 2);
			if(player.getHealth() > player.getMaxHealth()) {
				player.setHealth(player.getMaxHealth());
			}
		}
		
		if(boss != null) {
			boss.update(r.x - boss.getBoundingBox().width, r.y, player);
			if(boss.getHealth() <= 0) {
				game.setScreen(game.getEndScreen());
			}
		}
		
		// check if player dead
		if(player.getHealth() <= 0) {
			EndScreen screen = game.getEndScreen();
			screen.setWon(false);
			game.setScreen(screen);
		}
	}
}
