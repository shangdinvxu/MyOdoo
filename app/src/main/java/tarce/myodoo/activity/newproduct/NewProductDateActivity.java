package tarce.myodoo.activity.newproduct;

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
import tarce.model.SoOriginBean;
import tarce.model.inventory.MaterialDetailBean;
import tarce.model.inventory.PickingDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.activity.OrderDetailActivity;
import tarce.myodoo.activity.ProductLineListActivity;
import tarce.myodoo.activity.ProductingActivity;
import tarce.myodoo.activity.WaitProdListActivity;
import tarce.myodoo.adapter.product.PickingDetailAdapter;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.DateTool;
import tarce.myodoo.utils.StringUtils;
import tarce.support.SharePreferenceUtils;
import tarce.support.ToastUtils;
import tarce.support.ViewUtils;

/**
 * Created by zouwansheng on 2017/12/15.
 */

public class NewProductDateActivity extends BaseActivity {
    private static final int Refresh_Move = 1;//下拉动作
    private static final int Load_Move = 2;//上拉动作

    @InjectView(R.id.search_newdate)
    SearchView searchNewdate;
    @InjectView(R.id.delay_radio)
    RadioButton delayRadio;
    @InjectView(R.id.today_radio)
    RadioButton todayRadio;
    @InjectView(R.id.tomorrow_radio)
    RadioButton tomorrowRadio;
    @InjectView(R.id.after_radio)
    RadioButton afterRadio;
    @InjectView(R.id.all_radio)
    RadioButton allRadio;
    @InjectView(R.id.parent_radio_dan)
    RadioGroup parentRadioDan;
    @InjectView(R.id.one_radio)
    RadioButton oneRadio;
    @InjectView(R.id.two_radio)
    RadioButton twoRadio;
    @InjectView(R.id.three_radio)
    RadioButton threeRadio;
    @InjectView(R.id.four_radio)
    RadioButton fourRadio;
    @InjectView(R.id.five_radio)
    RadioButton fiveRadio;
    @InjectView(R.id.parent_radio)
    RadioGroup parentRadio;
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
    private String state;
    private String thisData;
    private List<PickingDetailBean.ResultBean.ResDataBean> beanList = new ArrayList<>();
    private List<PickingDetailBean.ResultBean.ResDataBean> dataBeanList = new ArrayList<>();
    private List<PickingDetailBean.ResultBean.ResDataBean> allList = new ArrayList<>();
    private PickingDetailAdapter adapter;
    private int loadTime = 0;
    private int process_id;
    private int origin_sale_id;
    private boolean isChange = false;//是否跳转
    private boolean search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdate);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(NewProductDateActivity.this).create(InventoryApi.class);
        Intent intent = getIntent();
        line_id = intent.getIntExtra("line_id", -1000);
        process_id = intent.getIntExtra("process_id", -1000);
        origin_sale_id = intent.getIntExtra("origin_sale_id", 0);
        setRecyclerview(swipeTarget);
        search = intent.getBooleanExtra("search", false);
        if (search){
            fiveRadio.setChecked(true);
            allRadio.setTextColor(Color.BLUE);
            delayRadio.setTextColor(Color.BLACK);
            tomorrowRadio.setTextColor(Color.BLACK);
            todayRadio.setTextColor(Color.BLACK);
            afterRadio.setTextColor(Color.BLACK);
            dataBeanList = (List<PickingDetailBean.ResultBean.ResDataBean>) intent.getSerializableExtra("data");
            adapter = new PickingDetailAdapter(R.layout.adapter_picking_activity, dataBeanList);
            swipeTarget.setAdapter(adapter);
            clickAdapterItem();
        }else {
            todayRadio.setTextColor(Color.BLUE);
            initRefresh();
            getData(40, 0, Refresh_Move, DateTool.getDate());
            thisData = DateTool.getDate();
        }
    }

    @Override
    protected void onResume() {
        if (isChange) {
            swipeToLoad.setRefreshing(true);
             loadTime = 0;
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        isChange = true;
        super.onPause();
    }

    private void initRefresh() {
        showDefultProgressDialog();
        swipeRefreshHeader.setGravity(Gravity.CENTER);
        swipeLoadMoreFooter.setGravity(Gravity.CENTER);
        swipeToLoad.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoad.setLoadMoreFooterView(swipeLoadMoreFooter);

        swipeToLoad.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                showDefultProgressDialog();
                getData(40, 0, Refresh_Move, thisData);
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
                        getData(40, 40 * loadTime, Load_Move, thisData);
                        adapter.notifyDataSetChanged();
                        swipeToLoad.setLoadingMore(false);
                    }
                }, 1000);
            }
        });
    }

    private void getData(int limit, int offset, final int move, String date) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("limit", limit);
        hashMap.put("offset", offset);
        hashMap.put("state", "already_picking");
        hashMap.put("process_id", process_id);
        if (line_id != -1000) {
            hashMap.put("production_line_id", line_id);
        }else {
            hashMap.put("production_line_id", false);
        }
        if (origin_sale_id != 0){
            hashMap.put("origin_sale_id", origin_sale_id);
        }else {
            hashMap.put("origin_sale_id", false);
        }
        int partner_id = SharePreferenceUtils.getInt("partner_id", 1000, NewProductDateActivity.this);
        hashMap.put("partner_id", partner_id);
        hashMap.put("date", date);
        Call<PickingDetailBean> listWait = inventoryApi.getNewRecentOr(hashMap);
        listWait.enqueue(new MyCallback<PickingDetailBean>() {
            @Override
            public void onResponse(Call<PickingDetailBean> call, Response<PickingDetailBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null || response.body().getResult() == null) return;
                if (response.body().getError() != null) {
                    new TipDialog(NewProductDateActivity.this, R.style.MyDialogStyle, response.body().getError().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                    beanList = response.body().getResult().getRes_data();
                    swipeTarget.setVisibility(View.VISIBLE);
                    if (move == Refresh_Move) {
                        dataBeanList = beanList;
                        adapter = new PickingDetailAdapter(R.layout.adapter_picking_activity, beanList);
                        swipeTarget.setAdapter(adapter);
                        allList = dataBeanList;
                    } else {
                        if (beanList == null) {
                            ToastUtils.showCommonToast(NewProductDateActivity.this, "没有更多数据...");
                            return;
                        }
                        dataBeanList = adapter.getData();
                        dataBeanList.addAll(beanList);
                        adapter.setData(dataBeanList);
                        allList = dataBeanList;
                    }
                    clickAdapterItem();
                    initRadio();
                    seach();
                } else if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == -1) {

                }else if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() == null
                        && move!=Load_Move){
                    swipeTarget.setVisibility(View.GONE);
                    initRadio();
                    ToastUtils.showCommonToast(NewProductDateActivity.this, "没有更多数据...");
                }
            }

            @Override
            public void onFailure(Call<PickingDetailBean> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(NewProductDateActivity.this, t.toString());
            }
        });
    }

    private void clickAdapterItem() {

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                try {
                    int order_id = dataBeanList.get(position).getOrder_id();
                    if (dataBeanList.get(position).getState().equals("progress")) {
                        Intent intent = new Intent(NewProductDateActivity.this, ProductingActivity.class);
                        intent.putExtra("order_name", dataBeanList.get(position).getDisplay_name());
                        intent.putExtra("order_id", order_id);
                        intent.putExtra("state", "progress");
                        intent.putExtra("name_activity", "等待生产");
                        intent.putExtra("state_activity", "already_picking");
                        intent.putExtra("origin_sale_id", origin_sale_id);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(NewProductDateActivity.this, OrderDetailActivity.class);
                        intent.putExtra("order_name", dataBeanList.get(position).getDisplay_name());
                        intent.putExtra("order_id", order_id);
                        intent.putExtra("state", dataBeanList.get(position).getState());
                        intent.putExtra("name_activity", "生产中");
                        intent.putExtra("state_activity", "already_picking");
                        intent.putExtra("origin_sale_id", origin_sale_id);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    ToastUtils.showCommonToast(NewProductDateActivity.this, e.toString());
                }
            }
        });
    }

    private void initRadio() {
        parentRadioDan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.delay_radio) {
                    oneRadio.setChecked(true);
                    delayRadio.setTextColor(Color.BLUE);
                    todayRadio.setTextColor(Color.BLACK);
                    tomorrowRadio.setTextColor(Color.BLACK);
                    afterRadio.setTextColor(Color.BLACK);
                    allRadio.setTextColor(Color.BLACK);
                    getData(40, 0, Refresh_Move, "delay");
                    thisData = "delay";
                } else if (checkedRadioButtonId == R.id.today_radio) {
                    twoRadio.setChecked(true);
                    todayRadio.setTextColor(Color.BLUE);
                    delayRadio.setTextColor(Color.BLACK);
                    tomorrowRadio.setTextColor(Color.BLACK);
                    afterRadio.setTextColor(Color.BLACK);
                    allRadio.setTextColor(Color.BLACK);
                    getData(40, 0, Refresh_Move, DateTool.getDate());
                    thisData = DateTool.getDate();
                } else if (checkedRadioButtonId == R.id.tomorrow_radio) {
                    threeRadio.setChecked(true);
                    tomorrowRadio.setTextColor(Color.BLUE);
                    delayRadio.setTextColor(Color.BLACK);
                    todayRadio.setTextColor(Color.BLACK);
                    afterRadio.setTextColor(Color.BLACK);
                    allRadio.setTextColor(Color.BLACK);
                    getData(40, 0, Refresh_Move, DateTool.getDateTime(DateTool.addDays(1), DateTool.DEFAULT_DATE_FORMAT));
                    thisData  = DateTool.getDateTime(DateTool.addDays(1), DateTool.DEFAULT_DATE_FORMAT);
                } else if (checkedRadioButtonId == R.id.after_radio) {
                    fourRadio.setChecked(true);
                    afterRadio.setTextColor(Color.BLUE);
                    delayRadio.setTextColor(Color.BLACK);
                    tomorrowRadio.setTextColor(Color.BLACK);
                    todayRadio.setTextColor(Color.BLACK);
                    allRadio.setTextColor(Color.BLACK);
                    getData(40, 0, Refresh_Move, DateTool.getDateTime(DateTool.addDays(2), DateTool.DEFAULT_DATE_FORMAT));
                    thisData = DateTool.getDateTime(DateTool.addDays(2), DateTool.DEFAULT_DATE_FORMAT);
                } else if (checkedRadioButtonId == R.id.all_radio) {
                    fiveRadio.setChecked(true);
                    allRadio.setTextColor(Color.BLUE);
                    delayRadio.setTextColor(Color.BLACK);
                    tomorrowRadio.setTextColor(Color.BLACK);
                    todayRadio.setTextColor(Color.BLACK);
                    afterRadio.setTextColor(Color.BLACK);
                    getData(40, 0, Refresh_Move, "all");
                    thisData = "all";
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
                    ViewUtils.collapseSoftInputMethod(NewProductDateActivity.this, searchNewdate);
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
                adapter.setNewData(beanList);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }
}
