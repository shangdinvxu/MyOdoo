package tarce.myodoo.activity.retail;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.inventory.RetailSubBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.adapter.retail.RetailSubAdapter;
import tarce.support.MyLog;

/**
 * Created by zouzou on 2017/7/4.
 * 零售子页面
 */

public class RetailSubActivity extends BaseActivity {
    @InjectView(R.id.recycler_robotime)
    RecyclerView recyclerRobotime;
    private InventoryApi inventoryApi;
    private RetailSubAdapter subAdapter;
    private List<RetailSubBean.ResultBean.ResDataBean> res_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailsub);
        ButterKnife.inject(this);

        setTitle("零售");
        setRecyclerview(recyclerRobotime);
        inventoryApi = RetrofitClient.getInstance(RetailSubActivity.this).create(InventoryApi.class);
        initData();
    }

    //获取商店列表
    private void initData() {
        showDefultProgressDialog();
        Call<RetailSubBean> ebShopList = inventoryApi.getEbShopList(new HashMap());
        ebShopList.enqueue(new MyCallback<RetailSubBean>() {
            @Override
            public void onResponse(Call<RetailSubBean> call, Response<RetailSubBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)return;
                if (response.body().getResult().getRes_data()!=null && response.body().getResult().getRes_code() == 1){
                    res_data = response.body().getResult().getRes_data();
                    subAdapter = new RetailSubAdapter(R.layout.adapter_retailsub, res_data);
                    recyclerRobotime.setAdapter(subAdapter);
                    initLisener();
                }
            }

            @Override
            public void onFailure(Call<RetailSubBean> call, Throwable t) {
                dismissDefultProgressDialog();
                MyLog.e("RetailSubActivity",t.toString());
            }
        });
    }

    //item点击事件
    private void initLisener() {
        subAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }
}
