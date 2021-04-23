package fi.tuni.tamk.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;


import fi.tuni.tamk.tiko.utils.Card;
import fi.tuni.tamk.tiko.utils.Deck;
import fi.tuni.tamk.tiko.utils.GameState;

public class CoreGameplayLoop implements Screen {
    Main host;
    SpriteBatch batch;
    BitmapFont font;
<<<<<<< HEAD

    //Decs that include all the cards for the game
=======
    Sprite visualCard;
    Sprite cardForAnimation;
>>>>>>> 8946dfc832d9132897f445e3978d970d14c3636e
    Deck commonDeck;
    Deck endConditionDeck;
    Deck storyDeck;

    Sprite visualCard;
    Sprite cardForAnimation;

    Card currentCard;
    Sound cardSwipeAudio;
    Sprite returnButton2;
    int howManyCardsPlayed;

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
    boolean cardHasBeenSwipedFully;
    boolean gameOver;
    boolean tutorialCompleted;
    int scriptCounter = 0;
    boolean firstDeath;
    boolean devilItroduced;
    int roundCounter;

    public CoreGameplayLoop(final Main host) {
        this.host = host;
        batch = host.batch;
        commonDeck = new Deck(Gdx.files.internal("deckJson.txt"));
        endConditionDeck = new Deck(Gdx.files.internal("endConditionDeck.txt"));
        storyDeck = new Deck(Gdx.files.internal("storyDeck.txt"));
        backgroundImage = new Texture("purple.png");
        cardHasBeenSwipedFully = true;
        cardSwipeAudio = Gdx.audio.newSound(Gdx.files.internal("card_swipe.mp3"));

        //Checks if the save file is empty
        if (Gdx.files.local("savedGameState.txt").length() == 0) {
            //if it is the game state is initialized

            gameOver = false;

            social = 50;
            sleep = 50;
            hunger = 50;
            duty = 50;
            tutorialCompleted = false;
            howManyCardsPlayed = 0;
            firstDeath = false;
            devilItroduced = false;
            roundCounter = 0;
            drawCard();

        } else {
            //if a save file exists
            //the game state is read
            Gson gson = new Gson();
            GameState savedGame;
            String saveJsonString = Gdx.files.local("savedGameState.txt").readString();
            savedGame= gson.fromJson(saveJsonString, GameState.class);

            //and attributes are set based on the saved GameState
            gameOver = savedGame.isGameOver();

            social = savedGame.getSocial();
            sleep = savedGame.getSleep();
            hunger = savedGame.getHunger();
            duty = savedGame.getDuty();

            currentCard = savedGame.getCurrentCard();
            howManyCardsPlayed = savedGame.getHowManyCardsPlayed();
            tutorialCompleted = savedGame.isTutorialCompleted();
            devilItroduced = savedGame.isDevilIntroduced();
            roundCounter = savedGame.getRoundCounter();
        }
        //batch.draw(returnButton, 530, 35, 50, 50);
        returnButton2 = new Sprite(new Texture("wme_button-return.png"));
        returnButton2.setSize(1, 1);
        returnButton2.setX(7);
        returnButton2.setY(0.25f);

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

        visualCard = new Sprite(new Texture(currentCard.getBearer()));
        visualCard.setSize(7,7);
        visualCard.setX(normalCamera.viewportWidth /2 - visualCard.getWidth()/2);
        visualCard.setY(normalCamera.viewportHeight /2.3f - visualCard.getHeight()/2);

        cardForAnimation = new Sprite(visualCard);
        cardForAnimation.setX(-cardForAnimation.getWidth());



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
                        if (!firstDeath) {
                            firstDeath = true;
                        }
                        resetAfterDeath();
                    }
                    if (velocityX <0) {

                        cardSwipeLeft();
                    }else if (velocityX > 0) {
                        cardSwipeRight();
                    }
                }
                return super.fling(velocityX, velocityY, button);
            }
            //tap control for "swiping" the card
            @Override
            public boolean tap(float x, float y, int count, int button) {
                Vector3 touchPos = new Vector3(x, y, 0);
                normalCamera.unproject(touchPos);
                if (touchPos.x > returnButton2.getX() && touchPos.x < returnButton2.getX() + returnButton2.getWidth()
                        && touchPos.y > returnButton2.getY() && touchPos.y < returnButton2.getY() + returnButton2.getHeight()) {

                    host.setScreen(new MainMenu(host));
                }
                if (cardHasBeenSwipedFully) {
                    if (gameOver) {
                        if (!firstDeath) {
                            firstDeath = true;
                        }
                        resetAfterDeath();
                    }
<<<<<<< HEAD
                    Vector3 touchPos = new Vector3(x, y, 0);
                    normalCamera.unproject(touchPos);
=======

                    //normalCamera.unproject(touchPos);
                    commonDeck.getDeck()[currentCard.getIndex()].setRotation(0);
>>>>>>> 8946dfc832d9132897f445e3978d970d14c3636e
                    if (touchPos.x > visualCard.getX() && touchPos.x < visualCard.getX() + visualCard.getWidth() / 2
                            && touchPos.y > visualCard.getY() && touchPos.y < visualCard.getY() + visualCard.getWidth()) {
                        if (howManyCardsPlayed/3 == 31) {
                            host.setScreen(new MainMenu(host));
                        }
                        cardSwipeLeft();
                    } else if (touchPos.x > visualCard.getX() && touchPos.x < visualCard.getX() + visualCard.getWidth() / 2 + visualCard.getWidth()
                            && touchPos.y > visualCard.getY() && touchPos.y < visualCard.getY() + visualCard.getHeight()) {
                        if (howManyCardsPlayed/3 == 31) {
                            host.setScreen(new MainMenu(host));
                        }
                        cardSwipeRight();
                    }
                }

                return super.tap(x, y, count, button);
            }
        }));
    }

    private void resetAfterDeath() {
            howManyCardsPlayed = 0;
            social = 50;
            sleep = 50;
            hunger = 50;
            duty = 50;
            gameOver = false;
            if (roundCounter == 9) {
                roundCounter = 0;
            } else {
                roundCounter++;
            }
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
        returnButton2.draw(batch);
        //only font renders after this line
        batch.setProjectionMatrix(fontCamera.combined);

        font.draw(batch, currentCard.getText(), fontCamera.viewportWidth/8, fontCamera.viewportHeight/1.25f,
                    500, 5, true);
        font.draw(batch, "Days survived:", fontCamera.viewportWidth/2,
                fontCamera.viewportHeight/18, 10, 0, false);
        font.draw(batch, Integer.toString(howManyCardsPlayed/3), fontCamera.viewportWidth/1.75f,
                fontCamera.viewportHeight/18, 10, 0, false);


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
        socialTexture.dispose();
        sleepTexture.dispose();
        hungerTexture.dispose();
        dutyTexture.dispose();
        backgroundImage.dispose();
    }
    /*
    Adds swipe left effect of the current card to the attribute int's, draws a new card and starts the card swipe animation
     */
    public void cardSwipeLeft() {
        //Adds the card values to the attributes
        sleep += currentCard.getNoSleep();
        hunger += currentCard.getNoHunger();
        social += currentCard.getNoSocial();
        duty += currentCard.getNoDuty();
        //updates card rotations
        //important that this is done before for drawing a new card
        updateRotations();
        //Sound effect for card swipe
        //important that this is done before for drawing a new card
        if(host.sfxOn) {
            if (currentCard.getBearer().equals("satan.png")) {
                cardSwipeAudio.play(0.5f, 0.5f, 0);
            }else {
                cardSwipeAudio.play(0.5f, MathUtils.random(0.9f, 1.1f), 0);
            }
        }
        //sets texture for the card animation
        cardForAnimation.setTexture(visualCard.getTexture());

        drawCard();
        //Sets the new texture to the card
        visualCard.setTexture(new Texture(currentCard.getBearer()));
        //starts the swipe animation
        cardForAnimation.setX(visualCard.getX());
        cardSpeed = -20;

        howManyCardsPlayed++;
        saveGame();

    }
    /*
    Adds swipe right effect of the current card to the attribute int's and starts the card swipe animation
     */
    public void cardSwipeRight() {
        //Adds the card values to the attributes
        sleep += currentCard.getYesSleep();
        hunger += currentCard.getYesHunger();
        social += currentCard.getYesSocial();
        duty += currentCard.getYesDuty();
        //updates card rotations
        //important that this is done before for drawing a new card
        updateRotations();
        //Sound effect for card swipe
        //important that this is done before for drawing a new card
        if(host.sfxOn) {
            if (currentCard.getBearer().equals("satan.png")) {
                cardSwipeAudio.play(0.5f, 0.5f, 0);
            }else {
                cardSwipeAudio.play(0.5f, MathUtils.random(0.9f, 1.1f), 0);
            }
        }
        //sets texture for the card animation
        cardForAnimation.setTexture(visualCard.getTexture());

        drawCard();
        //Sets the new texture to the card
        visualCard.setTexture(new Texture(currentCard.getBearer()));
        //starts the swipe animation
        cardForAnimation.setX(visualCard.getX());
        cardSpeed = 20;

        howManyCardsPlayed++;
        saveGame();

    }

    public void updateRotations() {
        for (int i = 0; i < commonDeck.getDeck().length; i++) {
            if (commonDeck.getDeck()[i].getRotation() < commonDeck.getDeck()[i].getRotationRequirement()) {
                commonDeck.getDeck()[i].setRotation(commonDeck.getDeck()[i].getRotation() + 1);
            }
        }
        commonDeck.getDeck()[currentCard.getIndex()].setRotation(0);
    }
    /*
    Updates the attribute visuals to match the attribute int's, and makes sure the visuals are not an unsuitable size
     */
    public void updateAttributes() {
        if (social <= 0) {
            socialDisplay.setSize(displayWidth , 0);
        } else if (social >= 100) {
            socialDisplay.setSize(displayWidth , 1);
        } else {
            socialDisplay.setSize(displayWidth , displayHeight * (social / 10));
        }

        if (sleep <= 0) {
            sleepDisplay.setSize(displayWidth , 0);
        } else if (sleep >= 100) {
            sleepDisplay.setSize(displayWidth , 1);
        } else {
            sleepDisplay.setSize(displayWidth , displayHeight * (sleep / 10));
        }

        if (hunger <= 0) {
            hungerDisplay.setSize(displayWidth , 0);
        } else if (hunger >= 100) {
            hungerDisplay.setSize(displayWidth , 1);
        } else {
            hungerDisplay.setSize(displayWidth , displayHeight * (hunger / 10));
        }

        if (duty <= 0) {
            dutyDisplay.setSize(displayWidth , 0);
        } else if (duty >= 100) {
            dutyDisplay.setSize(displayWidth , 1);
        }else {
            dutyDisplay.setSize(displayWidth , displayHeight * (duty / 10));
        }
    }

    /**
     * Saves the game to savedGameState.txt
     */
    @SuppressWarnings("NewApi")
    public void saveGame() {
        Gson gson = new Gson();
        GameState gameStateToBeSaved = new GameState(social, sleep, hunger, duty,
                currentCard, howManyCardsPlayed, host.musicOn, host.sfxOn, gameOver, tutorialCompleted, firstDeath, devilItroduced, roundCounter);

        String content = gson.toJson(gameStateToBeSaved);
        FileHandle path = Gdx.files.local("savedGameState.txt");
        path.writeString(content, false);
    }

    /**
     * Draws a new card.
     * Includes some "scripting" so the drawn card is not always random
     */
    public void drawCard(){
        // Checks if any end condition is reached
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
        } else if (howManyCardsPlayed/3 == 31) {
            currentCard = endConditionDeck.getDeck()[8];
         //If the tutorial is not yet completed, plays the tutorial
        } else if(!(tutorialCompleted)) {
                if (scriptCounter == 0) {
                    currentCard = storyDeck.getDeck()[2];
                    scriptCounter++;
                } else if (scriptCounter == 1) {
                    currentCard = storyDeck.getDeck()[3];
                    scriptCounter++;
                } else if (scriptCounter == 2) {
                    currentCard = storyDeck.getDeck()[0];
                    scriptCounter++;
                } else if (scriptCounter == 3) {
                    currentCard = storyDeck.getDeck()[4];
                    tutorialCompleted = true;
                    scriptCounter = 0;
                }
         //Scripted conversation with satan that introduces our lovable villain
        }else if (firstDeath && !devilItroduced && howManyCardsPlayed > 30 && howManyCardsPlayed <= 34) {
            if (scriptCounter == 0) {
                currentCard = storyDeck.getDeck()[5];
                scriptCounter++;
            } else if (scriptCounter == 1) {
                currentCard = storyDeck.getDeck()[6];
                scriptCounter++;
            } else if (scriptCounter == 2) {
                currentCard = storyDeck.getDeck()[7];
                scriptCounter++;
            } else if (scriptCounter == 3) {
                currentCard = storyDeck.getDeck()[8];
                devilItroduced = true;
                scriptCounter = 0;
            }
        //here satan quotes Rolling stones lyrics
        } else if (firstDeath && devilItroduced && howManyCardsPlayed > 60 && howManyCardsPlayed <= 63 && (roundCounter == 4 || roundCounter == 9)) {
            if (scriptCounter == 0) {
                currentCard = storyDeck.getDeck()[9];
                scriptCounter++;
            } else if (scriptCounter == 1) {
                currentCard = storyDeck.getDeck()[10];
                scriptCounter++;
            } else if (scriptCounter == 2) {
                currentCard = storyDeck.getDeck()[11];
               scriptCounter = 0;
            }
         // random interactions with satan
        } else if (firstDeath && devilItroduced && howManyCardsPlayed == 45 && roundCounter == 7) {
            currentCard = storyDeck.getDeck()[12];
        }else if (firstDeath && devilItroduced && howManyCardsPlayed == 12 && roundCounter == 2) {
            currentCard = storyDeck.getDeck()[13];
        }
        else if (firstDeath && devilItroduced && howManyCardsPlayed == 56 && roundCounter == 6) {
            currentCard = storyDeck.getDeck()[14];
        }else if (firstDeath && devilItroduced && howManyCardsPlayed == 81 && roundCounter == 3) {
            currentCard = storyDeck.getDeck()[15];
        }
        //if no "script" is activated, then a random card is drawn
        else {
            currentCard = commonDeck.drawACard();
        }
    }
}

