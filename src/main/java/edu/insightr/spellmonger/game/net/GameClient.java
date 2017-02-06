package edu.insightr.spellmonger.game.net;

import edu.insightr.spellmonger.game.packets.*;
import edu.insightr.spellmonger.model.MultiPlayer;
import edu.insightr.spellmonger.model.SpellmongerApp;
import edu.insightr.spellmonger.model.cards.Bear;

import java.io.IOException;
import java.net.*;

public class GameClient extends Thread{

    private InetAddress ipAdress;
    private DatagramSocket socket;
    private SpellmongerApp app;

    public GameClient(SpellmongerApp game, String ipAddress){
        this.app = game;
        try {
            this.socket = new DatagramSocket();
            this.ipAdress = InetAddress.getByName(ipAddress);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while(true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
            //String message = new String(packet.getData());
            //System.out.println(message+" has connected!");
        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
        Packet packet = null;
        switch (type){
            default:
            case INVALID:
                break;
            case LOGIN:
                packet = new Packet00Login(data);
                System.out.println("["+address.getHostAddress()+":"+port+"] "+((Packet00Login)packet).getUsername()+" has joined the game...");
                MultiPlayer player = new MultiPlayer(((Packet00Login)packet).getUsername(), address, port);
                app.setPlayer2(player); // add ig
                (app.getPlayer2()).setName(((Packet00Login)packet).getUsername());
                //System.out.println("name of player 2 changed to "+((Packet00Login)packet).getUsername());
                break;
            case DISCONNECT:
                packet = new Packet01Disconnect(data);
                System.out.println("["+address.getHostAddress()+":"+port+"] "+((Packet01Disconnect)packet).getUsername()+" has left the world...");
                app.setPlayer2(null); //should make a function to remove player from game
                break;
            case PLAYCARD:
                packet = new Packet02PlayCard(data);
                handlePlayCard((Packet02PlayCard)packet);
                break;
            case DRAWCARD:
                packet = new Packet03DrawCard(data);
                System.out.println(((Packet03DrawCard)packet).getUsername()+" draws a card - client");
                break;
        }
    }

    public void sendData(byte[] data){
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAdress, 1331);
        try {
            //System.out.println("sending packet on socket > "+packet.getAddress().getHostAddress()+":"+packet.getPort());
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handlePlayCard(Packet02PlayCard packet){
        System.out.println("Handling "+packet.getUsername()+" play of card "+packet.getCardName());
        if(packet.getCardName().equalsIgnoreCase(app.getPlayer1().getName())){
            app.setCurrentPlayer(app.getPlayer1());
        }else{
            app.setCurrentPlayer(app.getPlayer2());
        }
        //app.drawACard(app.getCurrentPlayer(), app.getOpponent());
        app.playCard(new Bear(packet.getCardName()), app.getCurrentPlayer(), app.getOpponent(), false);
        app.endOfTurn(app.getCurrentPlayer(), app.getOpponent());
    }

}
