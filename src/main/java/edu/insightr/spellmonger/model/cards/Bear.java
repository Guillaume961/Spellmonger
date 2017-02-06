package edu.insightr.spellmonger.model.cards;

import javafx.scene.image.Image;

public class Bear extends Creature {
    // TODO : no gui reference in the model. Don't define the images into the model.
    //private String owner;

    public Bear(String name, String owner) {
        super(name, owner);
        this.setHp(3);
        this.setAttack(3);
        this.setAlive(true);
        this.setCost(3);
        setImg(new Image(getClass().getResourceAsStream("/img/bear-card.jpg")));
    }

    public Bear(String name) {
        super(name, "");
        this.setHp(3);
        this.setAttack(3);
        this.setAlive(true);
        this.setCost(3);
        setImg(new Image(getClass().getResourceAsStream("/img/bear-card.jpg")));
    }
}
