package edu.washington.cathej.playground;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

import static android.R.attr.name;

public class StoryActivity extends Activity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private int slideNumber;
    private StorySlide[] storySlides = {
            new StorySlide("dialog", new String[] {"Ben: What’s up?",
                      "Kathy: We’re going to play kickball, what to join?"}, null),
            new StorySlide("decision", new String[] {"Want to join?"},
                    new String[] {"Yes, Play", "No"}),
            new StorySlide("dialog", new String[] {"(See kid get pushed down)",
                    "Kid: Ow!"}, null),
            new StorySlide("decision", new String[] {"What should I do?"},
                    new String[] {"Confront Bully", "Get Teacher", "Help Kid", "Do Nothing"})
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_story);

        updateSlide();

        Button continueStory = (Button) findViewById(R.id.continueStory);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        continueStory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        slideNumber++;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("slideNumber", slideNumber);
        editor.commit();
        Log.i("story", "New slide number of " + slideNumber);

        Button thisButton = (Button) v;

        if (slideNumber == 4) {
            editor.putInt("slideNumber", 0);
            editor.commit();
            Log.i("story", "Completed Story! Rseting story back to beginning");
            Intent intent = new Intent(StoryActivity.this, MapActivity.class);
            StoryActivity.this.startActivity(intent);
        } else if (thisButton.getText().toString().toLowerCase().contains("play")){
            Intent intent = new Intent(StoryActivity.this, MiniGameActivity.class);
            StoryActivity.this.startActivity(intent);
        } else {
            updateSlide();
        }
    }


    public void updateSlide() {
        sharedPreferences = getSharedPreferences("playGroundStory", Context.MODE_PRIVATE);
        slideNumber = sharedPreferences.getInt("slideNumber", 0);

        StorySlide slide = storySlides[slideNumber];
        TextView dialogBox = (TextView) findViewById(R.id.dialog);
        Button continueStory = (Button) findViewById(R.id.continueStory);
        LinearLayout buttonSet1 = (LinearLayout) findViewById(R.id.buttonSet1);
        LinearLayout buttonSet2 = (LinearLayout) findViewById(R.id.buttonSet2);

        if (slide.type.equals("dialog")) {
            buttonSet1.setVisibility(View.GONE);
            buttonSet2.setVisibility(View.GONE);
            continueStory.setVisibility(View.VISIBLE);

            dialogBox.setText(slide.dialog[0] + " " + slide.dialog[1]);

        } else if (slide.type.equals("decision")) {
            buttonSet1.setVisibility(View.VISIBLE);
            continueStory.setVisibility(View.GONE);

            Button button1 = (Button) findViewById(R.id.button1);
            Button button2 = (Button) findViewById(R.id.button2);
            button1.setText(slide.buttons[0]);
            button2.setText(slide.buttons[1]);
            dialogBox.setText(slide.dialog[0]);

            if (slide.buttons.length == 4) {
                buttonSet2.setVisibility(View.VISIBLE);

                Button button3 = (Button) findViewById(R.id.button3);
                Button button4 = (Button) findViewById(R.id.button4);
                button3.setText(slide.buttons[2]);
                button4.setText(slide.buttons[3]);
            }
        }
    }

    public class StorySlide implements Serializable {
        // specify tip amount
        public String type;
        public String[] dialog;
        public String[] buttons;


        // constructor to set tip
        public StorySlide(String type, String[] description, String[] buttons){
            this.type = type;
            this.dialog = description;
            this.buttons = buttons;
        }
    }
}
