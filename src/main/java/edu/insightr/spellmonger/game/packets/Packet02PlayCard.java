package edu.insightr.spellmonger.game.packets;

import edu.insightr.spellmonger.game.net.*;

public class Packet02PlayCard extends Packet{

    private String username;
    //private int x, y;
    private String cardName;

    public Packet02PlayCard(byte[] data){
        super(02);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.cardName = dataArray[1];
    }

    public Packet02PlayCard(String username, String cardName){
        super(02);
        this.username = username;
        this.cardName = cardName;
    }

    //@Override
    public byte[] getData(){
        return ("02"+ this.username +","+this.cardName).getBytes();
    }

    @Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    public String getUsername(){
        return username;
    }

    /*public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }*/

    public String getCardName(){
        return this.cardName;
    }
}
