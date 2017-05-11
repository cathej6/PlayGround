package edu.washington.cathej.playground;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by catherinejohnson on 4/25/17.
 */


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;
    public static final int MOVESPEED = -5;
    public Context context;
    private MainThread thread;
    private Background bg;
    private Player player;
    private Random rand = new Random();
    private boolean newGameCreated;

    //increase to slow down difficulty progression, decrease to speed up difficulty progression
    //private int progressDenom = 20;
    private Explosion explosion;
    private ArrayList<Ball> balls;
    private long ballStartTime;
    private ArrayList<Bomb> bombs;
    private long bombStartTime;
    private long startReset;
    private boolean reset;
    private boolean dissapear;
    private boolean started;
    private int best;
    private boolean justLaunched;



    public GamePanel(Context context)
    {
        super(context);
        this.context = context;

        SharedPreferences sharedPreferences = context.getSharedPreferences("best", Context.MODE_PRIVATE);
        best = sharedPreferences.getInt("best", 0);

        justLaunched = true;
        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        int counter = 0;
        while(retry && counter<1000)
        {
            counter++;
            try{thread.setRunning(false);
                thread.join();
                retry = false;
                thread = null;

            }catch(InterruptedException e){e.printStackTrace();}

        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.grassbg1));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.helicopter), 65, 25, 3);
        balls = new ArrayList<Ball>();
        bombs = new ArrayList<Bomb>();

        thread = new MainThread(getHolder(), this);
        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {


        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(!player.getPlaying() && newGameCreated && reset)
            {
                player.setPlaying(true);
                player.setMoving(true);
            }
            if(player.getPlaying())
            {
                if(!started) {
                    started = true;
                }
                reset = false;
                player.setMoving(true);
            }
            return true;
        }
        if(event.getAction()== MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            if (x > 0) {
                player.setNextX((int) (x));
            }
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            player.setMoving(false);
            return true;
        }

        return super.onTouchEvent(event);
    }

    public void update()

    {
        if(player.getPlaying()) {

            bg.update();
            player.update();

            //add balls on timer
            long ballElapsed = (System.nanoTime()-ballStartTime)/1000000;
            if(ballElapsed >(2000 - player.getScore()/4)){


                //first ball always goes down the middle
                if(balls.size() == 0) {
                    balls.add(new Ball(BitmapFactory.decodeResource(getResources(),R.drawable.
                            google), 1, WIDTH / 2, HEIGHT + 10, 45, 15, 10, 13));
                } else {

                    balls.add(new Ball(BitmapFactory.decodeResource(getResources(),R.drawable.google),
                            2, (int)(rand.nextDouble() * (WIDTH)), HEIGHT + 10, 45, 15, 10, 13));
                }

                //reset timer
                ballStartTime = System.nanoTime();
            }
            //loop through every ball and check collision and remove
            for(int i = 0; i<balls.size();i++)
            {
                //update ball
                balls.get(i).update();

                if(collision(balls.get(i),player))
                {
                    balls.remove(i);
                    player.setPlaying(false);
                    break;
                }
                //remove ball if it is way off the screen
                if(balls.get(i).getX()<-100)
                {
                    balls.remove(i);
                    break;
                }
            }

        } else if (justLaunched) {
            newGame();
            justLaunched = false;
        } else {
            player.resetDX();
            if(!reset)
            {
                newGameCreated = false;
                startReset = System.nanoTime();
                reset = true;
                dissapear = true;
                explosion = new Explosion(BitmapFactory.decodeResource(getResources(),R.drawable.explosion),player.getX(),
                        player.getY()-30, 100, 100, 25);
            }

            explosion.update();
            long resetElapsed = (System.nanoTime() - startReset)/1000000;

            if(resetElapsed > 1500 && !newGameCreated)
            {
                newGame();
            }
        }

    }

    public boolean collision(GameObject a, GameObject b)
    {
        return (Rect.intersects(a.getRectangle(), b.getRectangle()));
    }

    @Override
    public void draw(Canvas canvas)
    {
        final float scaleFactorX = getWidth()/(WIDTH * 1.f);
        final float scaleFactorY = getHeight()/(HEIGHT * 1.f);

        if(canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            if(!dissapear) {
                player.draw(canvas);
            }

            //draw balls
            for(Ball ball: balls) {
                ball.draw(canvas);
            }
            //draw bombs
            for(Bomb bomb: bombs) {
                bomb.draw(canvas);
            }

            //draw explosion
            if(started)
            {
                explosion.draw(canvas);
            }
            drawText(canvas);
            canvas.restoreToCount(savedState);

        }
    }



    public void newGame()
    {
        dissapear = false;


        player.resetDX();
        player.setY(HEIGHT/2);

        balls.clear();
        bombs.clear();

        if(player.getScore() > best)
        {
            best = player.getScore();
            SharedPreferences sharedPreferences = context.getSharedPreferences("best", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("best", best);
            editor.commit();
        }
        player.resetScore();
        newGameCreated = true;
    }


    public void drawText(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("DISTANCE: " + (player.getScore() * 3), 10, 30, paint);
        canvas.drawText("BEST: " + best, WIDTH - 215, 30, paint);


        if(!player.getPlaying() && newGameCreated && reset)
        {
            Paint paint1 = new Paint();
            paint1.setTextSize(40);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("PRESS TO START", WIDTH/2-50, HEIGHT/2, paint1);

            paint1.setTextSize(20);
            canvas.drawText("PRESS AND HOLD TO GO UP", WIDTH/2-50, HEIGHT/2 + 20, paint1);
            canvas.drawText("RELEASE TO GO DOWN", WIDTH/2-50, HEIGHT/2 + 40, paint1);
        }
    }


}