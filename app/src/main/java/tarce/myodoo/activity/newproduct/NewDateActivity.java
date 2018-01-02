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
import tarce.model.inventory.MainMdBean;
import tarce.model.inventory.MaterialDetailBean;
import tarce.model.inventory.PickingDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.activity.MaterialDetailActivity;
import tarce.myodoo.activity.OrderDetailActivity;
import tarce.myodoo.adapter.processproduct.PrepareMdAdapter;
import tarce.myodoo.adapter.product.PickingDetailAdapter;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.DateTool;
import tarce.myodoo.utils.StringUtils;
import tarce.support.ToastUtils;
import tarce.support.ViewUtils;

/**
 * Created by zouwansheng on 2017/12/13.
 */

public class NewDateActivity extends BaseActivity {
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
    private int line_id;
    private int process_id;
    private InventoryApi inventoryApi;
    private String state;
    private List<PickingDetailBean.ResultBean.ResDataBean> dataBeanList = new ArrayList<>();
    private List<PickingDetailBean.ResultBean.ResDataBean> beanList = new ArrayList<>();
    private List<MainMdBean> allList = new ArrayList<>();
    private List<MainMdBean> mainMdBeen = new ArrayList<>();
    private List<MainMdBean> mainMdBeanList = new ArrayList<>();
    private PrepareMdAdapter prepareMdAdapter;
    private String thisData;
    private int origin_sale_id;
    private int loadTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdate);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(NewDateActivity.this).create(InventoryApi.class);

        setRecyclerview(swipeTarget);
        todayRadio.setTextColor(Color.BLUE);
        Intent intent = getIntent();
        line_id = intent.getIntExtra("line_id", -1000);
        process_id = intent.getIntExtra("process_id", -1112);
        state = intent.getStringExtra("state");
        origin_sale_id = intent.getIntExtra("origin_sale_id", 0);
        initRefresh();
        showDefultProgressDialog();
        initData(40, 0, Refresh_Move, DateTool.getDate());
        thisData = DateTool.getDate();
    }

    @Override
    protected void onResume() {
        if (dataBeanList == null) {
            swipeToLoad.setRefreshing(true);
            loadTime = 0;
        }
        super.onResume();
    }

    private void initRefresh() {
        swipeRefreshHeader.setGravity(Gravity.CENTER);
        swipeLoadMoreFooter.setGravity(Gravity.CENTER);
        swipeToLoad.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoad.setLoadMoreFooterView(swipeLoadMoreFooter);
        swipeToLoad.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                showDefultProgressDialog();
                initData(40, 0, Refresh_Move, thisData);
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
//                        initData(40, 40 * loadTime, Load_Move, thisData);
//                        prepareMdAdapter.notifyDataSetChanged();
                        ToastUtils.showCommonToast(NewDateActivity.this, "没有更多数据...");
                        swipeToLoad.setLoadingMore(false);
                    }
                }, 1000);
            }
        });
    }

    private void initData(int limit, int offset, final int move, String date) {
        if (dataBeanList != null) {
            dataBeanList.clear();
        }
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("offset", offset);
        hashMap.put("limit", limit);
        hashMap.put("process_id", process_id);
        if (line_id != -1000) {
            hashMap.put("production_line_id", line_id);
        }else {
            hashMap.put("production_line_id", false);
        }
        if (origin_sale_id != 0) {
            hashMap.put("origin_sale_id", origin_sale_id);
        }else {
            hashMap.put("origin_sale_id", false);
        }
        hashMap.put("date", date);
        hashMap.put("state", state);
        hashMap.put("is_group_by", true);
        final Call<PickingDetailBean> recentOr = inventoryApi.getNewRecentOr(hashMap);
        recentOr.enqueue(new MyCallback<PickingDetailBean>() {
            @Override
            public void onResponse(Call<PickingDetailBean> call, Response<PickingDetailBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError() != null) {
                    new TipDialog(NewDateActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1) {
                    dataBeanList = response.body().getResult().getRes_data();

                    swipeTarget.setVisibility(View.VISIBLE);
                    if (move == Refresh_Move) {
                        if (mainMdBeen!=null){
                            mainMdBeen.clear();
                        }
                        if (dataBeanList!=null){
                            for (int i = 0; i < dataBeanList.size(); i++) {
                                mainMdBeen.add(new MainMdBean(dataBeanList.get(i)));
                            }
                        }
                        prepareMdAdapter = new PrepareMdAdapter(R.layout.adapter_mater_detail, R.layout.adapter_head_md, mainMdBeen);
                        swipeTarget.setAdapter(prepareMdAdapter);
                        allList = mainMdBeen;
                    } else {
//                        if (dataBeanList == null) {
//                            ToastUtils.showCommonToast(NewDateActivity.this, "没有更多数据...");
//                            return;
//                        }
//                      //  mainMdBeanList = prepareMdAdapter.getData();
//                        if (dataBeanList != null) {
//                            for (int i = 0; i < dataBeanList.size(); i++) {
//                                mainMdBeanList.add(new MainMdBean(dataBeanList.get(i)));
//                            }
//                        }
//                        mainMdBeen.addAll(mainMdBeanList);
//                        prepareMdAdapter.setData(mainMdBeen);
                    }
                    seach();
                    initListener();
                    initRadio();
                }
            }

            @Override
            public void onFailure(Call<PickingDetailBean> call, Throwable t) {
                dismissDefultProgressDialog();
                // ToastUtils.showCommonToast(MaterialDetailActivity.this, t.toString());
            }
        });
    }

    private void seach() {
        searchNewdate.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (StringUtils.isNullOrEmpty(newText)) {
                    ViewUtils.collapseSoftInputMethod(NewDateActivity.this, searchNewdate);
                    mainMdBeen = allList;
                } else {
                    List<MainMdBean> filterDateList = new ArrayList<>();
                    for (MainMdBean bean : allList) {
                        if (bean.t.getDisplay_name().contains(newText)) {
                            filterDateList.add(bean);
                        }
                    }
                    mainMdBeen = filterDateList;
                }
                prepareMdAdapter.setNewData(mainMdBeen);
                prepareMdAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    private void initListener() {
        prepareMdAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mainMdBeen == null) {
                    return;
                }
                // List<MainMdBean> data = prepareMdAdapter.getData();
                int order_id = mainMdBeen.get(position).t.getOrder_id();
                Intent intent = new Intent(NewDateActivity.this, OrderDetailActivity.class);
                intent.putExtra("order_name", mainMdBeen.get(position).t.getDisplay_name());
                intent.putExtra("order_id", order_id);
                intent.putExtra("state", mainMdBeen.get(position).t.getState());
                intent.putExtra("line_id", line_id);
                intent.putExtra("process_id", process_id);
                intent.putExtra("origin_sale_id", origin_sale_id);
                startActivity(intent);
            }
        });
    }

    /**
     * 监测radiobutton
     *
     * @InjectView(R.id.delay_radio) RadioButton delayRadio;
     * @InjectView(R.id.today_radio) RadioButton todayRadio;
     * @InjectView(R.id.tomorrow_radio) RadioButton tomorrowRadio;
     * @InjectView(R.id.after_radio)
     */
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
                    initData(40, 0, Refresh_Move, "delay");
                    thisData = "delay";
                } else if (checkedRadioButtonId == R.id.today_radio) {
                    twoRadio.setChecked(true);
                    todayRadio.setTextColor(Color.BLUE);
                    delayRadio.setTextColor(Color.BLACK);
                    tomorrowRadio.setTextColor(Color.BLACK);
                    afterRadio.setTextColor(Color.BLACK);
                    allRadio.setTextColor(Color.BLACK);
                    initData(40, 0, Refresh_Move, DateTool.getDate());
                    thisData = DateTool.getDate();
                    loadTime = 0;
                } else if (checkedRadioButtonId == R.id.tomorrow_radio) {
                    threeRadio.setChecked(true);
                    tomorrowRadio.setTextColor(Color.BLUE);
                    delayRadio.setTextColor(Color.BLACK);
                    todayRadio.setTextColor(Color.BLACK);
                    afterRadio.setTextColor(Color.BLACK);
                    allRadio.setTextColor(Color.BLACK);
                    initData(40, 0, Refresh_Move, DateTool.getDateTime(DateTool.addDays(1), DateTool.DEFAULT_DATE_FORMAT));
                    thisData = DateTool.getDateTime(DateTool.addDays(1), DateTool.DEFAULT_DATE_FORMAT);
                    loadTime = 0;
                } else if (checkedRadioButtonId == R.id.after_radio) {
                    fourRadio.setChecked(true);
                    afterRadio.setTextColor(Color.BLUE);
                    delayRadio.setTextColor(Color.BLACK);
                    tomorrowRadio.setTextColor(Color.BLACK);
                    todayRadio.setTextColor(Color.BLACK);
                    allRadio.setTextColor(Color.BLACK);
                    initData(40, 0, Refresh_Move, DateTool.getDateTime(DateTool.addDays(2), DateTool.DEFAULT_DATE_FORMAT));
                    thisData = DateTool.getDateTime(DateTool.addDays(2), DateTool.DEFAULT_DATE_FORMAT);
                    loadTime = 0;
                } else if (checkedRadioButtonId == R.id.all_radio) {
                    fiveRadio.setChecked(true);
                    allRadio.setTextColor(Color.BLUE);
                    delayRadio.setTextColor(Color.BLACK);
                    tomorrowRadio.setTextColor(Color.BLACK);
                    todayRadio.setTextColor(Color.BLACK);
                    afterRadio.setTextColor(Color.BLACK);
                    initData(40, 0, Refresh_Move, "all");
                    thisData = "all";
                    loadTime = 0;
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
