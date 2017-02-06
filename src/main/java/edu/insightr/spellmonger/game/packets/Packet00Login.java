package edu.insightr.spellmonger.game.packets;

import edu.insightr.spellmonger.game.net.*;

public class Packet00Login extends Packet{

    private String username;

    public Packet00Login(byte[] data){
        super(00);
        this.username = readData(data);
    }

    public Packet00Login(String username){
        super(00);
        this.username = username;
    }

    //@Override
    public byte[] getData(){
        return ("00"+ this.username).getBytes();
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
