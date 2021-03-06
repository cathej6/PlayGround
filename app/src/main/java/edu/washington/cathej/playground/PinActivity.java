package edu.washington.cathej.playground;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PinActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pin);

        final PinEntryEditText txtPinEntry =
                (PinEntryEditText) findViewById(R.id.txt_pin_entry);
        txtPinEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s,
                                          int start,
                                          int count,
                                          int after) {}

            @Override
            public void onTextChanged(CharSequence s,
                                      int start,
                                      int before,
                                      int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("1234")) {
                    Toast.makeText(PinActivity.this,
                            "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PinActivity.this, ParentActivity.class);
                    PinActivity.this.startActivity(intent);
                } else if (s.length() == "1234".length()) {
                    Toast.makeText(PinActivity.this,
                            "Incorrect", Toast.LENGTH_SHORT).show();
                    txtPinEntry.setText(null);
                }
            }
        });

        if(txtPinEntry.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

    }

}
