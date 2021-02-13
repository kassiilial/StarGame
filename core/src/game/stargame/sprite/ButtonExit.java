package game.stargame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import game.stargame.base.BaseButton;
import game.stargame.math.Rect;

public class ButtonExit extends BaseButton {

    private static final float hieght = 0.2f;
    private static final float padding = 0.03f;

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(hieght);
        setBottom(worldBounds.getBottom()+padding);
        setRight(worldBounds.getRight()-padding);
    }


    @Override
    public void action() {
        Gdx.app.exit();
    }
}
