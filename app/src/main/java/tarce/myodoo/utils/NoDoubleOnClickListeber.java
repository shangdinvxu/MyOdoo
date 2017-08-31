package tarce.myodoo.utils;

import android.view.View;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/8/31.
 */

public abstract class NoDoubleOnClickListeber implements View.OnClickListener{
    private static final int MIN_CLICK_DELAY_TIME = 1500;
    private long lastClickTime = 0;
    public abstract void onNoDoubleClick(View v);
    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

}
