package fi.tuni.tamk.tiko;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

public class Main extends Game {
	SpriteBatch batch;
	OrthographicCamera camera;

	public SpriteBatch getBatch() {
		return batch;
	}
	Texture img;
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 9,16);
		img = new Texture("badlogic.jpg");
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
}
