package org.Model;


import java.util.ArrayList;

public class Message {
    public static ArrayList<Message> allMessages = new ArrayList<>();
    private String from;
    private String to;
    private String body;
    private String type;
    private int seq;

    public Message(String from, String to, String body, String type, int seq) {
        this.from = from;
        this.to = to;
        this.body = body;
        this.type = type;
        this.seq = seq;
    }

    public static void serialize(){

    }
    public static void deserialize(){

    }

}
