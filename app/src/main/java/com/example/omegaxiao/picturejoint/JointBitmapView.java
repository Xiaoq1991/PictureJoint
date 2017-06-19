package com.example.omegaxiao.picturejoint;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.R.attr.textColor;

/**
 * Created by omegaxiao on 2017/6/13.
 */

public class JointBitmapView extends View {
    private Bitmap start;
    private Bitmap end;
    private int width;
    private int height;
    private int startRes;
    private int endRes;
    private String TAG = "ApolloGameProcessBar";
    private boolean isScaled = false;
    public JointBitmapView(Context context,int startRes,int endRes) {
        super(context);
        start = BitmapFactory.decodeResource(context.getResources(),startRes);
        end = BitmapFactory.decodeResource(context.getResources(),endRes);
        width = start.getWidth();
        height = start.getHeight();
    }

    public JointBitmapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public JointBitmapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBitmaps(Bitmap start,Bitmap end){
        this.start = start;
        this.end = end;
        width = start.getWidth();
        height = end.getHeight();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = measureHanlder(widthMeasureSpec,width);
        height = measureHanlder(heightMeasureSpec,height);
        setMeasuredDimension(width,height);


    }

    private int measureHanlder(int measureSpec,int defaultSize){
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        } else {
            result = defaultSize;
        }
        return result;
    }

    private int progress = 0;
    public void setProgress(int scale){
        if(start == null || end == null){
            return;
        }
        if(scale > 100 || scale < 0){
            return;
        }
        progress = scale;
        postInvalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if(start == null || end == null){
            return;
        }
        if(!isScaled){
            start = Bitmap.createScaledBitmap(start,width,height,false);
            end = Bitmap.createScaledBitmap(end,width,height,false);
            isScaled = true;
        }
        if(progress == 0){
            canvas.drawBitmap(start,0,0,null);
        }else if(progress == 100){
            canvas.drawBitmap(end,0,0,null);

        }else{
            int scaleHeight = (int) Math.floor(height*progress/100);
            Bitmap bit1 = Bitmap.createBitmap(start, 0, 0, width,height-scaleHeight);
            Bitmap bit2 = Bitmap.createBitmap(end, 0 ,height-scaleHeight, width, scaleHeight);
            canvas.drawBitmap(bit1,0,0,null);
            canvas.drawBitmap(bit2,0,height-scaleHeight,null);
            bit1.recycle();
            bit2.recycle();
        }
    }


}
