package com.example.voteeverything.model;

import java.util.ArrayList;

public class User {
    private String userId;
    private String userName;
    private String email;

    private ArrayList<String> usersPostIDs;

    public User(String userId, String userName, String email, ArrayList<String> usersPostIDs) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.usersPostIDs = new ArrayList<>();

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getUsersPostIDs() {
        return usersPostIDs;
    }

    public void setUsersPostIDs(ArrayList<String> usersPostIDs) {
        this.usersPostIDs = usersPostIDs;
    }
}
