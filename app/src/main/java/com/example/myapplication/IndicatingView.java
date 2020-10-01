package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class IndicatingView extends View{

    public static final int NOTEXECUTED = 0;
    public static final int SUCCESS = 1;
    public static final int FAILED = 2;
    public static final int PROGRESSING = 3;

    int state = NOTEXECUTED;

    public IndicatingView (Context context) { super((context));}

    public IndicatingView (Context context, AttributeSet attrs) {super(context, attrs);}

    public IndicatingView (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getState() { return state; }
    public void setState(int state) { this.state = state; }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        Paint paint;
        switch (state){
            case PROGRESSING:
                paint = new Paint();
                paint.setColor(Color.YELLOW);
                paint.setStrokeWidth(20f);

                canvas.drawLine(0, 0, width/2, height, paint);
                canvas.drawLine(width/2, height, width, 0, paint);
                canvas.drawLine(0, 0, width, 0, paint);
                break;

            case SUCCESS:
                paint = new Paint();
                paint.setColor(Color.GREEN);
                paint.setStrokeWidth(20f);

               // canvas.drawLine(0, 0, width/2, height, paint);
               // canvas.drawLine(width/2, height, width, height/2, paint);
                canvas.drawLine(0, 0, width, 0, paint);
                canvas.drawLine(0, 0, 0, height, paint);
                canvas.drawLine(width, 0, width, height, paint);
                canvas.drawLine(0, height, width, height, paint);
            break;

            case FAILED:
                paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStrokeWidth(20f);

                canvas.drawLine(0, 0, width, height, paint);
                canvas.drawLine(0, height, width, 0, paint);
                break;

            default:
                break;
        }
    }
}
