package fi.tuni.tamk.WTME.utils;

/**
 * GameState includes all necessary information for creating a safe file
 */
public class GameState {
    private final int howManyCardsPlayed;
    private final int social;
    private final int sleep;
    private final int hunger;
    private final int duty;
    private final fi.tuni.tamk.WTME.utils.Card currentCard;
    private final boolean musicOn;
    private final boolean sfxOn;
    private final boolean gameOver;
    private final boolean tutorialCompleted;
    private final boolean firstDeath;
    private final boolean devilIntroduced;
    private final int roundCounter;

    public GameState(int social, int sleep, int hunger, int duty, fi.tuni.tamk.WTME.utils.Card currentCard,
                     int howManyCardsPlayed, boolean musicOn, boolean sfxOn, boolean gameOver, boolean tutorialCompleted,
                     boolean firstDeath, boolean devilIntroduced, int roundCounter) {
        this.social = social;
        this.sleep = sleep;
        this.hunger = hunger;
        this.duty = duty;
        this.currentCard = currentCard;
        this.howManyCardsPlayed = howManyCardsPlayed;
        this.musicOn = musicOn;
        this.sfxOn = sfxOn;
        this.gameOver = gameOver;
        this.tutorialCompleted = tutorialCompleted;
        this.firstDeath = firstDeath;
        this.devilIntroduced = devilIntroduced;
        this.roundCounter = roundCounter;
    }

    public int getHowManyCardsPlayed() {
        return howManyCardsPlayed;
    }

    public int getSocial() {
        return social;
    }

    public int getSleep() {
        return sleep;
    }

    public int getHunger() {
        return hunger;
    }

    public int getDuty() {
        return duty;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public boolean isMusicOn() {
        return musicOn;
    }

    public boolean isSfxOn() {
        return sfxOn;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isTutorialCompleted() {
        return tutorialCompleted;
    }

    public boolean isFirstDeath() {
        return firstDeath;
    }

    public boolean isDevilIntroduced() {
        return devilIntroduced;
    }

    public int getRoundCounter() {
        return roundCounter;
    }
}
