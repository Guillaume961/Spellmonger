package edu.insightr.spellmonger.model;

import edu.insightr.spellmonger.model.cards.Bear;
import edu.insightr.spellmonger.model.cards.Creature;
import edu.insightr.spellmonger.model.cards.Eagle;
import edu.insightr.spellmonger.model.cards.Wolf;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CreatureTest {
    SpellmongerApp app;
    Player player1,player;
    Bear b;
    Wolf w;
    Eagle e;

    private static final int ATTACK_CREATURE = 6;




    @Before
    public void init() {
        app = new SpellmongerApp();
        app.initPlayer("p1", null);
        player1 = app.getPlayer1();
        b = new Bear("jsp", "p1");
        b = new Bear("jsp2","Player");
        w = new Wolf("jsp","Player");
        e = new Eagle("jsp3","Player");
    }
    @Test
    public void getPlayerCreatures() throws Exception {
        player1.setNumberOfCreaOnBoard(4);
        Assert.assertEquals(player1.getNumberOfCreaOnBoard(), 4);
    }

    @Test
    public void attackOpponent() throws Exception {
        Player player = new Player("Player");
        player.setHp(20);
        player.setHp(player.getHp() - 5);
        Assert.assertEquals(player.getHp(), 15);
    }

    @Test
    public void attackCreature() throws Exception {
        // Class Creature is Abstract, so How can I do it ?

    }

    @Test
    public void getPlayerCreaOnBoard() throws Exception {

        Player p = new Player("Player");
        ArrayList<Creature> list = new ArrayList<Creature>();
        Creature bestCrea = null;
        list.add(w);
        list.add(b);
        list.add(e);

        Creature.allCreatures = list;


        Assert.assertEquals(0,Creature.getPlayerCreaOnBoard(p).size() );


        }

        @Test
    public void findBestTarget() throws Exception {

//        Player firstPlayer = new Player("Player")

            //;

            //List<Creature> list = new ArrayList<Creature>();

            //Creature bestCrea = null;

            //Wolf w = new Wolf("Player");

            //Bear b = new Bear("Player");

            //Eagle e = new Eagle("Player");

            //e.setHp(30);

            //list.add(w);

            //list.add(b);

            //list.add(e);

            //

            //Creature.getPlayerCreaOnBoard(firstPlayer);

            //bestCrea = Creature.findBestTarget(1, 20, firstPlayer);

            //System.out.print("loooooooooool");

            //System.out.print(bestCrea.toString());

            ////Assert.assertEquals(bestCrea,b );

        }

    @Test
    public void attack() throws Exception {

    }

    @Test
    public void getHp() throws Exception {
        Assert.assertEquals(3, b.getHp());

    }

    @Test
    public void setHp() throws Exception {
        b.setHp(4);
        Assert.assertEquals(4, b.getHp());

    }

    @Test
    public void getAttack() throws Exception {
        Assert.assertEquals(3, b.getAttack());

    }

    @Test
    public void setAttack() throws Exception {
        b.setAttack(ATTACK_CREATURE);
        Assert.assertEquals(ATTACK_CREATURE, b.getAttack());

    }

    @Test
    public void isAlive() throws Exception {
        Assert.assertTrue(b.isAlive());

    }

    @Test
    public void setAlive() throws Exception {
        b.setAlive(false);
        Assert.assertTrue(!b.isAlive());

    }

}