package game.stargame.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


import game.stargame.base.BaseButton;
import game.stargame.math.Rect;
import game.stargame.screen.GameScreen;

public class ButtonPlay extends BaseButton {

    private static final float hieght = 0.25f;
    private static final float padding = 0.03f;

    private final Game game;


    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game=game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(hieght);
        setBottom(worldBounds.getBottom()+padding);
        setLeft(worldBounds.getLeft()+padding);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
