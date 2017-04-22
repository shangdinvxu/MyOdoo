package tarce.support;

import android.view.View;

/**
 * Created by Daniel.Xu on 2017/4/22.
 */

public class ViewUtils {
    public static void ViewRotate(View image,int angle ){
        image.setPivotX(image.getWidth()/2);
        image.setPivotY(image.getHeight()/2);//支点在图片中心
        image.setRotation(angle);
    }
}
