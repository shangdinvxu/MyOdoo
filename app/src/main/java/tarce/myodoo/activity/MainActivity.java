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

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.hugeterry.updatefun.UpdateFunGO;
import cn.hugeterry.updatefun.config.UpdateKey;
import greendao.ContactsBeanDao;
import greendao.DaoSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.LoadActionBean;
import tarce.model.SearchSupplierResponse;
import tarce.model.greendaoBean.ContactsBean;
import tarce.myodoo.MyApplication;
import tarce.myodoo.R;
import tarce.myodoo.fragment.InspectionFragment;
import tarce.myodoo.fragment.MeFragment;
import tarce.myodoo.fragment.ProduceFragment;
import tarce.myodoo.fragment.WarehouseFragment;
import tarce.myodoo.view.NoScrollViewPager;
import tarce.support.MyLog;

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
    private InspectionFragment inspectionFragment;
    private MeFragment meFragment;

    private Fragment mContent;
    private View mCurrentTab;
    private final static int REQUEST_CODE = 5;
    private LoadActionBean.ResultBean.ResDataBean res_data;
    private ContactsBeanDao contactsBeanDao;
    private DaoSession daoSession;


    /*private List<TestBean.TestRSubBean.ListSubBean> listSubBeen  = new ArrayList<TestBean.TestRSubBean.ListSubBean>();
    private List<Integer> listInterge = new ArrayList<>();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        inventoryApi = RetrofitClient.getInstance(MainActivity.this).create(InventoryApi.class);
        /*daoSession = MyApplication.getInstances().getDaoSession();
        contactsBeanDao =  daoSession.getContactsBeanDao();*/
        initFragment();
        UpdateKey.API_TOKEN = "d8980dd0017f3e0a7b038aec2c52d737";
        UpdateKey.APP_ID = "5940d8ca959d6965c30002dc";
        //下载方式:
        //   UpdateKey.DialogOrNotification=UpdateKey.WITH_DIALOG;//通过Dialog来进行下载
        //UpdateKey.DialogOrNotification=UpdateKey.WITH_NOTIFITION;通过通知栏来进行下载(默认)
        UpdateFunGO.init(this);
        //   getSupplier();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (res_data == null) {
            refreshLoadAction();
        }
        UpdateFunGO.onResume(this);
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
        refreshLoadAction();
    }


    /***点击二维码扫描*/
    @OnClick(R.id.scan_button)
    void clickScanButton(View view) {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);

    }


    /*****点击搜索界面*/
    @OnClick(R.id.search_button)
    void clickSearchButton(View view) {
    }


    private void refreshLoadAction() {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        String[] names = {"linkloving_mrp_extend.menu_mrp_prepare_material_ing", "linkloving_mrp_extend.menu_mrp_waiting_material",
                "linkloving_mrp_extend.menu_mrp_waiting_warehouse_inspection",
                "linkloving_mrp_extend.mrp_production_action_qc_success"};
        objectObjectHashMap.put("xml_names", names);
        objectObjectHashMap.put("user_id", MyApplication.userID);
        //   AlertAialogUtils.showDefultProgressDialog(MainActivity.this);
        Call<LoadActionBean> objectCall = inventoryApi.load_actionCall(objectObjectHashMap);
        objectCall.enqueue(new Callback<LoadActionBean>() {
            @Override
            public void onResponse(Call<LoadActionBean> call, Response<LoadActionBean> response) {
                //  AlertAialogUtils.dismissDefultProgressDialog();
                if (response.body() != null && response.body().getResult().getRes_code() == 1) {
                    res_data = response.body().getResult().getRes_data();
                    Integer needaction_counter = res_data.getLinkloving_mrp_extend_menu_mrp_prepare_material_ing().getNeedaction_counter();
                    Integer needaction_counter1 = res_data.getLinkloving_mrp_extend_menu_mrp_waiting_warehouse_inspection().getNeedaction_counter();
                    Integer needaction_counter2 = res_data.getLinkloving_mrp_extend_mrp_production_action_qc_success().getNeedaction_counter();
                    Integer needaction_counter3 = res_data.getLinkloving_mrp_extend_menu_mrp_waiting_material().getNeedaction_counter();
                    warehouseFragment.list.get(5).t.setNumber(needaction_counter + needaction_counter3);
                    warehouseFragment.list.get(6).t.setNumber(needaction_counter1);
                    warehouseFragment.list.get(7).t.setNumber(needaction_counter2);
//                    //               warehouseFragment.list.get(7).t.setNumber(needaction_counter2);
                    warehouseFragment.sectionAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<LoadActionBean> call, Throwable t) {
                //      AlertAialogUtils.dismissDefultProgressDialog();
                MyLog.e(TAG, t.toString());
            }
        });
    }

    @OnClick(R.id.radio_button2)
    void clickRadio_Button2(View view) {
        mViewPager.setCurrentItem(1, true);
        titleText.setText("生产");
        produceFragment.onResume();
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
        produceFragment = new ProduceFragment();
        myfragment[1] = produceFragment;
        inspectionFragment = new InspectionFragment();
        myfragment[2] = inspectionFragment;
        meFragment = new MeFragment();
        myfragment[3] = meFragment;
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myViewPagerAdapter);
        mViewPager.setCurrentItem(0);
        radioButton1.setClickable(true);
        refreshLoadAction();
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
        res_data = null;
        super.onPause();
    }

    /**
     * 存储供应商信息
     */
    private void getSupplier() {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name", null);
        // type: ‘supplier’ or ‘customer’
        objectObjectHashMap.put("type", "supplier");
        Call<SearchSupplierResponse> stringCall = inventoryApi.searchSupplier(objectObjectHashMap);
        stringCall.enqueue(new Callback<SearchSupplierResponse>() {
            @Override
            public void onResponse(Call<SearchSupplierResponse> call, Response<SearchSupplierResponse> response) {
                if (response.body() == null) return;
                /*if (response.body().getResult() != null) {
                    List<SearchSupplierResponse.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                    if (res_data != null && res_data.size() > 0) {
                        for (SearchSupplierResponse.ResultBean.ResDataBean resDataBean : res_data) {
                            contactsBeanDao.insertOrReplace(new ContactsBean(resDataBean.getComment(), resDataBean.getPhone()
                                    , resDataBean.getPartner_id(), resDataBean.getName(), resDataBean.getX_qq()));
                        }
                    }
                }
                long count = contactsBeanDao.count();
                MyLog.e(TAG,"contactsBeanDao里面的数量是"+count);*/
            }

            @Override
            public void onFailure(Call<SearchSupplierResponse> call, Throwable t) {
            }
        });
    }

}
