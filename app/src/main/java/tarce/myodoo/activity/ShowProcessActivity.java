package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import tarce.model.inventory.ProcessDeatilBean;
import tarce.model.inventory.ProcessShowBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.processproduct.ProcessDetailAdapter;
import tarce.support.AlertAialogUtils;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/5/19.
 * 点击某个工序的详细页面
 */

public class ShowProcessActivity extends ToolBarActivity {

    private final static String TAG = ShowProcessActivity.class.getSimpleName();
    @InjectView(R.id.recycler_detail_process)
    RecyclerView recyclerDetailProcess;

    private ProcessDetailAdapter detailAdapter;
    private InventoryApi inventoryApi;
    private int delay_num;
    private List<ProcessShowBean> beanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_process);
        ButterKnife.inject(this);
        setRecyclerview(recyclerDetailProcess);

        AlertAialogUtils.showDefultProgressDialog(ShowProcessActivity.this);
        getDetailDelay();
    }

    /**
     * 连接接口
     */
    private void getDetailDelay() {
        Intent intent = getIntent();
        delay_num = intent.getIntExtra("delay_num", 3);
        setTitle(intent.getStringExtra("process_name"));
        beanList = new ArrayList<>();
        ProcessShowBean showBean = new ProcessShowBean();
        beanList.add(new ProcessShowBean("延误", 0));
        beanList.add(new ProcessShowBean("今天", 0));
        beanList.add(new ProcessShowBean("明天", 0));
        beanList.add(new ProcessShowBean("后天", 0));

        detailAdapter = new ProcessDetailAdapter(R.layout.adapter_process_detail, beanList);
        recyclerDetailProcess.setAdapter(detailAdapter);

        inventoryApi = RetrofitClient.getInstance(ShowProcessActivity.this).create(InventoryApi.class);
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        //按照展示process——name的顺序，请求相应的工序id
        hashMap.put("process_id", delay_num);
        Call<ProcessDeatilBean> threeCall = inventoryApi.getDetailProcess(hashMap);
        threeCall.enqueue(new Callback<ProcessDeatilBean>() {
            @Override
            public void onResponse(Call<ProcessDeatilBean> call, Response<ProcessDeatilBean> response) {
                AlertAialogUtils.dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getResult().getRes_code() == 1) {
                    if (response.body().getResult().getRes_data() == null) {
                        /*detailAdapter = new ProcessDetailAdapter(R.layout.adapter_process_detail, beanList);
                        recyclerDetailProcess.setAdapter(detailAdapter);*/
                        return;
                    }
                    ;
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
                AlertAialogUtils.dismissDefultProgressDialog();
            }
        });
    }

    /**
     * item点击事件
     */
    private void itemClick() {
        detailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (beanList.get(position).getProcess_num() == 0) {
                    return;
                }
                Intent intent = new Intent(ShowProcessActivity.this, MaterialDetailActivity.class);
                intent.putExtra("process_id", delay_num);
                intent.putExtra("state",beanList.get(position).getProcess_name());
                intent.putExtra("limit", beanList.get(position).getProcess_num());
                startActivity(intent);
            }
        });
    }
}
