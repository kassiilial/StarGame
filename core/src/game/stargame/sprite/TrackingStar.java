package game.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class TrackingStar extends Star{


    private final Vector2 sumV;
    private final Vector2 trackingV;

    public TrackingStar(TextureAtlas atlas) {
        super(atlas);
        sumV = new Vector2();
        trackingV = new Vector2();
    }


    public void update(float delta, float xV) {
        trackingV.set(xV,0);
        sumV.setZero().mulAdd(trackingV,0.2f).rotateDeg(180).add(v);
        pos.mulAdd(sumV, delta);
        checkBounds();
    }
}
