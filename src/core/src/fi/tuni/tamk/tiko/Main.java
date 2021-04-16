package fi.tuni.tamk.tiko;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;
import com.google.gson.Gson;

import fi.tuni.tamk.tiko.utils.GameState;

public class Main extends Game {
    Boolean sfxOn;
    Boolean musicOn;
	SpriteBatch batch;
	OrthographicCamera camera;
	Texture backgroundImage;
	Music music;

	@Override
	public void create () {




		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 9,16);

		backgroundImage = new Texture("wme_background.png");
		music = Gdx.audio.newMusic(Gdx.files.internal("mainmenu.mp3"));
		music.setLooping(true);
		music.play();

		if (Gdx.files.local("savedGameState.txt").length() == 0) {
			musicOn = true;
			sfxOn = true;
		} else {
			Gson gson = new Gson();
			GameState savedGame;
			String saveJsonString = Gdx.files.local("savedGameState.txt").readString();
			savedGame = gson.fromJson(saveJsonString, GameState.class);
			musicOn = savedGame.isMusicOn();
			sfxOn = savedGame.isSfxOn();
			toggleMusicAndSFX();
		}
		setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
	public void toggleMusicAndSFX() {
		if (musicOn) {
			music.setVolume(1);
		} else {
			music.setVolume(0);
		}
	}
}
