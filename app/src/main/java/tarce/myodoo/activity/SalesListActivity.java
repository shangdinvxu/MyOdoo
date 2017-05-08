package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.GetSaleResponse;
import tarce.model.inventory.SalesOutListResponse;
import tarce.myodoo.R;
import tarce.myodoo.adapter.SalesListAdapter;
import tarce.support.ToolBarActivity;

/**
 * Created by Daniel.Xu on 2017/4/20.
 */

public class SalesListActivity extends ToolBarActivity {
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private SalesListAdapter salesListAdapter;
    private InventoryApi inventoryApi;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_list);
        ButterKnife.inject(this);
        inventoryApi = RetrofitClient.getInstance(SalesListActivity.this).create(InventoryApi.class);
        setRecyclerview(recyclerview);
        initIntent();
        initListener();
    }

    private void initIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("intent");
        count = intent.getIntExtra("count", 0);
        count = intent.getIntExtra("count", 0);
        count = intent.getIntExtra("count", 0);
        List<SalesOutListResponse.TResult.TRes_data> bundle1 = (List<SalesOutListResponse.TResult.TRes_data>) bundle.getSerializable("bundle");
        salesListAdapter = new SalesListAdapter(R.layout.activity_saleslist_adapter_item, bundle1);
        recyclerview.setAdapter(salesListAdapter);
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

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Observable.timer(1000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                swipeLayout.setRefreshing(false);
                            }
                        });
            }
        });

        salesListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (salesListAdapter.getData().size()>=count){
                    salesListAdapter.loadMoreEnd();
                }else {


                }
            }
        },recyclerview);



    }
}
