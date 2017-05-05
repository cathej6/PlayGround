package edu.washington.cathej.playground;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by catherinejohnson on 4/25/17.
 */

public class Player extends GameObject{

    private Bitmap spritesheet;
    private int score;
    private boolean up;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;

    public Player(Bitmap res, int w, int h, int numFrames) {

        x = GamePanel.WIDTH / 2;
        y = GamePanel.HEIGHT * 2 - 10;
        dx = 0;
        score = 0;
        height = h;
        width = w;

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

    public void setUp(boolean b){up = b;}

    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>100)
        {
            score++;
            startTime = System.nanoTime();
        }
        animation.update();

        if(up){
            dx -= 1;
        }
        else{
            dx += 1;
        }

        if(dx>14)dx = 14;
        if(dx<-14)dx = -14;

        x += dx * 1.1;

        // Restricts the range the helicopter can go. In this case don't go off the screen!!!
        if (x > GamePanel.WIDTH - 20) {
            x = GamePanel.WIDTH - 20;
        } else if (x < 0) {
            x = 0;
        }
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
}