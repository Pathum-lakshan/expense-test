package com.example.expense.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class User implements Serializable {

        private int userId;
        private String name;
       private boolean validUser;

    private     User(boolean validUser) {
        this.validUser = validUser;
    }

    public boolean isValidUser() {
        return validUser;
    }

    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
        this.validUser = true;
    }

    public User() {
    }

    public static User parseUser(JSONObject userJsonInstance) throws JSONException {
        if (userJsonInstance == null) {
            return null;
        } else if (!userJsonInstance.getBoolean("result")) {
            return new User(false);
        }
        return new User(
                userJsonInstance.getInt("userId"),
                userJsonInstance.getString("name")
        );
    }

    @Override
    public String toString() {
        return name;
    }
}
