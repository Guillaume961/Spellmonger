package edu.insightr.spellmonger.model.cards;

import edu.insightr.spellmonger.model.Player;
import javafx.scene.image.Image;


public class MagmaFlood extends Rituol {

    private String effectDescription = "Magma Flood - Deals 2 damage to all non-flying enemy creatures on board";
    private boolean bonus;

    public MagmaFlood(String name, String owner) {
        super(name, owner);
        bonus = false;
        this.setCost(4);
        setImg(new Image(getClass().getResourceAsStream("/img/magma-flood-card.jpg")));
    }

    public MagmaFlood() {
        bonus = false;
        this.setCost(4);
        setImg(new Image(getClass().getResourceAsStream("/img/magma-flood-card.jpg")));
    }

    public void play(Player opponent) {

        boolean emptyOpponentBoard = Creature.getPlayerCreaOnBoard(opponent).isEmpty();
        boolean hasGround = false;
        if(!emptyOpponentBoard) {
            for (Creature crea : Creature.getPlayerCreaOnBoard(opponent)) {
                if (!(crea instanceof Flying)) {
                    hasGround = true;
                    break;
                }
            }
        }
        if(hasGround){
            for (Creature crea : Creature.getPlayerCreaOnBoard(opponent)) {
                if(!(crea instanceof Flying)) {
                    crea.setHp(crea.getHp() - 2);
                    if(crea.getHp()<=0){
                        crea.killCreature(crea);
                    }
                }
            }
            System.out.println(this.getName() + " used, " + opponent.getName() + " non-flying creatures lost 2 HP!");
        }
        else{
            System.out.println(this.getName() + " used, but " + opponent.getName() + " board has no non-flying creatures. Did nothing");
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