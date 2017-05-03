package tarce.myodoo;

import android.content.Context;
import android.content.Intent;

import tarce.myodoo.activity.MainActivity;
import tarce.myodoo.activity.SalesInActivity;
import tarce.myodoo.activity.SalesOutActivity;


/**
 * Created by Daniel.Xu on 2017/1/5.
 */

public class IntentFactory {
    /**
     * 开启主界面
     * @param context
     */
    public static void start_MainActivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    /**
     * 销售出库
     * @param context
     */
    public static void start_SalesOut_Activity(Context context){
        Intent intent = new Intent(context, SalesOutActivity.class);
        context.startActivity(intent);
    }

    /**
     * 客户退货
     * @param context
     */
    public static void start_SalesIn_Activity(Context context){
        Intent intent = new Intent(context, SalesInActivity.class);
        context.startActivity(intent);
    }




}
