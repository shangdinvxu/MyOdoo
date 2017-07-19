package tarce.myodoo.activity.inspect;

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
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.inventory.QcFeedbaskBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.adapter.InspectionSubAdapter;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/6/1.
 * 品检的子页面 列表
 */

public class InspectionSubActivity extends BaseActivity {

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
    private InventoryApi inventoryApi;
    private String state;
    private InspectionSubAdapter subAdapter;
    private List<QcFeedbaskBean.ResultBean.ResDataBean> res_data = new ArrayList<>();
    private List<QcFeedbaskBean.ResultBean.ResDataBean> for_transform = new ArrayList<>();
    private int loadTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_sub);
        ButterKnife.inject(this);

        setRecyclerview(swipeTarget);

        Intent intent = getIntent();
        state = intent.getStringExtra("state");
        getFeedback(0,20, Refresh_Move);
        setRecyc();
    }

    @Override
    protected void onResume(){
        if (res_data == null){
            swipeToLoad.setRefreshing(true);
            loadTime = 0;
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (res_data != null){
            res_data = null;
        }
    }

    private void setRecyc() {
        showDefultProgressDialog();
        swipeRefreshHeader.setGravity(Gravity.CENTER);
        swipeLoadMoreFooter.setGravity(Gravity.CENTER);
        swipeToLoad.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoad.setLoadMoreFooterView(swipeLoadMoreFooter);

        swipeToLoad.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFeedback(0, 20, Refresh_Move);
                if (subAdapter!=null){
                    subAdapter.notifyDataSetChanged();
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
                        getFeedback(20 * loadTime, 20, Load_Move);
                        subAdapter.notifyDataSetChanged();
                        swipeToLoad.setLoadingMore(false);
                    }
                }, 1000);
            }
        });
    }

    /**
     * 得到返回的数据
     * */
    private void getFeedback(int offset, int limit, final int move) {
        inventoryApi = RetrofitClient.getInstance(InspectionSubActivity.this).create(InventoryApi.class);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("limit", limit);
        hashMap.put("offset", offset);
        hashMap.put("state", state);
        Call<QcFeedbaskBean> qcfb = inventoryApi.getQcfb(hashMap);
        qcfb.enqueue(new MyCallback<QcFeedbaskBean>() {
            @Override
            public void onResponse(Call<QcFeedbaskBean> call, Response<QcFeedbaskBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null || response.body().getResult()==null)return;
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data()!=null){
                    if (move == Refresh_Move){
                        res_data = response.body().getResult().getRes_data();
                        for_transform = res_data;
                        subAdapter = new InspectionSubAdapter(R.layout.adapter_inspec_sub, res_data);
                        swipeTarget.setAdapter(subAdapter);
                    }else {
                        res_data = response.body().getResult().getRes_data();
                        if (res_data == null){
                            ToastUtils.showCommonToast(InspectionSubActivity.this, "没有更多数据...");
                            return;
                        }else {
                            for_transform = subAdapter.getData();
                            for_transform.addAll(res_data);
                            subAdapter.setData(for_transform);
                        }
                    }
                    clickItem();
                }else if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() == null){
                    ToastUtils.showCommonToast(InspectionSubActivity.this, "没有更多数据...");
                }
            }

            @Override
            public void onFailure(Call<QcFeedbaskBean> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(InspectionSubActivity.this, t.toString());
            }
        });
    }

    /**
     * item点击事件
     * */
    private void clickItem() {
        subAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(InspectionSubActivity.this, InspectMoDetailActivity.class);
                intent.putExtra("data", for_transform.get(position));
                startActivity(intent);
            }
        });
    }
}
