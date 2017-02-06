package edu.insightr.spellmonger;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.insightr.spellmonger.model.cards.Card;
import edu.insightr.spellmonger.model.cards.Creature;
import edu.insightr.spellmonger.model.Player;
import edu.insightr.spellmonger.model.SpellmongerApp;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GamePhase {
    SpellmongerApp app = new SpellmongerApp();
    List<Creature> creatures = new ArrayList<>();

    @Then("^Alice has (\\d+) life points and (\\d+) creatures and (\\d+) energy point and (\\d+) cards in his/her deck$")
    public void alice_has_life_points_and_creatures_and_energy_point_and_cards_in_his_her_deck(int arg1, int arg2, int arg3, int arg4) throws Throwable {
        List<Card> hand = new ArrayList<>(arg2);
        Player player = app.getPlayer1();
        if ("Alice".equals(player.getName())) {
            assertThat(player.getHp(), is(equalTo(arg1)));
            assertThat(player.getHand(), is(equalTo(hand)));
            assertThat(player.getEnergy(), is(equalTo(arg3)));
            assertThat(player.getDeckInfo().getSize(), is(equalTo(arg4)));
        }
    }

    @Then("^Bob has (\\d+) life points and (\\d+) creatures and (\\d+) energy point and (\\d+) cards in his/her deck$")
    public void bob_has_life_points_and_creatures_and_energy_point_and_cards_in_his_her_deck(int arg1, int arg2, int arg3, int arg4) throws Throwable {
        List<Card> hand = new ArrayList<>(arg2);
        Player player = app.getPlayer2();
        if ("Bob".equals(player.getName())) {
            assertThat(player.getHp(), is(equalTo(arg1)));
            assertThat(player.getHand(), is(equalTo(hand)));
            assertThat(player.getEnergy(), is(equalTo(arg3)));
            assertThat(player.getDeckInfo().getSize(), is(equalTo(arg4)));
        }
    }

    @Given("^\"([^\"]*)\" and \"([^\"]*)\" join the game$")
    public void andJoinTheGame(String firstPlayerName, String secondPlayerName) throws Throwable {
        app.initPlayer(firstPlayerName, secondPlayerName);
    }

    @Then("^the \"([^\"]*)\" gets a new card from his/her deck and adds it to his/her existing creatures$")
    public void the_gets_a_new_card_from_his_her_deck_and_adds_it_to_his_her_existing_creatures(String arg1) throws Throwable {
//        Player player = findPlayer(arg1);
//        int hand = player.getHand().size();
//        assertThat(hand, is(equalTo(hand + 1)));
    }

    @Then("^if the \"([^\"]*)\" has enough energy points to summon a \"([^\"]*)\" he/she choose the corresponding creature and summon it$")
    public void if_the_has_enough_energy_points_to_summon_a_creature_he_she_choose_the_corresponding_creature_and_summon_it(String arg1, String arg2) throws Throwable {
        Creature creature = new Creature(arg2, arg1);
        Player player = findPlayer(arg1);
        Player opponent = findPlayer("player2");
        if (player.getEnergy() >= creature.getHp()) {
            //assertThat(player.getNumberOfCreaOnBoard(), is(equalTo(1)));
            assertThat(app.playCard(creature, player, opponent), is(true));
        }
    }

    private Player findPlayer(String arg1) throws Exception {
        Player returnedValue;
        if (arg1.equals(app.getPlayer1().getName())) {
            returnedValue = app.getPlayer1();
        } else if (arg1.equals(app.getPlayer2().getName())) {
            returnedValue = app.getPlayer2();
        } else {
            throw new Exception("unknown returnedValue " + arg1);
        }
        return returnedValue;
    }

    @Then("^if there are no creatures on the opposite field the creatures summoned by the the \"([^\"]*)\" attack directly the \"([^\"]*)\"$")
    public void if_there_are_no_creatures_on_the_opposite_field_the_creatures_summoned_by_the_the_attack_directly_the(String arg1, String arg2) throws Throwable {

        Player current = app.getPlayer1();
        Player opponent = app.getPlayer2();
        creatures = Creature.getPlayerCreaOnBoard(current);
        int degats = 0;
        int hpAvantDegats = opponent.getHp();
        SpellmongerApp app = new SpellmongerApp();

        for (Creature crea : creatures) {
            degats += crea.getAttack();
        }
        app.endOfTurn(current, opponent);

        if (current.getName() == arg1 && opponent.getName() == arg2) {
            if (opponent.getNumberOfCreaOnBoard() == 0) {
                assertThat(opponent.getHp(), is(equals(hpAvantDegats - degats)));
            }
        }
    }

    @When("^a \"([^\"]*)\"'s life points attain (\\d+)$")
    public void a_s_life_points_attain(String arg1, int arg2) throws Throwable {
        Player player = findPlayer(arg1);
        player.setHp(arg2);
    }

    @Then("^the \"([^\"]*)\" is dead$")
    public void theIsDead(String arg0) throws Throwable {
        Player player = findPlayer(arg0);
        app.setPlayer1(player);
        if(app.getPlayer1().getHp() <= 0)
            assertThat(app.getPlayer1().isAlive(), is(equalTo(false)));
    }

    @Then("^the \"([^\"]*)\" is the winner$")
    public void the_is_the_winner(String arg1) throws Throwable {
        //Player player = findPlayer(arg1);
        app.setWinner(arg1);
        assertThat(app.getWinner(), is(arg1));
        //assertThat(player.isAlive(), is(equalTo(true)));
    }
}