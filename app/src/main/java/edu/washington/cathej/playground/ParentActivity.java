package edu.washington.cathej.playground;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import static android.os.Build.VERSION_CODES.M;

public class ParentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("button", "Text updated.");
                Intent intent = new Intent(ParentActivity.this, MainActivity.class);
                ParentActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_math_overview, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_preferences:
                // search action
                goToPreferences();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToPreferences() {
        Intent intent = new Intent(ParentActivity.this, PreferencesActivity.class);
        ParentActivity.this.startActivity(intent);
    }
    */
}
