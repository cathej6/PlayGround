package edu.washington.cathej.playground;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

import static android.R.attr.animation;

/**
 * Created by catherinejohnson on 5/7/17.
 */
public class Ball extends GameObject{

    private int points;
    private int speed;
    private Random rand = new Random();
    private Bitmap image;

    public Ball(Bitmap res, int x, int y, int w, int h, int p, int speed) {
        super.x = x;
        super.y = y;
        super.width = res.getWidth();
        super.height = res.getHeight();
        points = p;
        this.speed = speed;

        if (speed == 0) {
            this.speed = 5 + rand.nextInt(points / 5);
            // Max missle speed.
            if (this.speed > 40) {
                this.speed = 40;
            }
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

    public int getPoints() {
        return points;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}