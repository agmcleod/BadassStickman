package badassstickman;
import java.util.Iterator;

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


public class Stickman implements Animation.AnimationEventListener {
	private ObjectMap<String, Animation> animations;
	private float attackSpeed;
	private Rectangle boundingBox;
	private String currentAnimation;
	private boolean facingRight = true;
	private float health;
	private Texture image;
	private float lastAttack = 0;
	private float maxHealth;
	private Vector2 position;
	private ShapeRenderer renderer;
	private float stateTime = 0;
	
	
	public Stickman(Vector2 position, int health, Texture image) {
		this.setPosition(position);
		this.setHealth(health);
		this.setMaxHealth(health);
		this.setImage(image);
		
		animations = new ObjectMap<String, Animation>();
		boundingBox = new Rectangle(40, 0, 46, 128);
		renderer = new ShapeRenderer();
	}
	
	public void addAnimation(String name, float frameDuration, TextureRegion[] keyFrames, boolean loop) {
		Animation animation = new Animation(frameDuration, keyFrames, loop);
		animation.addEventListener(this);
		animations.put(name, animation);
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
	
	public float getAttackSpeed() {
		return attackSpeed;
	}
	
	public Rectangle getBoundingBox() {
		return this.boundingBox;
	}

	public Animation getCurrentAnimation() {
		return animations.get(currentAnimation);
	}
	
	public float getHealth() {
		return health;
	}
	
	public Texture getImage() {
		return image;
	}
	
	public float getMaxHealth() {
		return maxHealth;
	}

	public Vector2 getPosition() {
		return position;
	}

	public ShapeRenderer getRenderer() {
		return renderer;
	}
	
	public float getStateTime() {
		return stateTime;
	}

	public TextureRegion getTextureRegionForFrame(AnimationFrame frame, boolean flipped) {
		TextureRegion region = new TextureRegion(image, frame.getXCoordinate(), frame.getYCoordinate(), frame.width, frame.height);
		region.flip(flipped, false);
		return region;
	}

	public Rectangle getWorldBoundingBox() {
		Rectangle bounding = getBoundingBox();
		return new Rectangle(bounding.x + position.x, bounding.y + position.y, bounding.width, bounding.height);
	}
	
	public boolean isFacingRight() {
		return facingRight;
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

	public void setAttackSpeed(float attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public void setCurrentAnimation(String animation) {
		this.currentAnimation = animation;
	}

	public void setFacingRight(boolean facingRight) {
		this.facingRight = facingRight;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public void setImage(Texture image) {
		this.image = image;
	}

	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public float getLastAttack() {
		return lastAttack;
	}

	public void setLastAttack(float lastAttack) {
		this.lastAttack = lastAttack;
	}
}
