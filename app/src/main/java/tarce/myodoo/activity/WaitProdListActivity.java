package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

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
import tarce.model.inventory.MaterialDetailBean;
import tarce.model.inventory.PickingDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.product.PickingDetailAdapter;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;
import tarce.myodoo.utils.DateTool;
import tarce.support.SharePreferenceUtils;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/6/15.
 * 等待生产延误的列表
 */

public class WaitProdListActivity extends ToolBarActivity {
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
    private String state_delay;
    private InventoryApi inventoryApi;
    private List<PickingDetailBean.ResultBean.ResDataBean> beanList = new ArrayList<>();
    private List<PickingDetailBean.ResultBean.ResDataBean> dataBeanList = new ArrayList<>();
    ;
    private PickingDetailAdapter adapter;
    private int loadTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_ll);
        ButterKnife.inject(this);

        setRecyclerview(swipeTarget);
        Intent intent = getIntent();
        state_delay = intent.getStringExtra("state_delay");
        initRecycler();
        getData(20,0,Refresh_Move);
    }

    @Override
    protected void onResume() {
        if (dataBeanList == null){
            swipeToLoad.setRefreshing(true);
            loadTime = 0;
        }
        super.onResume();
    }

    private void initRecycler() {
        showDefultProgressDialog();
        swipeRefreshHeader.setGravity(Gravity.CENTER);
        swipeLoadMoreFooter.setGravity(Gravity.CENTER);
        swipeToLoad.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoad.setLoadMoreFooterView(swipeLoadMoreFooter);

        swipeToLoad.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                showDefultProgressDialog();
                getData(20, 0, Refresh_Move);
                adapter.notifyDataSetChanged();
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
                        getData(20, 20 * loadTime, Load_Move);
                        adapter.notifyDataSetChanged();
                        swipeToLoad.setLoadingMore(false);
                    }
                }, 1000);
            }
        });
    }

    private void getData(int limit, int offset, final int move) {
        inventoryApi = RetrofitClient.getInstance(WaitProdListActivity.this).create(InventoryApi.class);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("limit",limit);
        hashMap.put("offset",offset);
        int partner_id = SharePreferenceUtils.getInt("partner_id", 1000, WaitProdListActivity.this);
        hashMap.put("partner_id",partner_id);
        switch (state_delay) {
            case "延误":
                hashMap.put("date", "delay");
                break;
            case "今天":
                hashMap.put("date", DateTool.getDate());
                break;
            case "明天":
                hashMap.put("date", DateTool.getDateTime(DateTool.addDays(1), DateTool.DEFAULT_DATE_FORMAT));
                break;
            case "后天":
                hashMap.put("date", DateTool.getDateTime(DateTool.addDays(2), DateTool.DEFAULT_DATE_FORMAT));
                break;
        }
        Call<PickingDetailBean> listWait = inventoryApi.getListWait(hashMap);
        listWait.enqueue(new MyCallback<PickingDetailBean>() {
            @Override
            public void onResponse(Call<PickingDetailBean> call, Response<PickingDetailBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)return;
                if (response.body().getResult().getRes_code() == 1){
                    beanList = response.body().getResult().getRes_data();
                    if (move == Refresh_Move){
                        dataBeanList = beanList;
                        adapter = new PickingDetailAdapter(R.layout.adapter_picking_activity, beanList);
                        swipeTarget.setAdapter(adapter);
                    }else {
                        if (beanList == null){
                            ToastUtils.showCommonToast(WaitProdListActivity.this, "没有更多数据...");
                            return;
                        }
                        dataBeanList = adapter.getData();
                        dataBeanList.addAll(beanList);
                        adapter.setData(dataBeanList);
                    }
                    clickAdapterItem();
                }
            }

            @Override
            public void onFailure(Call<PickingDetailBean> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
    }

    private void clickAdapterItem() {

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int order_id = dataBeanList.get(position).getOrder_id();
                if (dataBeanList.get(position).getState().equals("progress")) {
                    Intent intent = new Intent(WaitProdListActivity.this, ProductingActivity.class);
                    intent.putExtra("order_name", dataBeanList.get(position).getDisplay_name());
                    intent.putExtra("order_id", order_id);
                    intent.putExtra("state", "progress");
                    intent.putExtra("name_activity", state_delay);
                    intent.putExtra("state_activity", "already_picking");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(WaitProdListActivity.this, OrderDetailActivity.class);
                    intent.putExtra("order_name", dataBeanList.get(position).getDisplay_name());
                    intent.putExtra("order_id", order_id);
                    intent.putExtra("state", dataBeanList.get(position).getState());
                    intent.putExtra("name_activity", state_delay);
                    intent.putExtra("state_activity", "already_picking");
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
}
