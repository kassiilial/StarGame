package game.stargame.sprite;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import game.stargame.base.Sprite;
import game.stargame.math.Rect;

public class Message extends Sprite {

    public Message(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }


    @Override
    public void resize(Rect worldBounds) {
        this.pos.set(worldBounds.pos);
        setHeightProportion(worldBounds.getHeight());
        setScale(0.07f);
    }
}
