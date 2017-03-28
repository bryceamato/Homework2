package edu.up.homework2;

/*
    @author Bryce Amato
    @Version March 2017

    This class creates a scene of a snowman with a text view that displays the name
    of an image that the user selects and 3 seekbars which allow the user to edit rgb values
    of the image selected by the user

    BUGS: This program does not respond to the change in seekbars immediately. This is because
    I did not use a MySurfaceView object to draw it on and therefore was not able to call
    invalidate on it.

    With that being said, the selected picture WILL change colors when the user taps the square
    'tab' button on the bottom of the tablet and goes back into the app.
 */

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.graphics.Canvas;
import static edu.up.homework2.R.id.mySurfaceView;

public class MainActivity extends Activity implements View.OnTouchListener, SurfaceHolder.Callback
{
    //instance variables
    private TextView whichPic;
    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;
    private SurfaceView drawView;
    private TextView redText;
    private TextView greenText;
    private TextView blueText;

    //these are the actual drawings
    private CustomElement element = null;
    private CustomCircle circleHead;
    private CustomCircle circleBody;
    private CustomCircle circleBase;
    private CustomCircle circleSun;
    private CustomRect rectTreeTrunk;
    private CustomCircle circleTree;
    private CustomCircle circleApple;

    //used to draw on the surface view
    private Canvas canvas;

    //helps initiate the canvas, got help from STACK OVERFLOW "HOW TO DRAW ON
    //SURFACE VIEW WITH CANVAS
    private SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //set instance variables
        whichPic = (TextView)findViewById(R.id.mainTextView);
        redSeekBar = (SeekBar)findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar)findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar)findViewById(R.id.blueSeekBar);
        drawView = (SurfaceView) findViewById(mySurfaceView);
        redText = (TextView)findViewById(R.id.redTextView);
        greenText = (TextView)findViewById(R.id.greenTextView);
        blueText = (TextView)findViewById(R.id.blueTextView);

        //sets the surface view to the item being touched
        surfaceHolder = drawView.getHolder();
        drawView.setOnTouchListener(this);
        drawView.setWillNotDraw(false);

        this.invisible();

        //giving values for the elements of the picture to be drawn
        //this uses Nux's custom elements

        circleHead = new CustomCircle("head", 0xFFFFFFFF, 400, 540, 100);
        circleBody = new CustomCircle("body", 0xFFFFFFFF, 400, 840, 200);
        circleBase = new CustomCircle("base", 0xFFFFFFFF, 400, 1340, 300);
        circleSun = new CustomCircle("sun", 0xFFEFFF00, 1300, 300, 200);
        circleTree = new CustomCircle("leaves", 0xFF00FF00, 1150, 900, 330);
        rectTreeTrunk = new CustomRect("tree_trunk", 0xFF8B4513, 1000, 1200, 1300, 1640);
        circleApple = new CustomCircle("apple", 0xFFFF0000, 880, 1130, 30);


        drawView.getHolder().addCallback(this);

        redSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser)
                    {
                        //only draw this if there is a picture selected
                        if(element != null)
                        {
                            //get the initial color of the element
                            int initColor = element.getColor();
                            int initGreen = Color.green(initColor);
                            int initBlue = Color.blue(initColor);
                            seekBar.setProgress(progress);
                            redText.setText("Red: " + progress);
                            greenText.setText("Green: " + initGreen);
                            blueText.setText("Blue: " + initBlue);

                            //set the color to whatever is was plus the new red value and draw
                            element.setColor(Color.rgb(progress, initGreen, initBlue));
                            element.drawMe(canvas);
                        }

                    }
                }
        );//redSeekBar

        greenSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser)
                    {
                        //only draw this if there is a picture selected
                        if(element != null)
                        {
                            //get initial color of element
                            int initColor = element.getColor();
                            int initRed = Color.red(initColor);
                            int initBlue = Color.blue(initColor);
                            redText.setText("Red: " + initRed);
                            greenText.setText("Green: " + progress);
                            blueText.setText("Blue: " + initBlue);

                            //set color to initial colors plus new green value and draw
                            element.setColor(Color.rgb(initRed, progress, initBlue));
                            element.drawMe(canvas);
                        }
                    }
                }
        );//greenSeekBar

        blueSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser)
                    {
                        //only draw this if there is a picture selected
                        if(element != null)
                        {
                            //get initial colors of element
                            int initColor = element.getColor();
                            int initRed = Color.red(initColor);
                            int initGreen = Color.green(initColor);
                            redText.setText("Red: " + initRed);
                            greenText.setText("Green: " + initGreen);
                            blueText.setText("Blue: " + progress);

                            //set color to initial color plus new blue color and draw
                            element.setColor(Color.rgb(initRed, initGreen, progress));
                            element.drawMe(canvas);
                        }
                    }
                }
        );//blueSeekBar
    }//onCreate

    /*
    The initial picture to be drawn at startup
     */
    public void initPicture(Canvas canvas)
    {
        circleBase.drawMe(canvas);
        circleBody.drawMe(canvas);
        circleHead.drawMe(canvas);
        circleSun.drawMe(canvas);
        rectTreeTrunk.drawMe(canvas);
        circleTree.drawMe(canvas);
        circleApple.drawMe(canvas);
    }//initPicture


    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        int x = (int)event.getX();
        int y = (int)event.getY();
        int initRed;
        int initGreen;
        int initBlue;
        int initColor;

        //I am basically overriding the containsPoint method in CustomRect because I
        //mixed up my lefts and rights and didn't realize it until I did it this way
        Rect r = new Rect(1000, 1200, 1300, 1640);

        if(circleBase.containsPoint(x, y))
        {
            whichPic.setText("Snowman's hefty base");
            element = circleBase;

            //set the seekbars to have the color values at time of touch
            this.visible();
            initColor = element.getColor();
            initRed = Color.red(initColor);
            initGreen = Color.green(initColor);
            initBlue = Color.blue(initColor);
            redSeekBar.setProgress(initRed);
            greenSeekBar.setProgress(initGreen);
            blueSeekBar.setProgress(initBlue);
            return true;
        }else if(circleBody.containsPoint(x, y))
        {
            whichPic.setText("Snowman's fit body");
            element = circleBody;

            //set the seekbars to have the color values at time of touch
            this.visible();
            initColor = element.getColor();
            initRed = Color.red(initColor);
            initGreen = Color.green(initColor);
            initBlue = Color.blue(initColor);
            redSeekBar.setProgress(initRed);
            greenSeekBar.setProgress(initGreen);
            blueSeekBar.setProgress(initBlue);
            return true;
        } else if(circleHead.containsPoint(x, y))
        {
            whichPic.setText("Snowman's tiny head");
            element = circleHead;

            //set the seekbars to have the color values at time of touch
            this.visible();
            initColor = element.getColor();
            initRed = Color.red(initColor);
            initGreen = Color.green(initColor);
            initBlue = Color.blue(initColor);
            redSeekBar.setProgress(initRed);
            greenSeekBar.setProgress(initGreen);
            blueSeekBar.setProgress(initBlue);
            return true;
        }else if(r.contains(x, y))
        {
            whichPic.setText("The sturdy tree trunk");
            element = rectTreeTrunk;

            //set the seekbars to have the color values at time of touch
            this.visible();
            initColor = element.getColor();
            initRed = Color.red(initColor);
            initGreen = Color.green(initColor);
            initBlue = Color.blue(initColor);
            redSeekBar.setProgress(initRed);
            greenSeekBar.setProgress(initGreen);
            blueSeekBar.setProgress(initBlue);
            return true;
        }else if(circleSun.containsPoint(x, y))
        {
            whichPic.setText("The beautiful sun");
            element = circleSun;

            //set the seekbars to have the color values at time of touch
            this.visible();
            initColor = element.getColor();
            initRed = Color.red(initColor);
            initGreen = Color.green(initColor);
            initBlue = Color.blue(initColor);
            redSeekBar.setProgress(initRed);
            greenSeekBar.setProgress(initGreen);
            blueSeekBar.setProgress(initBlue);
            return true;

        }else if(circleTree.containsPoint(x, y))
        {
            whichPic.setText("Luscious tree leaves");
            element = circleTree;

            //set the seekbars to have the color values at time of touch
            this.visible();
            initColor = element.getColor();
            initRed = Color.red(initColor);
            initGreen = Color.green(initColor);
            initBlue = Color.blue(initColor);
            redSeekBar.setProgress(initRed);
            greenSeekBar.setProgress(initGreen);
            blueSeekBar.setProgress(initBlue);
            return true;

        }else if(circleApple.containsPoint(x, y))
        {
            whichPic.setText("Delicious apple");
            element = circleApple;

            //set the seekbars to have the color values at time of touch
            this.visible();
            initColor = element.getColor();
            initRed = Color.red(initColor);
            initGreen = Color.green(initColor);
            initBlue = Color.blue(initColor);
            redSeekBar.setProgress(initRed);
            greenSeekBar.setProgress(initGreen);
            blueSeekBar.setProgress(initBlue);
            return true;

        }else
        {
            //if the user touches an area that isn't a picture, let them know
            whichPic.setText("Nothing selected");
            element = null;
            this.invisible();
            redText.setText("");
            greenText.setText("");
            blueText.setText("");
            return true;
        }
    }//onTouch

    /*
    Helper method to set all of the seekbars to INVISIBLE
     */
    public void invisible()
    {
        redSeekBar.setVisibility(View.INVISIBLE);
        greenSeekBar.setVisibility(View.INVISIBLE);
        blueSeekBar.setVisibility(View.INVISIBLE);
    }//invisible


    /*
    Helper method to set all of the seekbars to VISIBLE
     */
    public void visible()
    {
        redSeekBar.setVisibility(View.VISIBLE);
        greenSeekBar.setVisibility(View.VISIBLE);
        blueSeekBar.setVisibility(View.VISIBLE);
    }//visible

    /*
    I got the following help from stack overflow. It was in the same page as the
    piece about canvas earlier on.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        tryDrawing(holder);
    }//surfaceCreated

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        tryDrawing(holder);
    }//surfaceChanged

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        //do nothing
    }//surfaceDestroyed

    public void tryDrawing(SurfaceHolder holder)
    {
        canvas = surfaceHolder.lockCanvas();

        if(canvas != null)
        {
            this.initPicture(canvas);
            holder.unlockCanvasAndPost(canvas);
        }

    }//tryDrawing
}
