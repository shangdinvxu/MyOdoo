package tarce.myodoo.activity.incentroy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.inventory.InventroyResultBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.inventroy.InventroyAdapter;
import tarce.support.MyLog;

/**
 * Created by zouzou on 2017/7/4.
 */

public class InventroyActivity extends AppCompatActivity {
    @InjectView(R.id.recycler_inventroy)
    RecyclerView recyclerInventroy;
    @InjectView(R.id.swipe_refresh_my)
    SwipeRefreshLayout swipeRefreshMy;
    @InjectView(R.id.image_inven)
    ImageView imageInven;
    private InventoryApi inventoryApi;
    private InventroyAdapter inventroyAdapter;
    private List<InventroyResultBean.ResultBean.ResDataBean> res_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventroy);
        ButterKnife.inject(this);

        recyclerInventroy.setLayoutManager(new LinearLayoutManager(InventroyActivity.this));
        recyclerInventroy.addItemDecoration(new DividerItemDecoration(InventroyActivity.this,
                DividerItemDecoration.VERTICAL));
        inventoryApi = RetrofitClient.getInstance(InventroyActivity.this).create(InventoryApi.class);
        initData(0, 80);
        swipeRe();
    }

    //下拉刷新
    private void swipeRe() {
        swipeRefreshMy.setColorSchemeColors(Color.BLUE);
        swipeRefreshMy.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(0, 80);
                swipeRefreshMy.setRefreshing(false);
            }
        });
    }

    private void initData(final int offset, final int limit) {
        res_data = new ArrayList<>();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("offset", offset);
        hashMap.put("limit", limit);
        Call<InventroyResultBean> stockInvenList = inventoryApi.getStockInvenList(hashMap);
        stockInvenList.enqueue(new MyCallback<InventroyResultBean>() {
            @Override
            public void onResponse(Call<InventroyResultBean> call, Response<InventroyResultBean> response) {
                if (response.body() == null) return;
                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                    res_data = response.body().getResult().getRes_data();
                    inventroyAdapter = new InventroyAdapter(R.layout.adapter_inventroy, res_data);
                    recyclerInventroy.setAdapter(inventroyAdapter);
                    initListener();
                }
            }

            @Override
            public void onFailure(Call<InventroyResultBean> call, Throwable t) {
                MyLog.e("Inventroy", t.toString());
            }
        });
    }

    //点击事件
    private void initListener() {
        inventroyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position >= adapter.getData().size() - 1) {
                    return;
                }
                Intent intent = new Intent(InventroyActivity.this, InventroyListActivity.class);
                intent.putExtra("name", inventroyAdapter.getData().get(position).getName());
                intent.putExtra("inventory_id", inventroyAdapter.getData().get(position).getId());
                startActivity(intent);
            }
        });
    }
}
