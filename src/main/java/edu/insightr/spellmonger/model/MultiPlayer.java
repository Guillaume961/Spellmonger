package edu.insightr.spellmonger.model;

import java.net.InetAddress;

public class MultiPlayer extends Player {

    public InetAddress ipAddress;
    public int port;

    public MultiPlayer(String name, InetAddress ipAddress, int port){
        super(name);
        this.ipAddress = ipAddress;
        this.port = port;
    }

}
