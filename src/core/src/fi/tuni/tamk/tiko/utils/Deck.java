package fi.tuni.tamk.tiko.utils;

import com.badlogic.gdx.math.MathUtils;

public class Deck {
    Card [] deck;

    private Deck(Card [] deck){
        this.deck = deck;
    }

    public Card[] drawACard() {
        //finds all cards that can be played
        Card[] playableCards = new Card[0];
        for(int i = 0; i < deck.length; i++) {
            if (deck[i].isPlayable()) {
                Card[] tmp = new Card[playableCards.length + 1];
                tmp[playableCards.length] = deck[i];
                playableCards = tmp;
            }
        }
        //randomly selects four cards from playable cards
        Card [] fourSelectedCards = new Card[4];
        for (int i = 0; i < 4; i++) {
            int selectedCardIndex = MathUtils.random(0, playableCards.length -1);
            fourSelectedCards[i] = playableCards[selectedCardIndex];
            int cardsProcessed = 0;
            Card[] tmp = new Card[playableCards.length - 1];
            for (int y = 0; y < playableCards.length; y++) {
                if (!(y == selectedCardIndex)) {
                    tmp[cardsProcessed] = playableCards[y];
                    cardsProcessed++;
                }
            }
            playableCards = tmp;
        }
        return fourSelectedCards;
    }
}
