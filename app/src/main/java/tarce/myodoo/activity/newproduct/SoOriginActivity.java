package tarce.myodoo.activity.newproduct;

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
import com.chad.library.adapter.base.BaseViewHolder;

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
import tarce.model.SoOriginBean;
import tarce.model.inventory.MainMdBean;
import tarce.model.inventory.PickingDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.activity.ProductLineListActivity;
import tarce.myodoo.activity.ProductLlActivity;
import tarce.myodoo.activity.WaitProdListActivity;
import tarce.myodoo.activity.inspect.InspectionSubActivity;
import tarce.myodoo.activity.product.AgainProductActivity;
import tarce.myodoo.adapter.SoOriginAdapter;
import tarce.myodoo.adapter.processproduct.PrepareMdAdapter;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.StringUtils;
import tarce.support.SharePreferenceUtils;
import tarce.support.ViewUtils;

/**
 * Created by zouwansheng on 2017/12/19.
 */

public class SoOriginActivity extends BaseActivity {
    @InjectView(R.id.search_newdate)
    SearchView searchNewdate;
    @InjectView(R.id.swipe_refresh_header)
    RecyclerHeaderView swipeRefreshHeader;
    @InjectView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @InjectView(R.id.swipe_load_more_footer)
    RecyclerFooterView swipeLoadMoreFooter;
    @InjectView(R.id.swipeToLoad)
    SwipeToLoadLayout swipeToLoad;
    private InventoryApi inventoryApi;
    private int line_id;
    private int process_id;
    private String state;
    private SoOriginAdapter soOriginAdapter;
    private List<SoOriginBean.ResultBean.ResDataBean> res_data = new ArrayList<>();
    private List<SoOriginBean.ResultBean.ResDataBean> allList = new ArrayList<>();
    private String name_activity;
    private boolean isChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soorigin);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(SoOriginActivity.this).create(InventoryApi.class);
        Intent intent = getIntent();
        line_id = intent.getIntExtra("line_id", -1000);
        process_id = intent.getIntExtra("process_id", -1000);
        state = intent.getStringExtra("state");
        name_activity = intent.getStringExtra("name_activity");
        setTitle(name_activity);
        setRecyclerview(swipeTarget);
        showDefultProgressDialog();
        initData();
        initFresh();
    }

    private void initFresh() {
        swipeRefreshHeader.setGravity(Gravity.CENTER);
        swipeLoadMoreFooter.setGravity(Gravity.CENTER);
        swipeToLoad.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoad.setLoadMoreFooterView(swipeLoadMoreFooter);

        swipeToLoad.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                showDefultProgressDialog();
                initData();
                swipeToLoad.setRefreshing(false);
            }
        });
        swipeToLoad.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                swipeToLoad.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeToLoad.setLoadingMore(false);
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isChange){
            initData();
        }
    }

    private void initData() {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("process_id", process_id);
        if (line_id != -1000) {
            hashMap.put("production_line_id", line_id);
        }else {
            hashMap.put("production_line_id", false);
        }
        if (!"waiting_material".equals(state) && !"waiting_warehouse_inspection".equals(state) &&
                !"force_cancel_waiting_warehouse_inspection".equals(state)){
            int partner_id = SharePreferenceUtils.getInt("partner_id", 1000, SoOriginActivity.this);
            hashMap.put("partner_id", partner_id);
        }
        hashMap.put("state", state);
        hashMap.put("is_group_by", true);
        final Call<SoOriginBean> recentOr = inventoryApi.getSoMrp(hashMap);
        recentOr.enqueue(new MyCallback<SoOriginBean>() {
            @Override
            public void onResponse(Call<SoOriginBean> call, Response<SoOriginBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError()!=null){
                    new TipDialog(SoOriginActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1){
                    res_data = response.body().getResult().getRes_data();
                    allList = res_data;
                    soOriginAdapter = new SoOriginAdapter(R.layout.item_soorigin, res_data);
                    swipeTarget.setAdapter(soOriginAdapter);
                    initListener();
                    seach();
                }
            }

            @Override
            public void onFailure(Call<SoOriginBean> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
    }

    private void initListener() {
        soOriginAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<SoOriginBean.ResultBean.ResDataBean> data = soOriginAdapter.getData();
                switch (name_activity){
                    case "生产备料"://生产备料
                        Intent intent = new Intent(SoOriginActivity.this, NewDateActivity.class);
                        intent.putExtra("line_id", line_id);
                        intent.putExtra("process_id", process_id);
                        intent.putExtra("state", "waiting_material");
                        intent.putExtra("origin_sale_id", data.get(position).getOrigin_id());
                        startActivity(intent);
                        break;
                    case "仓库检验退料":
                    case "取消MO待检验退料":
                    case "清点退料":
                    case "取消MO待清点退料":
                    case "返工中":
                    case "已完成":
                        Intent intent1 = new Intent(SoOriginActivity.this, ProductLlActivity.class);
                        intent1.putExtra("name_activity",name_activity);
                        intent1.putExtra("state_product",state);
                        intent1.putExtra("process_id", process_id);
                        intent1.putExtra("line_id", line_id);
                        intent1.putExtra("origin_sale_id", data.get(position).getOrigin_id());
                        startActivity(intent1);
                        break;
                    case "等待生产":
                        Intent intent3 = new Intent(SoOriginActivity.this, NewProductDateActivity.class);
                        intent3.putExtra("state", "already_picking");
                        intent3.putExtra("process_id", process_id);
                        intent3.putExtra("line_id", line_id);
                        intent3.putExtra("origin_sale_id", data.get(position).getOrigin_id());
                        startActivity(intent3);
                        break;
                    case "生产中":
                        Intent intent4 = new Intent(SoOriginActivity.this, ProductLineListActivity.class);
                        intent4.putExtra("production_line_id", line_id);
                        intent4.putExtra("process_id", process_id);
                        intent4.putExtra("state_activity", "progress");
                        intent4.putExtra("origin_sale_id", data.get(position).getOrigin_id());
                        startActivity(intent4);
                        break;
                    case "二次生产":
                        Intent intent5 = new Intent(SoOriginActivity.this, AgainProductActivity.class);
                        intent5.putExtra("production_line_id", line_id);
                        intent5.putExtra("process_id", process_id);
                        intent5.putExtra("state", "is_secondary_produce");
                        intent5.putExtra("origin_sale_id", data.get(position).getOrigin_id());
                        startActivity(intent5);
                        break;
                }
            }
        });
    }

    //搜索
    private void seach() {
        searchNewdate.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (StringUtils.isNullOrEmpty(newText)) {
                    ViewUtils.collapseSoftInputMethod(SoOriginActivity.this, searchNewdate);
                    res_data = allList;
                } else {
                    List<SoOriginBean.ResultBean.ResDataBean> filterDateList = new ArrayList<>();
                    for (SoOriginBean.ResultBean.ResDataBean bean : allList) {
                        if (bean.getOrigin_name().contains(newText)) {
                            filterDateList.add(bean);
                        }
                    }
                    res_data = filterDateList;
                }
                soOriginAdapter.setNewData(res_data);
                soOriginAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        isChange = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (res_data!=null){
            res_data = null;
        }
    }
}

