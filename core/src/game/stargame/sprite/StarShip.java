package game.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


import game.stargame.base.Sprite;
import game.stargame.math.Rect;

public class StarShip extends Sprite {


    private static final float hieght = 0.2f;
    private static final float padding = 0.03f;


    public StarShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"));
    }


    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(hieght);
        setBottom(worldBounds.getBottom()+padding);
        setLeft(worldBounds.getLeft()+worldBounds.getHalfWidth());
    }
}
