package tarce.support;

import android.content.Context;
import android.widget.Toast;

import java.lang.reflect.Method;

/**
 * Created by Daniel.Xu on 2017/4/28.
 */

public class DbUtils {
    public static void showDebugDBAddressLogToast(Context context) {
        if (BuildConfig.DEBUG) {
            try {
                Class<?> debugDB = Class.forName("com.amitshekhar.DebugDB");
                Method getAddressLog = debugDB.getMethod("getAddressLog");
                Object value = getAddressLog.invoke(null);
                Toast.makeText(context, (String) value, Toast.LENGTH_LONG).show();
            } catch (Exception ignore) {
            }
        }
    }
}
