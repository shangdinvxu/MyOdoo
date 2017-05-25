package tarce.myodoo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.LoadActionBean;
import tarce.myodoo.MyApplication;
import tarce.myodoo.R;
import tarce.myodoo.fragment.InspectionFragment;
import tarce.myodoo.fragment.MeFragment;
import tarce.myodoo.fragment.ProduceFragment;
import tarce.myodoo.fragment.WarehouseFragment;
import tarce.myodoo.view.NoScrollViewPager;
import tarce.support.AlertAialogUtils;
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

    /*private List<TestBean.TestRSubBean.ListSubBean> listSubBeen  = new ArrayList<TestBean.TestRSubBean.ListSubBean>();
    private List<Integer> listInterge = new ArrayList<>();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        inventoryApi = RetrofitClient.getInstance(MainActivity.this).create(InventoryApi.class);
        initFragment();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //   mViewPager.setCurrentItem(0, false);
        //  refreshLoadAction();
    }

    @OnClick(R.id.radio_button1)
    void clickRadio_Button1(View view) {
        mViewPager.setCurrentItem(0, false);
        refreshLoadAction();
    }

    private void refreshLoadAction() {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        String[] names = {"linkloving_mrp_extend.menu_mrp_prepare_material_ing",
                "linkloving_mrp_extend.menu_mrp_waiting_inventory_material",
                "linkloving_mrp_extend.menu_mrp_waiting_post_inventory"};
        objectObjectHashMap.put("xml_names", names);
        objectObjectHashMap.put("user_id", MyApplication.userID);
        AlertAialogUtils.showDefultProgressDialog(MainActivity.this);
        Call<LoadActionBean> objectCall = inventoryApi.load_actionCall(objectObjectHashMap);
        objectCall.enqueue(new Callback<LoadActionBean>() {
            @Override
            public void onResponse(Call<LoadActionBean> call, Response<LoadActionBean> response) {
                AlertAialogUtils.dismissDefultProgressDialog();
                if (response.body() != null && response.body().getResult().getRes_code() == 1) {
                    Integer needaction_counter = response.body().getResult().getRes_data().getLinkloving_mrp_extend_menu_mrp_prepare_material_ing().getNeedaction_counter();
                    Integer needaction_counter1 = response.body().getResult().getRes_data().getLinkloving_mrp_extend_menu_mrp_waiting_inventory_material().getNeedaction_counter();
//                    Integer needaction_counter2 = response.body().getResult().getRes_data().getLinkloving_mrp_extend_menu_mrp_waiting_post_inventory().getNeedaction_counter();
                    warehouseFragment.list.get(5).t.setNumber(needaction_counter);
                    warehouseFragment.list.get(6).t.setNumber(needaction_counter1);
                    //               warehouseFragment.list.get(7).t.setNumber(needaction_counter2);
                    warehouseFragment.sectionAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<LoadActionBean> call, Throwable t) {
                AlertAialogUtils.dismissDefultProgressDialog();
                MyLog.e(TAG, t.toString());
            }
        });
    }

    @OnClick(R.id.radio_button2)
    void clickRadio_Button2(View view) {
        mViewPager.setCurrentItem(1, true);
    }

    @OnClick(R.id.radio_button3)
    void clickRadio_Button3(View view) {
        mViewPager.setCurrentItem(2, false);
    }

    @OnClick(R.id.radio_button4)
    void clickRadio_Button4(View view) {
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
        radioButton1.setChecked(true);
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

}
