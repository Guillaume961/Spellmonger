package edu.insightr.spellmonger.model.cards;

import edu.insightr.spellmonger.model.Player;
import javafx.scene.image.Image;

//import static edu.insightr.spellmonger.model.SpellmongerApp.app;
import static edu.insightr.spellmonger.MenuController.app;

public class Curse extends Rituol {

    private String effectDescription = "Curse - Deals 3 damage to your opponent";
    private boolean bonus;

    public Curse(String name, String owner) {
        super(name, owner);
        bonus = false;
        this.setCost(1);
        setImg(new Image(getClass().getResourceAsStream("/img/curse-card.jpg")));
    }

    public Curse() {
        bonus = false;
        this.setCost(1);
        setImg(new Image(getClass().getResourceAsStream("/img/curse-card.jpg")));
    }

    public void play(Player opponent) {
        //Problem
        if (!Creature.getPlayerCreaOnBoard(opponent).isEmpty()) {
            opponent.setHp(opponent.getHp() - 3);
            System.out.println(this.getName() + " used, " + opponent.getName() + " loses 3 health points !");
            if(opponent.getHp() <= 0){
                app.setOnePlayerDead(true);
                app.setWinner(this.getName());
            }
        }
        else{
            System.out.println(this.getName()+" does nothing because opponent has no creature on board.");
        }
    }

    @Override
    public void play(Player target, Player emetter) {

    }

    public String getEffectDescription() {
        return effectDescription;
    }

    public void setEffectDescription(String effectDescription) {
        this.effectDescription = effectDescription;
    }

    public boolean isBonus() {
        return bonus;
    }

    public void setBonus(boolean bonus) {
        this.bonus = bonus;
    }

}
