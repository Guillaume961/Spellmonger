package edu.insightr.spellmonger.model;
import edu.insightr.spellmonger.model.cards.Card;
import edu.insightr.spellmonger.model.cards.Creature;
import javafx.scene.Node;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static edu.insightr.spellmonger.MenuController.app;

public class MyModel {

    public ArrayList<Creature> checkdeadcrea() { // actually just checks dead crea then call real end of turn method (go rename)
        ArrayList<Creature> temp = new ArrayList<>();
        if (app.getLastDeadCrea().isEmpty()) {
            return temp;
        } else {
             int count=0;
            //System.out.println("last death not empty : size = "+app.getLastDeadCrea().size());
            for (Creature crea : app.getLastDeadCrea()) {
                temp.add(crea);
                count++;
                //System.out.println(crea.getName()+" imageview is : "+crea.getPic())
            }
            return temp;
        }
    }
    public void TurnEndModel()
    {
        app.getLastDeadCrea().clear();
        app.verifyVaultOverclock(app.getOpponent());
        app.endOfTurn(app.getCurrentPlayer(), app.getOpponent());
        app.setTmpPlayer(app.getCurrentPlayer());
        app.setCurrentPlayer(app.getOpponent());
        app.setOpponent(app.getTmpPlayer());
    }
     public boolean CheckWinner()
     {
         if(app.checkIfWinner())
         {
             JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"Le vainqueur est : "+app.getWinner()+"", "Winner",  JOptionPane.PLAIN_MESSAGE);
         }
         return app.checkIfWinner();
     }
    public String MsgIg ()
    {
        return app.getIgMsg();
    }
    public void setMsg(String toto)
    {
        app.setIgMsg(toto);
    }
    public Player getOpponent()
    {
        return app.getOpponent();
    }
    public Player getCurrent()
    {
        return app.getCurrentPlayer();
    }
    public Player getPlayer(int i)
    {
        if(i==1)
        {
          return app.getPlayer1();
        }
        else if (i==2)
        {
            return app.getPlayer2();
        }
        return null;
    }

    public void DrawPlayer(int i)
    {
        if (i ==1)
        {
            app.setCurrentPlayer(app.getPlayer1());
            app.setOpponent(app.getPlayer2());
        }
        if(i==2)
        {
            app.setCurrentPlayer(app.getPlayer2());
            app.setOpponent(app.getPlayer1());
        }
    }

    public void DrawCard(Player Playercurrent, Player Playeropponent)
    {
        app.drawACard(Playercurrent,Playeropponent);
    }
    public int GetSizeHand(int i)
    {
        if(i==1) {
           return app.getCurrentPlayer().getHand().size();
        }
        else if (i==2)
        {
            return app.getOpponent().getHand().size();
        }
        return 0;
    }
    public List<Card> GetHandOpponent()
    {
        return app.getCurrentPlayer().getHand();
    }

    public void CardPlay()
    {
        app.cardPlayed();
    }
    public List<Creature> CheckCreaBoard()
    {
        return app.getAllCreaOnBoard();
    }

    public boolean GoPlayCardModel(Card card, Player currentPlayer, Player opponent, Node cardToMove)
    {
        if (card.getCost() <= currentPlayer.getEnergy()) {
            app.playCard(card, currentPlayer, opponent);
            app.setAllCreaOnBoard(Creature.getPlayerCreaOnBoard(currentPlayer));
            for (Creature crea : Creature.getPlayerCreaOnBoard(opponent)) {
                app.getAllCreaOnBoard().add(crea);
            }
            return true;
        }
        else
        {
            setMsg("You have not enough energy,\n choose another card");
            return false;}
    }
    public void RemoveHandCard(Card card, Player currentPlayer)
    {
        for (int i = 0; i < currentPlayer.getHand().size(); i++) {
            if (card == currentPlayer.getHand().get(i)) {
                currentPlayer.getHand().remove(i);
            }
        }
    }
}
