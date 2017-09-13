package tarce.myodoo.activity.engineer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.ProjectBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.uiutil.TipDialog;

/**
 * Created by zouwansheng on 2017/9/5.
 */

public class EngineerActivity extends BaseActivity {

    @InjectView(R.id.line_product)
    LinearLayout lineProduct;
    @InjectView(R.id.line_engineer)
    LinearLayout lineEngineer;
    @InjectView(R.id.one_num)
    TextView oneNum;
    @InjectView(R.id.two_num)
    TextView twoNum;
    private InventoryApi inventoryApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(EngineerActivity.this).create(InventoryApi.class);
        showDefultProgressDialog();
        initData();
    }

    //计算红点数量
    private void initData() {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("picking_type", "pick_type");
        Call<ProjectBean> bypartner = inventoryApi.getPickrequest(hashMap);
        bypartner.enqueue(new MyCallback<ProjectBean>() {
            @Override
            public void onResponse(Call<ProjectBean> call, Response<ProjectBean> response) {
                // dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError() != null) {
                    new TipDialog(EngineerActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                    ProjectBean.ResultBean.ResDataBean resDataBean = response.body().getResult().getRes_data();
                    int one = resDataBean.getWaiting_data().size();
                    oneNum.setText(one+"");
                }
            }

            @Override
            public void onFailure(Call<ProjectBean> call, Throwable t) {
                dismissDefultProgressDialog();
                Log.e("zws", t.toString());
            }
        });
        HashMap<Object, Object> hashMap1 = new HashMap<>();
        hashMap1.put("picking_type", "proofing");
        Call<ProjectBean> bypartner1 = inventoryApi.getPickrequest(hashMap);
        bypartner1.enqueue(new MyCallback<ProjectBean>() {
            @Override
            public void onResponse(Call<ProjectBean> call, Response<ProjectBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError() != null) {
                    new TipDialog(EngineerActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                    ProjectBean.ResultBean.ResDataBean resDataBean = response.body().getResult().getRes_data();
                    int two = resDataBean.getWaiting_data().size();
                    twoNum.setText(two+"");
                }
            }

            @Override
            public void onFailure(Call<ProjectBean> call, Throwable t) {
                dismissDefultProgressDialog();
                Log.e("zws", t.toString());
            }
        });
    }

    //产线领用
    @OnClick(R.id.line_product)
    void setLineProduct(View view) {
        Intent intent = new Intent(EngineerActivity.this, ProjectActivity.class);
        intent.putExtra("type", "pick_type");
        startActivity(intent);
    }

    //工程领用
    @OnClick(R.id.line_engineer)
    void setLineEngineer(View view) {
        Intent intent = new Intent(EngineerActivity.this, ProjectActivity.class);
        intent.putExtra("type", "proofing");
        startActivity(intent);
    }
}
