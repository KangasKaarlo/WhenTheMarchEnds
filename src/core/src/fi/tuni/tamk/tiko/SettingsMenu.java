package fi.tuni.tamk.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
    Texture yes;
    Texture no;
    BitmapFont font;
    OrthographicCamera fontCamera;
    public SettingsMenu(final Main host) {

        this.host = host;
        batch = host.batch;
        camera = host.camera;

        //Generates the font and sets a camera to use it with
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);
        fontCamera = new OrthographicCamera();
        fontCamera.setToOrtho(false, 675,1200);

        yes = new Texture("wme_button-yes.png");
        no = new Texture("wme_button-no.png");

        sfxButton = new Sprite(yes);
        sfxButton.setSize(2, 2);
        sfxButton.setX(camera.viewportWidth / 2);
        sfxButton.setY(8);

        musicButton = new Sprite(yes);
        musicButton.setSize(2, 2);
        musicButton.setX(camera.viewportWidth / 2);
        musicButton.setY(5);

        returnButton = new Sprite(new Texture("default.png"));
        returnButton.setSize(6, 2);
        returnButton.setX(camera.viewportWidth / 2 - returnButton.getWidth() / 2);
        returnButton.setY(2);

        updateTextures();

        backgroundImage = new Texture("room.png");
        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetector.GestureAdapter() {
        @Override
            public boolean tap(float x, float y, int count, int button) {

                Vector3 touchPos = new Vector3(x, y, 0);
                camera.unproject(touchPos);

                if (touchPos.x > sfxButton.getX() && touchPos.x < sfxButton.getX() + sfxButton.getWidth()
                        && touchPos.y > sfxButton.getY() && touchPos.y < sfxButton.getY() + sfxButton.getHeight()) {
                    if (host.sfxOn) {
                        host.sfxOn = false;
                    } else {
                        host.sfxOn = true;
                    }
                    host.toggleMusicAndSFX();
                    updateTextures();
                }
                if (touchPos.x > musicButton.getX() && touchPos.x < musicButton.getX() + musicButton.getWidth()
                        && touchPos.y > musicButton.getY() && touchPos.y < musicButton.getY() + musicButton.getHeight()) {
                    if (host.musicOn) {
                        host.musicOn = false;
                    } else {
                        host.musicOn = true;
                    }
                    host.toggleMusicAndSFX();
                    updateTextures();
                }

                if (touchPos.x > returnButton.getX() && touchPos.x < returnButton.getX() + returnButton.getWidth()
                        && touchPos.y > returnButton.getY() && touchPos.y < returnButton.getY() + returnButton.getHeight()) {

                    host.setScreen(new MainMenu(host));
                }
                return super.tap(x, y,1, button);
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
        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        batch.draw(backgroundImage,0,0, camera.viewportWidth, camera.viewportHeight);
        sfxButton.draw(batch);
        musicButton.draw(batch);
        returnButton.draw(batch);

        //only font renders after this line
        batch.setProjectionMatrix(fontCamera.combined);
        font.draw(batch, "music on:", fontCamera.viewportWidth/2.5f,
                fontCamera.viewportHeight/2.8f, 10, 0, false);
        font.draw(batch, "sfx on:", fontCamera.viewportWidth/2.5f,
                fontCamera.viewportHeight/1.8f, 10, 0, false);
        batch.end();
    }

    private void updateTextures() {
        if (host.musicOn) {
            musicButton.setTexture(yes);
        } else {
            musicButton.setTexture(no);
        }
        if (host.sfxOn) {
            sfxButton.setTexture(yes);
        } else {
            sfxButton.setTexture(no);
        }
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


