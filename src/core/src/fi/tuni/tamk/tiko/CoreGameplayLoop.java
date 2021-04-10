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
import com.badlogic.gdx.math.Vector3;

import fi.tuni.tamk.tiko.utils.Card;
import fi.tuni.tamk.tiko.utils.Deck;

public class CoreGameplayLoop implements Screen {
    Main host;
    SpriteBatch batch;
    BitmapFont font;

    Sprite visualCard;
    Sprite cardForAnimation;
    Deck commonDeck;
    Deck endConditionDeck;
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
    Texture socialTexture;
    Texture sleepTexture;
    Texture hungerTexture;
    Texture dutyTexture;

    Texture backgroundImage;
    Texture statbarTexture;
    int cardSpeed;
    Boolean cardHasBeenSwipedFully;
    Boolean gameOver;

    public CoreGameplayLoop(Main host) {
        this.host = host;
        batch = host.batch;
        commonDeck = new Deck(Gdx.files.internal("deckJson.txt"));
        endConditionDeck = new Deck(Gdx.files.internal("endConditionDeck.txt"));
        currentCard = commonDeck.drawACard();
        backgroundImage = new Texture("purple.png");
        cardHasBeenSwipedFully = true;
        gameOver = false;

        //Generates the font and sets a camera to use it with
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);
        fontCamera = new OrthographicCamera();
        fontCamera.setToOrtho(false, 675,1200);

        normalCamera = host.camera;

        visualCard = new Sprite(new Texture("cardtest.png"));
        visualCard.setSize(7,7);
        visualCard.setX(normalCamera.viewportWidth /2 - visualCard.getWidth()/2);
        visualCard.setY(normalCamera.viewportHeight /2.3f - visualCard.getHeight()/2);

        cardForAnimation = new Sprite(visualCard);
        cardForAnimation.setX(-cardForAnimation.getWidth());

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

        socialDisplay.setX(1);
        socialDisplay.setY(14);
        sleepDisplay.setX(3);
        sleepDisplay.setY(14);
        hungerDisplay.setX(5);
        hungerDisplay.setY(14);
        dutyDisplay.setX(7);
        dutyDisplay.setY(14);


        socialTexture = new Texture("wme_icon_social.png");
        sleepTexture = new Texture("wme_icon_sleep.png");
        hungerTexture = new Texture("wme_icon_food.png");
        dutyTexture = new Texture("wme_icon_duty.png");
        statbarTexture = new Texture("wme_statbar_gold.png");
        //this detects if the screen is swiped
        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetector.GestureAdapter() {

            //Input for swiping the card
            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                if (cardHasBeenSwipedFully) {
                    if (gameOver) {
                        social = 50;
                        sleep = 50;
                        hunger = 50;
                        duty = 50;
                        gameOver = false;
                    }
                    commonDeck.getDeck()[currentCard.getIndex()].setRotation(0);
                    if (velocityX <0) {
                        cardSwipeLeft();
                    }else if (velocityX > 0) {
                        cardSwipeRight();
                    }
                }
                return super.fling(velocityX, velocityY, button);
            }
            //tap control for "swiping the card
            @Override
            public boolean tap(float x, float y, int count, int button) {
                if (cardHasBeenSwipedFully) {
                    if (gameOver) {
                        social = 50;
                        sleep = 50;
                        hunger = 50;
                        duty = 50;
                        gameOver = false;
                    }
                    Vector3 touchPos = new Vector3(x, y, 0);
                    normalCamera.unproject(touchPos);
                    commonDeck.getDeck()[currentCard.getIndex()].setRotation(0);
                    if (touchPos.x > visualCard.getX() && touchPos.x < visualCard.getX() + visualCard.getWidth()/2
                            && touchPos.y > visualCard.getY() && touchPos.y < visualCard.getY() + visualCard.getWidth()) {
                        cardSwipeLeft();
                    }else if (touchPos.x > visualCard.getX() && touchPos.x < visualCard.getX() + visualCard.getWidth()/2 + visualCard.getWidth()
                            && touchPos.y > visualCard.getY() && touchPos.y < visualCard.getY() + visualCard.getHeight()) {
                        cardSwipeRight();
                    }
                }
                return super.tap(x, y, count, button);
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
        //updates the visuals for the attribute meters
        updateAttributes();
        //checks if the game continues
        checkForDeath();

        //animates the card swipe
        cardForAnimation.setX(cardForAnimation.getX() + cardSpeed * Gdx.graphics.getDeltaTime());
        if (cardForAnimation.getX() <= 0 - cardForAnimation.getWidth()
                || cardForAnimation.getX() >= normalCamera.viewportWidth) {
            cardSpeed = 0;
            cardHasBeenSwipedFully = true;
        } else {
            cardHasBeenSwipedFully = false;
        }

        batch.begin();
        batch.setProjectionMatrix(normalCamera.combined);

        //draws the background
        batch.draw(backgroundImage,0,0, normalCamera.viewportWidth, normalCamera.viewportHeight);

        //draws the card
        visualCard.draw(batch);
        cardForAnimation.draw(batch);

        //draws the attribute displays
        batch.draw(statbarTexture, 0, normalCamera.viewportHeight-3, 9, 3);
        sleepDisplay.draw(batch);
        dutyDisplay.draw(batch);
        hungerDisplay.draw(batch);
        socialDisplay.draw(batch);

        batch.draw(sleepTexture, sleepDisplay.getX(), sleepDisplay.getY(),
                1, 1);
        batch.draw(hungerTexture, hungerDisplay.getX(), hungerDisplay.getY(),
                1, 1);
        batch.draw(dutyTexture, dutyDisplay.getX(), dutyDisplay.getY(),
                1, 1);
        batch.draw(socialTexture, socialDisplay.getX(), socialDisplay.getY(),
                1, 1);
        batch.draw(statbarTexture, 0, 0, 9, 1.5f);

        //only font renders after this line
        batch.setProjectionMatrix(fontCamera.combined);

        //The drawNewCard function has a error in it that it can draw an empty card
        //I'll hopefully have time to fix it properly, but this works
        try {
            font.draw(batch, currentCard.getText(), fontCamera.viewportWidth/8, fontCamera.viewportHeight/1.25f, 500, 5, true);
        } catch (NullPointerException e) {
            currentCard = commonDeck.drawACard();
        }

        batch.end();
    }
    /**
     * Checks if any of the attributes has dropped under 0
     *     or gone over 100
     */
    private void checkForDeath() {
        if (sleep <= 0) {
            currentCard = endConditionDeck.getDeck()[0];
            gameOver = true;
        } else if (sleep >=100) {
            currentCard = endConditionDeck.getDeck()[1];
            gameOver = true;
        } else if (hunger <= 0) {
            currentCard = endConditionDeck.getDeck()[2];
            gameOver = true;
        } else if (hunger >=100) {
            currentCard = endConditionDeck.getDeck()[3];
            gameOver = true;
        } else if (duty <= 0) {
            currentCard = endConditionDeck.getDeck()[4];
            gameOver = true;
        } else if (duty >=100) {
            currentCard = endConditionDeck.getDeck()[5];
            gameOver = true;
        } else if (social <= 0) {
            currentCard = endConditionDeck.getDeck()[6];
            gameOver = true;
        } else if (social >=100) {
            currentCard = endConditionDeck.getDeck()[7];
            gameOver = true;
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
        socialTexture.dispose();
        sleepTexture.dispose();
        hungerTexture.dispose();
        dutyTexture.dispose();
        backgroundImage.dispose();
    }
    /*
    Adds swipe left effect of the current card to the attribute int's and starts the card swipe animation
     */
    private void cardSwipeLeft() {
        sleep += currentCard.getNoSleep();
        hunger += currentCard.getNoHunger();
        social += currentCard.getNoSocial();
        duty += currentCard.getNoDuty();
        currentCard = commonDeck.drawACard();
        cardForAnimation.setX(visualCard.getX());
        cardSpeed = -20;
        updateRotations();
    }
    /*
    Adds swipe right effect of the current card to the attribute int's and starts the card swipe animation
     */
    private void cardSwipeRight() {
        sleep += currentCard.getYesSleep();
        hunger += currentCard.getYesHunger();
        social += currentCard.getYesSocial();
        duty += currentCard.getYesDuty();
        currentCard = commonDeck.drawACard();
        cardForAnimation.setX(visualCard.getX());
        cardSpeed = 20;
        updateRotations();
    }

    private void updateRotations() {
        for (int i = 0; i < commonDeck.getDeck().length; i++) {
            if (commonDeck.getDeck()[i].getRotation() < commonDeck.getDeck()[i].getRotationRequirement()) {
                commonDeck.getDeck()[i].setRotation(commonDeck.getDeck()[i].getRotation() + 1);
            }
        }
        commonDeck.getDeck()[currentCard.getIndex()].setRotation(0);
    }
    /*
    Updates the attribute visuals to match the attribute int's
     */
    public void updateAttributes() {

        socialDisplay.setSize(displayWidth , displayHeight * (social / 10));

        sleepDisplay.setSize(displayWidth , displayHeight * (sleep / 10));

        hungerDisplay.setSize(displayWidth , displayHeight * (hunger / 10));

        dutyDisplay.setSize(displayWidth , displayHeight * (duty / 10));

    }
}

