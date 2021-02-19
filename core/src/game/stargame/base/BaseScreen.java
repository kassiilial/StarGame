package game.stargame.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import game.stargame.math.MatrixUtils;
import game.stargame.math.Rect;

public class BaseScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;

    private Rect screenBounds;
    private Rect glBounds;
    protected Rect worldBounds;

    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;

    private Vector2 touch;

    @Override
    public void show() {
        //System.out.println("show");
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        screenBounds = new Rect();
        glBounds = new Rect(0,0,1f,1f);
        worldBounds = new Rect();
        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();
        touch = new Vector2();
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        screenBounds.setSize(width,height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width / (float)height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f*aspect);

        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);
        resize(worldBounds);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
    }

    public void resize(Rect worldBounds) {


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        return false;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

       touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
       touchDown(touch, pointer, button);
       return false;
    }
    public boolean touchDown(Vector2 touch, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        touchUp(touch, pointer, button);
        return false;
    }
    public boolean touchUp(Vector2 touch, int pointer, int button) {


        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        touchDragged(touch, pointer);
        return false;
    }
    public boolean touchDragged(Vector2 touch, int pointer) {


        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {

        return false;
    }
}
