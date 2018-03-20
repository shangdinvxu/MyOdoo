package tarce.myodoo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import tarce.myodoo.utils.StringUtils;
import tarce.support.SharePreferenceUtils;
import tarce.support.ToastUtils;
import tarce.support.ViewUtils;

/**
 * Created by zouwansheng on 2017/10/18.
 */

public class ProductLineListActivity extends BaseActivity {
    private static final int Refresh_Move = 1;//下拉动作
    private static final int Load_Move = 2;//上拉动作

    @InjectView(R.id.search_mo_product)
    SearchView searchMoProduct;
    @InjectView(R.id.swipe_refresh_header)
    RecyclerHeaderView swipeRefreshHeader;
    @InjectView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @InjectView(R.id.swipe_load_more_footer)
    RecyclerFooterView swipeLoadMoreFooter;
    @InjectView(R.id.swipeToLoad)
    SwipeToLoadLayout swipeToLoad;
    @InjectView(R.id.no_radio)
    RadioButton noRadio;
    @InjectView(R.id.have_radio)
    RadioButton haveRadio;
    @InjectView(R.id.parent_radio_dan)
    RadioGroup parentRadioDan;
    @InjectView(R.id.one_radio)
    RadioButton oneRadio;
    @InjectView(R.id.two_radio)
    RadioButton twoRadio;
    @InjectView(R.id.parent_radio)
    RadioGroup parentRadio;
    private int production_line_id;
    private String state_activity;
    private int process_id;
    private InventoryApi loginApi;
    private List<PickingDetailBean.ResultBean.ResDataBean> beanList = new ArrayList<>();
    private List<PickingDetailBean.ResultBean.ResDataBean> dataBeanList = new ArrayList<>();
    private List<PickingDetailBean.ResultBean.ResDataBean> haveList = new ArrayList<>();
    private List<PickingDetailBean.ResultBean.ResDataBean> allList = new ArrayList<>();
    private List<PickingDetailBean.ResultBean.ResDataBean> searchList = new ArrayList<>();
    private PickingDetailAdapter pickingDetailAdapter;
    private int loadTime = 0;
    private int origin_sale_id;
    private boolean isChange = false;
    private boolean search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_ll);
        ButterKnife.inject(this);

        loginApi = RetrofitClient.getInstance(ProductLineListActivity.this).create(InventoryApi.class);
        setRecyclerview(swipeTarget);
        Intent intent = getIntent();
        production_line_id = intent.getIntExtra("production_line_id", -1000);
        state_activity = intent.getStringExtra("state_activity");
        process_id = intent.getIntExtra("process_id", 1000);
        origin_sale_id = intent.getIntExtra("origin_sale_id", 0);
        search = intent.getBooleanExtra("search", false);
        if (search) {
            searchList = (List<PickingDetailBean.ResultBean.ResDataBean>) intent.getSerializableExtra("data");
            for (int i = 0; i < searchList.size(); i++) {
                if (searchList.get(i).isHas_produced_product()){
                    dataBeanList.add(searchList.get(i));
                }else {
                    haveList.add(searchList.get(i));
                }
            }
            pickingDetailAdapter = new PickingDetailAdapter(R.layout.adapter_picking_activity, haveList);
            swipeTarget.setAdapter(pickingDetailAdapter);
            allList = haveList;
            clickAdapterItem();
            initRadio();
        } else {
            initView();
            showDefultProgressDialog();
//             getPicking(0, 40, Refresh_Move);
            getPicking();
        }
    }

    private void initView() {
        swipeRefreshHeader.setGravity(Gravity.CENTER);
        swipeLoadMoreFooter.setGravity(Gravity.CENTER);
        swipeToLoad.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoad.setLoadMoreFooterView(swipeLoadMoreFooter);

        swipeToLoad.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                showDefultProgressDialog();
                getPicking();
//                getPicking(0, 40, Refresh_Move);
                if (pickingDetailAdapter != null) {
                    pickingDetailAdapter.notifyDataSetChanged();
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
//                        loadTime++;
                        getPicking();
//                        getPicking(40 * loadTime, 40, Load_Move);
                        pickingDetailAdapter.notifyDataSetChanged();
                        swipeToLoad.setLoadingMore(false);
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void onResume() {
        if (isChange) {
            noRadio.setChecked(true);
            oneRadio.setChecked(true);
            swipeToLoad.setRefreshing(true);
//            loadTime = 0;
        }
        super.onResume();
    }

//    private void getPicking(final int offset, final int limit, final int move) {
    private void getPicking() {
        if (haveList!=null){
            haveList.clear();
        }
        if (dataBeanList!=null){
            dataBeanList.clear();
        }
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("state", state_activity);
        int partner_id = SharePreferenceUtils.getInt("partner_id", 1000, ProductLineListActivity.this);
        hashMap.put("partner_id", partner_id);
        if (process_id != -1000) {
            hashMap.put("process_id", process_id);
        }
        if (production_line_id == -1000) {
            hashMap.put("production_line_id", false);
        } else {
            hashMap.put("production_line_id", production_line_id);
        }
        if (origin_sale_id != 0) {
            hashMap.put("origin_sale_id", origin_sale_id);
        } else {
            hashMap.put("origin_sale_id", false);
        }
        hashMap.put("date", "all");
        Call<PickingDetailBean> picking = loginApi.getNewRecentOr(hashMap);
        picking.enqueue(new MyCallback<PickingDetailBean>() {
            @Override
            public void onResponse(Call<PickingDetailBean> call, Response<PickingDetailBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError() != null) {
                    new TipDialog(ProductLineListActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                    beanList = response.body().getResult().getRes_data();
//                    if (move == Refresh_Move) {
//                        dataBeanList = beanList;
//                        pickingDetailAdapter = new PickingDetailAdapter(R.layout.adapter_picking_activity, beanList);
//                        swipeTarget.setAdapter(pickingDetailAdapter);
//                    } else {
//                        if (beanList == null) {
//                            ToastUtils.showCommonToast(ProductLineListActivity.this, "没有更多数据...");
//                            return;
//                        }
//                        dataBeanList = pickingDetailAdapter.getData();
//                        dataBeanList.addAll(beanList);
//                        pickingDetailAdapter.setData(dataBeanList);
//                    }
//                    dataBeanList = beanList;
                    for (int i = 0; i < beanList.size(); i++) {
                        if (beanList.get(i).isHas_produced_product()){
                            dataBeanList.add(beanList.get(i));
                        }else {
                            haveList.add(beanList.get(i));
                        }
                    }
                    pickingDetailAdapter = new PickingDetailAdapter(R.layout.adapter_picking_activity, haveList);
                    swipeTarget.setAdapter(pickingDetailAdapter);
                    allList = haveList;
                    clickAdapterItem();
                    initRadio();
                    seach();
                } else if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() == null) {
                    ToastUtils.showCommonToast(ProductLineListActivity.this, "没有更多数据...");
                }
            }

            @Override
            public void onFailure(Call<PickingDetailBean> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
    }

    /**
     * 点击切换未产出
     * */
    private void initRadio() {
        parentRadioDan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.no_radio){
                    oneRadio.setChecked(true);
                    noRadio.setTextColor(Color.BLUE);
                    haveRadio.setTextColor(Color.BLACK);
                    pickingDetailAdapter.setNewData(haveList);
                    pickingDetailAdapter.notifyDataSetChanged();
                    allList = haveList;
                }else if (i == R.id.have_radio){
                    twoRadio.setChecked(true);
                    noRadio.setTextColor(Color.BLACK);
                    haveRadio.setTextColor(Color.BLUE);
                    pickingDetailAdapter.setNewData(dataBeanList);
                    pickingDetailAdapter.notifyDataSetChanged();
                    allList = dataBeanList;
                }
            }
        });
    }

    //搜索
    private void seach() {
        searchMoProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (StringUtils.isNullOrEmpty(newText)) {
                    ViewUtils.collapseSoftInputMethod(ProductLineListActivity.this, searchMoProduct);
                    beanList = allList;
                } else {
                    List<PickingDetailBean.ResultBean.ResDataBean> filterDateList = new ArrayList<>();
                    for (PickingDetailBean.ResultBean.ResDataBean bean : allList) {
                        if (bean.getDisplay_name().contains(newText)) {
                            filterDateList.add(bean);
                        }
                    }
                    beanList = filterDateList;
                }
                pickingDetailAdapter.setNewData(beanList);
                pickingDetailAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    private void clickAdapterItem() {
        pickingDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                List<PickingDetailBean.ResultBean.ResDataBean> data = pickingDetailAdapter.getData();
                int order_id = allList.get(position).getOrder_id();
                if (allList.get(position).getState().equals("progress")) {
                    Intent intent = new Intent(ProductLineListActivity.this, ProductingActivity.class);
                    intent.putExtra("order_name", allList.get(position).getDisplay_name());
                    intent.putExtra("order_id", order_id);
                    intent.putExtra("state", "progress");
                    intent.putExtra("name_activity", "生产中");
                    intent.putExtra("state_activity", state_activity);
                    intent.putExtra("production_line_id", production_line_id);
                    intent.putExtra("origin_sale_id", origin_sale_id);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ProductLineListActivity.this, OrderDetailActivity.class);
                    intent.putExtra("order_name", allList.get(position).getDisplay_name());
                    intent.putExtra("order_id", order_id);
                    intent.putExtra("state", allList.get(position).getState());
                    // intent.putExtra("name_activity", name_activity);
                    intent.putExtra("state_activity", state_activity);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        isChange = true;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
