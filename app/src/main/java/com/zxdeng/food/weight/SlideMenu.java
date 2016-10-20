package com.zxdeng.food.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by zengxudeng on 16/10/16.
 */

public class SlideMenu extends ViewGroup {
    
    private static final int SLIDE_2_MENU = 0;
    private static final int SLIDE_2_CONTENT= 1;

    private View mMenuView;
    private View mContentView;
    
    private int mSlideState;

    private static final String TAG = "SlideMenu";
    private float mStartX;
    private Scroller mScroller;


    public SlideMenu(Context context) {
        this(context, null, 0);
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public SlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mMenuView.layout(-mMenuView.getLayoutParams().width,0,0,b);
//        int x = 800;
//        mMenuView.layout(-x,0,0,b);
        Log.d(TAG, "onLayout: mMenuView.getMeasuredWidth()－－》"+mMenuView.getLayoutParams().width);


        mContentView.layout(0,0,mContentView.getMeasuredWidth(),mContentView.getMeasuredHeight());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        measureChildren(widthMeasureSpec,heightMeasureSpec);

        mMenuView = getChildAt(0);

        mMenuView.measure(mMenuView.getLayoutParams().width,heightMeasureSpec);


        Log.d(TAG, "onMeasure: "+mMenuView.getLayoutParams().width);



        mContentView = getChildAt(1);
        mContentView.measure(widthMeasureSpec,heightMeasureSpec);

//        MeasureSpec.getMode(mContentView.getMeasuredWidth());
//        Log.d(TAG, "onMeasure: MeasureSpec.getMode(mContentView.getMeasuredWidth())"+MeasureSpec.getMode(mContentView.getMeasuredWidth()));
//        measureChild(mMenuView,widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                Log.d(TAG, "onTouchEvent: mStartX"+mStartX);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX=event.getX();
                Log.d(TAG, "onTouchEvent: moveX" +moveX);
                float diff=mStartX-moveX;

                int delta=(int) (getScrollX()+diff);

                Log.d(TAG, "onTouchEvent: delta"+delta);

                if (delta<-mMenuView.getMeasuredWidth()) {
                    scrollTo(- mMenuView.getMeasuredWidth(), 0);
                }else  if (delta<-mMenuView.getLayoutParams().width) {
                    scrollTo(delta, 0);
                }else if (delta>0) {
                    scrollTo(0, 0);
                }else {
                    scrollBy((int) diff, 0);
                }
                mStartX=moveX;
                break;
            case MotionEvent.ACTION_UP:
                int menuCenter=-mMenuView.getMeasuredWidth()/2;
                if (getScrollX()<menuCenter) {
                    mSlideState=SLIDE_2_MENU;
                }else {
                    mSlideState=SLIDE_2_CONTENT;
                }
                slideOneSide();
                break;
        }
        return true;
    }

    private void slideOneSide() {
        int endX=0;
        if (mSlideState==SLIDE_2_CONTENT) {
            endX=00;
        }else if (mSlideState==SLIDE_2_MENU) {
            endX=-mMenuView.getMeasuredWidth()+200;
        }
        //模拟数据
        mScroller.startScroll(getScrollX(), 0, endX-getScrollX(), 0, Math.abs(endX-getScrollX())*3);
        invalidate();
    }
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent: ");
        return true;
    }

    /**
     * 将传进来的数转化为dp
     */
    private int convertToDp(Context context , int num){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,num,context.getResources().getDisplayMetrics());
    }
}
