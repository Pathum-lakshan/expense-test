package com.example.expense.controller;

import android.content.Context;


import com.example.expense.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class UserController extends AbstractController {


    public static User authenticate(Context context, String userName, String password) throws IOException, JSONException {
        JSONObject userJson = getJsonObject(UserURLPack.LOGIN, UserURLPack.getLoginParameters(userName, password));
        return User.parseUser(userJson);
    }



}
