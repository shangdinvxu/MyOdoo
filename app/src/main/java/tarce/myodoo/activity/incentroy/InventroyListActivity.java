package tarce.myodoo.activity.incentroy;

import android.content.Intent;
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
import tarce.model.inventory.InventroyDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.adapter.inventroy.InventroyDetailAdapter;

/**
 * Created by zouzou on 2017/7/4.
 */

public class InventroyListActivity extends BaseActivity {
    @InjectView(R.id.recycler_change)
    RecyclerView recyclerChange;
    private int inventory_id;
    private InventoryApi inventoryApi;
    private InventroyDetailAdapter detailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventroy_list);
        ButterKnife.inject(this);

        setRecyclerview(recyclerChange);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        inventory_id = intent.getIntExtra("inventory_id", 1);
        setTitle(name);
        inventoryApi = RetrofitClient.getInstance(InventroyListActivity.this).create(InventoryApi.class);
        showDefultProgressDialog();
        initData();
    }

    //获取数据
    private void initData() {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("inventory_id",inventory_id);
        Call<InventroyDetailBean> stockDetail = inventoryApi.getStockDetail(hashMap);
        stockDetail.enqueue(new MyCallback<InventroyDetailBean>() {
            @Override
            public void onResponse(Call<InventroyDetailBean> call, Response<InventroyDetailBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null || response.body().getResult()== null)return;
                if (response.body().getResult().getRes_data()!=null && response.body().getResult().getRes_code() == 1){
                    List<InventroyDetailBean.ResultBean.ResDataBean.LineIdsBean> line_ids = response.body().getResult().getRes_data().getLine_ids();
                    detailAdapter = new InventroyDetailAdapter(R.layout.adapter_detaildeleive, line_ids);
                    recyclerChange.setAdapter(detailAdapter);
                    initListener();
                }
            }

            @Override
            public void onFailure(Call<InventroyDetailBean> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
    }

    //点击事件
    private void initListener() {
        detailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(InventroyListActivity.this, InventroyDetailActivity.class);
                intent.putExtra("bean", detailAdapter.getData().get(position));
                startActivity(intent);
            }
        });
    }
}
