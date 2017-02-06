package edu.insightr.spellmonger.model.cards;

import javafx.scene.image.Image;

public class Phoenix extends Flying{

    public Phoenix(String name, String owner) {
        super(name, owner);
        this.setHp(6);
        this.setAttack(2);
        this.setAlive(true);
        this.setCost(3);
        setImg(new Image(getClass().getResourceAsStream("/img/phoenix-card.jpg")));
    }
}
