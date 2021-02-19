package game.stargame.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import game.stargame.math.Rect;
import game.stargame.math.Rnd;
import game.stargame.pool.EnemyPool;
import game.stargame.sprite.EnemyShip;

public class EnemyEmitter {

    private static final float  enemySmallHeight = 0.1f;
    private static final float  enemySmallBulletHeight = 0.01f;
    private static final int enemySmallBulletDamage = 1;
    private static final float  enemySmallReloadInterval = 1f;
    private static final int enemySmallHP = 1;


    private static final float  enemyMediumHeight = 0.15f;
    private static final float  enemyMediumBulletHeight = 0.02f;
    private static final int enemyMediumBulletDamage = 5;
    private static final float  enemyMediumReloadInterval = 1.8f;
    private static final int enemyMediumHP = 5;


    private static final float  enemyBigHeight = 0.2f;
    private static final float  enemyBigBulletHeight = 0.04f;
    private static final int enemyBigBulletDamage = 10;
    private static final float  enemyBigReloadInterval = 1.9f;
    private static final int enemyBigHP = 10;


    private final Vector2 enemySmallBulletV = new Vector2(0, -0.3f);
    private final Vector2 enemyMediumBulletV = new Vector2(0, -0.25f);
    private final Vector2 enemyBigBulletV = new Vector2(0, -0.2f);

    private final Vector2 enemySmallV = new Vector2(0, -0.2f);
    private final Vector2 enemyMediumV = new Vector2(0, -0.03f);
    private final Vector2 enemyBigV = new Vector2(0, -0.005f);

    private TextureRegion[] enemySmallRegions;
    private TextureRegion[] enemyMediumRegions;
    private TextureRegion[] enemyBigRegions;


    private Rect worldBounds;

    private TextureRegion bulletRegion;
    private EnemyPool enemyPool;


    private  float generateInterval = 4f;
    private float generateTimer;


    public EnemyEmitter(TextureAtlas atlas, Rect worldBounds,  EnemyPool enemyPool) {
        this.worldBounds = worldBounds;

        this.enemyPool = enemyPool;

        enemySmallRegions = Regions.split(atlas.findRegion("enemy0"),1,2,2);
        enemyMediumRegions = Regions.split(atlas.findRegion("enemy1"),1,2,2);
        enemyBigRegions = Regions.split(atlas.findRegion("enemy2"),1,2,2);

        bulletRegion = atlas.findRegion("bulletEnemy");
    }

    public void generate(float delta) {
        generateTimer +=delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            EnemyShip enemyShip = enemyPool.obtain();
            float enemyType = (float)Math.random();
            if (enemyType < 0.5f) {
            enemyShip.set(
                    enemySmallRegions, enemySmallV,
                    bulletRegion, enemySmallBulletHeight,
                    enemySmallBulletV, enemySmallBulletDamage,
                    enemySmallReloadInterval, enemySmallHeight,
                    enemySmallHP
            );} else if (enemyType< 0.8f) {
                enemyShip.set(
                        enemyMediumRegions, enemyMediumV,
                        bulletRegion, enemyMediumBulletHeight,
                        enemyMediumBulletV, enemyMediumBulletDamage,
                        enemyMediumReloadInterval, enemyMediumHeight,
                        enemyMediumHP);
            }
            else {
                enemyShip.set(
                        enemyBigRegions, enemyBigV,
                        bulletRegion, enemyBigBulletHeight,
                        enemyBigBulletV, enemyBigBulletDamage,
                        enemyBigReloadInterval, enemyBigHeight,
                        enemyBigHP);
            }
            enemyShip.pos.x = Rnd.nextFloat(
                    worldBounds.getLeft()+enemyShip.getHalfWidth(),
                    worldBounds.getRight()-enemyShip.getHalfWidth());
            enemyShip.setBottom(worldBounds.getTop());
        }
    }

}
