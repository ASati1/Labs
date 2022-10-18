package com.example.labs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class CoinTossActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_toss);
        Log.i(  "Activity Lifecycle", "onCreate");

        Bundle extras=getIntent().getExtras();
        String name = extras.getString("Name+");
        Toast.makeText(getApplicationContext(),"This is the extra string that we passed in: "+name,
                Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(  "Activity Lifecycle", "onResume");
        TextView coinTossView = (TextView) findViewById(R.id.coinTossView);

        String result = getCoinToss();

        coinTossView.setText(result);
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i( "Activity Lifecycle", "onCreate");
    }
    private String getCoinToss(){
        Random random = new Random();
        if (random.nextBoolean()){
            return getString(R.string.coinTossResult1);
        }
        return getString(R.string.coinTossResult2);
    }
    @Override
    public void finish(){
        Intent data = new Intent();
        TextView coinTossView=(TextView) findViewById(R.id.coinTossView);
        String responseString=coinTossView.getText().toString();
        data.putExtra("ResponseString",responseString);
        setResult(RESULT_OK,data);
        super.finish();
    }
}