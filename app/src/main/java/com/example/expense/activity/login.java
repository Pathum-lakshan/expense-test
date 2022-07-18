package com.example.expense.activity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expense.R;
import com.example.expense.controller.BaseController;
import com.example.expense.controller.ExpenseController;
import com.example.expense.controller.UserController;
import com.example.expense.model.CustomNameValuePair;
import com.example.expense.model.ExpensesType;
import com.example.expense.model.User;
import com.example.expense.util.GetToken;
import com.example.expense.util.NetworkAvailability;
import com.example.expense.util.SharedPref;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class login extends AppCompatActivity {
    private Intent intent;
    private TextInputEditText textName, textPassword;
    private Button loginButton;
    private SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textName=findViewById(R.id.text_name);
        textPassword=findViewById(R.id.text_password);
        loginButton = findViewById(R.id.button_login);
        sharedPref = new SharedPref(login.this);
        loginButton.setOnClickListener(view -> {

btnLoginClicked();

        });
    }

    private void btnLoginClicked() {

        new Thread() {
            private Handler handler = new Handler();
            private ProgressDialog progressDialog;
            private User user;
            private volatile boolean internetAvailability;

            @Override
            public void run() {
                try {
                    internetAvailability = NetworkAvailability.isInternetAvailable(login.this);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                if (internetAvailability) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog = ProgressDialog.show(login.this, null, "DOWNLOADING DATA...",false);
                        }
                    });
                    try {
                        publishProgress("Authenticating...");

                        user = UserController.authenticate(login.this, textName.getText().toString().trim(), textPassword.getText().toString().trim());



                        // validation
                        if (user != null) {


                            publishProgress("Authenticated");

                        }

                    } catch (Exception e) {


                        e.printStackTrace();

                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (user == null) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(login.this);
                                alertDialogBuilder.setTitle(R.string.app_name);
                                alertDialogBuilder.setMessage("ERROR");
                                alertDialogBuilder.setPositiveButton("OK", null);
                                alertDialogBuilder.show();

                            }else if (user.isValidUser()) {

                                sharedPref.saveUser(user);

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        intent = new Intent(login.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {


                                        try {
                                            ArrayList<CustomNameValuePair> customNameValuePairs = new ArrayList<>();
                                            customNameValuePairs.add(new CustomNameValuePair("username","sr101"));
                                            customNameValuePairs.add(new CustomNameValuePair("password", "123"));


                                            String response = BaseController.postToServerGzip("http://nbc3.salespad.lk/nbc3test/api/sales/v1/login", customNameValuePairs);



                                            JSONObject jsonObject = new JSONObject(response);



                                        } catch (IOException | JSONException e) {
                                            e.printStackTrace();
                                        }

                                        try {
                                            List<CustomNameValuePair> valuePairs = new ArrayList<>();
                                            String response = BaseController.postToServerGzipWithToken(BaseController.baseURL + "get_expenses", valuePairs);
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);


                                                if (jsonObject.getBoolean("result")) {
                                                    JSONArray jsonArray = jsonObject.getJSONArray("expenses");

                                                    ArrayList<ExpensesType> expensesTypes = ExpensesType.getExpensesTypes(jsonArray);

                                                    ExpenseController.ExpensesType(login.this, expensesTypes);


                                                }

                                            } catch (Exception e) {

                                                Log.e("ERROR-E7", e.toString());

                                            }


                                        } catch (Exception e) {
                                            Log.e("ERROR", e.toString());
                                        }

                                    }
                                }).start();


                            }
                            else {

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(login.this);
                                alertDialogBuilder.setTitle(R.string.app_name);
                                alertDialogBuilder.setMessage("INCORRECT USERNAME PASSWORD COMBINATION  ");
                                alertDialogBuilder.setPositiveButton("OK", null);
                                alertDialogBuilder.show();

                            }
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(login.this);
                            alertDialogBuilder.setTitle(R.string.app_name);
                            alertDialogBuilder.setMessage("PLEASE TURN ON MOBILE DATA");
                            alertDialogBuilder.setPositiveButton("OK", null);
                            alertDialogBuilder.show();
                        }
                    });
                }
            }

            private void publishProgress(final String message) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        publishProgress(message);
                      //  Toast.makeText(login.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }.start();
    }


}