package fi.tuni.tamk.tiko;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

import java.awt.Rectangle;

public class Main extends Game {

	Sprite playButton;
	Texture playButtonTexture;
	SpriteBatch batch;
	OrthographicCamera camera;
	Texture backgroundImage;
	float playButtonX;
	float playButtonY;
	float playButtonWidth;
	float playButtonHeight;
	public SpriteBatch getBatch() {
		return batch;
	}
	Texture img;
	@Override
	public void create () {
		playButtonX = 250;
		playButtonY = 600-playButtonHeight;
		playButtonWidth = 200;
		playButtonHeight = 100;

		playButtonTexture = new Texture("default.png");
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 9,16);
		img = new Texture("badlogic.jpg");
		setScreen(new MainMenu(this));
		backgroundImage = new Texture("room.png");
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
}
