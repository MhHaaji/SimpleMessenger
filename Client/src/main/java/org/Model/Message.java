package org.Model;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

    public static void serialize() {

    }

    public static void deserialize() {

    }

    public JsonObject getJSONOfThisMessage() {


        String json = "{\"Success\":true,\"Message\":\"Invalid access token.\"}";
        Gson toJ = new Gson();
        Gson fromJ = new Gson();

        String JS = toJ.toJson(this);
        JsonObject object = new JsonObject();

        return fromJ.fromJson(JS, JsonObject.class);


//        Gson gson = new Gson();
//        JsonParser parser = new JsonParser();
//        JsonObject object = new JsonObject();
//        object = (JsonObject) parser.

//        JsonObject jsonObject = new JsonObject;

    }

}
