package edu.insightr.spellmonger.model;

import edu.insightr.spellmonger.model.cards.Bear;
import edu.insightr.spellmonger.model.cards.Card;
import org.junit.Test;

import static org.junit.Assert.*;


public class CardTest {

    @Test
    public void declareCard() throws Exception {
        Card card = new Bear("toto", "paul");
        assertEquals("paul", card.getOwner());

    }
}