package edu.insightr.spellmonger.utils;

import edu.insightr.spellmonger.game.packets.Packet01Disconnect;
import edu.insightr.spellmonger.model.SpellmongerApp;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowHandler implements WindowListener {

    private final SpellmongerApp game;
    //private JFrame frame; //

    public WindowHandler(SpellmongerApp game) {
        this.game = game;
        this.game.frame.addWindowListener(this); //
    }

    @Override
    public void windowActivated(WindowEvent event) {
    }

    @Override
    public void windowClosed(WindowEvent event) {
    }

    @Override
    public void windowClosing(WindowEvent event) {
        Packet01Disconnect packet = new Packet01Disconnect(this.game.getPlayer1().getName());
        packet.writeData(this.game.getSocketClient());
    }

    @Override
    public void windowDeactivated(WindowEvent event) {
    }

    @Override
    public void windowDeiconified(WindowEvent event) {
    }

    @Override
    public void windowIconified(WindowEvent event) {
    }

    @Override
    public void windowOpened(WindowEvent event) {
    }

}