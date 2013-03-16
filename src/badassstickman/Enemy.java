package badassstickman;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Stickman {
	private boolean flipped;
	private int movementSpeed = 5;
	
	public Enemy(Vector2 position, Texture image, boolean flipped) {
		super(position, 5, image);
		this.flipped = flipped;
		AnimationFrame[] frames = new AnimationFrame[1];
		frames[0] = new AnimationFrame(0, 4, 128, 128);
		TextureRegion[] regions = new TextureRegion[1];
		regions[0] = getTexturRegionForFrame(frames[0], flipped);
		addAnimation("idle", 0, regions, false);
	}
	
	public static Enemy spawn(Texture image) {
		int y = 32;
		int x = 0;
		boolean leftOfPlayer = Math.random() < 0.5;
		if(leftOfPlayer) {
			x = MathUtils.random(350);
		}
		else {
			x = MathUtils.random(450, 750);
		}
		return new Enemy(new Vector2(x-60, y), image, !leftOfPlayer);
	}
	
	public void drawHealth() {
		ShapeRenderer renderer = getRenderer();
		renderer.begin(ShapeType.FilledRectangle);
		Vector2 position = getPosition();
		float width = getBoundingBox().width;
		float percent = ((float) getHealth() / (float) getMaxHealth());
		width *= percent;
		if(percent >= 0.5) {
			renderer.setColor(Color.GREEN);
		}
		else if(percent < 0.5 && percent > 0.25) {
			renderer.setColor(Color.YELLOW);
		}
		else {
			renderer.setColor(Color.RED);
		}
		
		renderer.filledRect(position.x + getBoundingBox().x, position.y + getBoundingBox().height + 3, width, 6);
		renderer.end();
	}
	
	public boolean getFlipped() {
		return this.flipped;
	}
	
	public void render(SpriteBatch batch) {
		super.render(batch, false);
	}
	
	public void update(float x, float y) {
		float posX = getWorldBoundingBox().x;
		float upX = posX;
		if(posX < x) {
			upX += movementSpeed;
			if(upX > x) {
				upX = x;
			}
		}
		else if(posX > x) {
			upX -= movementSpeed;
			if(upX < x) {
				upX = x;
			}
		}
		
		posX = upX;
		Vector2 position = getPosition();
		position.x = posX - getBoundingBox().x;
		setPosition(position);
	}
}
