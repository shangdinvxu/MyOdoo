package tarce.myodoo.activity.inquiriesstock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;

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
import tarce.model.inventory.StockListBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.adapter.stock.StockListAdapter;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;
import tarce.support.ToastUtils;

/**
 * Created by zouzou on 2017/7/5.
 */

public class StockListActivity extends BaseActivity {
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
    @InjectView(R.id.search_stock)
    SearchView searchStock;
    @InjectView(R.id.btn_num)
    Button btnNum;
    @InjectView(R.id.btn_name)
    Button btnName;
    @InjectView(R.id.linear_button)
    LinearLayout linearButton;
    private InventoryApi inventoryApi;
    private StockListAdapter stockListAdapter;
    private List<StockListBean.ResultBean.ResDataBean> res_data;
    private List<StockListBean.ResultBean.ResDataBean> dataBeanList;
    private int loadTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocklist);
        ButterKnife.inject(this);

        setTitle("产品");
        setRecyclerview(swipeTarget);
        initSearch();
        inventoryApi = RetrofitClient.getInstance(StockListActivity.this).create(InventoryApi.class);
        showDefultProgressDialog();
        initData(0, 20, Refresh_Move);
        initRecycler();
    }

    //搜索监听
    private void initSearch() {
        searchStock.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                linearButton.setVisibility(View.VISIBLE);
                return false;
            }
        });
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
                if (stockListAdapter != null) {
                    stockListAdapter.notifyDataSetChanged();
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
                        stockListAdapter.notifyDataSetChanged();
                        swipeToLoad.setLoadingMore(false);
                    }
                }, 1000);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData(final int offset, final int limit, final int move) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("product_type", "all");
        hashMap.put("limit", limit);
        hashMap.put("offset", offset);
        Call<StockListBean> productList = inventoryApi.getProductList(hashMap);
        productList.enqueue(new MyCallback<StockListBean>() {
            @Override
            public void onResponse(Call<StockListBean> call, Response<StockListBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null || response.body().getResult() == null) return;
                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                    res_data = response.body().getResult().getRes_data();
                    if (move == Refresh_Move) {
                        dataBeanList = res_data;
                        stockListAdapter = new StockListAdapter(R.layout.adapter_detaildeleive, res_data);
                        swipeTarget.setAdapter(stockListAdapter);
                    } else {
                        if (res_data == null) {
                            ToastUtils.showCommonToast(StockListActivity.this, "没有更多数据...");
                            return;
                        }
                        dataBeanList = stockListAdapter.getData();
                        dataBeanList.addAll(res_data);
                        stockListAdapter.setData(dataBeanList);
                    }
                    initListener();
                }
            }

            @Override
            public void onFailure(Call<StockListBean> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
    }

    //点击事件
    private void initListener() {
        stockListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<StockListBean.ResultBean.ResDataBean> data = stockListAdapter.getData();
                Intent intent = new Intent(StockListActivity.this, StockDetailActivity.class);
                intent.putExtra("bean", data.get(position));
                startActivity(intent);
            }
        });
    }
}
