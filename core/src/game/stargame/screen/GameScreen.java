package game.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import game.stargame.base.BaseScreen;
import game.stargame.math.Rect;
import game.stargame.pool.BulletPool;
import game.stargame.sprite.Background;
import game.stargame.sprite.MainShip;
import game.stargame.sprite.Star;


public class GameScreen extends BaseScreen {

    private Texture bg;
    private Background background;

    private TextureAtlas atlas;
    private Star[] stars;
    private static final  int starCount = 64;

    private BulletPool bulletPool;

    private MainShip mainShip;

    private Music music;


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
        bulletPool = new BulletPool();
        mainShip = new MainShip(atlas, bulletPool);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setVolume(0.5f);
        music.setLooping(true);


        }


    @Override
    public void render(float delta) {
        update(delta);
        freeAllDestroyed();
        draw();
    }



    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star:stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        music.play();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        music.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    private void update (float delta) {
        for (Star star:stars) {
            star.update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);

    }
    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
    }

    private void draw () {
        Gdx.gl.glClearColor(0, 0.02184f, 0.10943f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star:stars) {
            star.draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        batch.end();

    }


}
