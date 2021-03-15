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
import com.badlogic.gdx.input.GestureDetector;

import fi.tuni.tamk.tiko.utils.Card;
import fi.tuni.tamk.tiko.utils.Deck;

public class CoreGameplayLoop implements Screen {
    Main host;
    SpriteBatch batch;
    BitmapFont font;

    Sprite visualCard;
    Deck deck;
    Card currentCard;

    //one camera for fonts and other for everything else
    OrthographicCamera fontCamera;
    OrthographicCamera normalCamera;
    //Attributes
    int social;
    int sleep;
    int hunger;
    int duty;
    //displays for attributes
    int displayWidth = 1;
    float displayHeight = 0.1f;
    Sprite socialDisplay;
    Sprite sleepDisplay;
    Sprite hungerDisplay;
    Sprite dutyDisplay;

    public CoreGameplayLoop(Main host) {
        this.host = host;
        batch = host.batch;
        deck = new Deck();
        currentCard = deck.drawACard();


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);
        fontCamera = new OrthographicCamera();
        fontCamera.setToOrtho(false, 675,1200);

        normalCamera = new OrthographicCamera();
        normalCamera.setToOrtho(false, 9,16);

        visualCard = new Sprite(new Texture("default.png"));
        visualCard.setSize(5,7);
        visualCard.setX(normalCamera.viewportWidth /2 - visualCard.getWidth()/2);
        visualCard.setY(normalCamera.viewportHeight /2 - visualCard.getHeight()/2);

        //initializing attributes
        social = 50;
        sleep = 50;
        hunger = 50;
        duty = 50;

        //and their displays
        socialDisplay = new Sprite(new Texture("default.png"));
        sleepDisplay = new Sprite(new Texture("default.png"));
        hungerDisplay = new Sprite(new Texture("default.png"));
        dutyDisplay = new Sprite(new Texture("default.png"));

        //cursed code refactor if you have time
        socialDisplay.setX(1);
        socialDisplay.setY(14);
        sleepDisplay.setX(3);
        sleepDisplay.setY(14);
        hungerDisplay.setX(5);
        hungerDisplay.setY(14);
        dutyDisplay.setX(7);
        dutyDisplay.setY(14);
        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetector.GestureAdapter() {

            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                if (velocityX >0) {
                    cardSwipeLeft();
                }else if (velocityX < 0) {
                    cardSwipeRight();
                }
                return super.fling(velocityX, velocityY, button);
            }
        }));
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateAttributes();
        checkForDeath();

        batch.begin();
        batch.setProjectionMatrix(normalCamera.combined);
        visualCard.draw(batch);

        sleepDisplay.draw(batch);
        dutyDisplay.draw(batch);
        hungerDisplay.draw(batch);
        socialDisplay.draw(batch);


        //only font renders after this line
        batch.setProjectionMatrix(fontCamera.combined);
        font.draw(batch, currentCard.getText(), fontCamera.viewportWidth/2 - 100, fontCamera.viewportHeight/2);
        batch.end();
    }

    private void checkForDeath() {
        if (sleep < 0 || sleep >100 ||
                hunger < 0 || hunger >100 ||
                duty < 0 || duty >100 ||
                social < 0 || social >100) {
            host.setScreen(new MainMenu(host));
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
        font.dispose();
    }

    private void cardSwipeLeft() {
        sleep += currentCard.getNoSleep();
        hunger += currentCard.getNoHunger();
        social += currentCard.getNoSocial();
        duty += currentCard.getNoDuty();
        currentCard = deck.drawACard();
    }
    private void cardSwipeRight() {
        sleep += currentCard.getYesSleep();
        hunger += currentCard.getYesHunger();
        social += currentCard.getYesSocial();
        duty += currentCard.getYesDuty();
        currentCard = deck.drawACard();
    }

    public void updateAttributes() {

        socialDisplay.setSize(displayWidth , displayHeight * social / 10);

        sleepDisplay.setSize(displayWidth , displayHeight * sleep / 10);

        hungerDisplay.setSize(displayWidth , displayHeight * hunger / 10);

        dutyDisplay.setSize(displayWidth , displayHeight * duty / 10);

    }
}

