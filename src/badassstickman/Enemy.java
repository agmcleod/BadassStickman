package badassstickman;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Enemy extends Stickman {
	private boolean flipped;
	private int movementSpeed = 5;
	private boolean isMoving = true;
	
	public Enemy(Vector2 position, Texture image, boolean flipped) {
		super(position, 5, image);
		setAttackSpeed(1.2f);
		this.flipped = flipped;
		setFacingRight(!flipped);
		AnimationFrame[] frames = new AnimationFrame[1];
		frames[0] = new AnimationFrame(0, 4, 128, 128);
		TextureRegion[] regions = new TextureRegion[1];
		regions[0] = getTextureRegionForFrame(frames[0], flipped);
		addAnimation("idle", 0, regions, false);
	}
	
	public static Enemy spawn(Texture image) {
		int y = 32;
		int x = 0;
		int midPoint = Gdx.graphics.getWidth() / 2;
		boolean leftOfPlayer = Math.random() < 0.5;
		if(leftOfPlayer) {
			x = MathUtils.random(midPoint - 200);
		}
		else {
			x = MathUtils.random(midPoint + 150, 750);
		}
		return new Enemy(new Vector2(x-60, y), image, !leftOfPlayer);
	}
	
	public void attack(Player player) {
		float time = getStateTime();
		if(time > getAttackSpeed()) {
			float xCoord = 0;
			setStateTime(0);
			Rectangle box = getWorldBoundingBox();
			if(isFacingRight()) {
				xCoord = box.x + box.width + 10;
			}
			else {
				xCoord = box.x - 10;
			}
			if(player.getWorldBoundingBox().contains(xCoord, box.y + box.height / 2)) {
				player.setHealth(player.getHealth() - 1);
			}
		}
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
	
	public void update(float x, float y, Player player) {
		float posX = getWorldBoundingBox().x;
		float upX = posX;
		isMoving = true;
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
		else {
			isMoving = false;
		}
		
		if(isMoving) {
			posX = upX;
			Vector2 position = getPosition();
			position.x = posX - getBoundingBox().x;
			setPosition(position);
		}
		else {
			attack(player);
		}
	}
}
