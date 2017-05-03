package tarce.support;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Daniel.Xu on 2017/4/24.
 */

public class ToastUtils {
    public static void showCommonToast(Context context ,String string){
        Toast.makeText(context, string,Toast.LENGTH_SHORT).show();
    }
}
