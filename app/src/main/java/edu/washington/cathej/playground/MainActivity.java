package edu.washington.cathej.playground;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
//import com.facebook.AccessToken;
//import com.facebook.Profile;
//import com.facebook.ProfileTracker;

public class MainActivity extends Activity {

    //private AccessToken userToken = AccessToken.getCurrentAccessToken();
    //private ProfileTracker profileTracker;
    //private Profile userProfile = Profile.getCurrentProfile();
    private SharedPreferences sharedPreferences;
    private boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("playGroundPref", Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean("loggedIn", false);
        if (!loggedIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        // Goes to Parent Mode
        Button pModeButton = (Button) findViewById(R.id.pMode);
        pModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("button", "Text updated.");
                Intent intent = new Intent(MainActivity.this, ParentActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        // Goes to Child Mode
        Button cModeButton = (Button) findViewById(R.id.cMode);
        cModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("button", "Text updated.");
            }
        });
    }
}
