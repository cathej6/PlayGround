package edu.washington.cathej.playground;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

import static android.R.attr.name;
import static edu.washington.cathej.playground.R.id.button4;
import static edu.washington.cathej.playground.R.id.imageContainer;

public class StoryActivity extends Activity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private int slideNumber;
    private StorySlide[] storySlides = {
            new StorySlide("dialog", new String[] {"Ben: What’s up?",
                      "Kathy: We’re going to play kickball, what to join?"},
                    null, R.drawable.play),
            new StorySlide("decision", new String[] {"Want to join?"},
                    new String[] {"Yes, Play", "No"}, R.drawable.play),
            new StorySlide("dialog", new String[] {"(See kid get pushed down)",
                    "Kid: Ow!"}, null, R.drawable.hurt),
            new StorySlide("decision", new String[] {"What should I do?"},
                    new String[] {"Confront Bully", "Get Teacher", "Help Kid", "Do Nothing"},
                    R.drawable.decision)
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
        FrameLayout imageContainer = (FrameLayout) findViewById(R.id.imageContainer);

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        imageContainer.setBackgroundResource(slide.imageId);

        if (slide.type.equals("dialog")) {
            button1.setVisibility(View.GONE);
            button2.setVisibility(View.GONE);
            button3.setVisibility(View.GONE);
            button4.setVisibility(View.GONE);
            continueStory.setVisibility(View.VISIBLE);

            dialogBox.setText(slide.dialog[0] + " " + slide.dialog[1]);

        } else if (slide.type.equals("decision")) {
            continueStory.setVisibility(View.GONE);
            button3.setVisibility(View.GONE);
            button4.setVisibility(View.GONE);

            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button1.setText(slide.buttons[0]);
            button2.setText(slide.buttons[1]);
            dialogBox.setText(slide.dialog[0]);

            if (slide.buttons.length == 4) {
                button3.setVisibility(View.VISIBLE);
                button4.setVisibility(View.VISIBLE);
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
        public int imageId;



        // constructor to set tip
        public StorySlide(String type, String[] description, String[] buttons,
                          int imageId){
            this.type = type;
            this.dialog = description;
            this.buttons = buttons;
            this.imageId = imageId;
        }
    }
}
