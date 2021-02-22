package game.stargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import game.stargame.base.Sprite;

public class Explosion extends Sprite {

    private static final float animatedInterval = 0.017f;

    private  float animatedTimer;

    private Sound sound;

    public Explosion(TextureAtlas atlas, Sound sound) {
        super(atlas.findRegion("explosion"), 9,9,74);
        this.sound = sound;
    }

    public void set (float height, Vector2 pos) {
        setHeightProportion(height);
        this.pos.set(pos);
        sound.play();
    }

    @Override
    public void update(float delta) {
        animatedTimer += delta;
        if (animatedTimer >= animatedInterval) {
            animatedTimer = 0f;
            if (++frame == regions.length) destroy();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame=0;
    }
}
