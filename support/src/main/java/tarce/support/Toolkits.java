package tarce.support;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Daniel.Xu on 2017/5/4.
 */

public class Toolkits {
    private static final String TAG = Toolkits.class.getSimpleName();
    public static int getVersionCode(Context context){
        PackageInfo info;
        int versionCode = 0;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            MyLog.d(TAG, "读程序版本信息时出错," + e.getMessage());
        }
        return versionCode;
    }

    public static String getVersionName(Context context){
        PackageInfo info;
        String versionName = "";
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            MyLog.d(TAG, "读程序版本信息时出错," + e.getMessage());
        }
        return versionName;
    }

}
