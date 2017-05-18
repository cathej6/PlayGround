package edu.washington.cathej.playground;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by catherinejohnson on 4/25/17.
 */

public class Background {
    private Bitmap image;

    public Background(Bitmap res) {
        image = res;
    }

    public void update() {
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, 0, 0, null);
    }
}