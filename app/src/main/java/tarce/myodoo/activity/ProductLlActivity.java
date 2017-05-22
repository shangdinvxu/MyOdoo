package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import tarce.model.inventory.GetNumProcess;
import tarce.model.inventory.PickingDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.product.PickingDetailAdapter;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;
import tarce.support.AlertAialogUtils;
import tarce.support.SharePreferenceUtils;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/5/19.
 * 用于生产页面的跳转子页面
 */

public class ProductLlActivity extends ToolBarActivity {
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
    private InventoryApi loginApi;
    private List<PickingDetailBean.ResultBean.ResDataBean> beanList = new ArrayList<>();
    private List<PickingDetailBean.ResultBean.ResDataBean> dataBeanList;
    private PickingDetailAdapter adapter;
    private int loadTime = 1;
    private String state_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_ll);
        ButterKnife.inject(this);

        setRecyclerview(swipeTarget);
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("name_activity"));
        state_activity = intent.getStringExtra("state_product");

        initView();
        getPicking(0, 15, Refresh_Move);
    }

    private void initView() {
        AlertAialogUtils.showDefultProgressDialog(ProductLlActivity.this);
        swipeRefreshHeader.setGravity(Gravity.CENTER);
        swipeToLoad.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoad.setLoadMoreFooterView(swipeLoadMoreFooter);

        swipeToLoad.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                AlertAialogUtils.showDefultProgressDialog(ProductLlActivity.this);
               // beanList.clear();
                getPicking(0, 15, Refresh_Move);
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
                        dataBeanList = new ArrayList<PickingDetailBean.ResultBean.ResDataBean>();
                        getPicking(15 * loadTime + 1, 15 * (loadTime + 1) + 1, Load_Move);
                        adapter.notifyDataSetChanged();
                        swipeToLoad.setLoadingMore(false);
                        loadTime = 2;
                    }
                }, 2000);
            }
        });
    }

    /**
     * 获取领料的详情
     */
    private void getPicking(int offset, int limit, final int action) {
        loginApi = RetrofitClient.getInstance(ProductLlActivity.this).create(InventoryApi.class);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("state", state_activity);
        hashMap.put("offset", offset);
        hashMap.put("limit", limit);
        if (!state_activity.equals("rework_ing") || !state_activity.equals("done")) {
            int partner_id = SharePreferenceUtils.getInt("partner_id", 1000, ProductLlActivity.this);
            hashMap.put("partner_id", partner_id);
        }
        Call<PickingDetailBean> stringCall = loginApi.getPicking(hashMap);
        stringCall.enqueue(new MyCallback<PickingDetailBean>() {
            @Override
            public void onResponse(Call<PickingDetailBean> call, Response<PickingDetailBean> response) {
                AlertAialogUtils.dismissDefultProgressDialog();
                if (response.body() == null) return;

                if (action == 2) {//上拉加载
                    dataBeanList = response.body().getResult().getRes_data();
                    for (int i = 0; i < dataBeanList.size(); i++) {
                        beanList.add(beanList.size() + i - 1, dataBeanList.get(i));
                    }
                    //    beanList.addAll(beanList.size()-1, dataBeanList);
                } else {//下拉刷新
                    beanList = response.body().getResult().getRes_data();
                }
                adapter = new PickingDetailAdapter(R.layout.adapter_picking_activity, beanList);
                swipeTarget.setAdapter(adapter);
                clickAdapterItem();
            }

            @Override
            public void onFailure(Call<PickingDetailBean> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    /**
     * recycleView的item的点击事件
     * */
    private void clickAdapterItem() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int order_id = beanList.get(position).getOrder_id();
                Intent intent = new Intent(ProductLlActivity.this, OrderDetailActivity.class);
                intent.putExtra("order_name",beanList.get(position).getDisplay_name());
                intent.putExtra("order_id", order_id);
                startActivity(intent);
            }
        });
    }
}
