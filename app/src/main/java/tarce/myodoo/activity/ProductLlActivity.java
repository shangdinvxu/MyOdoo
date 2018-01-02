package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.SearchView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
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
import tarce.model.inventory.PickingDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.product.PickingDetailAdapter;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;
import tarce.myodoo.uiutil.TipDialog;
import tarce.support.SharePreferenceUtils;
import tarce.support.ToastUtils;

/**
 * Created by rose.zou on 2017/5/19.
 * 用于生产页面的跳转子页面
 */

public class ProductLlActivity extends BaseActivity {
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
    @InjectView(R.id.search_mo_product)
    SearchView searchMoProduct;
    private InventoryApi loginApi;
    private List<PickingDetailBean.ResultBean.ResDataBean> beanList = new ArrayList<>();
    private List<PickingDetailBean.ResultBean.ResDataBean> dataBeanList = new ArrayList<>();
    private PickingDetailAdapter adapter;
    private int loadTime = 0;
    private String state_activity;//生产状态
    private String name_activity;
    private int process_id;
    private int production_line_id;
    private int origin_sale_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_ll);
        ButterKnife.inject(this);

        searchMoProduct.setVisibility(View.GONE);
        setRecyclerview(swipeTarget);
        Intent intent = getIntent();
        name_activity = intent.getStringExtra("name_activity");
        setTitle(name_activity);
        state_activity = intent.getStringExtra("state_product");
        process_id = intent.getIntExtra("process_id", -1000);
        production_line_id = intent.getIntExtra("line_id", -1000);
        origin_sale_id = intent.getIntExtra("origin_sale_id", 0);

        initView();
        getPicking(0, 20, Refresh_Move);
    }

    @Override
    protected void onResume() {
        if (dataBeanList == null) {
            swipeToLoad.setRefreshing(true);
            loadTime = 0;
        }
        super.onResume();
    }

    private void initView() {
        showDefultProgressDialog();
        swipeRefreshHeader.setGravity(Gravity.CENTER);
        swipeLoadMoreFooter.setGravity(Gravity.CENTER);
        swipeToLoad.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoad.setLoadMoreFooterView(swipeLoadMoreFooter);

        swipeToLoad.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                showDefultProgressDialog();
                getPicking(0, 20, Refresh_Move);
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
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
                        getPicking(20 * loadTime, 20, Load_Move);
                        adapter.notifyDataSetChanged();
                        swipeToLoad.setLoadingMore(false);
                    }
                }, 1000);
            }
        });
    }

    /**
     * 获取领料的详情
     */
    private void getPicking(final int offset, final int limit, final int move) {
        loginApi = RetrofitClient.getInstance(ProductLlActivity.this).create(InventoryApi.class);
        HashMap<Object, Object> hashMap = new HashMap<>();
        if (!"rework_ing".equals(state_activity)) {
            hashMap.put("state", state_activity);
        }
        hashMap.put("offset", offset);
        hashMap.put("limit", limit);
        if ("rework_ing".equals(state_activity) || "finish_prepare_material".equals(state_activity) || "already_picking".equals(state_activity)
                || "progress".equals(state_activity) || "waiting_rework".equals(state_activity)
                || "waiting_inventory_material".equals(state_activity)
                || "force_cancel_waiting_return".equals(state_activity)) {
            int partner_id = SharePreferenceUtils.getInt("partner_id", 1000, ProductLlActivity.this);
            hashMap.put("partner_id", partner_id);
        }
        if (process_id != -1000) {
            hashMap.put("process_id", process_id);
        }else {

        }
        if (production_line_id != -1000){
            hashMap.put("production_line_id", production_line_id);
        }else {
            hashMap.put("production_line_id", false);
        }
        if (origin_sale_id!=0){
            hashMap.put("origin_sale_id", origin_sale_id);
        }else {
            hashMap.put("origin_sale_id", false);
        }
        hashMap.put("date", "all");
        Call<PickingDetailBean> stringCall;
        if ("rework_ing".equals(state_activity)) {
            stringCall = loginApi.newReworkIng(hashMap);
        } else {
            stringCall = loginApi.getNewRecentOr(hashMap);
        }

        stringCall.enqueue(new MyCallback<PickingDetailBean>() {
            @Override
            public void onResponse(Call<PickingDetailBean> call, Response<PickingDetailBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError() != null) {
                    new TipDialog(ProductLlActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                    beanList = response.body().getResult().getRes_data();
                    if (move == Refresh_Move) {
                        dataBeanList = beanList;
                        adapter = new PickingDetailAdapter(R.layout.adapter_picking_activity, beanList);
                        swipeTarget.setAdapter(adapter);
                    } else {
                        if (beanList == null) {
                            ToastUtils.showCommonToast(ProductLlActivity.this, "没有更多数据...");
                            return;
                        }
                        dataBeanList = adapter.getData();
                        dataBeanList.addAll(beanList);
                        adapter.setData(dataBeanList);
                    }
                    clickAdapterItem();
                } else if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() == null
                        && move != Load_Move) {
                    swipeTarget.setVisibility(View.GONE);
                    ToastUtils.showCommonToast(ProductLlActivity.this, "没有更多数据...");
                }
            }

            @Override
            public void onFailure(Call<PickingDetailBean> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
    }

    /**
     * recycleView的item的点击事件
     */
    private void clickAdapterItem() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (dataBeanList == null) return;
                int order_id = dataBeanList.get(position).getOrder_id();
                if (dataBeanList.get(position).getState().equals("progress")) {
                    Intent intent = new Intent(ProductLlActivity.this, ProductingActivity.class);
                    intent.putExtra("order_name", dataBeanList.get(position).getDisplay_name());
                    intent.putExtra("order_id", order_id);
                    intent.putExtra("state", "progress");
                    intent.putExtra("name_activity", name_activity);
                    intent.putExtra("state_activity", state_activity);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ProductLlActivity.this, OrderDetailActivity.class);
                    intent.putExtra("order_name", dataBeanList.get(position).getDisplay_name());
                    intent.putExtra("order_id", order_id);
                    intent.putExtra("state", dataBeanList.get(position).getState());
                    intent.putExtra("name_activity", name_activity);
                    intent.putExtra("process_id", process_id);
                    intent.putExtra("line_id", production_line_id);
                    intent.putExtra("origin_sale_id", origin_sale_id);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        dataBeanList = null;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        dataBeanList = null;
        super.onDestroy();
    }
}
