package badassstickman;
import badassstickman.Animation.AnimationEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;


public class Stickman {
	private int health;
	private Texture image;
	private Vector2 position;
	private float stateTime = 0;
	private String currentAnimation;
	private ObjectMap<String, Animation> animations;
	private Rectangle boundingBox;
	
	public Stickman(Vector2 position, int health, Texture image) {
		this.setPosition(position);
		this.setHealth(health);
		this.setImage(image);
		
		animations = new ObjectMap<String, Animation>();
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
	
	public Animation getCurrentAnimation() {
		return animations.get(currentAnimation);
	}

	public int getHealth() {
		return health;
	}
	
	public void onAnimationEnded(AnimationEvent e) {
		animationCallback();
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Texture getImage() {
		return image;
	}
	
	public TextureRegion getTexturRegionForFrame(AnimationFrame frame) {
		return new TextureRegion(image, frame.getXCoordinate(), frame.getYCoordinate(), frame.width, frame.height);
	}

	public void setImage(Texture image) {
		this.image = image;
	}

	public Vector2 getPosition() {
		return position;
	}
	
	public void render(SpriteBatch batch) {
		TextureRegion currentFrame;
		float t = Gdx.graphics.getDeltaTime();
		stateTime += t;
		Animation currentAnimation = getCurrentAnimation();
		currentFrame = currentAnimation.getKeyFrame(stateTime);
		batch.draw(currentFrame, position.x, position.y);
	}
	
	public void setCurrentAnimation(String animation) {
		this.currentAnimation = animation;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
}
