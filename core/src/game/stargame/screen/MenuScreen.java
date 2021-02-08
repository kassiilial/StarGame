package game.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import game.stargame.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Vector2 touch;
    private Vector2 dot_for_img;
    private float dlina;

    @Override
    public void show() {
        super.show();
        img = new Texture("fon.jpg");
        touch = new Vector2(0, 0);
        dot_for_img = new Vector2();
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0.02184f, 0.10943f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, touch.x, touch.y);
        batch.end();
        if (dlina > 0) {
            dlina -= 1;
            touch.add(dot_for_img);
        }
    }

    @Override
    public void dispose() {
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        dot_for_img.set(screenX, Gdx.graphics.getHeight() - screenY);
        dot_for_img.sub(touch);
        dlina = dot_for_img.len();
        dot_for_img.nor();
            return false;
    }

    @Override
    public boolean keyDown(int keycode) {
       switch (keycode) {
           case Input.Keys.UP:
               touch.y+=10;
               break;
           case Input.Keys.DOWN:
               touch.y-=10;
               break;
           case Input.Keys.LEFT:
               touch.x-=10;
               break;
           case Input.Keys.RIGHT:
               touch.x+=10;
               break;
       }
        return false;
    }
}
