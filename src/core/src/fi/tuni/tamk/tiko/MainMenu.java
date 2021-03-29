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
    Sprite settingsButton;
    Sprite quitButton;
    Boolean sfx;
    Boolean music;
    SpriteBatch batch;
    OrthographicCamera camera;
    Texture backgroundImage;


    Main host;

    public MainMenu(Main host) {
        this.host = host;
        batch = host.batch;
        camera = host.camera;
        sfx = true;
        music = true;
        playButton = new Sprite(new Texture("default.png"));
        playButton.setSize(6, 2);
        playButton.setX(camera.viewportWidth/2 - playButton.getWidth()/2);
        playButton.setY(8);

       settingsButton = new Sprite(new Texture("default.png"));
        settingsButton.setSize(6, 2);
        settingsButton.setX(camera.viewportWidth/2 - playButton.getWidth()/2);
        settingsButton.setY(5);

        quitButton = new Sprite(new Texture("default.png"));
        quitButton.setSize(6, 2);
        quitButton.setX(camera.viewportWidth/2 - playButton.getWidth()/2);
        quitButton.setY(2);

        backgroundImage = new Texture("room.png");

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 3, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (Gdx.input.isTouched()){
            int realX = Gdx.input.getX();
            int realY = Gdx.input.getY();
            Vector3 touchPos = new Vector3(realX, realY,0);
            camera.unproject(touchPos);
            if (touchPos.x > playButton.getX() && touchPos.x < playButton.getX() + playButton.getWidth()
                    &&  touchPos.y > playButton.getY() && touchPos.y < playButton.getY() + playButton.getHeight()) {

                host.setScreen(new CoreGameplayLoop(host));

            }
            if (touchPos.x > settingsButton.getX() && touchPos.x < settingsButton.getX() + settingsButton.getWidth()
                    &&  touchPos.y > settingsButton.getY() && touchPos.y < settingsButton.getY() + settingsButton.getHeight()) {

                host.setScreen(new SettingsMenu(host));

            }

            if (touchPos.x > quitButton.getX() && touchPos.x < quitButton.getX() + quitButton.getWidth()
                    &&  touchPos.y > quitButton.getY() && touchPos.y < quitButton.getY() + quitButton.getHeight()) {

                host.setScreen(new CoreGameplayLoop(host));
                //Quit button need to quit the game

            }




        }

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        batch.draw(backgroundImage,0,0, camera.viewportWidth, camera.viewportHeight);
        playButton.draw(batch);
        settingsButton.draw(batch);
        quitButton.draw(batch);
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
