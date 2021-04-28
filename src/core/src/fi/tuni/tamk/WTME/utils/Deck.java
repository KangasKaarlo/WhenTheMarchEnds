package fi.tuni.tamk.WTME.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.google.gson.Gson;

/**
 * Deck includes a Card array and a function to draw a semi random Card from it
 */
public class Deck {
    fi.tuni.tamk.WTME.utils.Card[] deck;
    Gson gson = new Gson();

    public Deck(FileHandle file) {
        String deckJson = file.readString();
        deck = gson.fromJson(deckJson, fi.tuni.tamk.WTME.utils.Card[].class);
    }


    /**
     * Picks a card from the deck.
     * Cards with larger Weight value have a bigger change to be big
     * @return a card from the deck
     */
    public fi.tuni.tamk.WTME.utils.Card drawACard() {
        fi.tuni.tamk.WTME.utils.Card output = new fi.tuni.tamk.WTME.utils.Card();
        //finds all cards that can be played
        fi.tuni.tamk.WTME.utils.Card[] playableCards = new fi.tuni.tamk.WTME.utils.Card[0];
        for (int i = 0; i < deck.length; i++) {
            if (deck[i].isPlayable()) {
                fi.tuni.tamk.WTME.utils.Card[] tmp = new fi.tuni.tamk.WTME.utils.Card[playableCards.length + 1];
                for (int y = 0; y < playableCards.length; y++) {
                    tmp[y] = playableCards[y];
                }
                tmp[playableCards.length] = deck[i];
                playableCards = tmp;
            }
        }
        //randomly selects four cards from playable cards
        fi.tuni.tamk.WTME.utils.Card[] fourSelectedCards = new fi.tuni.tamk.WTME.utils.Card[4];
        for (int i = 0; i < 4; i++) {
            int selectedCardIndex = MathUtils.random(0, (playableCards.length - 1));
            fourSelectedCards[i] = playableCards[selectedCardIndex];
            int cardsProcessed = 0;
            fi.tuni.tamk.WTME.utils.Card[] tmp = new fi.tuni.tamk.WTME.utils.Card[playableCards.length - 1];
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
