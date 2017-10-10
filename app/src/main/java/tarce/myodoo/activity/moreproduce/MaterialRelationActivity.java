package tarce.myodoo.activity.moreproduce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.MaterialRelationBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.adapter.moreproduce.RelationAdapter;
import tarce.myodoo.uiutil.TipDialog;

/**
 * Created by zouwansheng on 2017/9/25.
 * 物料关系页面
 */

public class MaterialRelationActivity extends BaseActivity {

    @InjectView(R.id.produce_out)
    TextView produceOut;
    @InjectView(R.id.recycler_out)
    RecyclerView recyclerOut;
    @InjectView(R.id.produce_in)
    TextView produceIn;
    @InjectView(R.id.recycler_in)
    RecyclerView recyclerIn;
    private int rule_id;
    private InventoryApi inventoryApi;
    private RelationAdapter inAdapter;
    private RelationAdapter outAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materialrelation);
        ButterKnife.inject(this);

        setTitle("物料关系");
        setRecyclerview(recyclerIn);
        setRecyclerview(recyclerOut);
        inventoryApi = RetrofitClient.getInstance(MaterialRelationActivity.this).create(InventoryApi.class);
        Intent intent = getIntent();
        rule_id = intent.getIntExtra("rule_id", 1);
        initData();
    }

    //获取数据
    private void initData() {
        showDefultProgressDialog();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("rule_id", rule_id);
        Call<MaterialRelationBean> mrpRule = inventoryApi.getMrpRule(hashMap);
        mrpRule.enqueue(new Callback<MaterialRelationBean>() {
            @Override
            public void onResponse(Call<MaterialRelationBean> call, Response<MaterialRelationBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)return;
                if (response.body().getError()!=null){
                    new TipDialog(MaterialRelationActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_data()!=null &&response.body().getResult().getRes_code() == 1){
                    List<MaterialRelationBean.ResultBean.ResDataBean.InputBean> input = response.body().getResult().getRes_data().getInput();
                    List<MaterialRelationBean.ResultBean.ResDataBean.InputBean> output = response.body().getResult().getRes_data().getOutput();
                    inAdapter = new RelationAdapter(R.layout.item_relation_more, input);
                    outAdapter = new RelationAdapter(R.layout.item_relation_more, output);
                    recyclerIn.setAdapter(inAdapter);
                    recyclerOut.setAdapter(outAdapter);
                }else {

                }

            }

            @Override
            public void onFailure(Call<MaterialRelationBean> call, Throwable t) {
                dismissDefultProgressDialog();
                Log.e("zws", t.toString());
            }
        });
    }
}
