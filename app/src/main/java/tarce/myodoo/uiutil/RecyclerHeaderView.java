package tarce.myodoo.uiutil;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * Created by rose.zou on 2017/5/22.
 * 通用下啦刷新头部
 */

public class RecyclerHeaderView extends TextView implements SwipeTrigger,SwipeRefreshTrigger{
    public RecyclerHeaderView(Context context) {
        super(context);
    }

    public RecyclerHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onPrepare() {
    }

    @Override
    public void onMove(int i, boolean isComplete, boolean b1) {
        if (!isComplete){
            if (i>=getHeight()){
                setText("松开刷新");
            }else {
                setText("下拉刷新");
            }
        }else {
            setText("正在刷新");
        }
    }

    @Override
    public void onRelease() {
        setText("加载中...");
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onReset() {

    }
}
