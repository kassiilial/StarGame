package game.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import game.stargame.base.BaseButton;
import game.stargame.math.Rect;
import game.stargame.screen.GameScreen;

public class NewGame extends BaseButton {

    private static final float height = 0.06f;
    private GameScreen gameScreen;

    public NewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(height);
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}
