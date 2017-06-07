package tarce.support;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Daniel.Xu on 2017/4/22.
 */

public class ViewUtils {
    public static void ViewRotate(View image,int angle ){
        image.setPivotX(image.getWidth()/2);
        image.setPivotY(image.getHeight()/2);//支点在图片中心
        image.setRotation(angle);
    }

    /**
     * 收起软键盘
     */
    public static void collapseSoftInputMethod(Context context, View v){
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public static void showSoftInputMethod(Context context, View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(v, 0);
        }
    }
}
