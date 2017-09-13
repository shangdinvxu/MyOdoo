package tarce.myodoo.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.newland.me.ConnUtils;
import com.newland.me.DeviceManager;
import com.newland.mtype.ConnectionCloseEvent;
import com.newland.mtype.ModuleType;
import com.newland.mtype.event.DeviceEventListener;
import com.newland.mtype.module.common.rfcard.RFCardModule;
import com.newland.mtypex.nseries.NSConnV100ConnParams;

import tarce.myodoo.uiutil.NFCdialog;
import tarce.support.MyLog;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;
import tarce.support.ToolBarHelper;

/**
 * Created by rose.zou on 2017/6/21.
 * 替换ToolbarActivity
 */

public class BaseActivity extends AppCompatActivity {
    public static final String K21_DRIVER_NAME = "com.newland.me.K21Driver";
    public DeviceManager deviceManager;
    public RFCardModule rfCardModule;
    public NFCdialog nfCdialog;
    private ToolBarHelper mToolBarHelper ;
    public Toolbar toolbar ;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setRecyclerview(RecyclerView recyclerview){
        recyclerview.setLayoutManager(new LinearLayoutManager(BaseActivity.this));
        recyclerview.addItemDecoration(new DividerItemDecoration(BaseActivity.this,
                DividerItemDecoration.VERTICAL));
    }


    public void showDefultProgressDialog(){
        progressDialog = new ProgressDialog(BaseActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public  void dismissDefultProgressDialog(){
        if (progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
            progressDialog = null;
        }
        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void setContentView(int layoutResID) {

        mToolBarHelper = new ToolBarHelper(this,layoutResID) ;
        toolbar = mToolBarHelper.getToolBar() ;
        setContentView(mToolBarHelper.getContentView());
        /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
        /*自定义的一些操作*/
        onCreateCustomToolBar(toolbar);
    }

    public void onCreateCustomToolBar(Toolbar toolbar){
        toolbar.setContentInsetsRelative(0,0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissDefultProgressDialog();
    }

    //显示nfc的dialog
    public void showNfcDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nfCdialog = new NFCdialog(BaseActivity.this);
                nfCdialog.setCancel(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        processingUnLock();
                        nfCdialog.dismiss();
                        return;
                    }
                }).show();
            }
        });
    }

    //关闭dialog
    public void threadDismiss(final NFCdialog dialog) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    dialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /**
     * 连接设备打印机
     */
    public void initDevice() {
        deviceManager = ConnUtils.getDeviceManager();
        try {
            deviceManager.init(BaseActivity.this, K21_DRIVER_NAME, new NSConnV100ConnParams(), new DeviceEventListener<ConnectionCloseEvent>() {
                @Override
                public void onEvent(ConnectionCloseEvent connectionCloseEvent, Handler handler) {
                    if (connectionCloseEvent.isSuccess()) {
                        ToastUtils.showCommonToast(BaseActivity.this, "设备被客户主动断开！");
                    }
                    if (connectionCloseEvent.isFailed()) {
                        ToastUtils.showCommonToast(BaseActivity.this, "设备链接异常断开！");
                    }
                }

                @Override
                public Handler getUIHandler() {
                    return null;
                }
            });
            deviceManager.connect();
            MyLog.e("OrderDetailActivity", "连接成功");
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showCommonToast(BaseActivity.this, "链接异常,请检查设备或重新连接.." + e);
        }
        try {
            rfCardModule = (RFCardModule) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_RFCARDREADER);
        }catch (Exception e){
            Log.e("zws", "error");
        }
    }

    public void processingLock() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                SharedPreferences setting = getSharedPreferences("setting", 0);
                SharedPreferences.Editor editor = setting.edit();
                editor.putBoolean("PBOC_LOCK", true);
                editor.commit();
            }
        });

    }

    public void processingUnLock() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences setting = getSharedPreferences("setting", 0);
                SharedPreferences.Editor editor = setting.edit();
                editor.putBoolean("PBOC_LOCK", false);
                editor.commit();
            }
        });

    }
}
