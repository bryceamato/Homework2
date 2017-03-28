package edu.up.homework2;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * <!-- class CustomRect -->
 *
 * This class defines a custom drawing element that is a rectangle.
 *
 * @author Andrew Nuxoll
 * @version Spring 2015
 * @see CustomElement
 *
 */

public class CustomRect extends CustomElement {


    /** the position and size of the rectangle is stored here */
    protected Rect myRect;

    /** the rectangles dimensions must be defined at construction */
    public CustomRect(String name, int color,
                      int left, int top, int right, int bottom)
    {
        super(name, color);

        this.myRect = new Rect(left, top, right, bottom);
    }


    @Override
    public void drawMe(Canvas canvas) {
        canvas.drawRect(myRect, myPaint);  //main rectangle
        canvas.drawRect(myRect, outlinePaint);  //outline around rectangle
    }

    @Override
    public boolean containsPoint(int x, int y) {

        //Want to check for a tap within a slightly larger rectangle
        int left = this.myRect.left;
        int top = this.myRect.top;
        int right = this.myRect.right;
        int bottom = this.myRect.bottom;
        Rect r = new Rect(left, top, right, bottom);

        return r.contains(x, y);
    }//contaisPoint


    @Override
    public int getSize() {
        return this.myRect.width() * this.myRect.height();
    }

    @Override
    public void drawHighlight(Canvas canvas) {
        canvas.drawRect(myRect, highlightPaint);
        canvas.drawRect(myRect, outlinePaint);  //keep outline so it stands out
    }




}//class CustomRect
