package edu.washington.cathej.playground;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.provider.Settings;

/**
 * Created by catherinejohnson on 4/25/17.
 */

public class Player extends GameObject{

    private Bitmap image;
    private int score;
    private boolean moving;
    private boolean playing;
    private int newX;
    private int centeringFactor;

    public Player(Bitmap res, int w, int h, int numFrames) {
        height = 30;
        width = res.getWidth();
        centeringFactor = - width / 2 - 20;

        x = GamePanel.WIDTH / 2 + centeringFactor;
        y = GamePanel.HEIGHT - 100;
        dx = 0;
        score = 0;
        image = res;
    }


    public void update()
    {
        x = newX;
    }

    public void setNextX(int xThis) {
        newX = xThis + centeringFactor;
    }

    public void addToScore(int points) {
        score += points;
    }

    public void setMoving(boolean b){moving = b;}
    public boolean getMoving() {return moving;}

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y, null);
    }
    public int getScore(){return score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetScore(){score = 0;}
}