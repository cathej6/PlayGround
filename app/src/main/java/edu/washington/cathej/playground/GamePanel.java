package edu.washington.cathej.playground;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by catherinejohnson on 4/25/17.
 */


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final int WIDTH = 581;
    public static final int HEIGHT = 1025;
    public static final int MOVESPEED = -5;
    public Context context;
    private long ballStartTime;
    private long bombStartTime;
    private MainThread thread;
    private Background bg;
    private Player player;
    private ArrayList<Ball> balls;
    private ArrayList<Bomb> bombs;
    private Random rand = new Random();
    private boolean newGameCreated;

    //increase to slow down difficulty progression, decrease to speed up difficulty progression
    private int progressDenom = 20;

    private Explosion explosion;
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

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.skybg));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.player1), 80, 100, 3);
        balls = new ArrayList<Ball>();
        ballStartTime = System.nanoTime();

        bombs = new ArrayList<Bomb>();
        bombStartTime = System.nanoTime();

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

                int ballType = rand.nextInt(2);
                if (ballType == 0) {

                    balls.add(new Ball(BitmapFactory.decodeResource(getResources(), R.drawable.ball1),
                            (int) (rand.nextDouble() * (WIDTH - 150)), -150, 45, 15, 30, 13));
                } else {
                    balls.add(new Ball(BitmapFactory.decodeResource(getResources(), R.drawable.ball2),
                            (int) (rand.nextDouble() * (WIDTH - 150)), -150, 45, 15, 50, 13));
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
                    player.addToScore(balls.get(i).getPoints());
                    playSound();
                }
                //remove ball if it is way off the screen
                if(balls.get(i).getY() >= HEIGHT + 100)
                {
                    balls.remove(i);
                }
            }

            //add bombs on timer
            long bombElapsed = (System.nanoTime()-bombStartTime)/1000000;
            if(bombElapsed >(10000 - player.getScore()/4)){
                bombs.add(new Bomb(BitmapFactory.decodeResource(getResources(),R.drawable.bomb),
                        (int)(rand.nextDouble()*(WIDTH - 150)), -150, 45, 15, 13));

                //reset timer
                bombStartTime = System.nanoTime();
            }


            //loop through every bomb and check collision and remove
            for(int i = 0; i < bombs.size();i++)
            {
                //update ball
                bombs.get(i).update();

                if(collision(bombs.get(i),player))
                {
                    bombs.remove(i);
                    player.setPlaying(false);
                    break;
                }
                //remove ball if it is way off the screen
                if(bombs.get(i).getY() >= HEIGHT + 100)
                {
                    bombs.remove(i);
                }
            }
        } else if (justLaunched) {
            newGame();
            justLaunched = false;
        } else {
            if(!reset)
            {
                newGameCreated = false;
                startReset = System.nanoTime();
                reset = true;
                dissapear = true;
                explosion = new Explosion(BitmapFactory.decodeResource(getResources(),R.drawable.explosion),player.getX() + 20,
                        player.getY() + 30, 100, 100, 25);
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
            for(Ball b: balls)
            {
                b.draw(canvas);
            }

            //draw bombs
            for(Bomb b: bombs)
            {
                b.draw(canvas);
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

        balls.clear();
        bombs.clear();

        player.setY(GamePanel.HEIGHT - 270);

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
        canvas.drawText("POINTS: " + (player.getScore()), 10, 30, paint);
        canvas.drawText("BEST: " + best, WIDTH - 140, 30, paint);


        if(!player.getPlaying() && newGameCreated && reset)
        {
            Paint paint1 = new Paint();
            paint1.setTextSize(40);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("PRESS TO START", WIDTH/2-80, HEIGHT/2, paint1);

            paint1.setTextSize(20);
            canvas.drawText("PRESS AND HOLD TO GO UP", WIDTH/2-80, HEIGHT/2 + 20, paint1);
            canvas.drawText("RELEASE TO GO DOWN", WIDTH/2-80, HEIGHT/2 + 40, paint1);
        }
    }


    public List<Uri> listRingtones() {
        List<Uri> list = new ArrayList<Uri>();

        RingtoneManager manager = new RingtoneManager(context);
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();
        while (cursor.moveToNext()) {
            String title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            Uri ringtoneURI = manager.getRingtoneUri(cursor.getPosition());
            list.add(ringtoneURI);
            // Do something with the title and the URI of ringtone
        }
        return list;
    }

    public void playSound() {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();
    }
}