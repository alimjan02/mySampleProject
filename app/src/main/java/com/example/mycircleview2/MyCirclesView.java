package com.example.mycircleview2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyCirclesView extends View {
    private static final int CIRCLES_NUMBER = 300;
    Paint paint = new Paint();
    Random random = new Random();
    private List<Circle> circles = new ArrayList<>(CIRCLES_NUMBER);


    public MyCirclesView(Context context) {
        super(context);
        init();
    }

    public MyCirclesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCirclesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.d("CIRCLE","init");
        //设置画笔颜色
        paint.setColor(Color.BLUE);
        //抗锯齿
        paint.setAntiAlias(true);
        //设置画笔颜色
        paint.setColor(Color.BLUE);
        //设置画笔模式为填充
        paint.setStyle(Paint.Style.FILL);
        //设置画笔宽度为2
        paint.setStrokeWidth(2f);
        for (int i = 0; i < CIRCLES_NUMBER; i++) {
            addCircle();
            Log.d("CIRCLE","addCircle");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("CIRCLE","onDraw");
        for (int i = 0; i < circles.size(); i++) {
            Circle circle = circles.get(i);
            Log.d("CIRCLE","circle = x="+circle.getX()+",y="+circle.getY());
            canvas.drawCircle(circle.getX(),circle.getY(),circle.getRadius(),paint);
            circle.setY(circle.getY()-10);
            if(circle.getY()<=0){
                addCircle();
                circles.remove(i);
            }
//            if (circle.getY()<(getHeight()/3.0*2-5)){
//                addCircle();
//            }
        }
    }

    private void addCircle(){
        Circle circle = new Circle();
        circle.setX(random.nextInt(200));
        circle.setY(800/3*2);
        circle.setRadius((random.nextInt(100))/10);
        circles.add(circle);
    }

    public class Circle{
        private float x;
        private float y;
        private float radius;

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getRadius() {
            return radius;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }
    }

}
