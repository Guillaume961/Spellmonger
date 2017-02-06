package edu.insightr.spellmonger.model;

import edu.insightr.spellmonger.model.cards.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;


public class Deck {

    private Random randomGenerator;

    private String deckOwner; // type Player?
    private int size;
    private List<Card> deck = new ArrayList<Card>();

    Deck(int size, String playerName) {
        this.size = size;
        this.deck = createDeck(size, playerName);
        this.setDeckOwner(playerName);
    }

    private void addToList(List<Card> list, Card card, int dupli) {
        for (int i = 0; i < dupli; i++) {
            card.setOwner(deckOwner);
            list.add(card);
        }
    }

    public List<Card> createDeck(int size, String playerName) {

        List<Card> possibleCards = new ArrayList<>();

        int creatureNumber = (int) (size * 0.6); // ~75% of crea in a deck
        int rituolOrEnchantNumber = (int) (size * 0.4); // ~25% of ritual or enchant in a deck

        int differentCrea = 8;
        int differentRituOrEnch = 6;

        int uniqueCreaNumber = (int) (creatureNumber / differentCrea);
        int uniqueRituolNumber = (int) (rituolOrEnchantNumber / differentRituOrEnch);

        int cardMissingNumber = size - (uniqueCreaNumber * differentCrea + uniqueRituolNumber * differentRituOrEnch);

        for (int i = 0; i < uniqueCreaNumber; i++) {
            Bear bear = new Bear("bear", playerName);
            Eagle eagle = new Eagle("eagle", playerName);
            Wolf wolf = new Wolf("wolf", playerName);
            Fox fox = new Fox("fox", playerName);
            Dragon dragon = new Dragon("dragon", playerName);
            Snake snake = new Snake("snake", playerName);
            Tiger tiger = new Tiger("tiger", playerName);
            Phoenix phoenix = new Phoenix("phoenix", playerName);

            addToList(possibleCards, wolf, uniqueCreaNumber);
            addToList(possibleCards, eagle, uniqueCreaNumber);
            addToList(possibleCards, fox, uniqueCreaNumber);
            addToList(possibleCards, bear, uniqueCreaNumber);
            addToList(possibleCards, dragon, uniqueCreaNumber);
            addToList(possibleCards, snake, uniqueCreaNumber);
            addToList(possibleCards, tiger, uniqueCreaNumber);
            addToList(possibleCards, phoenix, uniqueCreaNumber);
        }

        for (int i = 0; i < uniqueRituolNumber; i++) {
            Curse curse = new Curse("curse", playerName);
            Blessing blessing = new Blessing("blessing", playerName);
            EnergyDrain energyDrain = new EnergyDrain("energy drain");
            VaultOverclocking vault = new VaultOverclocking("vault overclocking", playerName);
            SkinStrengthening skinStrength = new SkinStrengthening("skin strengthening", playerName);
            MagmaFlood magmaFlood = new MagmaFlood("magma flood", playerName);

            addToList(possibleCards, energyDrain, uniqueRituolNumber);
            addToList(possibleCards, blessing, uniqueRituolNumber);
            addToList(possibleCards, curse, uniqueRituolNumber);
            addToList(possibleCards, vault, uniqueRituolNumber);
            addToList(possibleCards, skinStrength, uniqueRituolNumber);
            addToList(possibleCards, magmaFlood, uniqueRituolNumber);
        }

        randomGenerator = new Random();
        int randIndex = randomGenerator.nextInt(uniqueCreaNumber * differentCrea + uniqueRituolNumber * differentRituOrEnch);
        addToList(possibleCards, possibleCards.get(randIndex), cardMissingNumber);

        Collections.shuffle(possibleCards);

        return possibleCards;
    }

    public int getSize() {
        return size;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public String getDeckOwner() {
        return deckOwner;
    }

    public void setDeckOwner(String deckOwner) {
        this.deckOwner = deckOwner;
    }
}