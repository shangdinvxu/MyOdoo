package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.hugeterry.updatefun.UpdateFunGO;
import cn.hugeterry.updatefun.config.UpdateKey;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tarce.api.OKHttpFactory;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.LoadActionBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.scancode.ScanCodeActivity;
import tarce.myodoo.fragment.InspectionFragment;
import tarce.myodoo.fragment.MeFragment;
import tarce.myodoo.fragment.NewProductFragment;
import tarce.myodoo.fragment.ProduceFragment;
import tarce.myodoo.fragment.WarehouseFragment;
import tarce.myodoo.view.NoScrollViewPager;

import static tarce.api.RetrofitClient.Url;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    @InjectView(R.id.viewpager)
    NoScrollViewPager mViewPager;
    @InjectView(R.id.radio_button1)
    RadioButton radioButton1;
    @InjectView(R.id.radio_button2)
    RadioButton radioButton2;
    @InjectView(R.id.radio_button3)
    RadioButton radioButton3;
    @InjectView(R.id.radio_button4)
    RadioButton radioButton4;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.title_text)
    TextView titleText;
    @InjectView(R.id.search_button)
    Button searchButton;
    @InjectView(R.id.scan_button)
    Button scanButton;
    /*@InjectView(R.id.show_result)
    TextView showResult;
    @InjectView(R.id.button_test)
    Button buttonTest;*/

    private Fragment[] myfragment;
    private InventoryApi inventoryApi;
    private WarehouseFragment warehouseFragment;
    private ProduceFragment produceFragment;
    private NewProductFragment newProductFragment;
    private InspectionFragment inspectionFragment;
    private MeFragment meFragment;

    private Fragment mContent;
    private View mCurrentTab;
    private final static int REQUEST_CODE = 5;
    private LoadActionBean.ResultBean.ResDataBean res_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        inventoryApi = RetrofitClient.getInstance(MainActivity.this).create(InventoryApi.class);

        initFragment();
        UpdateKey.API_TOKEN = "d8980dd0017f3e0a7b038aec2c52d737";
        UpdateKey.APP_ID = "5940d8ca959d6965c30002dc";
        //下载方式:
        //   UpdateKey.DialogOrNotification=UpdateKey.WITH_DIALOG;//通过Dialog来进行下载
        //UpdateKey.DialogOrNotification=UpdateKey.WITH_NOTIFITION;通过通知栏来进行下载(默认)
        UpdateFunGO.init(this);
    }

    @Override
    protected void onResume() {
        UpdateFunGO.onResume(this);
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        UpdateFunGO.onStop(this);
    }

    @OnClick(R.id.radio_button1)
    void clickRadio_Button1(View view) {
        mViewPager.setCurrentItem(0, false);
        titleText.setText("仓库");
    }


    /***点击二维码扫描*/
    @OnClick(R.id.scan_button)
    void clickScanButton(View view) {
        Intent intent = new Intent(MainActivity.this, ScanCodeActivity.class);
        startActivity(intent);
    }


    /*****点击搜索界面*/
    @OnClick(R.id.search_button)
    void clickSearchButton(View view) {
    }


    @OnClick(R.id.radio_button2)
    void clickRadio_Button2(View view) {
        mViewPager.setCurrentItem(1, true);
        titleText.setText("生产");
    }

    @OnClick(R.id.radio_button3)
    void clickRadio_Button3(View view) {
        titleText.setText("品检");
        mViewPager.setCurrentItem(2, false);
    }

    @OnClick(R.id.radio_button4)
    void clickRadio_Button4(View view) {
        titleText.setText("我");
        mViewPager.setCurrentItem(3, true);
    }


    private void initFragment() {
        myfragment = new Fragment[4];
        warehouseFragment = new WarehouseFragment();
        myfragment[0] = warehouseFragment;
//        produceFragment = new ProduceFragment();
//        myfragment[1] = produceFragment;
        newProductFragment = new NewProductFragment();
        myfragment[1] = newProductFragment;
        inspectionFragment = new InspectionFragment();
        myfragment[2] = inspectionFragment;
        meFragment = new MeFragment();
        myfragment[3] = meFragment;
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myViewPagerAdapter);
        mViewPager.setCurrentItem(0);
        radioButton1.setClickable(true);
    }


    class MyViewPagerAdapter extends FragmentPagerAdapter {
        private MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return myfragment[position];
        }

        @Override
        public int getCount() {
            return myfragment.length;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描二维码的返回
        if (requestCode == REQUEST_CODE) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
