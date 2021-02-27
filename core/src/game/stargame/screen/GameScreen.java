package game.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import game.stargame.base.BaseScreen;
import game.stargame.math.Rect;
import game.stargame.pool.BulletPool;
import game.stargame.pool.EnemyPool;
import game.stargame.pool.ExplosionPool;
import game.stargame.sprite.Background;
import game.stargame.sprite.Bullet;
import game.stargame.sprite.EnemyShip;
import game.stargame.sprite.MainShip;
import game.stargame.sprite.Message;
import game.stargame.sprite.NewGame;
import game.stargame.sprite.Star;
import game.stargame.utils.EnemyEmitter;
import game.stargame.utils.Font;


public class GameScreen extends BaseScreen {

    private enum State {PLAYING , GAME_OVER};
    private State state;
    private static final float padding = 0.01f;

    private Texture bg;
    private Background background;

    private TextureAtlas atlas;
    private Star[] stars;
    private static final  int starCount = 64;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private MainShip mainShip;

    private Music music;
    private Sound enemyBulletSound;
    private Sound exploisonSound;

    private EnemyEmitter enemyEmitter;

    private Message message;
    private NewGame newGame;

    private int frags;

    private static final String FRAGS = "Frags: ";
    private Font font;
    private StringBuilder sbFrags;
    private static final float fontSize = 0.02f;

    private static final String HP = "HP: ";
    private StringBuilder sbHP;


    private static final String LEVEL = "Level: ";
    private StringBuilder sbLEVEL;


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
        exploisonSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(atlas, exploisonSound);
        enemyBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        enemyPool = new EnemyPool(bulletPool,explosionPool , worldBounds, enemyBulletSound);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        newGame = new NewGame(atlas, this);

        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(fontSize);
        sbFrags = new StringBuilder();
        sbHP = new StringBuilder();
        sbLEVEL = new StringBuilder();

        message = new Message(atlas);
        enemyEmitter = new EnemyEmitter(atlas, worldBounds, enemyPool);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();

        state = State.PLAYING;

        }


    public void startNewGame () {
        state = State.PLAYING;

        mainShip.startNewGame();
        frags = 0;

        bulletPool.freeAllActiveActiveSprites();
        enemyPool.freeAllActiveActiveSprites();
        explosionPool.freeAllActiveActiveSprites();

    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
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
        message.resize(worldBounds);
        newGame.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        music.dispose();
        enemyBulletSound.dispose();
        exploisonSound.dispose();
        mainShip.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state==State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            newGame.touchDown(touch, pointer, button);

        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state==State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            newGame.touchUp(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state==State.PLAYING)
        {mainShip.keyUp(keycode);}
        return false;
    }

    private void update (float delta) {
        for (Star star:stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state==State.PLAYING) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, frags);
        }
    }

    private void checkCollisions() {
        if (state==State.GAME_OVER) {return;}
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (EnemyShip enemyShip:enemyShipList) {
            if (enemyShip.isDestroyed()) continue;
            float minDist = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
            if (enemyShip.pos.dst(mainShip.pos) < minDist) {
                enemyShip.destroy();
                mainShip.damage(enemyShip.getDamage());

            }
        }
        for (Bullet bullet:bulletList) {
            if (bullet.isDestroyed()) continue;
            if (bullet.getOwner() != mainShip) {
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
                continue;
            }
            for (EnemyShip enemyShip:enemyShipList) {
                if (enemyShip.isDestroyed()) continue;
                if (enemyShip.isBulletCollision(bullet)) {
                    enemyShip.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemyShip.isDestroyed()) {frags++;}
                }
            }
            if (mainShip.isDestroyed()) {
                state = State.GAME_OVER;
            }
        }
    }

    private void freeAllDestroyed() {

        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void draw () {
        Gdx.gl.glClearColor(0, 0.02184f, 0.10943f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star:stars) {
            star.draw(batch);
        }
        if (state==State.PLAYING) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);}

        else if (state==State.GAME_OVER) {
            message.draw(batch);
            newGame.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();

    }

    private void printInfo () {
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbLEVEL.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags),
                worldBounds.getLeft()+padding,
                worldBounds.getTop()-padding);
        font.draw(batch, sbHP.append(HP).append(mainShip.getHp()),
                   worldBounds.pos.x,
                worldBounds.getTop()-padding,
                Align.center);
        font.draw(batch, sbLEVEL.append(LEVEL).append(enemyEmitter.getLevel()),
                worldBounds.getRight() - padding,
                worldBounds.getTop()-padding,
                Align.right);
    }

}
