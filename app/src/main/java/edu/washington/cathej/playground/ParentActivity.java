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
import android.widget.ImageView;
import android.widget.TabHost;

import static android.os.Build.VERSION_CODES.M;
import static edu.washington.cathej.playground.R.drawable.lbg;

public class ParentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Ben");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Jen");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Fred");
        host.addTab(spec);


        // Branch buttons.
        Button lb = (Button) findViewById(R.id.lbbutton);
        Button rt = (Button) findViewById(R.id.rtbutton);
        Button lt = (Button) findViewById(R.id.ltbutton);
        Button lm = (Button) findViewById(R.id.lmbutton);

        Button lb2 = (Button) findViewById(R.id.lbbutton2);
        Button rt2 = (Button) findViewById(R.id.rtbutton2);
        Button lt2 = (Button) findViewById(R.id.ltbutton2);
        Button lm2 = (Button) findViewById(R.id.lmbutton2);

        Button lb3 = (Button) findViewById(R.id.lbbutton3);
        Button rt3 = (Button) findViewById(R.id.rtbutton3);
        Button lt3 = (Button) findViewById(R.id.ltbutton3);
        Button lm3 = (Button) findViewById(R.id.lmbutton3);

        onClick1(lb, rt, lt, lm);
        onClick2(lb2, rt2, lt2, lm2);
        onClick3(lb3, rt3, lt3, lm3);

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
            goToPreferences("email_password");
            return true;
        } else if (id == R.id.action_childs_profile) {
            goToPreferences("childs_profile");
            return true;
        } else if (id == R.id.action_pin) {
            goToPreferences("pin");
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    public void goToPreferences(String mode) {
        Intent intent = new Intent(ParentActivity.this, PreferencesActivity.class);
        intent.putExtra("mode", mode);
        ParentActivity.this.startActivity(intent);
    }


    public void onClick1(Button lb, Button rt, Button lt, Button lm) {

        lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView gImage = (ImageView) findViewById(R.id.lbg);
                if (gImage.getVisibility() == View.GONE) {
                    gImage.setVisibility(View.VISIBLE);
                } else {
                    gImage.setVisibility(View.GONE);
                }
            }
        });

        rt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView gImage = (ImageView) findViewById(R.id.rtg);
                if (gImage.getVisibility() == View.GONE) {
                    gImage.setVisibility(View.VISIBLE);
                } else {
                    gImage.setVisibility(View.GONE);
                }
            }
        });

        lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView gImage = (ImageView) findViewById(R.id.ltg);
                if (gImage.getVisibility() == View.GONE) {
                    gImage.setVisibility(View.VISIBLE);
                } else {
                    gImage.setVisibility(View.GONE);
                }
            }
        });

        lm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView gImage = (ImageView) findViewById(R.id.lmg);
                if (gImage.getVisibility() == View.GONE) {
                    gImage.setVisibility(View.VISIBLE);
                } else {
                    gImage.setVisibility(View.GONE);
                }
            }
        });
    }

    public void onClick2(Button lb2, Button rt2, Button lt2, Button lm2) {

        lb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView gImage = (ImageView) findViewById(R.id.lbg2);
                if (gImage.getVisibility() == View.GONE) {
                    gImage.setVisibility(View.VISIBLE);
                } else {
                    gImage.setVisibility(View.GONE);
                }
            }
        });

        rt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView gImage = (ImageView) findViewById(R.id.rtg2);
                if (gImage.getVisibility() == View.GONE) {
                    gImage.setVisibility(View.VISIBLE);
                } else {
                    gImage.setVisibility(View.GONE);
                }
            }
        });

        lt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView gImage = (ImageView) findViewById(R.id.ltg2);
                if (gImage.getVisibility() == View.GONE) {
                    gImage.setVisibility(View.VISIBLE);
                } else {
                    gImage.setVisibility(View.GONE);
                }
            }
        });

        lm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView gImage = (ImageView) findViewById(R.id.lmg2);
                if (gImage.getVisibility() == View.GONE) {
                    gImage.setVisibility(View.VISIBLE);
                } else {
                    gImage.setVisibility(View.GONE);
                }
            }
        });
    }

    public void onClick3(Button lb3, Button rt3, Button lt3, Button lm3) {
        lb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView gImage = (ImageView) findViewById(R.id.lbg3);
                if (gImage.getVisibility() == View.GONE) {
                    gImage.setVisibility(View.VISIBLE);
                } else {
                    gImage.setVisibility(View.GONE);
                }
            }
        });

        rt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView gImage = (ImageView) findViewById(R.id.rtg3);
                if (gImage.getVisibility() == View.GONE) {
                    gImage.setVisibility(View.VISIBLE);
                } else {
                    gImage.setVisibility(View.GONE);
                }
            }
        });

        lt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView gImage = (ImageView) findViewById(R.id.ltg3);
                if (gImage.getVisibility() == View.GONE) {
                    gImage.setVisibility(View.VISIBLE);
                } else {
                    gImage.setVisibility(View.GONE);
                }
            }
        });

        lm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView gImage = (ImageView) findViewById(R.id.lmg3);
                if (gImage.getVisibility() == View.GONE) {
                    gImage.setVisibility(View.VISIBLE);
                } else {
                    gImage.setVisibility(View.GONE);
                }
            }
        });
    }

}
