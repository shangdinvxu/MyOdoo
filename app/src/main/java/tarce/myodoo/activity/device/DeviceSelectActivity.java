package tarce.myodoo.activity.device;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;


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
import tarce.model.GetProcessBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.adapter.device.DeviceSelecAdapter;
import tarce.myodoo.uiutil.TipDialog;
import tarce.support.ToastUtils;

/**
 * Created by zouwansheng on 2017/9/18.
 */

public class DeviceSelectActivity extends BaseActivity {
    @InjectView(R.id.search_device)
    SearchView searchDevice;
    @InjectView(R.id.recycler_device)
    RecyclerView recyclerDevice;

    private InventoryApi inventoryApi;
    private List<GetProcessBean.TestRSubBean.ListSubBean> listSubBeen;
    private DeviceSelecAdapter selecAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deviceselect);
        ButterKnife.inject(this);

        setRecyclerview(recyclerDevice);
        getProcessNAme();
    }

    /**
     * 获取生产工序
     * */
    private void getProcessNAme() {
        showDefultProgressDialog();
        listSubBeen = new ArrayList<>();
        inventoryApi = RetrofitClient.getInstance(DeviceSelectActivity.this).create(InventoryApi.class);
        Call<GetProcessBean> processBeanCall = inventoryApi.getProcess(new HashMap());
        processBeanCall.enqueue(new MyCallback<GetProcessBean>() {
            @Override
            public void onResponse(Call<GetProcessBean> call, Response<GetProcessBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)return;
                if (response.body().getError()!=null){
                    new TipDialog(DeviceSelectActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data()!=null){
                    listSubBeen = response.body().getResult().getRes_data();
                    selecAdapter = new DeviceSelecAdapter(R.layout.adapter_process_list, listSubBeen);
                    recyclerDevice.setAdapter(selecAdapter);
                    initListener();
                }
            }
            @Override
            public void onFailure(Call<GetProcessBean> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(DeviceSelectActivity.this, t.toString());
                Log.i("DeviceSelectActivity",t.toString());
            }
        });
    }

    //点击事件
    private void initListener() {
        selecAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(DeviceSelectActivity.this, DeviceStateActivity.class);
                intent.putExtra("name", selecAdapter.getData().get(position).getName());
                intent.putExtra("id", selecAdapter.getData().get(position).getProcess_id());
                startActivity(intent);
            }
        });
    }
}
