package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.GetSaleListResponse;
import tarce.myodoo.R;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;

/**
 * Created by zou.zou on 2017/6/22.
 * 收货列表
 */

public class TakeDeliveListActivity extends BaseActivity {
    @InjectView(R.id.sale_number)
    TextView saleNumber;
    @InjectView(R.id.parnter_name)
    TextView parnterName;
    @InjectView(R.id.state_deliever)
    TextView stateDeliever;
    @InjectView(R.id.swipe_refresh_header)
    RecyclerHeaderView swipeRefreshHeader;
    @InjectView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @InjectView(R.id.swipe_load_more_footer)
    RecyclerFooterView swipeLoadMoreFooter;
    @InjectView(R.id.swipeToLoad)
    SwipeToLoadLayout swipeToLoad;
    private int loadTime = 0;
    private InventoryApi inventoryApi;
    private String type_code;
    private String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takedel_list);
        ButterKnife.inject(this);


        setRecyclerview(swipeTarget);
        Intent intent = getIntent();
        type_code = intent.getStringExtra("type_code");
        state = intent.getStringExtra("state");
        initData(0,40);
        initRecycler();
    }

    private void initRecycler() {
        swipeRefreshHeader.setGravity(Gravity.CENTER);
        swipeLoadMoreFooter.setGravity(Gravity.CENTER);
        swipeToLoad.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoad.setLoadMoreFooterView(swipeLoadMoreFooter);
        swipeToLoad.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                /*initIntent(0,40, Refresh_Move);
                if (salesListAdapter != null){
                    salesListAdapter.notifyDataSetChanged();
                }*/
                swipeToLoad.setRefreshing(false);
            }
        });
        swipeToLoad.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                swipeToLoad.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /*loadTime++;
                        initIntent(40*loadTime, 40, Load_Move);
                        salesListAdapter.notifyDataSetChanged();*/
                        swipeToLoad.setLoadingMore(false);
                    }
                }, 1000);
            }
        });
    }


    private void initData(final int offset, final int limit){
        inventoryApi = RetrofitClient.getInstance(TakeDeliveListActivity.this).create(InventoryApi.class);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("picking_type_code", type_code);
        hashMap.put("state", state);
        hashMap.put("limit", limit);
        hashMap.put("offset", offset);
        Call<GetSaleListResponse> inComingOutgoingList = inventoryApi.getInComingOutgoingList(hashMap);
        inComingOutgoingList.enqueue(new MyCallback<GetSaleListResponse>() {
            @Override
            public void onResponse(Call<GetSaleListResponse> call, Response<GetSaleListResponse> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)return;
            }

            @Override
            public void onFailure(Call<GetSaleListResponse> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
    }
}
