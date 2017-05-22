package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.myodoo.R;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/5/22.
 * 等待返工页面
 */

public class WaitReworkActivity extends ToolBarActivity {

    @InjectView(R.id.swipe_refresh_header)
    RecyclerHeaderView swipeRefreshHeader;
    @InjectView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @InjectView(R.id.swipe_load_more_footer)
    RecyclerFooterView swipeLoadMoreFooter;
    @InjectView(R.id.swipeToLoad)
    SwipeToLoadLayout swipeToLoad;
    private InventoryApi inventoryApi;
    private String state_product;//状态
    private String name_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiat_rework);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        state_product = intent.getStringExtra("state_product");
        name_activity = intent.getStringExtra("name_activity");
        setTitle(name_activity);
        getDetail(0,15);
    }

    /**
     * 获取详情
     * */
    private void getDetail(int offset, int limit) {

        inventoryApi = RetrofitClient.getInstance(WaitReworkActivity.this).create(InventoryApi.class);
        HashMap<Object,Object> hashMap = new HashMap<>();
        hashMap.put("state",state_product);
        hashMap.put("limit",limit);
        hashMap.put("offset",offset);
        Call<Object> reworkRuku = inventoryApi.getReworkRuku(hashMap);
        reworkRuku.enqueue(new MyCallback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.body() == null)return;
                Log.i("WaitReworkActivity","========");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
}
