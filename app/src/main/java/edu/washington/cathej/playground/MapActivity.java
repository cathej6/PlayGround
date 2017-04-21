package edu.washington.cathej.playground;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;


public class MapActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ImageButton one = (ImageButton) findViewById(R.id.imageButton);
        one.setOnClickListener(this);
        ImageButton two = (ImageButton) findViewById(R.id.imageButton2);
        two.setOnClickListener(this);
        ImageButton start = (ImageButton) findViewById(R.id.imageButton3);
        start.setOnClickListener(this);
        ImageButton four = (ImageButton) findViewById(R.id.imageButton4);
        four.setOnClickListener(this);
        ImageButton five = (ImageButton) findViewById(R.id.imageButton5);
        five.setOnClickListener(this);
        ImageButton six = (ImageButton) findViewById(R.id.imageButton6);
        six.setOnClickListener(this);
        ImageButton seven = (ImageButton) findViewById(R.id.imageButton7);
        seven.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MapActivity.this, StoryActivity.class);
        MapActivity.this.startActivity(intent);
    }
}
