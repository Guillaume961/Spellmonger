package edu.insightr.spellmonger.model.cards;

import javafx.scene.image.Image;

public class Tiger extends Creature{

    public Tiger(String name, String owner) {
        super(name, owner);
        this.setHp(3);
        this.setAttack(5);
        this.setAlive(true);
        this.setCost(4);
        setImg(new Image(getClass().getResourceAsStream("/img/tiger-card.jpg")));
    }
}
