package com.mileem.mileem.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.mileem.mileem.R;
import com.mileem.mileem.networking.DefinitionsDataManager;

import org.json.JSONException;

public class SplashScreenActivity extends BaseActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        context = getApplicationContext();
        try {
            new DefinitionsDataManager().getDefinitions(new DefinitionsDataManager.DefinitionsCallbackHandler() {
                @Override
                public void onComplete() {
                    startMainActivity();
                }

                @Override
                public void onFailure(Error error) {
                    Toast.makeText(context,"Error en la carga inicial de datos.",Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
       /* new Handler().postDelayed(new Runnable() {

            *//*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             *//*

            @Override
            public void run() {
                 startMainActivity();
            }
        }, SPLASH_TIME_OUT);*/
    }

    private void startMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }


}
