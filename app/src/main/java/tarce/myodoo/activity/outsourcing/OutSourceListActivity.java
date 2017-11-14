package tarce.myodoo.activity.outsourcing;

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
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.inventory.OutsourceBean;
import tarce.model.inventory.PickingDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.activity.ProductLlActivity;
import tarce.myodoo.adapter.outsource.OutSourceAdapter;
import tarce.myodoo.adapter.product.PickingDetailAdapter;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;
import tarce.myodoo.uiutil.TipDialog;
import tarce.support.ToastUtils;

/**
 * Created by zouwansheng on 2017/11/10.
 */

public class OutSourceListActivity extends BaseActivity {
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

    private String source;
    private InventoryApi inventoryApi;
    private OutSourceAdapter outSourceAdapter;
    private List<OutsourceBean.ResultBean.ResDataBean> beanList = new ArrayList<>();
    private List<OutsourceBean.ResultBean.ResDataBean> dataBeanList = new ArrayList<>();
    private int loadTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outsourcelist);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(OutSourceListActivity.this).create(InventoryApi.class);
        setRecyclerview(swipeTarget);
        Intent intent = getIntent();
        source = intent.getStringExtra("source");
        showDefultProgressDialog();
        initView();
        initData(0, 20, Refresh_Move);
    }

    @Override
    protected void onResume() {
        if (dataBeanList == null) {
            swipeToLoad.setRefreshing(true);
            loadTime = 0;
        }
        super.onResume();
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
                initData(0, 20, Refresh_Move);
                if (outSourceAdapter != null) {
                    outSourceAdapter.notifyDataSetChanged();
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
                        initData(20 * loadTime, 20, Load_Move);
                        outSourceAdapter.notifyDataSetChanged();
                        swipeToLoad.setLoadingMore(false);
                    }
                }, 1000);
            }
        });
    }

    private void initData(final int offset, final int limit, final int move) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("state", source);
        hashMap.put("limit", limit);
        hashMap.put("offset", offset);
        Call<OutsourceBean> outOrderbyState = inventoryApi.getOutOrderbyState(hashMap);
        outOrderbyState.enqueue(new Callback<OutsourceBean>() {
            @Override
            public void onResponse(Call<OutsourceBean> call, Response<OutsourceBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError() != null) {
                    new TipDialog(OutSourceListActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                    beanList = response.body().getResult().getRes_data();
                    if (move == Refresh_Move) {
                        dataBeanList = beanList;
                        outSourceAdapter = new OutSourceAdapter(R.layout.item_outsourcing, beanList);
                        swipeTarget.setAdapter(outSourceAdapter);
                    } else {
                        if (beanList == null) {
                            ToastUtils.showCommonToast(OutSourceListActivity.this, "没有更多数据...");
                            return;
                        }
                        dataBeanList = outSourceAdapter.getData();
                        dataBeanList.addAll(beanList);
                        outSourceAdapter.setData(dataBeanList);
                    }
                    initListener();
                }else if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() == null
                        && move != Load_Move) {
                    swipeTarget.setVisibility(View.GONE);
                    ToastUtils.showCommonToast(OutSourceListActivity.this, "没有更多数据...");
                } else if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == -1) {
                    ToastUtils.showCommonToast(OutSourceListActivity.this, "some error,please link admin");
                }
            }

            @Override
            public void onFailure(Call<OutsourceBean> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
    }

    private void initListener() {
        outSourceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<OutsourceBean.ResultBean.ResDataBean> data = outSourceAdapter.getData();
                Intent intent = new Intent(OutSourceListActivity.this, OutSourceDetailActivity.class);
                intent.putExtra("data", data.get(position));
                startActivity(intent);
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
