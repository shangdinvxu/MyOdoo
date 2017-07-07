package tarce.myodoo.activity.inquiriesstock;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
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
import tarce.myodoo.utils.StringUtils;
import tarce.support.ToastUtils;
import tarce.support.ViewUtils;

/**
 * Created by zouzou on 2017/7/6.
 */

public class SearchForActivity extends AppCompatActivity {
    private static final int Refresh_Move = 1;//下拉动作
    private static final int Load_Move = 2;//上拉动作

    @InjectView(R.id.search_stock)
    SearchView searchStock;
    @InjectView(R.id.tv_cancel)
    TextView tvCancel;
    @InjectView(R.id.btn_num)
    Button btnNum;
    @InjectView(R.id.btn_name)
    Button btnName;
    @InjectView(R.id.linear_button)
    LinearLayout linearButton;
    @InjectView(R.id.swipe_refresh_header)
    RecyclerHeaderView swipeRefreshHeader;
    @InjectView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @InjectView(R.id.swipe_load_more_footer)
    RecyclerFooterView swipeLoadMoreFooter;
    @InjectView(R.id.swipeToLoad)
    SwipeToLoadLayout swipeToLoad;
    private boolean numOrName = true;
    private InventoryApi inventoryApi;
    private ProgressDialog progressDialog;
    private List<StockListBean.ResultBean.ResDataBean> res_data;
    private List<StockListBean.ResultBean.ResDataBean> dataBeanList;
    private StockListAdapter stockListAdapter;
    private int loadTime = 0;
    private String queryNee;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for);
        ButterKnife.inject(this);

        swipeTarget.setLayoutManager(new LinearLayoutManager(SearchForActivity.this));
        swipeTarget.addItemDecoration(new DividerItemDecoration(SearchForActivity.this,
                DividerItemDecoration.VERTICAL));
        inventoryApi = RetrofitClient.getInstance(SearchForActivity.this).create(InventoryApi.class);
        progressDialog = new ProgressDialog(SearchForActivity.this);
        initSearch();
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
                if (StringUtils.isNullOrEmpty(queryNee)){
                    swipeToLoad.setRefreshing(false);
                    return;
                }
                searchList(0, 20, Refresh_Move);
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
                        if (StringUtils.isNullOrEmpty(queryNee)){
                            swipeToLoad.setLoadingMore(false);
                            return;
                        }
                        loadTime++;
                        searchList(20 * loadTime, 20, Load_Move);
                        stockListAdapter.notifyDataSetChanged();
                        swipeToLoad.setLoadingMore(false);
                    }
                }, 1000);
            }
        });
    }

    private void initSearch() {
        searchStock.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.show();
                queryNee = query;
                ViewUtils.collapseSoftInputMethod(SearchForActivity.this, searchStock);
                searchList(0,20,Refresh_Move);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void searchList(final int offset, final int limit, final int move) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        if (numOrName){
            map.put("default_code", queryNee);
        }else {
            map.put("name", queryNee);
        }
        hashMap.put("condition", map);
        hashMap.put("product_type", "all");
        hashMap.put("limit", limit);
        hashMap.put("offset", offset);
        Call<StockListBean> productList = inventoryApi.getProductList(hashMap);
        productList.enqueue(new MyCallback<StockListBean>() {
            @Override
            public void onResponse(Call<StockListBean> call, Response<StockListBean> response) {
                progressDialog.dismiss();
                if (response.body() == null || response.body().getResult() == null) return;
                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                    res_data = response.body().getResult().getRes_data();
                    if (move == Refresh_Move) {
                        dataBeanList = res_data;
                        stockListAdapter = new StockListAdapter(R.layout.adapter_detaildeleive, res_data);
                        swipeTarget.setAdapter(stockListAdapter);
                    } else {
                        if (res_data == null) {
                            ToastUtils.showCommonToast(SearchForActivity.this, "没有更多数据...");
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
                progressDialog.dismiss();
            }
        });
    }

    private void initListener() {
        stockListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<StockListBean.ResultBean.ResDataBean> data = stockListAdapter.getData();
                Intent intent = new Intent(SearchForActivity.this, StockDetailActivity.class);
                intent.putExtra("bean", data.get(position));
                startActivity(intent);
                finish();
            }
        });
    }

    @OnClick(R.id.tv_cancel)
    void cancelSearch(View view){
        finish();
    }

    @OnClick(R.id.btn_num)
    void searByNum(View view) {
        if (numOrName) {
            numOrName = true;
        } else {
            btnNum.setBackgroundResource(R.color.color_5693f8);
            btnName.setBackgroundResource(R.color.grgray);
            numOrName = true;
        }
    }

    @OnClick(R.id.btn_name)
    void searByName(View view) {
        if (numOrName) {
            btnName.setBackgroundResource(R.color.color_5693f8);
            btnNum.setBackgroundResource(R.color.grgray);
            numOrName = false;
        } else {
            numOrName = false;
        }
    }
}
