package org.Model;

import java.util.ArrayList;

public class User {
    String username;
    String password;
    String phoneNumber;
    String id;
    ArrayList<String> workSpaces = new ArrayList<>();

    public User(String username,String phoneNumber, String password, String id) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.id = id;
    }
}
