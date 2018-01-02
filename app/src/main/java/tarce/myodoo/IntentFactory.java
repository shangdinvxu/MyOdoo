package tarce.myodoo;

import android.content.Context;
import android.content.Intent;

import tarce.myodoo.activity.MainActivity;
import tarce.myodoo.activity.ProductLlActivity;
import tarce.myodoo.activity.SelectProcedureActivity;
import tarce.myodoo.activity.inspect.InspectionSubActivity;
import tarce.myodoo.activity.newproduct.SoOriginActivity;
import tarce.myodoo.activity.retail.RetailSubActivity;
import tarce.myodoo.activity.salesout.NewSaleoutActivity;


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
        Intent intent = new Intent(context, NewSaleoutActivity.class);
        context.startActivity(intent);
    }


    /**
     * 生产备料->选择工序
     * @param context
     * */
    public static void start_SeProce_Activity(Context context){
        Intent intent = new Intent(context, SelectProcedureActivity.class);
        context.startActivity(intent);
    }


    /**
     * 领料->详细
     * 这个方法适用于六个跳转页面，关键，state
     * @param context,name,state
     * */
    public static void start_ProducLl_Activity(Context context, String name, String state){
        Intent intent = new Intent(context, ProductLlActivity.class);
        intent.putExtra("name_activity",name);
        intent.putExtra("state_product",state);
        context.startActivity(intent);
    }
    /**
     * 领料->详细
     * 这个方法适用于六个跳转页面，关键，state
     * @param context,name,state
     * */
    public static void new_Start_ProducLl_Activity(Context context, String name, String state, int process_id,int line_id){
        Intent intent = new Intent(context, ProductLlActivity.class);
        intent.putExtra("name_activity",name);
        intent.putExtra("state_product",state);
        intent.putExtra("process_id", process_id);
        intent.putExtra("line_id", line_id);
        context.startActivity(intent);
    }

    /**
     * 跳转至等待返工，生产入库详细页面
     * */
    public static void start_WaitRework_Activity(Context context, String name, String state){
        Intent intent = new Intent(context, InspectionSubActivity.class);
        intent.putExtra("state",state);
        context.startActivity(intent);
    }

    /**
     * 跳转至等待返工，生产入库详细页面
     * */
    public static void new_Start_WaitRework_Activity(Context context, String name, String state, int process_id,int line_id){
        Intent intent = new Intent(context, InspectionSubActivity.class);
        intent.putExtra("state",state);
        intent.putExtra("process_id", process_id);
        intent.putExtra("line_id", line_id);
        context.startActivity(intent);
    }

    /**
     * 跳转至零售子页面
     * */
    public static void start_RetailSub_Activity(Context context){
        Intent intent = new Intent(context, RetailSubActivity.class);
        context.startActivity(intent);
    }

    /**
     * 生产新的跳转
     * @param context,name,state
     * */
    public static void new_Start_So_Activity(Context context, String name, String state, int process_id,int line_id){
        Intent intent = new Intent(context, SoOriginActivity.class);
        intent.putExtra("name_activity",name);
        intent.putExtra("state",state);
        intent.putExtra("process_id", process_id);
        intent.putExtra("line_id", line_id);
        context.startActivity(intent);
    }
}
