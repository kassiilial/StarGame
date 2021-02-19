package game.stargame.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import game.stargame.math.Rect;
import game.stargame.pool.BulletPool;
import game.stargame.sprite.Bullet;

public class Ship extends Sprite{

    protected Vector2 v0 = new Vector2(0, -0.5f);
    protected Vector2 v;

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected float bulletHeight;
    protected int damage;


    protected float reloadInterval;
    protected float reloadTimer;

    protected int hp;

    protected Sound sound;

    public Ship() {

    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        this.bulletHeight = bulletHeight;
    }

    @Override
    public void update(float delta) {
        if (this.getTop() < worldBounds.getTop()) {pos.mulAdd(v,delta);}
        else {pos.mulAdd(v.cpy().set(0f, -0.7f),delta);}
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this,
                bulletRegion, bulletPos, bulletV,
                bulletHeight, worldBounds,
                1
        );
        sound.play();
    }
}
