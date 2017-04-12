package edu.washington.cathej.playground;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import static android.R.attr.id;

public class PreferencesActivity extends Activity {

    private ChangePinFragment changePinFragment;
    private ChildProfileFragment childProfileFragment;
    private EmailPasswordFragment emailPasswordFragment;
    private String mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        mode = getIntent().getStringExtra("mode");
        changeFragment(mode);
    }

    public void changeFragment(String mode) {
        Fragment fragmentMode = null;

        if (mode.equals("email_password")) {
            fragmentMode = new EmailPasswordFragment();
        } else if (mode.equals("childs_profile")) {
            fragmentMode = new ChildProfileFragment();
        } else {
            fragmentMode = new ChangePinFragment();
        }
        commitTransaction(fragmentMode);
    }

    public void commitTransaction(Fragment f) {
        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.replace(R.id.content, f);
        tx.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_parent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_email_password) {
            changeFragment("email_password");
            return true;
        } else if (id == R.id.action_childs_profile) {
            changeFragment("childs_profile");
            return true;
        } else if (id == R.id.action_pin) {
            changeFragment("pin");
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}
