package tarce.myodoo.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.newland.me.ConnUtils;
import com.newland.me.DeviceManager;
import com.newland.mtype.ConnectionCloseEvent;
import com.newland.mtype.ModuleType;
import com.newland.mtype.event.DeviceEventListener;
import com.newland.mtype.module.common.printer.Printer;
import com.newland.mtype.module.common.scanner.BarcodeScanner;
import com.newland.mtype.module.common.scanner.BarcodeScannerManager;
import com.newland.mtype.module.common.scanner.ScannerListener;
import com.newland.mtypex.nseries.NSConnV100ConnParams;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tarce.myodoo.R;
import tarce.support.MyLog;
import tarce.support.ToastUtils;

/**
 * Created by Daniel.Xu on 2017/4/26.
 */

public class SalesInActivity extends Activity {
    private static final String K21_DRIVER_NAME = "com.newland.me.K21Driver";
    private static final String TAG = SalesInActivity.class.getSimpleName();
    @InjectView(R.id.connect)
    Button connect;
    @InjectView(R.id.print)
    Button print;
    @InjectView(R.id.edittext)
    EditText edittext;
    private DeviceManager deviceManager;
    private Printer printer;
    private BarcodeScanner scanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_in);
        ButterKnife.inject(this);

//        initDevice();
    }

    /**
     * 连接
     * */
    @OnClick(R.id.connect)
    void setConnect(View view){
        initDevice();
    }


    /**
     * 打印
     * */
    @OnClick(R.id.print)
    void  setPrint(View view){
        printer = (Printer) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_PRINTER);
        printer.init();
    }


    /**
     * s设置打印内容？
     * */
    @OnClick(R.id.printEdittext)
    void setPrintEdittext(View view)
    {
        String s = edittext.getText().toString();
        printer.print(s,30, TimeUnit.SECONDS);
    }


    /**
     * 打印的图片？
     * */
    @OnClick(R.id.printPicture)
    void setpicture(View view)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.erweima);
        printer.print(50,bitmap,30, TimeUnit.SECONDS);
//        scanner.stopScan();
    }


    @OnClick(R.id.scan)
    void setscan(View view)
    {
        BarcodeScannerManager scannerManager=(BarcodeScannerManager)deviceManager.
                getDevice().getStandardModule(ModuleType.COMMON_BARCODESCANNER);
        scanner = scannerManager.getDefault();
        scanner.initScanner(SalesInActivity.this);
        ScannerListener listener=new ScannerListener() {
            @Override
            public void onFinish() {
                MyLog.e(TAG,"onfinish");
            }
            @Override
            public void onResponse (String[] scanResults) {
                MyLog.e(TAG,scanResults[0].toString()+"");
            }
        };
//        scanner.startScan(30, TimeUnit.SECONDS, listener);
//        Intent intent = new Intent(SalesInActivity.this, ScanViewActivity.class);
//        intent.putExtra("scanType", 0x00);
//        mainActivity.startActivity(intent);
    }
    /**
     * 连接设备打印机
     * */
    private void initDevice() {
        deviceManager = ConnUtils.getDeviceManager
                ();
        try {
            deviceManager.init(SalesInActivity.this, K21_DRIVER_NAME, new NSConnV100ConnParams(), new DeviceEventListener<ConnectionCloseEvent>() {
                @Override
                public void onEvent(ConnectionCloseEvent connectionCloseEvent, Handler handler) {
                    if (connectionCloseEvent.isSuccess()) {
                        ToastUtils.showCommonToast(SalesInActivity.this, "设备被客户主动断开！");
                    }
                    if (connectionCloseEvent.isFailed()) {
                        ToastUtils.showCommonToast(SalesInActivity.this, "设备链接异常断开！");
                    }
                }

                @Override
                public Handler getUIHandler() {
                    return null;
                }
            });
            deviceManager.connect();
            MyLog.e(TAG, "连接成功");
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showCommonToast(SalesInActivity.this, "链接异常,请检查设备或重新连接.." + e);
        }
    }


}
