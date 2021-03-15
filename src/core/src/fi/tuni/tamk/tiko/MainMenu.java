package fi.tuni.tamk.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.awt.Rectangle;

public class MainMenu implements Screen {
    Sprite playButton;
    SpriteBatch batch;
    OrthographicCamera camera;
    Texture backgroundImage;
    float playButtonX;
    float playButtonY;
    float playButtonWidth;
    float playButtonHeight;

    Main host;
   // SpriteBatch batch;
   // OrthographicCamera camera;

    public MainMenu(Main host) {
        this.host = host;
        batch = host.batch;
        camera = host.camera;
        //button = host.button;
        //buttonTexture = host.buttonTexture;
        playButtonWidth = host.playButtonWidth;
        playButtonHeight = host.playButtonHeight;
        playButton = new Sprite(new Texture("default.png"));
        playButton.setSize(playButtonWidth, playButtonHeight);
        playButton.setX(host.playButtonX);
        playButton.setY(host.playButtonY);



    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 3, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //draws the background
        playButton.draw(batch);
        if (Gdx.input.isTouched()){
            int realX = Gdx.input.getX();
            int realY = Gdx.input.getY();
            Vector3 touchPos = new Vector3(realX, realY,0);
            camera.unproject(touchPos);
            System.out.println(touchPos.x);
            System.out.println(touchPos.y);
            if (touchPos.x > playButton.getX() && touchPos.x > playButton.getX() + playButton.getWidth()
                    &&  touchPos.y > playButton.getY() && touchPos.y > playButton.getY() + playButton.getHeight()) {

                          host.setScreen(new CoreGameplayLoop(host));

            }
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
