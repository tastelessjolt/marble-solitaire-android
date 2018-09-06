package com.example.harshithg.marblesolitaire;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MarbleView extends View {
    BoardView parent;
    boolean highlight;
    boolean hidden;
    int xpos, ypos;

    int MARBLE_SIZE;

    Bitmap empty, marble, selEmpty;
    Bitmap current;
    Paint mPaint;
    Rect bounds;
//    Fl_Image *img;
    public MarbleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        String pos[] = ((String) getContentDescription()).split(",");
        ypos = Integer.parseInt(pos[0]);
        xpos = Integer.parseInt(pos[1]);
        String str;
        if ((str = (String) getTag()) != null ) {
            if (str.equals("hide")){
                hidden = true;
            }
        }

        marble = BitmapFactory.decodeResource(getResources(), R.drawable.marble);
        empty = BitmapFactory.decodeResource(getResources(), R.drawable.empty);
        selEmpty = BitmapFactory.decodeResource(getResources(), R.drawable.selected);

        if (hidden)
            current = empty;
        else
            current = marble;

        mPaint = new Paint();

        MARBLE_SIZE = 100;

        bounds = new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    public void setParent(BoardView boardView) {
        parent = boardView;
    }


    void select() {
        current = selEmpty;
        invalidate();
    }

    void unselect() {
        current = empty;
        hidden = true;
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent e) {
        int ev =e.getActionMasked();
        if (ev == MotionEvent.ACTION_DOWN){
            Log.d("Touch", String.format("%d, %d", xpos, ypos));
            parent.move(xpos, ypos);
            return true;
        }
        return false;
    }

    void show_img() {
        current = marble;
        hidden = false;
        invalidate();
    }

    void hide_img() {
        current = empty;
        hidden = true;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        bounds.right = getMeasuredWidth();
        bounds.bottom = getMeasuredWidth();

        canvas.drawBitmap(current, null, bounds, mPaint);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
//    }
}
