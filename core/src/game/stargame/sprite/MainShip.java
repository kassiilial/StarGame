package game.stargame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import game.stargame.base.Sprite;
import game.stargame.math.Rect;
import game.stargame.pool.BulletPool;

public class MainShip extends Sprite {

    private static final float height = 0.15f;
    private static final float padding = 0.05f;
    private static final int invalidPointer = -1;


    private final Vector2 v0 = new Vector2(0.5f,0f);
    private final Vector2 v = new Vector2();

    private Rect worldBounds;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletV;
    private Vector2 bulletPos;

    private boolean pressedLeft;
    private boolean pressedRight;

    private int leftPointer = invalidPointer;
    private int rightPointer = invalidPointer;

    private float reloadInterval;
    private float reloadTimer;

    private Sound sound;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        bulletV = new Vector2(0, 0.5f);
        bulletPos = new Vector2();
        reloadInterval = 0.15f;
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(height);
        setBottom(worldBounds.getBottom()+padding);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v,delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        if (getRight()>worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft()<worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x<worldBounds.pos.x) {
            if (leftPointer != invalidPointer) {
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        }
        else {
            if (rightPointer!=invalidPointer){
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer) {
            leftPointer = invalidPointer;
            if (rightPointer!=invalidPointer) {
                moveRight();
            } else {stop();}

        } else if (pointer == rightPointer){
            rightPointer = invalidPointer;
            if (leftPointer != invalidPointer) {
                moveLeft();
            } else {stop();}
        }
        return false;
    }


    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                moveLeft();
                pressedLeft = true;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                moveRight();
                pressedRight= true;
                break;
            case Input.Keys.UP:
                shoot();
                break;
        }
        return false;
    }


    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                }
                else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                }
                else {stop();}
                break;
        }
        return false;
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft () {
        v.set(v0).rotateDeg(180);
    }

    private void stop () {
        v.setZero();
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bulletPos.set(pos.x, pos.y + getHalfHeight());
        bullet.set(this,
                bulletRegion, bulletPos, bulletV,
                0.01f, worldBounds,
                1
                );
        sound.play();
    }

}
