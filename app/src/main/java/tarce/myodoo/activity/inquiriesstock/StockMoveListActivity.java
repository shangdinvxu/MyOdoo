package tarce.myodoo.activity.inquiriesstock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
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
import tarce.model.inventory.StockMoveListBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.adapter.stock.StockListAdapter;
import tarce.myodoo.adapter.stock.StockMoveListAdapter;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;
import tarce.support.ToastUtils;

/**
 * Created by zouzou on 2017/7/5.
 */

public class StockMoveListActivity extends BaseActivity {
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
    private int product_id;
    private InventoryApi inventoryApi;
    private StockMoveListAdapter listAdapter;
    private int loadTime = 0;
    private List<StockMoveListBean.ResultBean.ResDataBean> res_data;
    private List<StockMoveListBean.ResultBean.ResDataBean> dataBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_movelist);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(StockMoveListActivity.this).create(InventoryApi.class);
        setRecyclerview(swipeTarget);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        setTitle(name);
        product_id = intent.getIntExtra("product_id", 0);
        showDefultProgressDialog();
        initData(0, 20, Refresh_Move);
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
                initData(0, 20, Refresh_Move);
                if (listAdapter != null) {
                    listAdapter.notifyDataSetChanged();
                }
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
                        initData(20 * loadTime, 20, Load_Move);
                        listAdapter.notifyDataSetChanged();
                        swipeToLoad.setLoadingMore(false);
                    }
                }, 1000);
            }
        });
    }

    private void initData(final int offset, final int limit, final int move) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("product_id", product_id);
        hashMap.put("offset", offset);
        hashMap.put("limit", limit);
        Call<StockMoveListBean> stockMoves = inventoryApi.getStockMoves(hashMap);
        stockMoves.enqueue(new MyCallback<StockMoveListBean>() {
            @Override
            public void onResponse(Call<StockMoveListBean> call, Response<StockMoveListBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null || response.body().getResult() ==null)return;
                if (response.body().getResult().getRes_data()!=null && response.body().getResult().getRes_code() == 1){
                    res_data = response.body().getResult().getRes_data();
                    if (move == Refresh_Move) {
                        dataBeanList = res_data;
                        listAdapter = new StockMoveListAdapter(R.layout.adapter_stock_movelist, res_data);
                        swipeTarget.setAdapter(listAdapter);
                    } else {
                        if (res_data == null) {
                            ToastUtils.showCommonToast(StockMoveListActivity.this, "没有更多数据...");
                            return;
                        }
                        dataBeanList = listAdapter.getData();
                        dataBeanList.addAll(res_data);
                        listAdapter.setData(dataBeanList);
                    }
                    initListener();
                }
            }

            @Override
            public void onFailure(Call<StockMoveListBean> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
    }

    //item点击事件
    private void initListener() {
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(StockMoveListActivity.this, StockSubDetailActivity.class);
                intent.putExtra("bean", listAdapter.getData().get(position));
                startActivity(intent);
            }
        });
    }
}
