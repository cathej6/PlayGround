package edu.washington.cathej.playground;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by catherinejohnson on 5/7/17.
 */

public class Bomb extends GameObject {
    private int speed;
    private Random rand = new Random();
    private Bitmap image;

    public Bomb(Bitmap res, int x, int y, int w, int h, int numFrames) {
        super.x = x;
        super.y = y;
        super.width = res.getWidth();
        super.height = res.getHeight();

        speed = 7 + (int) (rand.nextDouble() + 10);

        // Max missle speed.
        if(speed > 40) {
            speed = 40;
        }

        image = res;
    }

    public void update() {
        y += speed;
    }

    public void draw(Canvas canvas) {
        try {
            canvas.drawBitmap(image, x, y, null);
        } catch (Exception e) {
        }
    }

    @Override
    public int getWidth() {
        return width - 10;
    }
}