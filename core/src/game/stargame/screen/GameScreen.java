package game.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import game.stargame.base.BaseScreen;
import game.stargame.math.Rect;
import game.stargame.sprite.Background;
import game.stargame.sprite.Star;


public class GameScreen extends BaseScreen {

    private Texture bg;
    private Background background;

    private TextureAtlas atlas;
    private Star[] stars;
    private static final  int starCount = 64;

    @Override
    public void show() {
        super.show();
        bg = new Texture("texture/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas(Gdx.files.internal("texture/mainAtlas.tpack"));
        stars = new Star[starCount];
        for (int i=0; i<starCount; i++) {
            stars[i] = new Star(atlas);
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }



    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star:stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    private void update (float delta) {
        for (Star star:stars) {
            star.update(delta);
        }

    }
    private void draw () {
        Gdx.gl.glClearColor(0, 0.02184f, 0.10943f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star:stars) {
            star.draw(batch);
        }
        batch.end();

    }
}
