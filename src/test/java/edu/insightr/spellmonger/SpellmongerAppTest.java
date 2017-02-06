package edu.insightr.spellmonger;

import edu.insightr.spellmonger.model.cards.Card;
import edu.insightr.spellmonger.model.Player;
import edu.insightr.spellmonger.model.SpellmongerApp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class SpellmongerAppTest {
    SpellmongerApp app;
    Player firstPlayer;
    Player secondPlayer;

    @Before
    public void init() {
        app = new SpellmongerApp();
        app.initPlayer("P1", "secondPlayer");
        firstPlayer = app.getPlayer1();
        secondPlayer = app.getPlayer2();
    }

    @Test
    public void drawFirstTwoCards() throws Exception {
        firstPlayer.getDeckInfo().getDeck().get(1).draw(firstPlayer);
        firstPlayer.getDeckInfo().getDeck().get(1).draw(firstPlayer);

        Assert.assertEquals(2, firstPlayer.getHand().size());
    }

    @Test
    public void drawACard() throws Exception {

    }

    @Test
    public void displayCardInHand() throws Exception {

    }


    @Test
    public void playCard() throws Exception {
        app.setCurrentPlayer(firstPlayer);
        app.setOpponent(secondPlayer);
        app.drawFirstTwoCards();
        Card tmpCard = firstPlayer.getHand().get(0);
        firstPlayer.setEnergy(10);
        app.playCard(firstPlayer.getHand().get(0), firstPlayer, secondPlayer);
        Assert.assertTrue(!firstPlayer.getHand().contains(tmpCard));
    }

    @Test
    public void verifyVaultOverclock() throws Exception {
        firstPlayer.setEnergy(0);
        for (int i = 0; i < 999; i++) {
            SpellmongerApp.verifyVaultOverclock(firstPlayer);
        }
        Assert.assertTrue(firstPlayer.getEnergy() > 300 || firstPlayer.getEnergy() < 400);


    }
}