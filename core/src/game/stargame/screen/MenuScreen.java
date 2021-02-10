package game.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.stargame.base.BaseScreen;
import game.stargame.math.Rect;
import game.stargame.sprite.Background;
import game.stargame.sprite.Logo;

public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Background background;

    private Texture cat;
    private Logo logo;

    @Override
    public void show() {
        super.show();
        bg = new Texture("texture/bg.png");
        background = new Background(bg);

        cat = new Texture("fon.jpg");
        logo = new Logo(cat);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0.02184f, 0.10943f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        cat.dispose();
        bg.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        logo.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }
}
