package edu.insightr.spellmonger.model.cards;


import edu.insightr.spellmonger.model.Player;

public abstract class Rituol extends Card {

    private int cost = 1;
    private boolean bonus = false; // false will indicate it is a malus

    public Rituol(String name, String owner) {
        super(name, owner);
        this.setCost(1);
    }

    public Rituol(String name) {
        super(name);
        this.setCost(1);
    }

    public Rituol() {
        this.setCost(1);
    }

    public abstract void play(Player target);

    public abstract void play(Player target, Player emetter);

    public boolean isBonus() {
        return bonus;
    }


    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public void setCost(int cost) {
        this.cost = cost;
    }
}
