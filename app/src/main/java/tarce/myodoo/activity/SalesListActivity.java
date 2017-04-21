package tarce.myodoo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.GetSaleListResponse;
import tarce.model.GetSaleResponse;
import tarce.myodoo.R;
import tarce.myodoo.adapter.SalesListAdapter;
import tarce.support.ToolBarActivity;

/**
 * Created by Daniel.Xu on 2017/4/20.
 */

public class SalesListActivity extends ToolBarActivity {
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    private SalesListAdapter salesListAdapter;
    private InventoryApi inventoryApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_list);
        ButterKnife.inject(this);
        inventoryApi = RetrofitClient.getInstance(SalesListActivity.this).create(InventoryApi.class);
        setRecyclerview(recyclerview);
        initIntent();
    }

    private void initIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("intent");
        List<GetSaleListResponse.TResult.TRes_data> bundle1 = (List<GetSaleListResponse.TResult.TRes_data>) bundle.getSerializable("bundle");
        salesListAdapter = new SalesListAdapter(R.layout.activity_salelist_item, bundle1);
        recyclerview.setAdapter(salesListAdapter);
        initListener();
    }

    private void initListener() {
        salesListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showDefultProgressDialog();
                HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("picking_id", salesListAdapter.getData().get(position).getPicking_id());
                Call<GetSaleResponse> stringCall = inventoryApi.checkIsCanUse(objectObjectHashMap);
                stringCall.enqueue(new MyCallback<GetSaleResponse>() {
                    @Override
                    public void onResponse(Call<GetSaleResponse> call, Response<GetSaleResponse> response) {
                        dismissDefultProgressDialog();
                        GetSaleResponse.TResult result = response.body().getResult();
                        Intent intent = new Intent(SalesListActivity.this, SalesDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("bundle", result.getRes_data());
                        intent.putExtra("intent", bundle);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<GetSaleResponse> call, Throwable t) {
                        dismissDefultProgressDialog();
                        super.onFailure(call, t);
                    }
                });

            }
        });



    }
}
