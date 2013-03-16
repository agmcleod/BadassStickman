package badassstickman;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


public class GameScreen implements Screen {
	
	private Texture background;
	private SpriteBatch batch;
	private Array<Enemy> enemies;
	private boolean flipped = false;
	private BadassStickman game;
	private TextureRegion healthBar;
	private Player player;
	private Texture stickman;
	
	static final int TILE_SIZE = 32; 
	
	public GameScreen(BadassStickman game) {
		this.game = game;
	}
	
	public void attack() {
		float time = player.getStateTime();
		if(time - player.getLastAttack() > player.getAttackSpeed()) {
			player.setLastAttack(time);
			float xCoord = 0;
			Rectangle box =  player.getWorldBoundingBox();
			if(player.isFacingRight()) {
				xCoord = box.x + box.width + 10;
			}
			else {
				xCoord = box.x - 10;
			}
			Iterator<Enemy> it = enemies.iterator();
			while(it.hasNext()) {
				Enemy e = it.next();
				if(e.getWorldBoundingBox().contains(xCoord, box.y + box.height / 2)) {
					e.setHealth(e.getHealth() - 1);
				}
			}
		}
	}
	
	public void checkPlayerControls() {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if(player.isFacingRight()) {
				flipped = true;
				player.setFacingRight(false);
			}
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			if(!player.isFacingRight()) {
				flipped = true;
				player.setFacingRight(true);
			}
		}
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			attack();
		}
	}

	@Override
	public void dispose() {
		background.dispose();
		batch.dispose();
		stickman.dispose();
		player.dispose();
		Iterator<Enemy> it = enemies.iterator();
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
		batch.draw(healthBar, TILE_SIZE, Gdx.graphics.getHeight() - TILE_SIZE * 2);
		player.render(batch, flipped);
		flipped = false;
		
		Iterator<Enemy> it = enemies.iterator();
		while(it.hasNext()) {
			it.next().render(batch);
		}
		batch.end();
		
		it = enemies.iterator();
		while(it.hasNext()) {
			Enemy e = it.next();
			e.drawHealth();
			e.debug();
		}
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
		enemies = new Array<Enemy>();
		enemies.add(Enemy.spawn(stickman));
	}
	
	public void update() {
		checkPlayerControls();
		Iterator<Enemy> it = enemies.iterator();
		Rectangle r = player.getWorldBoundingBox();
		while(it.hasNext()) {
			Enemy e = it.next();
			if(e.getFlipped()) {
				e.update(r.x + player.getBoundingBox().width, r.y);
			}
			else {
				e.update(r.x - e.getBoundingBox().width, r.y);
			}
			
		}
	}
}
