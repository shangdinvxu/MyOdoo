package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import tarce.model.inventory.ProcessDeatilBean;
import tarce.model.inventory.ProcessShowBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.processproduct.ProcessDetailAdapter;
import tarce.support.SharePreferenceUtils;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/6/15.
 * 等待生产后续页面
 */

public class GetPickNumActivity extends BaseActivity {
    @InjectView(R.id.recycler_detail_process)
    RecyclerView recyclerDetailProcess;
    private ProcessDetailAdapter detailAdapter;
    private List<ProcessShowBean> beanList;
    private InventoryApi inventoryApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_process);
        ButterKnife.inject(this);
        setRecyclerview(recyclerDetailProcess);
        setTitle("等待生产");

        getData();
    }

    //获取数据
    private void getData() {
        showDefultProgressDialog();
        beanList = new ArrayList<>();
        ProcessShowBean showBean = new ProcessShowBean();
        beanList.add(new ProcessShowBean("延误", 0));
        beanList.add(new ProcessShowBean("今天", 0));
        beanList.add(new ProcessShowBean("明天", 0));
        beanList.add(new ProcessShowBean("后天", 0));

        detailAdapter = new ProcessDetailAdapter(R.layout.adapter_process_detail, beanList);
        recyclerDetailProcess.setAdapter(detailAdapter);

        inventoryApi = RetrofitClient.getInstance(GetPickNumActivity.this).create(InventoryApi.class);
        HashMap<Object, Object> hashMap = new HashMap<>();
        int partner_id = SharePreferenceUtils.getInt("partner_id", 1000, GetPickNumActivity.this);
        hashMap.put("partner_id", partner_id);
        Call<ProcessDeatilBean> pickCount = inventoryApi.getPickCount(hashMap);
        pickCount.enqueue(new MyCallback<ProcessDeatilBean>() {
            @Override
            public void onResponse(Call<ProcessDeatilBean> call, Response<ProcessDeatilBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data()!= null) {
                    for (int i = 0; i < response.body().getResult().getRes_data().size(); i++) {
                        if (response.body().getResult().getRes_data().get(i).getState().equals("delay")) {
                            beanList.set(0, new ProcessShowBean("延误", response.body().getResult().getRes_data().get(i).getCount()));
                        } else if (response.body().getResult().getRes_data().get(i).getState().equals("today")) {
                            beanList.set(1, new ProcessShowBean("今天", response.body().getResult().getRes_data().get(i).getCount()));
                        } else if (response.body().getResult().getRes_data().get(i).getState().equals("tomorrow")) {
                            beanList.set(2, new ProcessShowBean("明天", response.body().getResult().getRes_data().get(i).getCount()));
                        } else if (response.body().getResult().getRes_data().get(i).getState().equals("after")) {
                            beanList.set(3, new ProcessShowBean("后天", response.body().getResult().getRes_data().get(i).getCount()));
                        }
                    }
                    detailAdapter.notifyDataSetChanged();
                    itemClick();
                }
            }
            @Override
            public void onFailure(Call<ProcessDeatilBean> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(GetPickNumActivity.this, t.toString());
            }
        });
    }

    /**
     * 点击事件
     * */
    private void itemClick() {
        detailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (beanList.get(position).getProcess_num() == 0)return;
                Intent intent = new Intent(GetPickNumActivity.this, WaitProdListActivity.class);
                intent.putExtra("state_delay",beanList.get(position).getProcess_name());
                startActivity(intent);
            }
        });

    }
}
