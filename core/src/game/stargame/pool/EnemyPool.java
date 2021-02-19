package game.stargame.pool;

import com.badlogic.gdx.audio.Sound;

import game.stargame.base.SpritesPool;
import game.stargame.math.Rect;
import game.stargame.sprite.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private  BulletPool bulletPool;
    private Rect worldBounds;
    private Sound sound;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds, Sound sound) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.sound = sound;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, worldBounds, sound);
    }
}
