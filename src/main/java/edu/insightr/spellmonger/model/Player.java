package edu.insightr.spellmonger.model;


import edu.insightr.spellmonger.model.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private int hp;
    private int energy;
    private boolean alive;
    private Deck deckInfo;
    private int numberOfCreaOnBoard;
    private boolean vaultOverclocking;
    private List<Card> hand = new ArrayList<>();
    private List<Card> discard = new ArrayList<>();

    private static Player currentPlayer = new Player();
    private static Player currentOpponent = new Player();

    Player() {
        this.setName("");
        this.setHp(20);
        this.setEnergy(1);
        this.setDeckInfo(new Deck(40, ""));
        this.setNumberOfCreaOnBoard(0);
        this.alive = true;
        this.hand = new ArrayList<>(); // A CHANGER (2 card at the start)
        this.discard = new ArrayList<>();
        this.vaultOverclocking = false;
    }

    Player(String name) {
        this.setName(name);
        this.setHp(20);
        this.setEnergy(1);
        this.setDeckInfo(new Deck(40, name));
        this.setNumberOfCreaOnBoard(0);
        this.alive = true;
        this.vaultOverclocking = false;
        this.hand = new ArrayList<>(); // A CHANGER (2 card at the start)
        this.discard = new ArrayList<>();
    }



    public static Player getCurrentOpponent() {
        return currentOpponent;
    }

    public static void setCurrentOpponent(Player currentOpponent) {
        Player.currentOpponent = currentOpponent;
    }

    @Override
    public String toString() {
        return this.getName() + " has " + this.getHp() + " hp and " + this.getEnergy() + " energy.";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getNumberOfCreaOnBoard() {
        return numberOfCreaOnBoard;
    }

    public void setNumberOfCreaOnBoard(int numberOfCreaOnBoard) {
        this.numberOfCreaOnBoard = numberOfCreaOnBoard;
    }

    public Deck getDeckInfo() {
        return deckInfo;
    }

    public void setDeckInfo(Deck deckInfo) {
        this.deckInfo = deckInfo;
    }

    public boolean isVaultOverclocking() {
        return vaultOverclocking;
    }

    public void setVaultOverclocking(boolean vaultOverclocking) {
        this.vaultOverclocking = vaultOverclocking;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public List<Card> getDiscard() {
        return discard;
    }

    public void setDiscard(List<Card> discard) {
        this.discard = discard;
    }
}
