package fi.tuni.tamk.tiko;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

public class Main extends Game {
    Boolean sfxOn;
    Boolean musicOn;
	SpriteBatch batch;
	OrthographicCamera camera;
	Texture backgroundImage;
	Music music;

	Texture img;
	@Override
	public void create () {

		musicOn = true;
		sfxOn = true;
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 9,16);
		img = new Texture("badlogic.jpg");
		backgroundImage = new Texture("room.png");
		music = Gdx.audio.newMusic(Gdx.files.internal("mainmenu.mp3"));
		music.setLooping(true);
		music.play();

			setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	public void toggleMusicAndSFX() {
		if (musicOn) {
			music.setVolume(1);
		} else {
			music.setVolume(0);
		}
	}
}
