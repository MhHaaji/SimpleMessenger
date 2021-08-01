package org;

import java.util.ArrayList;

public class User {
    private String phoneNumber;
    private String username;
    private String password;
    private String id;
    private ArrayList<String> workSpaces = new ArrayList<>();
    public static ArrayList<User> allUser = new ArrayList<>();

    public User(String phoneNumber, String username, String password, String id) {
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.id = id;
        allUser.add(this);
    }

    public static User getUserByUserName(String username){
        for (User user : allUser){
            if (user.username.equals(username))
                return user;
        }
        return null;
    }

    public static User getUserByPhoneNumber(String phoneNumber){
        for (User user : allUser){
            if (user.phoneNumber.equals(phoneNumber))
                return user;
        }
        return null;
    }
}
