package edu.insightr.spellmonger.model;

import edu.insightr.spellmonger.model.cards.Bear;
import edu.insightr.spellmonger.model.cards.Card;
import edu.insightr.spellmonger.model.cards.Wolf;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ControllerTest {
    Player firstPlayer;

    @Before
    public void init() {
        firstPlayer = new Player("p1");
    }

    @Test
    public void removecardhand() throws Exception {
        List<Card> hand = new ArrayList<>();
        hand.add(new Bear("p1"));
        hand.add(new Wolf("p1"));
        firstPlayer.setHand(hand);
        firstPlayer.getHand().remove(1);

        Assert.assertEquals(firstPlayer.getHand().size(), 1);
    }


    @Test
    public void checkcardhand() throws Exception {
        List<Card> hand = new ArrayList<>();
        Bear b = new Bear("p1");
        hand.add(b);
        hand.add(new Wolf("p1"));
        firstPlayer.setHand(hand);

        Assert.assertEquals(firstPlayer.getHand().get(0), b);
    }

}