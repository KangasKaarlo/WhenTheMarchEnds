package fi.tuni.tamk.tiko.utils;

import com.badlogic.gdx.math.MathUtils;
import com.google.gson.Gson;
public class Deck {
    Card [] deck;
    Gson gson = new Gson();
    public Deck(){
        Card [] tmp = gson.fromJson(JsonDeck, Card [].class);
        deck = tmp;
    }
    public Card drawACard() {
        Card output = new Card();
        //finds all cards that can be played
        Card[] playableCards = new Card[0];
        for(int i = 0; i < deck.length; i++) {
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
        Card [] fourSelectedCards = new Card[4];
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
        int totalWeightInTheBag = 0;
        for (int i = 0; i < 4; i++) {
            totalWeightInTheBag += fourSelectedCards[i].getWeight();
        }
        int selectedWeight =MathUtils.random(totalWeightInTheBag);
        int previousWeightsCombined = 0;
        for (int i = 0; i < 4; i++) {
            if (selectedWeight < fourSelectedCards[i].getWeight() + previousWeightsCombined) {
                output = fourSelectedCards [i];
            }
            else {
                previousWeightsCombined += fourSelectedCards[i].getWeight();
            }
        }
        return output;
    }
    String JsonDeck = "[\n" +
            " {\n" +
            "   \"index\": 0,\n" +
            "   \"name\": \"snooze\",\n" +
            "   \"rotation\": 5,\n" +
            "   \"bearer\": \"bed.png\",\n" +
            "   \"weight\": 8,\n" +
            "   \"text\": \"You've woken up,but would love to sleep just a little longer. Snooze?\",\n" +
            "   \"requirement\": \"\",\n" +
            "   \"yesSocial\": 0,\n" +
            "   \"yesSleep\": 20,\n" +
            "   \"yesHunger\": -5,\n" +
            "   \"yesDuty\": -10,\n" +
            "   \"yesCustom\": \"\",\n" +
            "   \"noSocial\": 0,\n" +
            "   \"noSleep\": -10,\n" +
            "   \"noHunger\": 0,\n" +
            "   \"noDuty\": 10,\n" +
            "   \"noCustom\": \"\"\n" +
            " },\n" +
            " {\n" +
            "   \"index\": 1,\n" +
            "   \"name\": \"hesburger\",\n" +
            "   \"rotation\": 5,\n" +
            "   \"bearer\": \"phone.png\",\n" +
            "   \"weight\": 8,\n" +
            "   \"text\": \"Your friend calls you and asks if you want to go get a burger with them.\",\n" +
            "   \"requirement\": \"\",\n" +
            "   \"yesSocial\": 10,\n" +
            "   \"yesSleep\": 0,\n" +
            "   \"yesHunger\": 20,\n" +
            "   \"yesDuty\": 0,\n" +
            "   \"yesCustom\": \"\",\n" +
            "   \"noSocial\": -10,\n" +
            "   \"noSleep\": 0,\n" +
            "   \"noHunger\": -20,\n" +
            "   \"noDuty\": 0,\n" +
            "   \"noCustom\": \"\"\n" +
            " },\n" +
            " {\n" +
            "   \"index\": 2,\n" +
            "   \"name\": \"lectureBoring\",\n" +
            "   \"rotation\": 5,\n" +
            "   \"bearer\": \"zoom.png\",\n" +
            "   \"weight\": 12,\n" +
            "   \"text\": \"This online lecture is rather boring and dull, maybe you should go get some snacks?\",\n" +
            "   \"requirement\": \"\",\n" +
            "   \"yesSocial\": 0,\n" +
            "   \"yesSleep\": 0,\n" +
            "   \"yesHunger\": 5,\n" +
            "   \"yesDuty\": -10,\n" +
            "   \"yesCustom\": \"\",\n" +
            "   \"noSocial\": 0,\n" +
            "   \"noSleep\": 0,\n" +
            "   \"noHunger\": -5,\n" +
            "   \"noDuty\": 10,\n" +
            "   \"noCustom\": \"\"\n" +
            " },\n" +
            " {\n" +
            "   \"index\": 3,\n" +
            "   \"name\": \"foodHomemade\",\n" +
            "   \"rotation\": 5,\n" +
            "   \"bearer\": \"food.png\",\n" +
            "   \"weight\": 10,\n" +
            "   \"text\": \"You find you have some ingredients stocked up. Cook?\",\n" +
            "   \"requirement\": \"\",\n" +
            "   \"yesSocial\": 0,\n" +
            "   \"yesSleep\": -5,\n" +
            "   \"yesHunger\": 20,\n" +
            "   \"yesDuty\": 0,\n" +
            "   \"yesCustom\": \"\",\n" +
            "   \"noSocial\": 0,\n" +
            "   \"noSleep\": 0,\n" +
            "   \"noHunger\": -15,\n" +
            "   \"noDuty\": 5,\n" +
            "   \"noCustom\": \"\"\n" +
            " },\n" +
            " {\n" +
            "   \"index\": 4,\n" +
            "   \"name\": \"foodToOrder\",\n" +
            "   \"rotation\": 5,\n" +
            "   \"bearer\": \"food.png\",\n" +
            "   \"weight\": 10,\n" +
            "   \"text\": \"Something tempts you to order some takeout. Proceed?\",\n" +
            "   \"requirement\": \"\",\n" +
            "   \"yesSocial\": -10,\n" +
            "   \"yesSleep\": 0,\n" +
            "   \"yesHunger\": 20,\n" +
            "   \"yesDuty\": 0,\n" +
            "   \"yesCustom\": \"\",\n" +
            "   \"noSocial\": 0,\n" +
            "   \"noSleep\": 0,\n" +
            "   \"noHunger\": 0,\n" +
            "   \"noDuty\": 0,\n" +
            "   \"noCustom\": \"\"\n" +
            " },\n" +
            " {\n" +
            "   \"index\": 5,\n" +
            "   \"name\": \"tired\",\n" +
            "   \"rotation\": 5,\n" +
            "   \"bearer\": \"bed.png\",\n" +
            "   \"weight\": 12,\n" +
            "   \"text\": \"You should be doing homework, but your bed looks rather comfy. Sleep?\",\n" +
            "   \"requirement\": \"\",\n" +
            "   \"yesSocial\": 0,\n" +
            "   \"yesSleep\": 20,\n" +
            "   \"yesHunger\": 0,\n" +
            "   \"yesDuty\": -20,\n" +
            "   \"yesCustom\": \"\",\n" +
            "   \"noSocial\": 0,\n" +
            "   \"noSleep\": -10,\n" +
            "   \"noHunger\": -5,\n" +
            "   \"noDuty\": 15,\n" +
            "   \"noCustom\": \"\"\n" +
            " },\n" +
            " {\n" +
            "   \"index\": 6,\n" +
            "   \"name\": \"breakFromStudying\",\n" +
            "   \"rotation\": 5,\n" +
            "   \"bearer\": \"study.png\",\n" +
            "   \"weight\": 10,\n" +
            "   \"text\": \"Your friend calls you while you are studying. Take a break?\",\n" +
            "   \"requirement\": \"\",\n" +
            "   \"yesSocial\": 15,\n" +
            "   \"yesSleep\": 0,\n" +
            "   \"yesHunger\": 0,\n" +
            "   \"yesDuty\": -10,\n" +
            "   \"yesCustom\": \"\",\n" +
            "   \"noSocial\": -5,\n" +
            "   \"noSleep\": 0,\n" +
            "   \"noHunger\": 0,\n" +
            "   \"noDuty\": 20,\n" +
            "   \"noCustom\": \"\"\n" +
            " },\n" +
            " {\n" +
            "   \"index\": 7,\n" +
            "   \"name\": \"sleepOrStudy\",\n" +
            "   \"rotation\": 5,\n" +
            "   \"bearer\": \"study.png\",\n" +
            "   \"weight\": 10,\n" +
            "   \"text\": \"Your friend invites you to an all night study session. Go?\",\n" +
            "   \"requirement\": \"\",\n" +
            "   \"yesSocial\": 10,\n" +
            "   \"yesSleep\": -20,\n" +
            "   \"yesHunger\": -5,\n" +
            "   \"yesDuty\": 10,\n" +
            "   \"yesCustom\": \"\",\n" +
            "   \"noSocial\": -10,\n" +
            "   \"noSleep\": 10,\n" +
            "   \"noHunger\": 5,\n" +
            "   \"noDuty\": -5,\n" +
            "   \"noCustom\": \"\"\n" +
            " },\n" +
            " {\n" +
            "   \"index\": 8,\n" +
            "   \"name\": \"headache\",\n" +
            "   \"rotation\": 5,\n" +
            "   \"bearer\": \"headache.png\",\n" +
            "   \"weight\": 10,\n" +
            "   \"text\": \"You start feeling a creeping headache. Take a break?\",\n" +
            "   \"requirement\": \"\",\n" +
            "   \"yesSocial\": 0,\n" +
            "   \"yesSleep\": 20,\n" +
            "   \"yesHunger\": 0,\n" +
            "   \"yesDuty\": 0,\n" +
            "   \"yesCustom\": \"\",\n" +
            "   \"noSocial\": -5,\n" +
            "   \"noSleep\": -5,\n" +
            "   \"noHunger\": -5,\n" +
            "   \"noDuty\": 10,\n" +
            "   \"noCustom\": \"\"\n" +
            " },\n" +
            " {\n" +
            "   \"index\": 9,\n" +
            "   \"name\": \"diplomacy\",\n" +
            "   \"rotation\": 5,\n" +
            "   \"bearer\": \"diplomacy.png\",\n" +
            "   \"weight\": 5,\n" +
            "   \"text\": \"You hear in in the news that the diplomat of Netherlands has died in Finnish grounds. Does this unsettle you?\",\n" +
            "   \"requirement\": \"\",\n" +
            "   \"yesSocial\": -20,\n" +
            "   \"yesSleep\": -10,\n" +
            "   \"yesHunger\": -5,\n" +
            "   \"yesDuty\": -5,\n" +
            "   \"yesCustom\": \"\",\n" +
            "   \"noSocial\": 0,\n" +
            "   \"noSleep\": 0,\n" +
            "   \"noHunger\": 0,\n" +
            "   \"noDuty\": 0,\n" +
            "   \"noCustom\": \"\"\n" +
            " },\n" +
            " {\n" +
            "   \"index\": 10,\n" +
            "   \"name\": \"presidentOfFinland\",\n" +
            "   \"rotation\": 5,\n" +
            "   \"bearer\": \"diplomacy.png\",\n" +
            "   \"weight\": 5,\n" +
            "   \"text\": \"The president of Finland changes. Will this unsettle you?\",\n" +
            "   \"requirement\": \"\",\n" +
            "   \"yesSocial\": -20,\n" +
            "   \"yesSleep\": -10,\n" +
            "   \"yesHunger\": -5,\n" +
            "   \"yesDuty\": -5,\n" +
            "   \"yesCustom\": \"\",\n" +
            "   \"noSocial\": 0,\n" +
            "   \"noSleep\": 0,\n" +
            "   \"noHunger\": 0,\n" +
            "   \"noDuty\": 0,\n" +
            "   \"noCustom\": \"\"\n" +
            " },\n" +
            " {\n" +
            "   \"index\": 11,\n" +
            "   \"name\": \"trash\",\n" +
            "   \"rotation\": 5,\n" +
            "   \"bearer\": \"trash.png\",\n" +
            "   \"weight\": 8,\n" +
            "   \"text\": \"What is that nasty smell coming from the kitchen? Should you take out the trash?\",\n" +
            "   \"requirement\": \"\",\n" +
            "   \"yesSocial\": 10,\n" +
            "   \"yesSleep\": 0,\n" +
            "   \"yesHunger\": 0,\n" +
            "   \"yesDuty\": 10,\n" +
            "   \"yesCustom\": \"\",\n" +
            "   \"noSocial\": -10,\n" +
            "   \"noSleep\": 0,\n" +
            "   \"noHunger\": 0,\n" +
            "   \"noDuty\": -10,\n" +
            "   \"noCustom\": \"\"\n" +
            " },\n" +
            " {\n" +
            "   \"index\": 12,\n" +
            "   \"name\": \"groceryStore\",\n" +
            "   \"rotation\": 5,\n" +
            "   \"bearer\": \"food.png\",\n" +
            "   \"weight\": 10,\n" +
            "   \"text\": \"The fridge is looking rather empty. Go to the grocery store?\",\n" +
            "   \"requirement\": \"\",\n" +
            "   \"yesSocial\": 0,\n" +
            "   \"yesSleep\": -5,\n" +
            "   \"yesHunger\": 5,\n" +
            "   \"yesDuty\": 5,\n" +
            "   \"yesCustom\": \"\",\n" +
            "   \"noSocial\": 0,\n" +
            "   \"noSleep\": 0,\n" +
            "   \"noHunger\": -5,\n" +
            "   \"noDuty\": -10,\n" +
            "   \"noCustom\": \"\"\n" +
            " },\n" +
            " {\n" +
            "   \"index\": 13,\n" +
            "   \"name\": \"favoriteBand\",\n" +
            "   \"rotation\": 5,\n" +
            "   \"bearer\": \"band.png\",\n" +
            "   \"weight\": 6,\n" +
            "   \"text\": \"Your favorite band just released a new song. Will you listen it?\",\n" +
            "   \"requirement\": \"\",\n" +
            "   \"yesSocial\": 10,\n" +
            "   \"yesSleep\": 5,\n" +
            "   \"yesHunger\": -5,\n" +
            "   \"yesDuty\": 0,\n" +
            "   \"yesCustom\": \"\",\n" +
            "   \"noSocial\": -10,\n" +
            "   \"noSleep\": 0,\n" +
            "   \"noHunger\": 0,\n" +
            "   \"noDuty\": -5,\n" +
            "   \"noCustom\": \"\"\n" +
            " },\n" +
            " {\n" +
            "   \"index\": 14,\n" +
            "   \"name\": \"cry\",\n" +
            "   \"rotation\": 5,\n" +
            "   \"bearer\": \"cry.png\",\n" +
            "   \"weight\": 6,\n" +
            "   \"text\": \"The urge to listen to emo music and cry hits like a brick. Should you cry your eyes out?\",\n" +
            "   \"requirement\": \"\",\n" +
            "   \"yesSocial\": 0,\n" +
            "   \"yesSleep\": 0,\n" +
            "   \"yesHunger\": 0,\n" +
            "   \"yesDuty\": 0,\n" +
            "   \"yesCustom\": \"\",\n" +
            "   \"noSocial\": 0,\n" +
            "   \"noSleep\": 0,\n" +
            "   \"noHunger\": 0,\n" +
            "   \"noDuty\": -20,\n" +
            "   \"noCustom\": \"\"\n" +
            " },\n" +
            " {\n" +
            "   \"index\": 15,\n" +
            "   \"name\": \"assignment\",\n" +
            "   \"rotation\": 5,\n" +
            "   \"bearer\": \"study.png\",\n" +
            "   \"weight\": 12,\n" +
            "   \"text\": \"Oh no! Your english assignment is near. Should you start doing it?\",\n" +
            "   \"requirement\": \"\",\n" +
            "   \"yesSocial\": 0,\n" +
            "   \"yesSleep\": -10,\n" +
            "   \"yesHunger\": -5,\n" +
            "   \"yesDuty\": 20,\n" +
            "   \"yesCustom\": \"\",\n" +
            "   \"noSocial\": 0,\n" +
            "   \"noSleep\": 5,\n" +
            "   \"noHunger\": -5,\n" +
            "   \"noDuty\": -10,\n" +
            "   \"noCustom\": \"\"\n" +
            " },\n" +
            " {\n" +
            "   \"index\": 16,\n" +
            "   \"name\": \"\",\n" +
            "   \"rotation\": 5,\n" +
            "   \"bearer\": \"dog.png\",\n" +
            "   \"weight\": 5,\n" +
            "   \"text\": \"Your dog shat on the floor during the night. Should you wake up and clean it?\",\n" +
            "   \"requirement\": \"\",\n" +
            "   \"yesSocial\": 15,\n" +
            "   \"yesSleep\": -5,\n" +
            "   \"yesHunger\": 0,\n" +
            "   \"yesDuty\": 0,\n" +
            "   \"yesCustom\": \"\",\n" +
            "   \"noSocial\": -20,\n" +
            "   \"noSleep\": 5,\n" +
            "   \"noHunger\": 0,\n" +
            "   \"noDuty\": 0,\n" +
            "   \"noCustom\": \"\"\n" +
            " },\n" +
            " {\n" +
            "   \"index\": 17,\n" +
            "   \"name\": \"\",\n" +
            "   \"rotation\": 5,\n" +
            "   \"bearer\": \"exercise.png\",\n" +
            "   \"weight\": 3,\n" +
            "   \"text\": \"You've recently gained more weight. Should you go for a walk?\",\n" +
            "   \"requirement\": \"\",\n" +
            "   \"yesSocial\": 5,\n" +
            "   \"yesSleep\": 0,\n" +
            "   \"yesHunger\": -5,\n" +
            "   \"yesDuty\": 0,\n" +
            "   \"yesCustom\": \"\",\n" +
            "   \"noSocial\": -5,\n" +
            "   \"noSleep\": 0,\n" +
            "   \"noHunger\": 5,\n" +
            "   \"noDuty\": 0,\n" +
            "   \"noCustom\": \"\"\n" +
            " },\n" +
            " {\n" +
            "   \"index\": 18,\n" +
            "   \"name\": \"\",\n" +
            "   \"rotation\": 5,\n" +
            "   \"bearer\": \"computer.png\",\n" +
            "   \"weight\": 15,\n" +
            "   \"text\": \"Somebody roasts you on twitter. Should you roast him back?\",\n" +
            "   \"requirement\": \"\",\n" +
            "   \"yesSocial\": -10,\n" +
            "   \"yesSleep\": -5,\n" +
            "   \"yesHunger\": 0,\n" +
            "   \"yesDuty\": 0,\n" +
            "   \"yesCustom\": \"\",\n" +
            "   \"noSocial\": 10,\n" +
            "   \"noSleep\": 5,\n" +
            "   \"noHunger\": 0,\n" +
            "   \"noDuty\": 0,\n" +
            "   \"noCustom\": \"\"\n" +
            " }\n" +
            "]";
}
