package badassstickman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	
	public void render(SpriteBatch batch) {
		super.render(batch, false);
	}
	
	public void update(float x, float y) {
		Vector2 position = getPosition();
		float upX = position.x;
		if(position.x < x) {
			upX += movementSpeed;
			if(upX > x) {
				upX = x;
			}
		}
		else if(position.x > x) {
			upX -= movementSpeed;
			if(upX < x) {
				upX = x;
			}
		}
		
		position.x = upX;
		System.out.println(position.x);
		setPosition(position);
	}
}
