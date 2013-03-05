package badassstickman;
import badassstickman.Animation.AnimationEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;


public class Stickman {
	private ObjectMap<String, Animation> animations;
	private Rectangle boundingBox;
	private String currentAnimation;
	private int health;
	private Texture image;
	private Vector2 position;
	private ShapeRenderer renderer;
	private float stateTime = 0;
	private boolean facingRight = true;
	
	public Stickman(Vector2 position, int health, Texture image) {
		this.setPosition(position);
		this.setHealth(health);
		this.setImage(image);
		
		animations = new ObjectMap<String, Animation>();
		boundingBox = new Rectangle(40, 0, 46, 128);
		renderer = new ShapeRenderer();
	}
	
	public void addAnimation(String name, float frameDuration, TextureRegion[] keyFrames, boolean loop) {
		animations.put(name, new Animation(frameDuration, keyFrames, loop));
		if(animations.size == 1) {
			currentAnimation = name;
		}
	}
	
	public void animationCallback() {
		setCurrentAnimation("idle");
	}
	
	public void debug() {
		Rectangle r = getWorldBoundingBox();
		renderer.begin(ShapeType.Rectangle);
		renderer.setColor(Color.WHITE);
		renderer.rect(r.x, r.y, r.width, r.height);
		renderer.end();
	}
	
	public void dispose() {
		renderer.dispose();
	}
	
	public Rectangle getBoundingBox() {
		return this.boundingBox;
	}
	
	public Animation getCurrentAnimation() {
		return animations.get(currentAnimation);
	}

	public int getHealth() {
		return health;
	}
	
	public Texture getImage() {
		return image;
	}
	
	public Vector2 getPosition() {
		return position;
	}

	public float getStateTime() {
		return stateTime;
	}

	public TextureRegion getTexturRegionForFrame(AnimationFrame frame) {
		return new TextureRegion(image, frame.getXCoordinate(), frame.getYCoordinate(), frame.width, frame.height);
	}
	
	public Rectangle getWorldBoundingBox() {
		Rectangle bounding = getBoundingBox();
		return new Rectangle(bounding.x + position.x, bounding.y + position.y, bounding.width, bounding.height);
	}

	public void onAnimationEnded(AnimationEvent e) {
		animationCallback();
	}

	public void render(SpriteBatch batch, boolean flipped) {
		TextureRegion currentFrame;
		float t = Gdx.graphics.getDeltaTime();
		stateTime += t;
		Animation currentAnimation = getCurrentAnimation();
		currentFrame = currentAnimation.getKeyFrame(stateTime);
		currentFrame.flip(flipped, false);
		batch.draw(currentFrame, position.x, position.y);
	}
	
	public void setCurrentAnimation(String animation) {
		this.currentAnimation = animation;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}

	public void setImage(Texture image) {
		this.image = image;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public boolean isFacingRight() {
		return facingRight;
	}

	public void setFacingRight(boolean facingRight) {
		this.facingRight = facingRight;
	}
}
