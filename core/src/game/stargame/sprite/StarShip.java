package game.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


import game.stargame.base.Sprite;
import game.stargame.math.Rect;

public class StarShip extends Sprite {


    private static final float hieght = 0.2f;
    private static final float padding = 0.03f;
    public TextureRegion ship;


    public StarShip(TextureAtlas atlas) {
        super(new TextureRegion(atlas.findRegion("main_ship"), 0 ,0 , atlas.findRegion("main_ship").getRegionWidth()/2, atlas.findRegion("main_ship").getRegionHeight()));

    }


    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(hieght);
        setBottom(worldBounds.getBottom()+padding);
        setLeft(worldBounds.getLeft()+worldBounds.getHalfWidth());
    }
}
