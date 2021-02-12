package game.stargame.sprite;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import game.stargame.base.Sprite;
import game.stargame.math.Rect;

public class Logo extends Sprite {
    public Vector2 tmp = new Vector2(1f,1f);
    public Vector2 endPoint  = new Vector2(1f,1f);
    public Vector2 v = new Vector2(0f,0f);
    public Vector2 position = pos;

    public Logo(Texture texture) {
        super(new TextureRegion(texture));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        this.pos.set(worldBounds.pos);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                position.x, position.y,
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
        newPosition();
    }

    public void newPosition() {
        tmp.set(endPoint);
        if (tmp.sub(position).len()>v.len()) {

        position.add(v);
          }
        else {
            position.set(endPoint);
            }
     }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        endPoint.set(touch);
        v.set(endPoint.cpy().sub(position).setLength(0.001f));
        return super.touchDown(touch, pointer, button);
    }
}
