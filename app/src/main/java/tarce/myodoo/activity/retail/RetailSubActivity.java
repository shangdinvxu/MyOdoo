package tarce.myodoo.activity.retail;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.support.MyLog;

/**
 * Created by zouzou on 2017/7/4.
 * 零售子页面
 */

public class RetailSubActivity extends BaseActivity {
    @InjectView(R.id.recycler_robotime)
    RecyclerView recyclerRobotime;
    private InventoryApi inventoryApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailsub);
        ButterKnife.inject(this);

        setRecyclerview(recyclerRobotime);
        inventoryApi = RetrofitClient.getInstanceAno(RetailSubActivity.this, "").create(InventoryApi.class);
        initData();
    }

    //获取商店列表
    private void initData() {
        showDefultProgressDialog();
        Call<Object> ebShopList = inventoryApi.getEbShopList(new HashMap());
        ebShopList.enqueue(new MyCallback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)return;
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                dismissDefultProgressDialog();
                MyLog.e("RetailSubActivity",t.toString());
            }
        });
    }
}
