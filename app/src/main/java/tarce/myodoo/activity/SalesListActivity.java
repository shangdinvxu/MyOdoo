package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
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
import tarce.myodoo.adapter.product.PickingDetailAdapter;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;

/**
 * Created by Daniel.Xu on 2017/4/20.
 */

public class SalesListActivity extends ToolBarActivity {
    private static final int Refresh_Move = 1;//下拉动作
    private static final int Load_Move = 2;//上拉动作

    @InjectView(R.id.swipe_refresh_header)
    RecyclerHeaderView swipeRefreshHeader;
    @InjectView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @InjectView(R.id.swipe_load_more_footer)
    RecyclerFooterView swipeLoadMoreFooter;
    @InjectView(R.id.swipeToLoad)
    SwipeToLoadLayout swipeToLoad;
    private SalesListAdapter salesListAdapter;
    private InventoryApi inventoryApi;
    private int count;
    private String state;
    private int complete_rate;
    private List<SalesOutListResponse.TResult.TRes_data> res_data;
    private List<SalesOutListResponse.TResult.TRes_data> dataBeanList;
    private int loadTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_list);
        ButterKnife.inject(this);
        inventoryApi = RetrofitClient.getInstance(SalesListActivity.this).create(InventoryApi.class);
        setRecyclerview(swipeTarget);
        showDefultProgressDialog();
        Intent intent = getIntent();
        state = intent.getStringExtra("state");
        complete_rate = intent.getIntExtra("complete_rate", 1000);
        initIntent(0,40, Refresh_Move);
        initrecycler();
    }

    private void initrecycler() {
        swipeRefreshHeader.setGravity(Gravity.CENTER);
        swipeLoadMoreFooter.setGravity(Gravity.CENTER);
        swipeToLoad.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoad.setLoadMoreFooterView(swipeLoadMoreFooter);
        swipeToLoad.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                initIntent(0,40, Refresh_Move);
                salesListAdapter.notifyDataSetChanged();
                swipeToLoad.setRefreshing(false);
            }
        });
        swipeToLoad.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                swipeToLoad.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadTime++;
                        initIntent(40*loadTime, 40, Load_Move);
                        salesListAdapter.notifyDataSetChanged();
                        swipeToLoad.setLoadingMore(false);
                    }
                }, 1000);
            }
        });
    }

    private void initIntent(int offset, int limit, final int move) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("complete_rate", complete_rate);
        objectObjectHashMap.put("limit", limit);
        objectObjectHashMap.put("offset", offset);
        objectObjectHashMap.put("state", state);
        Call<SalesOutListResponse> inComingOutgoingList = inventoryApi.getOutgoingStockpickingList(objectObjectHashMap);
        inComingOutgoingList.enqueue(new MyCallback<SalesOutListResponse>() {
            @Override
            public void onResponse(Call<SalesOutListResponse> call, Response<SalesOutListResponse> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getResult().getRes_code() == 1) {
                    res_data = response.body().getResult().getRes_data();
                    if (move == Refresh_Move){
                        dataBeanList = res_data;
                        salesListAdapter = new SalesListAdapter(R.layout.activity_saleslist_adapter_item, res_data);
                        swipeTarget.setAdapter(salesListAdapter);
                    }else {
                        if (res_data == null){
                            ToastUtils.showCommonToast(SalesListActivity.this, "没有更多数据...");
                            return;
                        }
                        dataBeanList = salesListAdapter.getData();
                        dataBeanList.addAll(res_data);
                        salesListAdapter.setData(dataBeanList);
                    }
                    initListener();
                }
            }

            @Override
            public void onFailure(Call<SalesOutListResponse> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
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
