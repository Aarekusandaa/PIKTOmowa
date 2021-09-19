package com.example.piktomowafraglist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.provider.DocumentsContract;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Drawing extends AppCompatActivity {

    Draw drawing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        drawing = (Draw) findViewById(R.id.drawing);
    }

    public void onClick(View view){
        drawing.Remove();
    }

}


class Draw extends View {

    private android.graphics.Path DrawPath;
    private Paint DrawPaint;

    public Draw (Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        DrawPath = new Path();
        DrawPaint = new Paint();

        DrawPaint.setStyle(Paint.Style.STROKE);
        DrawPaint.setColor(Color.BLACK);
        DrawPaint.setStrokeWidth(7);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){

        float x = motionEvent.getX();
        float y = motionEvent.getY();

        switch (motionEvent.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                DrawPath.moveTo(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                DrawPath.lineTo(x, y);
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawPath(DrawPath, DrawPaint);
    }

    public void Remove(){
        DrawPath.reset();
        invalidate();
    }

}


