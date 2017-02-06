package edu.insightr.spellmonger.model.cards;


import javafx.scene.image.Image;

public class Wolf extends Creature {

    public Wolf(String name) {
        super(name, "");
        this.setHp(2);
        this.setAttack(2);
        this.setAlive(true);
        this.setCost(2);
        setImg(new Image(getClass().getResourceAsStream("/img/wolf-card.jpg")));
    }

    public Wolf(String name, String owner) {
        super(name, owner);
        this.setHp(2);
        this.setAttack(2);
        this.setAlive(true);
        this.setCost(2);
        setImg(new Image(getClass().getResourceAsStream("/img/wolf-card.jpg")));
    }

}