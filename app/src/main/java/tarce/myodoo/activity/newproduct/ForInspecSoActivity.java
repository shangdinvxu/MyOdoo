package tarce.myodoo.activity.newproduct;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.SearchView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.Serializable;
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
import tarce.model.SoQcBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.activity.inspect.InspectionSubActivity;
import tarce.myodoo.activity.inspect.NewSpectListActivity;
import tarce.myodoo.adapter.SoOriginAdapter;
import tarce.myodoo.adapter.newproduct.ForInspecSoAdapter;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;
import tarce.myodoo.uiutil.TipDialog;
import tarce.support.SharePreferenceUtils;

/**
 * Created by zouwansheng on 2017/12/21.
 */

public class ForInspecSoActivity extends BaseActivity {
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
    private String name_activity;
    private List<SoQcBean.ResultBean.ResDataBean> res_data;
    private ForInspecSoAdapter forInspecSoAdapter;
    private boolean isChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soorigin);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(ForInspecSoActivity.this).create(InventoryApi.class);
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

    @Override
    protected void onPause() {
        isChange = true;
        super.onPause();
    }

    private void initData() {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("process_id", process_id);
        if (line_id != -1000) {
            hashMap.put("production_line_id", line_id);
        }
        if ("qc_fail".equals(state)){
            int partner_id = SharePreferenceUtils.getInt("partner_id", 1000, ForInspecSoActivity.this);
            hashMap.put("partner_id", partner_id);
        }
        hashMap.put("state", state);
        hashMap.put("is_group_by", true);
        final Call<SoQcBean> recentOr = inventoryApi.getNewQcfb(hashMap);
        recentOr.enqueue(new MyCallback<SoQcBean>() {
            @Override
            public void onResponse(Call<SoQcBean> call, Response<SoQcBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError()!=null){
                    new TipDialog(ForInspecSoActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1){
                    res_data = response.body().getResult().getRes_data();
                    forInspecSoAdapter = new ForInspecSoAdapter(R.layout.item_soorigin, res_data);
                    swipeTarget.setAdapter(forInspecSoAdapter);
                    initListener();
                }
            }

            @Override
            public void onFailure(Call<SoQcBean> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
    }

    private void initListener() {
       forInspecSoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               List<SoQcBean.ResultBean.ResDataBean> data = forInspecSoAdapter.getData();
               Intent intent2 = new Intent(ForInspecSoActivity.this, NewSpectListActivity.class);
               intent2.putExtra("state",state);
               intent2.putExtra("process_id", process_id);
               intent2.putExtra("line_id", line_id);
               intent2.putExtra("data", (Serializable) data.get(position).getFeedback());
               startActivity(intent2);
           }
       });
    }
}
