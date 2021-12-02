package com.example.app_caso1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void depositos (View v)
    {
        startActivity(
                new Intent(
                        MenuActivity.this,
                        DepositosActivity.class
                )
        );
    }

    public void presas (View v)
    {
        startActivity(
                new Intent(
                        MenuActivity.this,
                        PresasActivity.class
                )
        );
    }

    public void tanques (View v)
    {
        startActivity(
                new Intent(
                        MenuActivity.this,
                        TanquesActivity.class
                )
        );
    }

    public void logout (View v)
    {
        startActivity(
                new Intent(
                    MenuActivity.this,
                    MainActivity.class
            )
        );finish();
    }
}