package edu.insightr.spellmonger.game.net;

import edu.insightr.spellmonger.game.packets.*;
import edu.insightr.spellmonger.model.MultiPlayer;
import edu.insightr.spellmonger.model.SpellmongerApp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends Thread {

    private DatagramSocket socket;
    private SpellmongerApp app;
    private List<MultiPlayer> connectedPlayers = new ArrayList<MultiPlayer>();

    public GameServer(SpellmongerApp game){
        this.app = game;
        try {
            this.socket = new DatagramSocket(1331);
        } catch (SocketException e) {
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

            String message = new String(packet.getData());
            //System.out.println(message+" is connecting...");
            /*System.out.println("CLIENT ["+packet.getAddress().getHostAddress()+":"+packet.getPort()+"]> "+message);
            if(message.trim().equalsIgnoreCase("ping")){
                sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
            }*/
            //sendData((message+" (from client)").getBytes(), packet.getAddress(), packet.getPort());
            if(message.trim().equalsIgnoreCase("cardplayed")){
                sendData("opponent played a card".getBytes(), packet.getAddress(), packet.getPort());
            }
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
                System.out.println("["+address.getHostAddress()+":"+port+"] "+((Packet00Login)packet).getUsername()+" has connected...");
                MultiPlayer player = new MultiPlayer(((Packet00Login)packet).getUsername(), address, port);
                /*if(address.getHostAddress().equalsIgnoreCase("127.0.0.1")){
                    player = new MultiPlayer(packet.getUsername(), address, port);
                }
                else{ // exterior player

                }*/
                this.addConnection(player, (Packet00Login)packet);
                /*if(player != null) {
                    this.connectedPlayers.add(player);
                    app.setPlayer1(player); // wrong
                }*/
                break;
            case DISCONNECT:
                packet = new Packet01Disconnect(data);
                System.out.println("["+address.getHostAddress()+":"+port+"] "+((Packet01Disconnect)packet).getUsername()+" has left...");
                this.removeConnection((Packet01Disconnect)packet);
                break;
            case PLAYCARD:
                packet = new Packet02PlayCard(data);
                System.out.println(((Packet02PlayCard)packet).getUsername()+" has played "+(((Packet02PlayCard) packet).getCardName())+" (from gameserver case)");
                this.handlePlay((Packet02PlayCard)packet);
                break;
            case DRAWCARD:
                packet = new Packet03DrawCard(data);
                System.out.println(((Packet03DrawCard)packet).getUsername()+" draws a card - server");
                break;
        }
    }

    public void removeConnection(Packet01Disconnect packet) {
        //MultiPlayer player = getMultiplayer(packet.getUsername());
        this.connectedPlayers.remove(getMultiplayerIndex(packet.getUsername()));
        packet.writeData(this);
    }

    public MultiPlayer getMultiplayer(String username){
        for (MultiPlayer player : this.connectedPlayers){
            if(player.getName().equals(username)){
                return player;
            }
        }
        return null;
    }

    public int getMultiplayerIndex(String username){
        int index =0;
        for (MultiPlayer player : this.connectedPlayers){
            if(player.getName().equals(username)){
                break;
            }
            index++;
        }
        return index;
    }

    public void addConnection(MultiPlayer player, Packet00Login packet) {
        boolean alreadyConnected = false;
        for(MultiPlayer p : this.connectedPlayers){
            if(player.getName().equalsIgnoreCase(p.getName())){
                if(p.ipAddress==null){
                    p.ipAddress=player.ipAddress;
                }
                if(p.port==-1){
                    p.port=player.port;
                }
                alreadyConnected=true;
            }
            else{
                //Packet00Login loginPacket = new Packet00Login(player.getName());
                sendData(packet.getData(), p.ipAddress, p.port);
                packet = new Packet00Login(p.getName());
                sendData(packet.getData(), player.ipAddress, player.port);
            }
        }
        if(!alreadyConnected){
            this.connectedPlayers.add(player);
        }
    }

    public void sendData(byte[] data, InetAddress ipAdress, int port){
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAdress, port);
        try {
            this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDataToAllClients(byte[] data) {
        for (MultiPlayer p : connectedPlayers){
            sendData(data, p.ipAddress, p.port);
        }
    }

    private void handlePlay(Packet02PlayCard packet){
        if(getMultiplayer(packet.getUsername()) != null){
            int index = getMultiplayerIndex(packet.getUsername());
            //this.connectedPlayers.get(index)... = packet.getCardName();
            packet.writeData(this);
        }
    }
}
