package edu.insightr.spellmonger.model.cards;

import edu.insightr.spellmonger.model.Player;
import javafx.scene.image.Image;

public class VaultOverclocking extends Enchantment {

    private String effectDescription = "+1 energy each turn but 35% that the vault overburn and is empty";

    public VaultOverclocking(String name, String owner) {
        super(name, owner);
        this.setCost(3);
        setImg(new Image(getClass().getResourceAsStream("/img/overclock-card.jpg")));
    }

    public VaultOverclocking() {
        this.setCost(3);
        setImg(new Image(getClass().getResourceAsStream("/img/overclock-card.jpg")));
    }

    public void play(Player currentPlayer) {
        currentPlayer.setVaultOverclocking(true);
        System.out.println(currentPlayer.getName() + " overclocks his energy vault !");
    }

    public String getEffectDescription() {
        return effectDescription;
    }

    public void setEffectDescription(String effectDescription) {
        this.effectDescription = effectDescription;
    }

}
