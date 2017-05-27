package tarce.support;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Daniel.Xu on 2017/2/13.
 */

public class AlertAialogUtils {
    private static ProgressDialog progressDialog;
    public static AlertDialog.Builder getCommonDialog(Context context , String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
         builder.setMessage(title)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        return builder;
    }

    public  static void showDefultProgressDialog(Context context){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(context);
        }
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
    }


    public static void dismissDefultProgressDialog(){
        if (progressDialog!=null || progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
