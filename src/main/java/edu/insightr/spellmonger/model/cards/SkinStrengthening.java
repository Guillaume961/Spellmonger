package edu.insightr.spellmonger.model.cards;

import edu.insightr.spellmonger.model.Player;
import javafx.scene.image.Image;


public class SkinStrengthening extends Rituol {

    private String effectDescription = "Skin Strengthening - Gives +1 HP to all your creatures on board";
    private boolean bonus;

    public SkinStrengthening(String name, String owner) {
        super(name, owner);
        bonus = true;
        this.setCost(3);
        setImg(new Image(getClass().getResourceAsStream("/img/skin-strengthening-card.jpg")));
    }

    public SkinStrengthening() {
        bonus = true;
        this.setCost(3);
        setImg(new Image(getClass().getResourceAsStream("/img/skin-strengthening-card.jpg")));
    }

    public void play(Player currentPlayer) {
        if (!Creature.getPlayerCreaOnBoard(currentPlayer).isEmpty()) {
            for (Creature crea : Creature.getPlayerCreaOnBoard(currentPlayer)) {
                crea.setHp(crea.getHp() + 1);
            }
            System.out.println(this.getName() + " used, " + currentPlayer.getName() + " increases his creatures HP by 1");
        } else {
            System.out.println(this.getName() + " used by " + currentPlayer.getName() + " but does nothing because his board is empty");
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
