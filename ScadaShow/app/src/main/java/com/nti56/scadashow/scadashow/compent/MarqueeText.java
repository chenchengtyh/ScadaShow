package com.nti56.scadashow.scadashow.compent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.nti56.scadashow.scadashow.activity.MainActivity;

/**
 * Created by chencheng on 2017/8/28.
 */

public class MarqueeText extends View{

    private Paint secondPaint;
    private String title ="1234567890";
    private boolean isStop;
    private int strWidth;
    private int x = 0;
    private int x1 = 100;
    private boolean isScrollOut = true;
    private boolean isScrollIn = false;
    private static final String TAG ="MarqueeText" ;
    private int screenWidth;
    private boolean isMeasure;
    private Paint mPaint;
    private boolean isFirst;
    private boolean b;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what==1){
                if(isScrollOut){
                    x-=5;
                    if(Math.abs(x)>=(strWidth-100)&&!isFirst){//
                        isFirst = true;
                        isScrollIn = true;
                        isScrollOut = true;
                        x1=screenWidth-100;
                    }
                    if(Math.abs(x)>=strWidth){
                        isFirst  = false;
                        isScrollOut = false;
                        b = false;
                    }
                    if(isScrollIn){
                        x1-=5;
                    }
                }else{
                    x1-=5;
                    if(Math.abs(x1)>=(strWidth-100)&&!b){
                        b = true;
                        isScrollOut = true;
                        isScrollIn = true;
                        x=screenWidth-100;
                    }
                    if(isScrollOut){
                        x-=5;
                    }
                }
                invalidate();
                mHandler.sendEmptyMessageDelayed(1,50);
            }
        }
    };
    public MarqueeText(Context context) {
        this(context,null);
    }
    public MarqueeText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public MarqueeText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        isStop = false;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(30);
        mPaint.setColor(Color.BLACK);
        secondPaint = new Paint();
        secondPaint.setAntiAlias(true);
        secondPaint.setTextSize(30);
        secondPaint.setColor(Color.RED);

        WindowManager wm = ((MainActivity)context).getWindowManager();
        screenWidth = wm.getDefaultDisplay().getWidth();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!TextUtils.isEmpty(title)){
            if(isScrollOut){
                canvas.drawText(title,x,24,mPaint);
            }

            if(isScrollIn){
                canvas.drawText(title,x1,24,secondPaint);
            }
        }
        if(!isMeasure){
            strWidth = (int) mPaint.measureText(title);
            isMeasure = true;
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(screenWidth,dip2px(getContext(),40));
    }
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(hasWindowFocus){
            mHandler.sendEmptyMessage(1);
        }else{
            mHandler.removeCallbacks(null);
        }
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
