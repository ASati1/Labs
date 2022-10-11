package com.example.labs;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class CoinTossActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_toss_xml);
        Log.i(  "Activity Lifecycle", "onCreate");
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
        if (random.nextBoolean()){
            return getString(R.string.coinTossResult1);
        }
        return getString(R.string.coinTossResult2);
    }
}