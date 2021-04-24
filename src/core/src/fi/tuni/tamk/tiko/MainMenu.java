package fi.tuni.tamk.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;

import java.awt.Rectangle;

/**
 * Main menu includes buttons to start game, go to settings menu and to quit the game
 */
public class MainMenu implements Screen {
    private Sprite playButton;
    private Sprite settingsButton;
    private Sprite quitButton;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture backgroundImage;
    private Music music;

    Main host;

    public MainMenu(final Main host) {
        this.host = host;
        batch = host.batch;
        camera = host.camera;
        // Adds 3 buttons to the main menu.
        playButton = new Sprite(new Texture("wme_button-start.png"));
        playButton.setSize(6, 2);
        playButton.setX(camera.viewportWidth/2 - playButton.getWidth()/2);
        playButton.setY(8);

        settingsButton = new Sprite(new Texture("wme_button-settings.png"));
        settingsButton.setSize(6, 2);
        settingsButton.setX(camera.viewportWidth/2 - playButton.getWidth()/2);
        settingsButton.setY(5);

        quitButton = new Sprite(new Texture("wme_button-quitt.png"));
        quitButton.setSize(6, 2);
        quitButton.setX(camera.viewportWidth/2 - playButton.getWidth()/2);
        quitButton.setY(2);

        backgroundImage = new Texture("room.png");
        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetector.GestureAdapter() {
            @Override
            public boolean tap(float x, float y, int count, int button) {
                //the game thinks that the players finger leaving after pressing start game is a swipe
                //this fixes that
                Vector3 touchPos = new Vector3(x, y, 0);
                camera.unproject(touchPos);

                // Pressing the playButton will start the game,
                // pressing the settingsButton will open the settings menu,
                // and pressing the quit button will end the game.

                if (touchPos.x > playButton.getX() && touchPos.x < playButton.getX() + playButton.getWidth()
                        && touchPos.y > playButton.getY() && touchPos.y < playButton.getY() + playButton.getHeight()) {
                    host.setScreen(new CoreGameplayLoop(host));
                }
                if (touchPos.x > settingsButton.getX() && touchPos.x < settingsButton.getX() + settingsButton.getWidth()
                        && touchPos.y > settingsButton.getY() && touchPos.y < settingsButton.getY() + settingsButton.getHeight()) {
                    host.setScreen(new SettingsMenu(host));

                }

                if (touchPos.x > quitButton.getX() && touchPos.x < quitButton.getX() + quitButton.getWidth()
                        && touchPos.y > quitButton.getY() && touchPos.y < quitButton.getY() + quitButton.getHeight()) {
                        System.exit(0);

                }
                return super.tap(x, y,1, button);
            }
        }));
        music = host.music;
        music.setLooping(true);
        music.play();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 3, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



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
