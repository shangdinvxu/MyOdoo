package tarce.myodoo.activity.newproduct;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.SoOriginBean;
import tarce.model.inventory.PickingDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.activity.ProductLineListActivity;
import tarce.myodoo.activity.ProductLlActivity;
import tarce.myodoo.activity.product.AgainProductActivity;
import tarce.myodoo.adapter.SoOriginAdapter;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.StringUtils;
import tarce.support.SharePreferenceUtils;
import tarce.support.ToastUtils;
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
    @InjectView(R.id.tv_tip_show)
    TextView tvTipShow;
    @InjectView(R.id.btn_show_cancel)
    Button btnShowCancel;
    @InjectView(R.id.linear_tip)
    RelativeLayout linearTip;
    private InventoryApi inventoryApi;
    private int line_id;
    private int process_id;
    private String state;
    private SoOriginAdapter soOriginAdapter;
    private List<SoOriginBean.ResultBean.ResDataBean> res_data = new ArrayList<>();
    private List<SoOriginBean.ResultBean.ResDataBean> allList = new ArrayList<>();
    private List<SoOriginBean.ResultBean.ResDataBean> produce_have = new ArrayList<>();
    private String name_activity;
    private boolean isChange = false;
    private List<String> searchList = new ArrayList<>();
    private ListPopupWindow mListPop;
    private boolean show_have = false;

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
        initListPop();
        if (state.equals("progress")) {
            linearTip.setVisibility(View.VISIBLE);
        }else {
            linearTip.setVisibility(View.GONE);
        }
    }

    /**
     * 筛选未产出的mo
     * */
    private void fileterData() {
        if (res_data!=null && res_data.size()>0){
            for (int i = 0; i < res_data.size(); i++) {
                if (!res_data.get(i).isHave()){
                    produce_have.add(res_data.get(i));
                }
            }
            if (produce_have!=null){
                soOriginAdapter = new SoOriginAdapter(R.layout.item_soorigin, produce_have);
                swipeTarget.setAdapter(soOriginAdapter);
                allList = produce_have;
            }
        }else {
            return;
        }
    }

    @OnClick(R.id.btn_show_cancel)
    void setProduce_have(View view){
        if (show_have){
            show_have = false;
            tvTipShow.setText("已自动隐藏有产出的MO");
            btnShowCancel.setText("取消隐藏");
            btnShowCancel.setBackgroundResource(R.drawable.product_button_have);
            btnShowCancel.setTextColor(getResources().getColor(R.color.color_666666));
            if (produce_have!=null){
                soOriginAdapter.setNewData(produce_have);
                soOriginAdapter.notifyDataSetChanged();
            }
            allList = produce_have;
        }else {
            show_have = true;
            tvTipShow.setText("已显示全部MO");
            btnShowCancel.setText("隐藏有产出");
            btnShowCancel.setBackgroundResource(R.drawable.product_button_no);
            btnShowCancel.setTextColor(getResources().getColor(R.color.color_5693f8));
            if (res_data!=null){
                soOriginAdapter.setNewData(res_data);
                soOriginAdapter.notifyDataSetChanged();
            }
            allList = res_data;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("zws", "SoOriginActivity = onNewIntent");
    }

    //设置listpopwindow
    private void initListPop() {
        searchList.add("搜索：按原单据");
        searchList.add("搜索：按产品名称");
        searchList.add("搜索：按MO单号");
        mListPop = new ListPopupWindow(this);
        mListPop.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, searchList));
        mListPop.setWidth(RecyclerView.LayoutParams.WRAP_CONTENT);
        mListPop.setHeight(RecyclerView.LayoutParams.WRAP_CONTENT);
        mListPop.setAnchorView(searchNewdate);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
        mListPop.setModal(true);//设置是否是模式
        mListPop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 0) {
                    if (allList != null && allList.size() == 1 && allList.size() > 0) {
                        if ("暂无源单据".equals(allList.get(0).getOrigin_name())) {
                            ToastUtils.showCommonToast(SoOriginActivity.this, "未找到相关单据");
                        }
                        return;
                    } else if (allList != null && allList.size() <= 0) {
                        ToastUtils.showCommonToast(SoOriginActivity.this, "未找到相关单据'");
                        return;
                    }
                    List<SoOriginBean.ResultBean.ResDataBean> filterDateList = new ArrayList<>();
                    for (SoOriginBean.ResultBean.ResDataBean bean : allList) {
                        if (bean.getOrigin_name().contains(searchNewdate.getQuery().toString())) {
                            filterDateList.add(bean);
                        }
                    }
                    res_data = filterDateList;
                    soOriginAdapter.setNewData(res_data);
                    soOriginAdapter.notifyDataSetChanged();
                } else if (position == 1) {
                    setSearchNewdate(1);
                } else if (position == 2) {
                    setSearchNewdate(2);
                }
                mListPop.dismiss();
            }
        });
    }

    /**
     * 搜索接口
     */
    private void setSearchNewdate(int type) {
        showDefultProgressDialog();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("process_id", process_id);
        if (line_id != -1000) {
            hashMap.put("production_line_id", line_id);
        } else {
            hashMap.put("production_line_id", false);
        }
        hashMap.put("state", state);
        hashMap.put("type", type);
        hashMap.put("searchText", searchNewdate.getQuery().toString());
        Call<PickingDetailBean> searchMrpPro = inventoryApi.getSearchMrpPro(hashMap);
        searchMrpPro.enqueue(new Callback<PickingDetailBean>() {
            @Override
            public void onResponse(Call<PickingDetailBean> call, Response<PickingDetailBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError() != null) {
                    new TipDialog(SoOriginActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1) {
                    List<PickingDetailBean.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                    if (res_data == null) {
                        ToastUtils.showCommonToast(SoOriginActivity.this, "未找到相关单据");
                        return;
                    }
                    switch (name_activity) {
                        case "生产备料"://生产备料
                            Intent intent = new Intent(SoOriginActivity.this, NewDateActivity.class);
                            intent.putExtra("line_id", line_id);
                            intent.putExtra("process_id", process_id);
                            intent.putExtra("state", "waiting_material");
                            intent.putExtra("search", true);
                            intent.putExtra("data", (Serializable) res_data);
                            startActivity(intent);
                            break;
                        case "仓库检验退料":
                        case "取消MO待检验退料":
                        case "清点退料":
                        case "取消MO待清点退料":
                        case "返工中":
                        case "已完成":
                            Intent intent1 = new Intent(SoOriginActivity.this, ProductLlActivity.class);
                            intent1.putExtra("name_activity", name_activity);
                            intent1.putExtra("state_product", state);
                            intent1.putExtra("process_id", process_id);
                            intent1.putExtra("line_id", line_id);
                            intent1.putExtra("search", true);
                            intent1.putExtra("data", (Serializable) res_data);
                            startActivity(intent1);
                            break;
                        case "等待生产":
                            Intent intent3 = new Intent(SoOriginActivity.this, NewProductDateActivity.class);
                            intent3.putExtra("state", "already_picking");
                            intent3.putExtra("process_id", process_id);
                            intent3.putExtra("line_id", line_id);
                            intent3.putExtra("search", true);
                            intent3.putExtra("data", (Serializable) res_data);
                            startActivity(intent3);
                            break;
                        case "生产中":
                            Intent intent4 = new Intent(SoOriginActivity.this, ProductLineListActivity.class);
                            intent4.putExtra("production_line_id", line_id);
                            intent4.putExtra("process_id", process_id);
                            intent4.putExtra("state_activity", "progress");
                            intent4.putExtra("search", true);
                            intent4.putExtra("data", (Serializable) res_data);
                            startActivity(intent4);
                            break;
                        case "二次生产":
                            Intent intent5 = new Intent(SoOriginActivity.this, AgainProductActivity.class);
                            intent5.putExtra("production_line_id", line_id);
                            intent5.putExtra("process_id", process_id);
                            intent5.putExtra("state", "is_secondary_produce");
                            intent5.putExtra("search", true);
                            intent5.putExtra("data", (Serializable) res_data);
                            startActivity(intent5);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<PickingDetailBean> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
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
        if (isChange) {
            if (state.equals("progress")){
                if (produce_have!=null){
                    produce_have.clear();
                }
                if (res_data!=null){
                    res_data.clear();
                }
            }
            initData();
        }
    }

    private void initData() {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("process_id", process_id);
        if (line_id != -1000) {
            hashMap.put("production_line_id", line_id);
        } else {
            hashMap.put("production_line_id", false);
        }
        if (!"waiting_material".equals(state) && !"waiting_warehouse_inspection".equals(state) &&
                !"force_cancel_waiting_warehouse_inspection".equals(state)) {
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
                if (response.body().getError() != null) {
                    new TipDialog(SoOriginActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1) {
                    res_data = response.body().getResult().getRes_data();
                    if (state.equals("progress")){
                        show_have = false;
                        tvTipShow.setText("已自动隐藏有产出的MO");
                        btnShowCancel.setText("取消隐藏");
                        btnShowCancel.setBackgroundResource(R.drawable.product_button_have);
                        btnShowCancel.setTextColor(getResources().getColor(R.color.color_666666));
                        fileterData();
                    }else {
                        soOriginAdapter = new SoOriginAdapter(R.layout.item_soorigin, res_data);
                        swipeTarget.setAdapter(soOriginAdapter);
                        allList = res_data;
                    }
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
        if (soOriginAdapter==null)return;
        soOriginAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<SoOriginBean.ResultBean.ResDataBean> data = soOriginAdapter.getData();
                switch (name_activity) {
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
                        intent1.putExtra("name_activity", name_activity);
                        intent1.putExtra("state_product", state);
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
                mListPop.show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (StringUtils.isNullOrEmpty(newText)) {
                    mListPop.dismiss();
                    ViewUtils.collapseSoftInputMethod(SoOriginActivity.this, searchNewdate);
//                    res_data = allList;
                }
//                soOriginAdapter.setNewData(res_data);
//                soOriginAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        isChange = true;
    }

}

