package com.example.labs;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Activity Lifecycle","onCreate");
    }
    /** Called when the user clicks/touches the button */
    public void openCoinToss(View view){
        Intent openCoinTossIntent = new Intent(getApplicationContext(), CoinTossActivity.class);
        openCoinTossIntent.putExtra("Name+","Value+");
        startActivity(openCoinTossIntent);
    }
    /** Called when the user clicks/touches the button */
    public void openUrl(View view){
        Intent openImplicitIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://goparker.com"));
        startActivity(openImplicitIntent);
    }

}