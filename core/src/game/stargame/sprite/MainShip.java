package game.stargame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import game.stargame.base.Ship;
import game.stargame.math.Rect;
import game.stargame.pool.BulletPool;
import game.stargame.pool.ExplosionPool;

public class MainShip extends Ship {

    private static final float height = 0.15f;
    private static final float padding = 0.05f;
    private static final int invalidPointer = -1;

    private boolean pressedLeft;
    private boolean pressedRight;

    private int leftPointer = invalidPointer;
    private int rightPointer = invalidPointer;



    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        v = new Vector2();
        v0 = new Vector2(0.5f,0);
        bulletV = new Vector2(0, 0.5f);
        bulletPos = new Vector2();
        bulletHeight = 0.01f;
        damage = 1;
        reloadInterval = 0.2f;
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        hp = 100;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(height);
        setBottom(worldBounds.getBottom()+padding);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, pos.y + getHalfHeight());
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

    public  void dispose() {
        sound.dispose();
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight()<getLeft()||
                bullet.getLeft()>getRight()||
                bullet.getBottom()>pos.y||
                bullet.getTop()< getBottom());
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



}
