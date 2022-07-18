package com.example.expense.controller;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * AbstractController - Performs Basic network operations
 *
 * @author Ceylon Linux (pvt) Ltd
 */

public abstract class AbstractController extends WebUrlController {


    protected AbstractController() {
    }


    public static JSONObject getJsonObjects(String url, HashMap<String, Object> params) {

        StringBuilder sbParams = new StringBuilder();
        StringBuilder result = new StringBuilder();
        String charset = "UTF-8";
        HttpURLConnection conn = null;
        JSONObject jObj = null;
        URL urlObj = null;
        DataOutputStream wr = null;

        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0) {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(String.valueOf(params.get(key)), charset));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }

        Log.d("HTTP Request", "params: " + sbParams);

        try {
            urlObj = new URL(url);

            conn = (HttpURLConnection) urlObj.openConnection();

            conn.setDoOutput(true);

            conn.setRequestMethod("POST");

            conn.setRequestProperty("Accept-Charset", charset);

            conn.setReadTimeout(600000);
            conn.setConnectTimeout(600000);

            conn.connect();

            String paramsString = sbParams.toString();

            wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(paramsString);
            wr.flush();
            wr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            InputStream inn = null;
            try {
                inn = conn.getInputStream();
            } catch (Exception e) {
                Log.wtf("Error", "server not found" + e);
            }
            if (inn != null) {
                InputStream in = new BufferedInputStream(inn);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } else {

                try {
                    String line = "{ \"response\" : -2 }";

                    result.append(line);


                } catch (Exception e) {

                }


            }

            Log.d("HTTP Request", "result: " + result);

        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.disconnect();

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            Log.e("HTTP Request", "Error parsing data " + e);
            Log.w("HTTP Response", result + "");
        }

        // return JSON Object
        return jObj;
    }

    public static JSONObject getJsonObject(String url, HashMap<String, String> params) {

        StringBuilder sbParams = new StringBuilder();
        StringBuilder result = new StringBuilder();
        String charset = "UTF-8";
        HttpURLConnection conn = null;
        JSONObject jObj = null;
        URL urlObj = null;
        DataOutputStream wr = null;

        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0) {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), charset));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }

        Log.d("HTTP Request", "params: " + sbParams);

        try {
            urlObj = new URL(url);

            conn = (HttpURLConnection) urlObj.openConnection();

            conn.setDoOutput(true);

            conn.setRequestMethod("POST");

            conn.setRequestProperty("Accept-Charset", charset);

            conn.setReadTimeout(600000);
            conn.setConnectTimeout(600000);

            conn.connect();

            String paramsString = sbParams.toString();

            wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(paramsString);
            wr.flush();
            wr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            InputStream inn = null;
            try {
                inn = conn.getInputStream();
            } catch (Exception e) {
                Log.wtf("Error", "server not found" + e);
            }
            if (inn != null) {
                InputStream in = new BufferedInputStream(inn);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } else {

                try {
                    String line = "{ \"response\" : -2 }";

                    result.append(line);


                } catch (Exception e) {

                }


            }

            Log.d("HTTP Request", "result: " + result);

        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.disconnect();

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            Log.e("HTTP Request", "Error parsing data " + e);
            Log.w("HTTP Response", result + "");
        }

        // return JSON Object
        return jObj;
    }


}
