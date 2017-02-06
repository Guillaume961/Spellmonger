package edu.insightr.spellmonger.model.cards;

import javafx.scene.image.Image;

public class Snake extends Creature{

    public Snake(String name, String owner) {
        super(name, owner);
        this.setHp(1);
        this.setAttack(3);
        this.setAlive(true);
        this.setCost(2);
        setImg(new Image(getClass().getResourceAsStream("/img/snake-card.jpg")));
    }

    public Snake(String name) {
        super(name, "");
        this.setHp(1);
        this.setAttack(3);
        this.setAlive(true);
        this.setCost(2);
        setImg(new Image(getClass().getResourceAsStream("/img/snake-card.jpg")));
    }

}
