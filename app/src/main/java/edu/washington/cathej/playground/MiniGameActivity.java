package edu.washington.cathej.playground;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MiniGameActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new GamePanel(this));
        //playAudio(null);
       // MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.up);
        //mediaPlayer.start();
    }
//
//    public void playAudio(View view) {
//        Intent objIntent = new Intent(this, PlayAudio.class);
//        startService(objIntent);
//    }
//
//    public void stopAudio(View view) {
//        Intent objIntent = new Intent(this, PlayAudio.class);
//        stopService(objIntent);
//    }

    @Override
    public void onPause() {
        super.onPause();
        //stopAudio(null);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(MiniGameActivity.this, MapActivity.class);
        MiniGameActivity.this.startActivity(intent);
    }
}
