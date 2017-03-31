package edu.washington.cathej.playground;

import android.app.Activity;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);

        /*if (!this.isLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }*/

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("button", "Text updated.");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    public boolean isLoggedIn() {
        return true;
    }
}
