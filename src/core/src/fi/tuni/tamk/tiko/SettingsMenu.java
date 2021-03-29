package fi.tuni.tamk.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.input.GestureDetector;

public class SettingsMenu implements Screen {
    Sprite sfxButton;
    Sprite musicButton;
    Sprite returnButton;
    OrthographicCamera camera;
    Main host;
    SpriteBatch batch;
    Texture backgroundImage;
    public SettingsMenu(Main host) {

        this.host = host;
        batch = host.batch;
        camera = host.camera;
        sfxButton = new Sprite(new Texture("default.png"));
        sfxButton.setSize(6, 2);
        sfxButton.setX(camera.viewportWidth / 2 - sfxButton.getWidth() / 2);
        sfxButton.setY(8);

        musicButton = new Sprite(new Texture("default.png"));
        musicButton.setSize(6, 2);
        musicButton.setX(camera.viewportWidth / 2 - musicButton.getWidth() / 2);
        musicButton.setY(5);

        returnButton = new Sprite(new Texture("default.png"));
        returnButton.setSize(6, 2);
        returnButton.setX(camera.viewportWidth / 2 - returnButton.getWidth() / 2);
        returnButton.setY(2);

        backgroundImage = new Texture("room.png");
        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetector.GestureAdapter() {
        @Override
            public boolean tap(float X, float Y, int count, int button) {
                //the game thinks that the players finger leaving after pressing start game is a swipe
                //this fixes that
            if (Gdx.input.isTouched()) {
                float realX = X;
                float realY = Y;
                Vector3 touchPos = new Vector3(realX, realY, 0);
                camera.unproject(touchPos);

                if (touchPos.x > sfxButton.getX() && touchPos.x < sfxButton.getX() + sfxButton.getWidth()
                        && touchPos.y > sfxButton.getY() && touchPos.y < sfxButton.getY() + sfxButton.getHeight()) {
                    host.sfx = false;

                }
                if (touchPos.x > musicButton.getX() && touchPos.x < musicButton.getX() + musicButton.getWidth()
                        && touchPos.y > musicButton.getY() && touchPos.y < musicButton.getY() + musicButton.getHeight()) {
                    host.music = false;

                }

                if (touchPos.x > returnButton.getX() && touchPos.x < returnButton.getX() + returnButton.getWidth()
                        && touchPos.y > returnButton.getY() && touchPos.y < returnButton.getY() + returnButton.getHeight()) {

                    host.setScreen(new MainMenu(host));
                }
            }

                return super.tap(X, Y,1, button);
            }
        }));
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.5f, 0.67f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        /*if (Gdx.input.isTouched()) {
            int realX = Gdx.input.getX();
            int realY = Gdx.input.getY();
            Vector3 touchPos = new Vector3(realX, realY, 0);
            camera.unproject(touchPos);

            if (touchPos.x > sfxButton.getX() && touchPos.x < sfxButton.getX() + sfxButton.getWidth()
                    && touchPos.y > sfxButton.getY() && touchPos.y < sfxButton.getY() + sfxButton.getHeight()) {
                        host.sfx = false;

            }
            if (touchPos.x > musicButton.getX() && touchPos.x < musicButton.getX() + musicButton.getWidth()
                    && touchPos.y > musicButton.getY() && touchPos.y < musicButton.getY() + musicButton.getHeight()) {
                        host.music = false;

            }

            if (touchPos.x > returnButton.getX() && touchPos.x < returnButton.getX() + returnButton.getWidth()
                    && touchPos.y > returnButton.getY() && touchPos.y < returnButton.getY() + returnButton.getHeight()) {

                host.setScreen(new MainMenu(host));
            }
        }*/

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        batch.draw(backgroundImage,0,0, camera.viewportWidth, camera.viewportHeight);
        sfxButton.draw(batch);
        musicButton.draw(batch);
        returnButton.draw(batch);
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


