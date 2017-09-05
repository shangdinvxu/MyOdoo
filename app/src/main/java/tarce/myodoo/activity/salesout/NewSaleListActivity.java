package tarce.myodoo.activity.salesout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.GetSaleResponse;
import tarce.model.inventory.GetDonePickBean;
import tarce.model.inventory.NewSaleListBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.adapter.takedeliver.NewSaleListAdapte;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;
import tarce.myodoo.utils.StringUtils;
import tarce.support.MyLog;
import tarce.support.ToastUtils;

import static tarce.myodoo.R.id.parent_radio_dan;

/**
 * Created by zws on 2017/8/9.
 */

public class NewSaleListActivity extends BaseActivity {
    private static final int Refresh_Move = 1;//下拉动作
    private static final int Load_Move = 2;//上拉动作
    private static final int LOAD_NUM = 30;//每次加载的数量
    /*@InjectView(R.id.finish_this)
    ImageView finishThis;
    @InjectView(R.id.sale_title_dan)
    TextView saleTitleDan;*/
    /*@InjectView(R.id.right_detail)
    ImageView rightDetail;*/
    @InjectView(R.id.wait_radio_dan)
    RadioButton waitRadioDan;
    @InjectView(R.id.can_radio_dan)
    RadioButton canRadioDan;
    @InjectView(parent_radio_dan)
    RadioGroup parentRadioDan;
    @InjectView(R.id.search_newsalelist)
    SearchView searchNewsalelist;
    @InjectView(R.id.all_done)
    RadioButton allDone;
    @InjectView(R.id.swipe_refresh_header)
    RecyclerHeaderView swipeRefreshHeader;
    @InjectView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @InjectView(R.id.swipe_load_more_footer)
    RecyclerFooterView swipeLoadMoreFooter;
    @InjectView(R.id.swipeToLoad)
    SwipeToLoadLayout swipeToLoad;
    private int partner_id;
    private InventoryApi inventoryApi;
    private ProgressDialog progressDialog;
    private NewSaleListAdapte listAdapte;
    private List<NewSaleListBean.ResultBean.ResDataBean.AbleToDataBean> res_data;
    private List<NewSaleListBean.ResultBean.ResDataBean.AbleToDataBean> allList;
    private NewSaleListBean.ResultBean.ResDataBean resDataBean;
    private String from;
    private String isUpdate = "";
    private String danhao;
    private int loadTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsalelist);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(NewSaleListActivity.this).create(InventoryApi.class);
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if ("fistPage".equals(from)) {
            danhao = intent.getStringExtra("danhao");
            setTitle(danhao + "搜索结果");
            showDefultProgressDialog();
            getSearch(danhao);
//            allDone.setVisibility(View.GONE);
//            canRadioDan.setVisibility(View.GONE);
            parentRadioDan.setVisibility(View.GONE);
        } else {
            partner_id = intent.getIntExtra("partner_id", -1);
            String partner_name = intent.getStringExtra("partner_name");
            setTitle(partner_name);
            getData();
        }
        setRecyclerview(swipeTarget);
    }

    /**
     * 根据首页的单号搜索
     */
    private void getSearch(String danhao) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("order_name", danhao);
        Call<NewSaleListBean> getSaleListByNumberResponseCall = inventoryApi.getPickby(objectObjectHashMap);
        getSaleListByNumberResponseCall.enqueue(new Callback<NewSaleListBean>() {
            @Override
            public void onResponse(Call<NewSaleListBean> call, Response<NewSaleListBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null || response.body().getResult() == null) return;
                if (response.body().getResult().getRes_code() == 1) {
                    resDataBean = response.body().getResult().getRes_data();
                    if (resDataBean.getWaiting_data().size() == 0 && resDataBean.getAble_to_data().size() == 0) {
                        ToastUtils.showCommonToast(NewSaleListActivity.this, "没有找到相关数据");
                        finish();
                        return;
                    }
                    res_data = resDataBean.getWaiting_data();
                    allList = res_data;
                    listAdapte = new NewSaleListAdapte(R.layout.item_newsalelist, res_data);
                    swipeTarget.setAdapter(listAdapte);
                    initListner();
                    searchListener();
                }
            }

            @Override
            public void onFailure(Call<NewSaleListBean> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(NewSaleListActivity.this, t.toString());
                MyLog.e("NewSaleListActivity", t.toString());
            }
        });
    }

    /**
     * 监测radiobutton
     */
    private void initRadio() {
        parentRadioDan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.wait_radio_dan) {
                    if (resDataBean != null)
                        res_data = resDataBean.getWaiting_data();
                    allList = res_data;
                    listAdapte.setNewData(res_data);
                } else if (checkedRadioButtonId == R.id.can_radio_dan) {
                    if (resDataBean != null)
                        res_data = resDataBean.getAble_to_data();
                    allList = res_data;
                    listAdapte.setNewData(res_data);
                } else if (checkedRadioButtonId == R.id.all_done) {
                    initRefresh();
                    getDone(LOAD_NUM, 0, Refresh_Move);
                }
            }
        });
    }

    //加载刷新
    private void initRefresh() {
        swipeRefreshHeader.setGravity(Gravity.CENTER);
        swipeLoadMoreFooter.setGravity(Gravity.CENTER);
        swipeToLoad.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoad.setLoadMoreFooterView(swipeLoadMoreFooter);
        swipeToLoad.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDone(LOAD_NUM, 0, Refresh_Move);
                if (listAdapte != null) {
                    listAdapte.notifyDataSetChanged();
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
                        getDone(LOAD_NUM, LOAD_NUM*loadTime, Load_Move);
                        swipeToLoad.setLoadingMore(false);
                    }
                }, 500);
            }
        });
    }

    /*@OnClick(R.id.finish_this)
    void finishThis(View view){
        finish();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        if (!StringUtils.isNullOrEmpty(isUpdate)) {
            if ("fistPage".equals(from)) {
                getSearch(danhao);
                waitRadioDan.setChecked(true);
            } else {
                getData();
                waitRadioDan.setChecked(true);
            }
        }
    }

    //获取已完成数据getDonePicking
    private void getDone(final int limit, final int offset, final int move) {
        showDefultProgressDialog();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("partner_id", partner_id);
        hashMap.put("limit", limit);
        hashMap.put("offset", offset);
        Call<GetDonePickBean> bypartner = inventoryApi.getDonePicking(hashMap);
        bypartner.enqueue(new MyCallback<GetDonePickBean>() {
            @Override
            public void onResponse(Call<GetDonePickBean> call, Response<GetDonePickBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null || response.body().getResult() == null) return;
                if (response.body().getResult().getRes_code() == 1) {
                    res_data = response.body().getResult().getRes_data();
                    if (move == Refresh_Move){
                    allList = res_data;
                    listAdapte = new NewSaleListAdapte(R.layout.item_newsalelist, res_data);
                    swipeTarget.setAdapter(listAdapte);
                }else {
                    if (res_data == null){
                        ToastUtils.showCommonToast(NewSaleListActivity.this, "没有更多数据...");
                        return;
                    }
                    allList = listAdapte.getData();
                    allList.addAll(res_data);
                    listAdapte.setDataAll(allList);
                }
                    initListner();
                    searchListener();
                    initRadio();
                }
            }

            @Override
            public void onFailure(Call<GetDonePickBean> call, Throwable t) {
                dismissDefultProgressDialog();
                Log.e("zws", t.toString());
            }
        });
    }

    //获取单据可处理待处理数据
    private void getData() {
        showDefultProgressDialog();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("partner_id", partner_id);
        Call<NewSaleListBean> bypartner = inventoryApi.getBypartner(hashMap);
        bypartner.enqueue(new MyCallback<NewSaleListBean>() {
            @Override
            public void onResponse(Call<NewSaleListBean> call, Response<NewSaleListBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null || response.body().getResult() == null) return;
                if (response.body().getResult().getRes_code() == 1) {
                    resDataBean = response.body().getResult().getRes_data();
                    res_data = resDataBean.getWaiting_data();
                    allList = res_data;
                    listAdapte = new NewSaleListAdapte(R.layout.item_newsalelist, res_data);
                    swipeTarget.setAdapter(listAdapte);
                    initListner();
                    searchListener();
                    initRadio();
                }
            }

            @Override
            public void onFailure(Call<NewSaleListBean> call, Throwable t) {
                dismissDefultProgressDialog();
                Log.e("zws", t.toString());
            }
        });
    }

    private void searchListener() {
        searchNewsalelist.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fillData(newText);
                return false;
            }
        });
    }

    //筛选数据搜索
    private void fillData(String newText) {
        if (StringUtils.isNullOrEmpty(newText)) {
            res_data = allList;
        } else {
            List<NewSaleListBean.ResultBean.ResDataBean.AbleToDataBean> filterDateList = new ArrayList<>();
            for (NewSaleListBean.ResultBean.ResDataBean.AbleToDataBean bean : allList) {
                if (bean.getOrigin().contains(newText)) {
                    filterDateList.add(bean);
                }
            }
            res_data = filterDateList;
        }
        listAdapte.setNewData(res_data);
        listAdapte.notifyDataSetChanged();
    }

    private void initListner() {
        listAdapte.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showDefultProgressDialog();
                HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("picking_id", listAdapte.getData().get(position).getPicking_id());
                Call<GetSaleResponse> stringCall = inventoryApi.checkIsCanUse(objectObjectHashMap);
                stringCall.enqueue(new MyCallback<GetSaleResponse>() {
                    @Override
                    public void onResponse(Call<GetSaleResponse> call, Response<GetSaleResponse> response) {
                        dismissDefultProgressDialog();
                        if (response.body() == null || response.body().getResult() == null) return;
                        if (response.body().getResult().getRes_data() == null) return;
                        if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                            GetSaleResponse.TResult result = response.body().getResult();
                            Intent intent = new Intent(NewSaleListActivity.this, SalesDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("bundle", result.getRes_data());
                            intent.putExtra("intent", bundle);
                            startActivity(intent);
                        } else {
                            ToastUtils.showCommonToast(NewSaleListActivity.this, "加载失败，请稍后重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<GetSaleResponse> call, Throwable t) {
                        dismissDefultProgressDialog();
                        ToastUtils.showCommonToast(NewSaleListActivity.this, t.toString());
                    }
                });
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        isUpdate = "update";
    }
}
