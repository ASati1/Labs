package com.example.labs;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_DIALOG_RESPONSE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Activity Lifecycle","onCreate");
    }
    /** Called when the user clicks/touches the button */
    public void openCoinToss(View view){
//        Something in response to the button being clicked
        Intent openCoinTossIntent = new Intent(getApplicationContext(), CoinTossActivity.class);
        openCoinTossIntent.putExtra("Name+","Value+");
        startActivityForResult(openCoinTossIntent, REQUEST_DIALOG_RESPONSE);
    }
    /** Called when the user clicks/touches the button */
    public void openUrl(View view){
        Intent openImplicitIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://goparker.com"));
        startActivity(openImplicitIntent);
    }
    /** Called when the user clicks/touches the button*/
    public void openList(View view){
        Intent openExplicitIntent = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(openExplicitIntent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK && requestCode == REQUEST_DIALOG_RESPONSE){
            if(data.hasExtra("ResponseString")){
//                Do something here with the data
                String responseString = data.getExtras().getString("ResponseString");
                Toast.makeText(getApplicationContext(),
                "This is the response we got :" + responseString,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

}