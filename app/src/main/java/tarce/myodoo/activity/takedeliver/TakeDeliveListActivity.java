package tarce.myodoo.activity.takedeliver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
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
import tarce.model.inventory.TakeDelListBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.adapter.takedeliver.TakeDelListAdapter;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;
import tarce.support.ToastUtils;

/**
 * Created by zou.zou on 2017/6/22.
 * 收货列表
 */

public class TakeDeliveListActivity extends BaseActivity {
    private static final int Refresh_Move = 1;//下拉动作
    private static final int Load_Move = 2;//上拉动作
    @InjectView(R.id.sale_number)
    TextView saleNumber;
    @InjectView(R.id.parnter_name)
    TextView parnterName;
    @InjectView(R.id.state_deliever)
    TextView stateDeliever;
    @InjectView(R.id.swipe_refresh_header)
    RecyclerHeaderView swipeRefreshHeader;
    @InjectView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @InjectView(R.id.swipe_load_more_footer)
    RecyclerFooterView swipeLoadMoreFooter;
    @InjectView(R.id.swipeToLoad)
    SwipeToLoadLayout swipeToLoad;
    private int loadTime = 0;
    private InventoryApi inventoryApi;
    private String type_code;
    private String state;
    private TakeDelListAdapter listAdapter;
    private List<TakeDelListBean.ResultBean.ResDataBean> res_data = new ArrayList<>();
    private List<TakeDelListBean.ResultBean.ResDataBean> not_need = new ArrayList<>();
    private List<TakeDelListBean.ResultBean.ResDataBean> dataBeanList = new ArrayList<>();
    private String from;
    private String notneed;
    private long partner_id;
    private int picking_type_id;
    private boolean isNull = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takedel_list);
        ButterKnife.inject(this);


        setRecyclerview(swipeTarget);
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        partner_id = intent.getLongExtra("partner_id", 0);

        if (from.equals("no")){
            notneed = intent.getStringExtra("notneed");
            if (notneed.equals("yes")){
                not_need = (List<TakeDelListBean.ResultBean.ResDataBean>) intent.getSerializableExtra("intent");
                listAdapter = new TakeDelListAdapter(R.layout.adapter_takedel_list, not_need);
                swipeTarget.setAdapter(listAdapter);
                isNull = false;//为了区分其他三种情况，这种情况下返回本页面时候不清空list
                initListener();
            }else {
                type_code = intent.getStringExtra("type_code");
                state = intent.getStringExtra("state");
                showDefultProgressDialog();
                initData(0,40, Refresh_Move);
                initRecycler();
            }
        }else {
            picking_type_id = intent.getIntExtra("picking_type_id", 1);
            type_code = intent.getStringExtra("type_code");
            state = intent.getStringExtra("state");
            showDefultProgressDialog();
            initData(0,40, Refresh_Move);
            initRecycler();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dataBeanList == null && res_data == null){
            initData(0, 40, Refresh_Move);
        }
    }

    private void initRecycler() {
        swipeRefreshHeader.setGravity(Gravity.CENTER);
        swipeLoadMoreFooter.setGravity(Gravity.CENTER);
        swipeToLoad.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoad.setLoadMoreFooterView(swipeLoadMoreFooter);
        swipeToLoad.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(0,40, Refresh_Move);
                if (listAdapter != null){
                    listAdapter.notifyDataSetChanged();
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
                        initData(40*loadTime, 40, Load_Move);
                        listAdapter.notifyDataSetChanged();
                        swipeToLoad.setLoadingMore(false);
                    }
                }, 1000);
            }
        });
    }


    private void initData(final int offset, final int limit, final int move){
        inventoryApi = RetrofitClient.getInstance(TakeDeliveListActivity.this).create(InventoryApi.class);
        HashMap<Object, Object> hashMap = new HashMap<>();
        Call<TakeDelListBean> inComingOutgoingList = null;
        hashMap.put("state", state);
        hashMap.put("limit", limit);
        hashMap.put("offset", offset);
        if ("no".equals(from)){
            hashMap.put("picking_type_code", type_code);
            inComingOutgoingList = inventoryApi.getInComingOutgoingList(hashMap);
        }else if ("yes".equals(from)){
            hashMap.put("partner_id", partner_id);
            hashMap.put("picking_type_id", picking_type_id);
            inComingOutgoingList = inventoryApi.getstockList(hashMap);
        }
        inComingOutgoingList.enqueue(new MyCallback<TakeDelListBean>() {
            @Override
            public void onResponse(Call<TakeDelListBean> call, Response<TakeDelListBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)return;
                if (response.body().getResult()==null)return;
                if (response.body().getResult().getRes_data()!=null && response.body().getResult().getRes_code() == 1){
                    res_data = response.body().getResult().getRes_data();
                    if (move == Refresh_Move){
                        dataBeanList = res_data;
                        listAdapter = new TakeDelListAdapter(R.layout.adapter_takedel_list, res_data);
                        swipeTarget.setAdapter(listAdapter);
                    }else {
                        if (res_data == null){
                            ToastUtils.showCommonToast(TakeDeliveListActivity.this, "没有更多数据...");
                            return;
                        }
                        dataBeanList = listAdapter.getData();
                        dataBeanList.addAll(res_data);
                        listAdapter.setData(dataBeanList);
                    }
                    initListener();
                }
            }

            @Override
            public void onFailure(Call<TakeDelListBean> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(TakeDeliveListActivity.this, t.toString());
            }
        });
    }

    /**
     * 点击事件
     * */
    private void initListener() {
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(TakeDeliveListActivity.this, TakeDeliverDetailActivity.class);
                intent.putExtra("dataBean", listAdapter.getData().get(position));
                intent.putExtra("type_code", listAdapter.getData().get(position).getPicking_type_code());
                intent.putExtra("state",listAdapter.getData().get(position).getState());
                intent.putExtra("from", from);
                intent.putExtra("notneed", notneed);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dataBeanList != null && res_data != null && isNull){
            dataBeanList = null;
            res_data = null;
        }
    }
}
