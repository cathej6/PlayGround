package edu.washington.cathej.playground;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.provider.Settings;

/**
 * Created by catherinejohnson on 4/25/17.
 */

public class Player extends GameObject{

    private Bitmap spritesheet;
    private int score;
    private boolean moving;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    private int newX;

    public Player(Bitmap res, int w, int h, int numFrames) {

        x = GamePanel.WIDTH / 2;
        y = GamePanel.HEIGHT * 2 - 10;
        dx = 0;
        score = 0;
        height = h;
        width = w;
        newX = 0;

        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

        for (int i = 0; i < image.length; i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();

    }

    public void setNextX(int xThis) {
        newX = xThis;
    }

    public void setMoving(boolean b){moving = b;}

    public void update()
    {
        x = newX;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }
    public int getScore(){return score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetDX(){dx = 0;}
    public void resetScore(){score = 0;}
    public int getX() {return x;}
}