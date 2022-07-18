package com.example.expense.util;



import com.example.expense.controller.BaseController;
import com.example.expense.model.CustomNameValuePair;
import com.example.expense.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class GetToken {
    public static String token (){
        String token = null;
        try {
            ArrayList<CustomNameValuePair> customNameValuePairs = new ArrayList<>();
            customNameValuePairs.add(new CustomNameValuePair("username", "sr101"));
            customNameValuePairs.add(new CustomNameValuePair("password", "123"));


            String response = BaseController.postToServerGzip("http://nbc3.salespad.lk/nbc3test/api/sales/v1/login", customNameValuePairs);



            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getBoolean("result"))
            {


             token =   jsonObject.getString("token");




            }



        } catch (IOException | JSONException e) {

        }
        return token;
    }
}
