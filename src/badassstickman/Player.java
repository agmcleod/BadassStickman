package badassstickman;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


public class Player extends Stickman {
	public Player(Texture image) {
		super(new Vector2(Gdx.graphics.getWidth() / 2 - 60, 32), 20, image);
		AnimationFrame[] frames = new AnimationFrame[1];
		frames[0] = new AnimationFrame(0, 0, 128, 128);
		TextureRegion[] regions = new TextureRegion[1];
		regions[0] = getTexturRegionForFrame(frames[0]);
		addAnimation("idle", 0, regions, false);
	}
}
