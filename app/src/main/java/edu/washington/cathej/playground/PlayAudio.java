package edu.washington.cathej.playground;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by catherinejohnson on 5/17/17.
 */

public class PlayAudio extends Service {

    private static final String LOGCAT = null;
    MediaPlayer objPlayer;

    public void onCreate(){
        super.onCreate();
        Log.d(LOGCAT, "Service Started!");
        setAudio("up");
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        objPlayer.start();
        Log.d(LOGCAT, "Media Player started!");
        if(objPlayer.isLooping() != true){
            Log.d(LOGCAT, "Problem in Playing Audio");
        }
        return 1;
    }

    public void onStop(){
        objPlayer.stop();
        objPlayer.release();
    }

    public void onPause(){
        objPlayer.stop();
        objPlayer.release();
    }

    public void onDestroy(){
        objPlayer.stop();
        objPlayer.release();
    }

    public void setAudio(String file) {
        if (file.equals("up")) {
            objPlayer = MediaPlayer.create(this,R.raw.up);
        } else if (file.equals("game_b")) {
            objPlayer = MediaPlayer.create(this, R.raw.game_b);
        } else if (file.equals("beep")) {
            objPlayer = MediaPlayer.create(this,R.raw.beep);
        } else if (file.equals("bomb")) {
            objPlayer = MediaPlayer.create(this,R.raw.bomb);
        }
    }

    @Override
    public IBinder onBind(Intent objIndent) {
        return null;
    }
}
