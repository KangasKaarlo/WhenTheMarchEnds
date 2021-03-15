package fi.tuni.tamk.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import fi.tuni.tamk.tiko.utils.Card;
import fi.tuni.tamk.tiko.utils.Deck;

public class CoreGameplayLoop implements Screen {
    Main host;
    SpriteBatch batch;
    BitmapFont font;

    Sprite visualCard;
    Deck deck;
    Card currentCard;

    //Attributes
    int social;
    int sleep;
    int hunger;
    int duty;

    public CoreGameplayLoop(Main host) {
        this.host = host;
        batch = host.batch;
        deck = new Deck();
        currentCard = deck.drawACard();
        visualCard = new Sprite();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);

        //initializing attributes
        social = 50;
        sleep = 50;
        hunger = 50;
        duty = 50;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, "testi", 10, 10);
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
        font.dispose();
    }
}

