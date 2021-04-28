package fi.tuni.tamk.WTME.utils;

/**
 * Card is the basic unit of the game and includes a bunch of variables necessary for the game
 */
public class Card {
    //variable to help debugging
    private int index;
    //information held in the card
    private String name;
    private int rotation;
    private int rotationRequirement;
    private String bearer;
    private int weight;
    private String text;
    private String requirement;
    private int yesSocial;
    private int yesSleep;
    private int yesHunger;
    private int yesDuty;
    private String yesCustom;
    private  int noSocial;
    private int noSleep;
    private int noHunger;
    private int noDuty;
    private String noCustom;

    //constructor with all the required information to create a full card
    public Card(int index,
            String name,
            int rotation,
            String bearer,
            int weight,
            String text,
            String requirement,
            int yesSocial,
            int yesSleep,
            int yesHunger,
            int yesDuty,
            String yesCustom,
            int noSocial,
            int noSleep,
            int noHunger,
            int noDuty,
            String noCustom) {
        this.index = index;
        this.name = name;
        this.rotation = rotation;
        this.bearer = bearer;
        this.weight = weight;
        this.text = text;
        this.requirement = requirement;
        this.yesSocial = yesSocial;
        this.yesSleep = yesSleep;
        this.yesHunger = yesHunger;
        this.yesDuty = yesDuty;
        this.yesCustom = yesCustom;
        this.noSocial = noSocial;
        this.noSleep = noSleep;
        this.noHunger = noHunger;
        this.noDuty = noDuty;
        this.noCustom = noCustom;
    }

    public Card() {
        rotationRequirement = 20;
    }
    /*
    Checks if the card has NOT been played within the turn amount given in int rotationRequirement
     */
    public boolean isPlayable(){
        boolean output = false;
        if (rotation == rotationRequirement) {
            output = true;
        }
        return output;
    }

    //setters and getters
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getRotationRequirement() {
        return rotationRequirement;
    }

    public void setRotationRequirement(int rotationRequirement) {
        this.rotation = rotationRequirement;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public int getYesSocial() {
        return yesSocial;
    }

    public void setYesSocial(int yesSocial) {
        this.yesSocial = yesSocial;
    }

    public int getYesSleep() {
        return yesSleep;
    }

    public void setYesSleep(int yesSleep) {
        this.yesSleep = yesSleep;
    }

    public int getYesHunger() {
        return yesHunger;
    }

    public void setYesHunger(int yesHunger) {
        this.yesHunger = yesHunger;
    }

    public int getYesDuty() {
        return yesDuty;
    }

    public void setYesDuty(int yesDuty) {
        this.yesDuty = yesDuty;
    }

    public String getYesCustom() {
        return yesCustom;
    }

    public void setYesCustom(String yesCustom) {
        this.yesCustom = yesCustom;
    }

    public int getNoSocial() {
        return noSocial;
    }

    public void setNoSocial(int noSocial) {
        this.noSocial = noSocial;
    }

    public int getNoSleep() {
        return noSleep;
    }

    public void setNoSleep(int noSleep) {
        this.noSleep = noSleep;
    }

    public int getNoHunger() {
        return noHunger;
    }

    public void setNoHunger(int noHunger) {
        this.noHunger = noHunger;
    }

    public int getNoDuty() {
        return noDuty;
    }

    public void setNoDuty(int noDuty) {
        this.noDuty = noDuty;
    }

    public String getNoCustom() {
        return noCustom;
    }

    public void setNoCustom(String noCustom) {
        this.noCustom = noCustom;
    }
}
