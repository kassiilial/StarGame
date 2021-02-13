package game.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import game.stargame.base.BaseScreen;
import game.stargame.math.Rect;
import game.stargame.sprite.Background;
import game.stargame.sprite.ButtonExit;
import game.stargame.sprite.ButtonPlay;
import game.stargame.sprite.Star;


public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Background background;

    private TextureAtlas atlas;
    private Star[] stars;
    private static final  int starCount = 256;

    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;

    private final Game game;
    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("texture/bg.png");
        atlas = new TextureAtlas(Gdx.files.internal("texture/menuAtlas.tpack"));
        background = new Background(bg);
        stars = new Star[starCount];
        for (int i=0; i<starCount; i++) {
         stars[i] = new Star(atlas);
        }
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);
    }

    @Override
    public void render (float delta) {
        update(delta);
        draw();


    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {

        background.resize(worldBounds);
        for (Star star:stars) {
            star.resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        buttonExit.touchDown(touch, pointer, button);
        buttonPlay.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch, pointer, button);
        buttonPlay.touchUp(touch, pointer, button);
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
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();

    }
}
