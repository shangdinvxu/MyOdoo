package tarce.myodoo.activity.salesout;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.inventory.CustomerSaleBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.NewSaleCustomAdapter;
import tarce.myodoo.fragment.CanSaleFragment;
import tarce.myodoo.fragment.WaitSaleFragment;

/**
 * Created by zws on 2017/8/9.
 */

public class NewSaleActivity extends Activity {
    /*@InjectView(R.id.wait_radio)
    RadioButton waitRadio;
    @InjectView(R.id.can_radio)
    RadioButton canRadio;
    @InjectView(R.id.parent_radio)
    RadioGroup parentRadio;*/
    /*@InjectView(R.id.fragment_sale)
    FrameLayout fragmentSale;*/
    @InjectView(R.id.sale_title)
    TextView saleTitle;
    @InjectView(R.id.recycler_customer)
    RecyclerView recyclerCustomer;
    @InjectView(R.id.back_no_left)
    ImageView backNoLeft;
    @InjectView(R.id.right_detail)
    ImageView rightDetail;
    private Fragment currentFragment;
    private WaitSaleFragment waitSaleFragment;
    private CanSaleFragment canSaleFragment;
    private int team_id;
    private InventoryApi inventoryApi;
    private ProgressDialog progressDialog;
    private NewSaleCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsale);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(NewSaleActivity.this).create(InventoryApi.class);
        Intent intent = getIntent();
        team_id = intent.getIntExtra("team_id", -1);
        String team_name = intent.getStringExtra("team_name");
        saleTitle.setText(team_name);
        progressDialog = new ProgressDialog(NewSaleActivity.this);
        progressDialog.setMessage("加载中...");
        recyclerCustomer.setLayoutManager(new LinearLayoutManager(NewSaleActivity.this));
        recyclerCustomer.addItemDecoration(new DividerItemDecoration(NewSaleActivity.this,
                DividerItemDecoration.VERTICAL));
        getData();
    }

    private void getData() {
        progressDialog.show();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("team_id", team_id);
        Call<CustomerSaleBean> partnerByTeam = inventoryApi.getPartnerByTeam(hashMap);
        partnerByTeam.enqueue(new MyCallback<CustomerSaleBean>() {
            @Override
            public void onResponse(Call<CustomerSaleBean> call, Response<CustomerSaleBean> response) {
                progressDialog.dismiss();
                if (response.body() == null) return;
                List<CustomerSaleBean.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                adapter = new NewSaleCustomAdapter(R.layout.item_customsale, res_data);
                recyclerCustomer.setAdapter(adapter);
                initListner();
            }

            @Override
            public void onFailure(Call<CustomerSaleBean> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.back_no_left)
    void finishThis(View view) {
        finish();
    }

    private void initListner() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<CustomerSaleBean.ResultBean.ResDataBean> data = adapter.getData();
                Intent intent = new Intent(NewSaleActivity.this, NewSaleListActivity.class);
                intent.putExtra("partner_id", data.get(position).getPartner_id());
                intent.putExtra("partner_name", data.get(position).getName());
                startActivity(intent);
            }
        });
    }

    /*waitRadio.setChecked(true);
        waitSaleFragment = new WaitSaleFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        currentFragment = waitSaleFragment;
        ft.add(R.id.fragment_sale, waitSaleFragment).commit(); // 隐藏当前的fragment，add下一个到Activity中

        parentRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.wait_radio) {
                    if (waitSaleFragment == null) {
                        waitSaleFragment = new WaitSaleFragment();
                    }
                    switchFragment(waitSaleFragment);
                } else if (checkedRadioButtonId == R.id.can_radio) {
                    if (canSaleFragment == null) {
                        canSaleFragment = new CanSaleFragment();
                    }
                    switchFragment(canSaleFragment);
                }
            }
        });*/
    /*private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.fragment_sale, targetFragment)
                    .commit();
        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment)
                    .commit();
        }
        currentFragment = targetFragment;
    }*/
}
