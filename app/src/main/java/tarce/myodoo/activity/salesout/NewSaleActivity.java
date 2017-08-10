package tarce.myodoo.activity.salesout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
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
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.inventory.CustomerSaleBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.NewSaleCustomAdapter;
import tarce.myodoo.adapter.SalesListAdapter;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;
import tarce.myodoo.utils.StringUtils;
import tarce.support.ToastUtils;
import tarce.support.ViewUtils;

/**
 * Created by zws on 2017/8/9.
 */

public class NewSaleActivity extends Activity {
    private static final int Refresh_Move = 1;//下拉动作
    private static final int Load_Move = 2;//上拉动作

    @InjectView(R.id.sale_title)
    TextView saleTitle;
    @InjectView(R.id.back_no_left)
    ImageView backNoLeft;
    @InjectView(R.id.right_detail)
    ImageView rightDetail;
    @InjectView(R.id.search_newsale)
    SearchView searchNewsale;
    @InjectView(R.id.swipe_refresh_header)
    RecyclerHeaderView swipeRefreshHeader;
    @InjectView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @InjectView(R.id.swipe_load_more_footer)
    RecyclerFooterView swipeLoadMoreFooter;
    @InjectView(R.id.swipeToLoad)
    SwipeToLoadLayout swipeToLoad;
    private int team_id;
    private InventoryApi inventoryApi;
    private ProgressDialog progressDialog;
    private NewSaleCustomAdapter newSaleCustomAdapter;
    private List<CustomerSaleBean.ResultBean.ResDataBean> res_data;
    private List<CustomerSaleBean.ResultBean.ResDataBean> allList;
    private int loadTime = 0;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsale);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(NewSaleActivity.this).create(InventoryApi.class);
        progressDialog = new ProgressDialog(NewSaleActivity.this);
        progressDialog.setMessage("加载中...");
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if ("firstPage".equals(from)){
            String name = intent.getStringExtra("name");
            serchData(name);
        }else {
            team_id = intent.getIntExtra("team_id", -1);
            String team_name = intent.getStringExtra("team_name");
            saleTitle.setText(team_name);
            getData(40, 0, Refresh_Move);
            searchLinst();
            initView();
        }
        swipeTarget.setLayoutManager(new LinearLayoutManager(NewSaleActivity.this));
        swipeTarget.addItemDecoration(new DividerItemDecoration(NewSaleActivity.this,
                DividerItemDecoration.VERTICAL));
    }

    private void initView() {
        swipeRefreshHeader.setGravity(Gravity.CENTER);
        swipeLoadMoreFooter.setGravity(Gravity.CENTER);
        swipeToLoad.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoad.setLoadMoreFooterView(swipeLoadMoreFooter);
        swipeToLoad.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(40, 0, Refresh_Move);
                if (newSaleCustomAdapter != null){
                    newSaleCustomAdapter.notifyDataSetChanged();
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
                        getData(40, 40*loadTime, Load_Move);
                        swipeToLoad.setLoadingMore(false);
                    }
                }, 500);
            }
        });
    }


    private void getData(final int limit, final int offset, final int move) {
        progressDialog.show();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("team_id", team_id);
        hashMap.put("limit", limit);
        hashMap.put("offset", offset);
        Call<CustomerSaleBean> partnerByTeam = inventoryApi.getPartnerByTeam(hashMap);
        partnerByTeam.enqueue(new MyCallback<CustomerSaleBean>() {
            @Override
            public void onResponse(Call<CustomerSaleBean> call, Response<CustomerSaleBean> response) {
                progressDialog.dismiss();
                if (response.body() == null || response.body().getResult() == null) return;
                res_data = response.body().getResult().getRes_data();
                if (move == Refresh_Move){
                    allList = res_data;
                    newSaleCustomAdapter = new NewSaleCustomAdapter(R.layout.item_customsale, res_data);
                    swipeTarget.setAdapter(newSaleCustomAdapter);
                }else {
                    if (res_data == null){
                        ToastUtils.showCommonToast(NewSaleActivity.this, "没有更多数据...");
                        return;
                    }
                    allList = newSaleCustomAdapter.getData();
                    allList.addAll(res_data);
                    newSaleCustomAdapter.setData(allList);
                }
                initListner();
            }

            @Override
            public void onFailure(Call<CustomerSaleBean> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void searchLinst() {
        searchNewsale.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                serchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (StringUtils.isNullOrEmpty(newText)){
                    res_data = allList;
                    newSaleCustomAdapter.setNewData(res_data);
                }
                return false;
            }
        });
    }

    //云搜索数据
    private void serchData(String query) {
        progressDialog.show();
        HashMap<Object, Object> hashMap = new HashMap<>();
        if (!"firstPage".equals(from)){
            hashMap.put("team_id", team_id);
        }
        hashMap.put("name", query);
        Call<CustomerSaleBean> partnerByTeam = inventoryApi.getPartnerByTeam(hashMap);
        partnerByTeam.enqueue(new MyCallback<CustomerSaleBean>() {
            @Override
            public void onResponse(Call<CustomerSaleBean> call, Response<CustomerSaleBean> response) {
                progressDialog.dismiss();
                if (response.body() == null || response.body().getResult() == null) return;
                final List<CustomerSaleBean.ResultBean.ResDataBean> data = response.body().getResult().getRes_data();
                if (newSaleCustomAdapter!=null){
                    newSaleCustomAdapter.setNewData(data);
                }else {
                    newSaleCustomAdapter = new NewSaleCustomAdapter(R.layout.item_customsale, data);
                    swipeTarget.setAdapter(newSaleCustomAdapter);
                }
                newSaleCustomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter listAdapter, View view, int position) {
                        Intent intent = new Intent(NewSaleActivity.this, NewSaleListActivity.class);
                        intent.putExtra("partner_id", data.get(position).getPartner_id());
                        intent.putExtra("partner_name", data.get(position).getName());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<CustomerSaleBean> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.back_no_left)
    void finishThis(View view) {
        finish();
    }

    private void initListner() {
        newSaleCustomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter listAdapter, View view, int position) {
                List<CustomerSaleBean.ResultBean.ResDataBean> data = listAdapter.getData();
                Intent intent = new Intent(NewSaleActivity.this, NewSaleListActivity.class);
                intent.putExtra("partner_id", data.get(position).getPartner_id());
                intent.putExtra("partner_name", data.get(position).getName());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        ViewUtils.collapseSoftInputMethod(NewSaleActivity.this, searchNewsale);
    }

    /*waitRadio.setChecked(true);
        waitSaleFragment = new WaitSaleFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        currentFragment = waitSaleFragment;
        ft.add(R.id.fragment_sale, waitSaleFragment).commit(); // 隐藏当前的fragment，add下一个到Activity中

        parentRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.wait_radio) {
                    if (waitSaleFragment == null) {
                        waitSaleFragment = new WaitSaleFragment();
                    }
                    switchFragment(waitSaleFragment);
                } else if (checkedRadioButtonId == R.id.can_radio) {
                    if (canSaleFragment == null) {
                        canSaleFragment = new CanSaleFragment();
                    }
                    switchFragment(canSaleFragment);
                }
            }
        });*/
    /*private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.fragment_sale, targetFragment)
                    .commit();
        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment)
                    .commit();
        }
        currentFragment = targetFragment;
    }*/
}
