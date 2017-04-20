package edu.washington.cathej.playground;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ChildActivity extends Activity {

    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        changeFragment("map");

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("button", "Text updated.");
                Intent intent = new Intent(ChildActivity.this, MainActivity.class);
                ChildActivity.this.startActivity(intent);
            }
        });
    }

    public void changeFragment(String mode) {
        Fragment fragmentMode = null;

        if (mode.equals("map")) {
            fragmentMode = new EmailPasswordFragment();
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

}
