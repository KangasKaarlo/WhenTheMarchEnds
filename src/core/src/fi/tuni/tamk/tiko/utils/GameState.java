package fi.tuni.tamk.tiko.utils;


public class GameState {
    private final int howManyCardsPlayed;
    private final int social;
    private final int sleep;
    private final int hunger;
    private final int duty;
    private final Card currentCard;
    private final boolean musicOn;
    private final boolean sfxOn;
    private final boolean gameOver;

    public GameState(int social, int sleep, int hunger, int duty, Card currentCard, int howManyCardsPlayed, boolean musicOn, boolean sfxOn, boolean gameOver) {
        this.social = social;
        this.sleep = sleep;
        this.hunger = hunger;
        this.duty = duty;
        this.currentCard = currentCard;
        this.howManyCardsPlayed = howManyCardsPlayed;
        this.musicOn = musicOn;
        this.sfxOn = sfxOn;
        this.gameOver = gameOver;
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
}
