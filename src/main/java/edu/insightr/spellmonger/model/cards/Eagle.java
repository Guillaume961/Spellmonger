package edu.insightr.spellmonger.model.cards;


import javafx.scene.image.Image;

public class Eagle extends Flying {

    public Eagle(String name) {
        super(name, "");
        this.setHp(1);
        this.setAttack(1);
        this.setAlive(true);
        this.setCost(1);
        setImg(new Image(getClass().getResourceAsStream("/img/eagle-card.jpg")));
    }

    public Eagle(String name, String owner) {
        super(name, owner);
        this.setHp(1);
        this.setAttack(1);
        this.setAlive(true);
        this.setCost(1);
        setImg(new Image(getClass().getResourceAsStream("/img/eagle-card.jpg")));
    }

}
