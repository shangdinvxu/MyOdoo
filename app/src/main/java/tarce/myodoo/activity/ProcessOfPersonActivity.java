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
import tarce.model.inventory.ProcessShowBean;
import tarce.model.inventory.ProductProcessBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.processproduct.ProcessListAdapter;
import tarce.support.SharePreferenceUtils;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/6/15.
 * 生产中  后续改为  进入这个生产工序界面 按工序分类了就是
 */

public class ProcessOfPersonActivity extends BaseActivity {
    @InjectView(R.id.recyc_select_proce)
    RecyclerView recycSelectProce;
    private InventoryApi inventoryApi;
    private ProcessListAdapter adapter;
    private List<ProcessShowBean> beanList;
    private List<ProductProcessBean.ResultBean.ResDataBean> res_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_proce);
        ButterKnife.inject(this);

        setRecyclerview(recycSelectProce);
        showDefultProgressDialog();
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (res_data == null){
            getData();
        }
    }

    @Override
    protected void onPause() {
        if (res_data != null){
            res_data = null;
        }
        super.onPause();
    }

    private void getData() {
        res_data = new ArrayList<>();
        beanList = new ArrayList<>();
        inventoryApi = RetrofitClient.getInstance(ProcessOfPersonActivity.this).create(InventoryApi.class);
        HashMap<Object, Object> hashMap = new HashMap<>();
        int partner_id = SharePreferenceUtils.getInt("partner_id", 1000, ProcessOfPersonActivity.this);
        hashMap.put("partner_id", partner_id);
        Call<ProductProcessBean> objectCall = inventoryApi.processGroup(hashMap);
        objectCall.enqueue(new MyCallback<ProductProcessBean>() {
            @Override
            public void onResponse(Call<ProductProcessBean> call, Response<ProductProcessBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)return;
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data()!= null){
                    res_data = response.body().getResult().getRes_data();
                    for (int i = 0; i < res_data.size(); i++) {
                        ProcessShowBean bean = new ProcessShowBean(res_data.get(i).getName(), res_data.get(i).getProcess_count());
                        beanList.add(bean);
                    }
                    adapter = new ProcessListAdapter(R.layout.adapter_process_list, beanList);
                    recycSelectProce.setAdapter(adapter);
                    initListen();
                }
            }

            @Override
            public void onFailure(Call<ProductProcessBean> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(ProcessOfPersonActivity.this, t.toString());
            }
        });
    }

    //点击事件
    private void initListen() {
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (res_data == null || position>=res_data.size()){
                        return;
                    }
                    Intent intent = new Intent(ProcessOfPersonActivity.this, ProductLlActivity.class);
                    intent.putExtra("name_activity","生产中");
                    intent.putExtra("state_product","progress");
                    intent.putExtra("process_id",res_data.get(position).getProcess_id());
                    startActivity(intent);
                }
            });
    }
}
