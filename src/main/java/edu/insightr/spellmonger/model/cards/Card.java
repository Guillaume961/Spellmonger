package edu.insightr.spellmonger.model.cards;

import edu.insightr.spellmonger.model.Player;
import javafx.scene.image.Image;

public class Card {

    private String name;
    private boolean draw;
    private String owner;
    private int cost;
    private int idCode;
    private int cardCreatedCount = 0;
    private Image img;

    public Card(String name, String owner) {
        //Random rand = new Random();
        setName(name);
        setDraw(false);
        setOwner(owner);
        setIdCode(System.identityHashCode(this) + cardCreatedCount);
        cardCreatedCount++;
        //setImg(new Image(getClass().getResourceAsStream("/img/"+this.getName()+"-card.jpg")));
    }

    public Card(String name) {
        setName(name);
        setDraw(false);
        setOwner("");
        setIdCode(this.hashCode());
        cardCreatedCount++;
        //setImg(new Image(getClass().getResourceAsStream("/img/"+name+"-card.jpg")));
    }

    public Card() {
        setName("");
        setDraw(false);
        setOwner("");
        setIdCode(this.hashCode());
        cardCreatedCount++;
    }

    @Override
    public String toString() {
        return "code : " + this.idCode + ", name : " + this.name + ", draw : " + this.draw + ", owner : " + this.owner;
    }

    public void draw(Player player) {
        this.setDraw(true);
        player.getHand().add(this);
        player.getDeckInfo().getDeck().remove(this);
        this.setOwner(player.getName()); //necessary?
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDraw() {
        return draw;
    }

    private void setDraw(boolean draw) {
        this.draw = draw;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    private void setIdCode(int idCode) {
        this.idCode = idCode;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
}
