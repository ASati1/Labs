package com.example.labs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void openCoinToss(View view){
        Intent openCoinTossIntent = new Intent(getApplicationContext(),CoinTossActivity.class);
        startActivity(openCoinTossIntent);
    }
}