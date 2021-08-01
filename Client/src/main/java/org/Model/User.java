package org.Model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Stack;

public class User {
    String username;
    String password;
    String phoneNumber;
    String id;
    ArrayList<String> workSpaces = new ArrayList<>();

    public User(String username, String phoneNumber, String password, String id) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.id = id;
    }

    public static User deserializedUser(String json) {
        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);
        System.out.println(user.toString());
        return user;
    }
}
