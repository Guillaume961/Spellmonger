package edu.insightr.spellmonger.model;

import edu.insightr.spellmonger.model.cards.Card;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class DeckTest {
    public class EnergyDrainTest {
        Player p1;
        Player p2;
        Deck d1;
        Random randomGenerator;

        @Before
        public void init() {
            p1 = new Player("p1");
            p2 = new Player("secondPlayer");
            d1 = new Deck(20, "owner1");
            randomGenerator = new Random();
        }

        @Test
        public void checkDeckSize() throws Exception {
            int size = randomGenerator.nextInt(100);
            Deck deck = new Deck(size, "Owner");
            Assert.assertEquals(deck.getSize(), size);
        }

        @Test
        public void changeDeckOwner() throws Exception {
            d1.setDeckOwner("owner2");
            Assert.assertEquals("owner2", d1.getDeckOwner());
        }

        @Test
        public void createDeck() throws Exception {
            List<Card> d1 = p1.getDeckInfo().getDeck();
            List<Card> d2 = p2.getDeckInfo().getDeck();
            assertNotEquals(d1, d2);
        }

        @Test
        public void getDeck() throws Exception {
            p1.getDeckInfo().getDeck();
        }

    }
}