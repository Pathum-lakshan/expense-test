package com.example.expense.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expense.R;
import com.example.expense.util.NetworkAvailability;
import com.example.expense.util.SharedPref;

import java.net.UnknownHostException;

public class splash extends AppCompatActivity {
    private Handler handler;
    private Intent intent;
    private ProgressBar progressBar;
    private int i = 0;
    private SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler(Looper.myLooper());
        progressBar = findViewById(R.id.progressBar);

        sharedPref = new SharedPref(splash.this);
        try {
            if (NetworkAvailability.isInternetAvailable(splash.this)) {

                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try {
                            Thread.sleep(2300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        loadLoginActivity();

                    }


                });
                thread.start();

            } else {
                progressBar();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public void progressBar() {
        i = progressBar.getProgress();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    progressBar.setProgress(i);
                    i++;
                    try {
                        if (NetworkAvailability.isInternetAvailable(splash.this)) {
                            break;
                        }

                    } catch (Exception e) {
                    }
                }
                    loadLoginActivity();
            }
        }).start();
    }



    private void loadLoginActivity(){
        handler.post(new Runnable() {
            @Override
            public void run() {


                if (sharedPref.getUser()!=null){
                    intent = new Intent(splash.this, MainActivity.class);
                }else {
                    intent = new Intent(splash.this, login.class);
                }
                startActivity(intent);
                finish();

            }
        });

    }

}