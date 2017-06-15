package tarce.myodoo.activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import tarce.support.MyLog;

/**
 * Created by Daniel.Xu on 2017/5/31.
 */

public class MyFatherRecyclerView extends RecyclerView {
    private static final String TAG = MyFatherRecyclerView.class.getSimpleName();
    private int LastInterceptX ;
    private int LastInterceptY ;
    public MyFatherRecyclerView(Context context) {
        super(context);
    }

    public MyFatherRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyFatherRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        boolean intercept = false ;
        int x = (int) e.getX();
        int y = (int) e.getY();
        int action = e.getAction() & MotionEvent.ACTION_MASK;
        switch (action){
            case MotionEvent.ACTION_DOWN:
                intercept = false ;
                super.onInterceptTouchEvent(e);
                break;
            case MotionEvent.ACTION_MOVE:
                int delex = x-LastInterceptX;
                if(delex>10){
                    intercept = true ;
                    MyLog.e(TAG,"父布局拦截了这个事件"+"--------------");
                }else {
                    intercept = false ;
                    MyLog.e(TAG,"没有拦截了这个事件"+"--------------");
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false ;
                break;
            default:
                break;
        }
        LastInterceptX = x ;
        return intercept ;
    }
}
