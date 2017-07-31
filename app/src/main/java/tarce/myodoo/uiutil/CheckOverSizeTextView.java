package tarce.myodoo.uiutil;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by zws on 2017/7/31.
 */

public class CheckOverSizeTextView extends TextView{
    protected boolean isOverSize;
    private OnOverSizeChangedListener changedListener;

    public CheckOverSizeTextView(Context context) {
        super(context);
        init();
    }

    public CheckOverSizeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CheckOverSizeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (changedListener != null) {
            changedListener.onChanged(checkOverLine());
        }
    }

    private void init() {
        // invalidate when layout end
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if (changedListener != null) {
                    changedListener.onChanged(checkOverLine());
                }
            }
        });
    }

    public boolean checkOverLine() {
        int maxLine = getMaxLines();
        try {
            Field field = getClass().getSuperclass().getDeclaredField("mLayout");
            field.setAccessible(true);
            Layout mLayout = (Layout) field.get(this);
            if (mLayout == null)
                return false;
            isOverSize = mLayout.getEllipsisCount(maxLine - 1) > 0 ? true : false;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return isOverSize;
    }

    public boolean isOverSize() {
        return isOverSize;
    }

    public void displayAll() {
        setMaxLines(Integer.MAX_VALUE);
        setEllipsize(null);
    }

    public void hide(int maxlines) {
        setEllipsize(TextUtils.TruncateAt.END);
        setMaxLines(maxlines);
    }

    // set a listener for callback
    public OnOverSizeChangedListener getChangedListener() {
        return changedListener;
    }

    public void setOnOverLineChangedListener(OnOverSizeChangedListener changedListener) {
        this.changedListener = changedListener;
    }

    public interface OnOverSizeChangedListener {
        /**
         * <span style="color:purple">when invalide,the method will be called
         * and tell you whether the content text is over size
         *
         * @param isOverSize
         *            whether content text is over size
         */
        public void onChanged(boolean isOverSize);
    }
}
