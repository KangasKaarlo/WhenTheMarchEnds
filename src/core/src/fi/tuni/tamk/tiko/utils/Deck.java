package fi.tuni.tamk.tiko.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.google.gson.Gson;


public class Deck {
    Card[] deck;
    Gson gson = new Gson();

    public Deck(FileHandle file) {
        String deckJson = file.readString();
        deck = gson.fromJson(deckJson, Card[].class);
    }



    public Card drawACard() {
        Card output = new Card();
        //finds all cards that can be played
        Card[] playableCards = new Card[0];
        for (int i = 0; i < deck.length; i++) {
            if (deck[i].isPlayable()) {
                Card[] tmp = new Card[playableCards.length + 1];
                for (int y = 0; y < playableCards.length; y++) {
                    tmp[y] = playableCards[y];
                }
                tmp[playableCards.length] = deck[i];
                playableCards = tmp;
            }
        }
        //randomly selects four cards from playable cards
        Card[] fourSelectedCards = new Card[4];
        for (int i = 0; i < 4; i++) {
            int selectedCardIndex = MathUtils.random(0, (playableCards.length - 1));
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
        // Randomly picks one of the four cards.
        // Cards with bigger weight have a bigger change to be picked
        int totalWeightInTheBag = 0;
        for (int i = 0; i < 4; i++) {
            totalWeightInTheBag += fourSelectedCards[i].getWeight();
        }
        int selectedWeight = MathUtils.random(totalWeightInTheBag);
        int previousWeightsCombined = 0;
        for (int i = 0; i < 4; i++) {
            if (selectedWeight <= fourSelectedCards[i].getWeight() + previousWeightsCombined) {
                output = fourSelectedCards[i];
            } else {
                previousWeightsCombined += fourSelectedCards[i].getWeight();
            }
        }

        //returns picked card
        return output;
    }

    public Card[] getDeck() {
        return deck;
    }
}
