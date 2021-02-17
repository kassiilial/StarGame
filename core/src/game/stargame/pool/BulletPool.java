package game.stargame.pool;

import game.stargame.base.SpritesPool;
import game.stargame.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
