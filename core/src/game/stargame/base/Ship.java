package game.stargame.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import game.stargame.math.Rect;
import game.stargame.pool.BulletPool;
import game.stargame.pool.ExplosionPool;
import game.stargame.sprite.Bullet;
import game.stargame.sprite.Explosion;

public class Ship extends Sprite{

    private static final float damageAnimatedInterval = 0.1f;

    protected Vector2 v0;
    protected Vector2 v;

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected float bulletHeight;
    protected int damage;


    protected float reloadInterval;
    protected float reloadTimer;

    protected int hp;

    protected Sound sound;

    private float getDamageAnimatedTimer = damageAnimatedInterval;

    public Ship() {

    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        this.bulletHeight = bulletHeight;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v,delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        getDamageAnimatedTimer +=delta;
        if (getDamageAnimatedTimer>damageAnimatedInterval) {frame=0;}
    }
    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    public void damage(int damage) {
        hp -=damage;
        if (hp<=0) {
            hp = 0;
            destroy();
        }
        frame = 1;
        getDamageAnimatedTimer = 0f;
    }

    public int getDamage() {
        return damage;
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

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }
}
