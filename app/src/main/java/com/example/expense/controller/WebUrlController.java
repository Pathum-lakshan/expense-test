package com.example.expense.controller;


import java.util.HashMap;

public abstract class WebUrlController {

    public static final String domain = "https://sfa.zestatea.com/watawala_sfa_test/";

    private static final String webServiceURL = domain + "andr_manager/";

    protected static final class UserURLPack {

        public static final String LOGIN = webServiceURL + "login_api.php";

        public static HashMap<String, String> getLoginParameters(
                String userName, String password) {
            HashMap<String, String> parameters = new HashMap<String, String>();
            parameters.put("username", userName);
            parameters.put("password", password);
            return parameters;
        }
    }



}
