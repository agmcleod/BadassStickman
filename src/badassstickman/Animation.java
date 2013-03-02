package badassstickman;
import java.util.EventObject;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
	public class AnimationEvent extends EventObject {  
        public AnimationEvent(Object source) {  
              super(source);  
        }  
    }  
    public interface AnimationEventListener {  
       public void onAnimationEnded(AnimationEvent e);  
    }
    
    final TextureRegion[] keyFrames;
    final float frameDuration;
    
    private Array<Animation.AnimationEventListener> _listeners;
      
    private boolean loop;
    
    public Animation (float frameDuration, TextureRegion[] keyFrames, boolean loop) {  
        this.frameDuration = frameDuration;  
        this.keyFrames = keyFrames;  
        _listeners = new Array<Animation.AnimationEventListener>();
        this.loop = loop;
    }
    
    public void addEventListener(Animation.AnimationEventListener listener) {
    	_listeners.add(listener);
    }
    
    public int getFrameNumber(float stateTime) {
    	return (int) (stateTime / frameDuration);
    }
    
    public TextureRegion getKeyFrame(float stateTime) {
    	int frameNumber = getFrameNumber(stateTime);
    	
    	if(!loop) {
    		frameNumber = Math.min(keyFrames.length - 1, frameNumber);  
            if (frameNumber == keyFrames.length - 1) {  
                sendEvent();  
            } 
    	}
    	else {
    		frameNumber = frameNumber % keyFrames.length;
    	}
    	
    	return keyFrames[frameNumber];
    }
    
    public void removeEventListener(Animation.AnimationEventListener listener) {
    	_listeners.removeValue(listener, true);
    }
    
    private void sendEvent() {
    	AnimationEvent event = new AnimationEvent(this);
    	int len = _listeners.size;
    	for(int i = 0; i < len; i++) {
    		_listeners.get(i).onAnimationEnded(event);
    	}
    }
}
