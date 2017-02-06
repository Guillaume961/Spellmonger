package edu.insightr.spellmonger.model.cards;


import edu.insightr.spellmonger.model.Player;
import javafx.scene.image.Image;

public class Blessing extends Rituol {

    //TODO : be careful to unused code !

    private String effectDescription = "Blessing - Restores 3 hp to you";
    private boolean bonus;

    public Blessing(String name, String owner) {
        super(name, owner);
        bonus = true;
        this.setCost(1);
        setImg(new Image(getClass().getResourceAsStream("/img/blessing-card.jpg")));
    }


    public void play(Player currentPlayer) {
        int lifeGain = 0;
        if (currentPlayer.getHp() <= 17) {
            currentPlayer.setHp(currentPlayer.getHp() + 3);
            lifeGain = 3;
        } else {
            lifeGain = 20 - currentPlayer.getHp();
            currentPlayer.setHp(20);
        }
        // TODO : don't use the Standard Output, prefer Loggers. Sometimes there's no standard output
        System.out.println(this.getName() + " used, " + currentPlayer.getName() + " has regenerated " + lifeGain + " hp !");
    }

    @Override
    public void play(Player target, Player emetter) {

    }

    public boolean isBonus() {
        return bonus;
    }

}
