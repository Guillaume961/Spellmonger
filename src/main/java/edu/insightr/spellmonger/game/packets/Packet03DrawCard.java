package edu.insightr.spellmonger.game.packets;

import edu.insightr.spellmonger.game.net.*;

public class Packet03DrawCard extends Packet{

    private String username;

    public Packet03DrawCard(byte[] data){
        super(03);
        this.username = readData(data);
    }

    public Packet03DrawCard(String username){
        super(03);
        this.username = username;
    }

    //@Override
    public byte[] getData(){
        return ("03"+ this.username).getBytes();
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

}
