package org;

import java.util.ArrayList;

public class WorkSpace {
    private String name;
    private String ownHostIP;
    private String ownerUsername;
    private ArrayList<String> membersUsername = new ArrayList<>();
    private int port;



    public WorkSpace(String name, String ownHostIP, String ownerUsername) {
        this.name = name;
        this.ownHostIP = ownHostIP;
        this.ownerUsername = ownerUsername;
    }

}
