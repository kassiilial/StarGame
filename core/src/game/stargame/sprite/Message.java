package game.stargame.sprite;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import game.stargame.base.Sprite;
import game.stargame.math.Rect;

public class Message extends Sprite {

    private static final float height = 0.08f;
    private static final float top = 0.15f;

    public Message(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }


    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(height);
        setTop(top);
    }
}
