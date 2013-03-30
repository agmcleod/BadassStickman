package badassstickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartScreen implements Screen {
	
	private BitmapFont font;
	private BadassStickman game;
	private SpriteBatch batch;
	
	public StartScreen(BadassStickman game) {
		this.game = game;
	}

	@Override
	public void dispose() {
		font.dispose();
		batch.dispose();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float arg0) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		font.draw(batch, "To play, use the arrow or A/D keys to change your direction.", 50, 700);
		font.draw(batch, "To attack enemies as they come in range, simply click the mouse.", 50, 665);
		font.draw(batch, "You will attack in the same direction that you face.", 50, 630);
		font.draw(batch, "Press enter to continue", 50, 550);
		batch.end();
		
		if(Gdx.input.isKeyPressed(Keys.ENTER)) {
			game.setScreen(game.getGameScreen());
		}
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		font = new BitmapFont(Gdx.files.internal("assets/font.fnt"), Gdx.files.internal("assets/font.png"), false);
		batch = new SpriteBatch();
	}

}
