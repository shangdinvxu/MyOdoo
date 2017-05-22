package tarce.myodoo.uiutil;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * Created by rose.zou on 2017/5/22.
 * 通用上啦加载
 */

public class RecyclerFooterView extends TextView implements SwipeTrigger,SwipeRefreshTrigger{
    public RecyclerFooterView(Context context) {
        super(context);
    }

    public RecyclerFooterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onRefresh() {
        setText("上拉加载更多");
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onMove(int i, boolean b, boolean b1) {

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
