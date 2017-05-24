package edu.washington.cathej.playground;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity {

    private SharedPreferences sharedPreferences;
    private boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        sharedPreferences = getSharedPreferences("playGroundPref", Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean("loggedIn", false);

        //Log.i("loggin", "loggedIn bool: " + loggedIn);

        //if (!loggedIn) {
            //Intent intent = new Intent(this, LoginActivity.class);
            //startActivity(intent);
        //}

        // Goes to Parent Mode
        Button pModeButton = (Button) findViewById(R.id.pMode);
        pModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("button", "Text updated.");
                Intent intent = new Intent(MainActivity.this, PinActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        // Goes to Child Mode
        Button cModeButton1 = (Button) findViewById(R.id.cMode1);
        cModeButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("button", "Text updated.");
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        // Goes to Child Mode
        Button cModeButton2 = (Button) findViewById(R.id.cMode2);
        cModeButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("button", "Text updated.");
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }
}
