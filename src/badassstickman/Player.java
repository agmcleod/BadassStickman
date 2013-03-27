package badassstickman;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class Player extends Stickman {
	public Player(Texture image) {
		super(new Vector2(Gdx.graphics.getWidth() / 2 - 60, 32), 20, image);
		setAttackSpeed(0.2f);
		AnimationFrame[] frames = new AnimationFrame[1];
		frames[0] = new AnimationFrame(0, 0, 128, 128);
		TextureRegion[] regions = new TextureRegion[1];
		regions[0] = getTextureRegionForFrame(frames[0], false);
		addAnimation("idle", 0, regions, false);
		
		frames = new AnimationFrame[7];
		for(int i = 0; i < frames.length; i++) {
			frames[i] = new AnimationFrame(i + 1, 0, 128, 128);
		}
		regions = new TextureRegion[13];
		
		for(int i = 0; i < frames.length; i++) {
			regions[i] = getTextureRegionForFrame(frames[i], false);
		}
		
		int index = 7;
		for(int i = frames.length - 2; i > -1; i--) {
			regions[index] = getTextureRegionForFrame(frames[i], false);
			index++;
		}
		addAnimation("attack", 0.02f, regions, false, true);
	}
	
	public void attack(Boss boss) {
		float time = getStateTime();
		if(time > getAttackSpeed()) {
			setCurrentAnimation("attack");
			float xCoord = 0;
			Rectangle box =  getWorldBoundingBox();
			if(isFacingRight()) {
				xCoord = box.x + box.width + 10;
			}
			else {
				xCoord = box.x - 10;
			}
			
		}
	}
	
	public void attack(Array<Enemy> enemies, Boss boss) {
		float time = getStateTime();
		if(time > getAttackSpeed()) {
			setCurrentAnimation("attack");
			float xCoord = 0;
			Rectangle box =  getWorldBoundingBox();
			if(isFacingRight()) {
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
			if(boss != null && boss.getWorldBoundingBox().contains(xCoord, box.y + box.height / 2)) {
				boss.setHealth(boss.getHealth() - 1);
			}
		}
		
	}
	
	public void setHealth(int health) {
		super.setHealth(health);
		if(this.getHealth() <= 0) {
			Gdx.app.exit();
		}
	}
}
