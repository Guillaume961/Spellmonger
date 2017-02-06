package edu.insightr.spellmonger.model.cards;


import edu.insightr.spellmonger.model.Player;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static edu.insightr.spellmonger.MenuController.app;

public class Creature extends Card {

    public static ArrayList<Creature> allCreatures = new ArrayList<Creature>();
    private static ArrayList<Creature> temp;
    private int hp;
    private int attack;
    private boolean alive;
    private int played;
    private boolean putOnBoard;
    private ImageView pic;

    public Creature(String name, String owner) {
        super(name, owner);
        this.setHp(0);
        this.setAttack(0);
        this.setAlive(true);
        this.setPlayed(0);
        allCreatures.add(this);
        this.setPutOnBoard(false);
    }

    public static int getCreaDamageForPlayer(Player player) {
        int damage = 0;
        List<Creature> creatures = getPlayerCreatures(player.getName());
        for (Creature crea : creatures) {
            damage += crea.getAttack();
        }
        return damage;
    }

    public static void displayGroupOfCrea(List<Creature> listOfCrea) {
        int i = 1;
        //System.out.println("********Displaying the creatures :");
        for (Creature crea : listOfCrea) {
            //System.out.println("Creature " + i + " : " + crea.getName() + " (" + crea.getOwner() + ")");
            i++;
        }
        //System.out.println("********END");
    }

    public static List<Creature> getPlayerCreaOnBoard(Player player) {

        List<Creature> creaOnBoard = (ArrayList<Creature>) allCreatures.clone();

        if (creaOnBoard.isEmpty()) {
            return null;
        } else {
            Iterator<Creature> i = creaOnBoard.iterator();
            while (i.hasNext()) {
                Creature crea = i.next();
                if ((crea.getOwner() != player.getName()) || !(crea.isDraw()) || !(crea.isAlive()) || (player.getHand().contains(crea))) {
                    i.remove();
                }
            }
            return creaOnBoard;
        }
    }

    private static List<Creature> getPlayerCreatures(String playerName) {
        temp = allCreatures; // use clone?
        for (int i = 0; i < temp.size(); i++) {
            if (!temp.get(i).getOwner().equals(playerName)) {
                temp.remove(temp.get(i));
            }
        }
        return temp;
    }

    private static Creature searchGoodSacrifice(int attack, int hp, List<Creature> killableTarg) {
        List<Creature> killableTargets = killableTarg;
        Creature targetWorthSuicide = null;
        ArrayList toRemove = new ArrayList();

        //System.out.print("sacrifice targets are now : ");
        for (Creature crea : killableTargets) {
            //System.out.print(crea.getName() + ", ");
        }
        //System.out.print("\n");

        for (Creature crea : killableTargets) {
            //cannot kill me, not a sacrifice - we only keep targets that kill us
            if ((crea.getAttack() < hp)) {
                //killableTargets.remove(crea);
                toRemove.add(crea);
            }
            if ((crea.getAttack() < attack)) {
                toRemove.add(crea);
            }
            //if attack of target is the same, but target has less life, not worth
            if ((crea.getAttack() == attack) && (crea.getHp() < hp)) {
                toRemove.add(crea);
            }
            //doesnt sacrifice on same stats
            if((crea.getAttack() == attack) && (crea.getHp() == hp)){
                toRemove.add(crea);
            }
        }
        killableTargets.removeAll(toRemove);

        //System.out.print("sacrifice targets are now : ");
        for (Creature crea : killableTargets) {
            //System.out.print(crea.getName() + ", ");
        }
        //System.out.print("\n");

        int maxAttack = 0;
        //we search the targets max attack
        for (Creature crea : killableTargets) {
            if (crea.getAttack() > maxAttack) {
                maxAttack = crea.getAttack();
            }
        }
        toRemove = new ArrayList();
        //we chose our final target taking the one with the 'max attack' and with the most hp
        for (Creature crea : killableTargets) {
            if (crea.getAttack() != maxAttack) {
                //killableTargets.remove(crea);
                toRemove.add(crea);
            }
        }
        killableTargets.removeAll(toRemove);
        int maxLifeWhenMaxAttack = 0;
        for (Creature crea : killableTargets) {
            if (crea.getHp() > maxLifeWhenMaxAttack) {
                maxLifeWhenMaxAttack = crea.getHp();
            }
        }
        for (Creature crea : killableTargets) {
            if (crea.getHp() == maxLifeWhenMaxAttack) {
                targetWorthSuicide = crea;
            }
        }
        return targetWorthSuicide;
    }

    private static Creature findBestTarget(int attack, int hp, Player opponent) {

        ArrayList toRemove = new ArrayList();
        Creature bestTarget = null;
        List<Creature> potentialTargets = new ArrayList<>();
        List<Creature> opponentCrea;
        opponentCrea = getPlayerCreaOnBoard(opponent);

        //we retrieve all opponent creatures on board
        if (opponentCrea == null) {
            return null;
        } else {
            for (Creature crea : opponentCrea) {
                if (crea.getOwner() == opponent.getName()) {
                    potentialTargets.add(crea);
                }
            }
        }

        //we only keep the target we can kill (if they are none, we'll attack opponent)
        for (Creature crea : potentialTargets) {
            //System.out.println(crea.getName() + " attacks = " + crea.getAttack());
            if (crea.getHp() > attack) {
                toRemove.add(crea);
            }
        }
        potentialTargets.removeAll(toRemove);

        List<Creature> potentialTargets0 = new ArrayList<Creature>(potentialTargets);
        Creature worthSacrifice = searchGoodSacrifice(attack, hp, potentialTargets0);
        if (worthSacrifice != null) {
            //System.out.println(" ********** sacrifice found ! Name:" + worthSacrifice.getName() + " / HP:" + worthSacrifice.getHp() + " / Attack:" + worthSacrifice.getAttack() + " ********** ");
            return worthSacrifice;
        }

        toRemove = new ArrayList();
        //we only keep targets that won't kill us while killing them
        for (int i = 0; i < potentialTargets.size(); i++) {
            if (potentialTargets.get(i).getAttack() >= hp) {
                //if (potentialTargets.get(i).getAttack() <= attack) { // was <= attack ... why ? // test < hp : bug
                //potentialTargets.remove(potentialTargets.get(i));
                toRemove.add(potentialTargets.get(i));
                //}
            }
        }
        potentialTargets.removeAll(toRemove);

        //then we choose the one with the highest health
        int healthiest = 0;
        for (int i = 0; i < potentialTargets.size(); i++) {
            if (potentialTargets.get(i).getHp() > healthiest) {
                healthiest = potentialTargets.get(i).getHp();
            }
        }
        for (Creature target : potentialTargets) {
            if (target.getHp() == healthiest)
                bestTarget = target;
        }

        return bestTarget;
    }

    public void killCreature(Creature creatures) {
        allCreatures.remove(this);
        app.getAllCreaOnBoard().remove(this);
        app.getLastDeadCrea().add(this);
        if (this.getOwner().equals(app.getCurrentPlayer().getName())) {
            app.getCurrentPlayer().getDiscard().add(this);
        } else if (this.getOwner().equals(app.getOpponent().getName())) {
            app.getOpponent().getDiscard().add(this);
        }
        app.setIgMsg(app.getIgMsg() + "" +creatures.getOwner() + "'s "+creatures.getName()+" killed " + this.getOwner() + "'s " + this.getName()+"\n");
    }

    /*public void killCreature(Creature creatures) {
        allCreatures.remove(creatures);
        app.getAllCreaOnBoard().remove(creatures);
        app.getLastDeadCrea().add(creatures);
        if (creatures.getOwner().equals(app.getCurrentPlayer().getName())) {
            app.getCurrentPlayer().getDiscard().add(this); //creatures
        } else if (creatures.getOwner().equals(app.getOpponent().getName())) {
            app.getOpponent().getDiscard().add(this); //readded
        }
        //app.setIgMsg(app.getIgMsg() + "" +creatures.getOwner() + "'s "+creatures.getName()+" killed " + this.getOwner() + "'s " + this.getName()+"\n");
        app.setIgMsg(app.getIgMsg() + "" +creatures.getOwner() + "'s "+creatures.getName()+" killed " + this.getOwner() + "'s " + this.getName()+"\n");
    }*/

    @Override
    public String toString() {
        return super.toString() + ", hp = " + this.hp + ", attack = " + this.attack + ", alive = " + this.alive;
    }

    private void attackOpponent(Player opponent) {
        opponent.setHp(opponent.getHp() - this.getAttack());
        if (opponent.getHp() <= 0) {
            opponent.setAlive(false);
            //System.out.println(opponent.getName() + " is dead !");
            app.setOnePlayerDead(true);
        }
    }

    private void attackCreature(Creature creature) {
        creature.setHp(creature.getHp() - this.getAttack());
        this.setHp(this.getHp() - creature.getAttack());
        if (creature.getHp() <= 0) {
            creature.setAlive(false);
            creature.killCreature(this);
            //killCreature((creature));
        }
        if (this.getHp() <= 0) {
            this.setAlive(false);
            this.killCreature(creature);
            //killCreature(this);
        }
    }

    public void attack(Player opponent) {

        //System.out.println("Searching best target for "+this.getName()+" : att:"+this.getAttack()+"/hp:"+this.getHp());
        Creature bestCreaTarget = findBestTarget(this.getAttack(), this.getHp(), opponent);

        if (bestCreaTarget == null) {
            attackOpponent(opponent);
            //System.out.println(this.getName() + " attacks " + opponent.getName() + " and deals " + this.getAttack() + " damage");
        } else {
            attackCreature(bestCreaTarget);
            //System.out.println(this.getName() + " attacks " + bestCreaTarget.getName() + " and deals it " + this.getAttack() + " damage and receives " + bestCreaTarget.getAttack() + " damage");
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int play) {
        this.played = play;
    }

    public boolean isPutOnBoard() {
        return putOnBoard;
    }

    public void setPutOnBoard(boolean putOnBoard) {
        this.putOnBoard = putOnBoard;
    }

    public ImageView getPic() {
        return pic;
    }

    public void setPic(ImageView pic) {
        this.pic = pic;
    }
}