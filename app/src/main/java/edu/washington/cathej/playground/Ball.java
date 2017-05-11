package edu.washington.cathej.playground;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

import static android.R.attr.animation;

/**
 * Created by catherinejohnson on 5/7/17.
 */

public class Ball extends GameObject {

    private int score;
    private int speed;
    private Random rand = new Random();
    private Bitmap spritesheet;
    private int type;
    private Animation animation = new Animation();
    private int points;
    private long startTime;

    public Ball(Bitmap res, int type, int x, int y, int w, int h, int s, int numFrames) {
        super.x = x;
        super.y = y;
        super.width = w;
        super.height = h;
        score = s;
        speed = 7 + (int) (rand.nextDouble() * score / 30);
        this.type = type;

        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

        for (int i = 0; i < image.length; i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();
    }

    public void update() {
        y -= speed;
        animation.update();
    }

    public void draw(Canvas canvas) {
        try {
            canvas.drawBitmap(animation.getImage(), x, y, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
