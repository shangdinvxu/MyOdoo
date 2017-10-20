package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zaihuishou.expandablerecycleradapter.adapter.BaseExpandableAdapter;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.ProductLinesBean;
import tarce.model.inventory.PickingDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.LinesProductAdapter;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.StringUtils;
import tarce.support.SharePreferenceUtils;
import tarce.support.ToastUtils;


/**
 * Created by zouwansheng on 2017/10/18.
 */

public class LineProductActivity extends BaseActivity {
    @InjectView(R.id.recycler_line)
    RecyclerView recyclerLine;

    private BaseExpandableAdapter adapter;
    private final int ITEM_TYPE_COMPANY = 1;
    private final int ITEM_TYPE_DEPARTMENT = 2;
    private InventoryApi loginApi;
    private String name_activity;
    private String state_activity;
    private int process_id;
    private LinesProductAdapter linesProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lineproduct);
        ButterKnife.inject(this);

        loginApi = RetrofitClient.getInstance(LineProductActivity.this).create(InventoryApi.class);
        setRecyclerview(recyclerLine);
        Intent intent = getIntent();
        name_activity = intent.getStringExtra("name_activity");
        setTitle(name_activity);
        state_activity = intent.getStringExtra("state_product");
        if ("生产中".equals(name_activity)) {
            process_id = intent.getIntExtra("process_id", 1000);
        }
        initData();
    }

    private void initData() {
        showDefultProgressDialog();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("state", state_activity);
        hashMap.put("offset", 0);
        hashMap.put("limit", 20);
        int partner_id = SharePreferenceUtils.getInt("partner_id", 1000, LineProductActivity.this);
        hashMap.put("partner_id", partner_id);
        if (process_id != -1000) {
            hashMap.put("process_id", process_id);
        }
        //     hashMap.put("production_line_id", 12);
        Call<ProductLinesBean> productLines = loginApi.getProductLines(hashMap);
        productLines.enqueue(new Callback<ProductLinesBean>() {
            @Override
            public void onResponse(Call<ProductLinesBean> call, Response<ProductLinesBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError() != null) {
                    new TipDialog(LineProductActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                    List<ProductLinesBean.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                    linesProductAdapter = new LinesProductAdapter(R.layout.item_lineproduct, res_data);
                    recyclerLine.setAdapter(linesProductAdapter);
                    initListener();
                }
            }

            @Override
            public void onFailure(Call<ProductLinesBean> call, Throwable t) {
                dismissDefultProgressDialog();
                Log.e("zws", t.toString());
            }
        });

    }

    /**
     * 点击事件
     */
    private void initListener() {
        linesProductAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(LineProductActivity.this, ProductLineListActivity.class);
                List production_line_id = (List) linesProductAdapter.getData().get(position).getProduction_line_id();
                Double o = (Double) production_line_id.get(0);
                int a =  StringUtils.doubleToInt(o);
                if (a == -1000){
                    intent.putExtra("production_line_id", -1000);
                }else {
                    intent.putExtra("production_line_id", a);
                }
                intent.putExtra("state_activity", state_activity);
                if (process_id != -1000) {
                    intent.putExtra("process_id", process_id);
                }
                startActivity(intent);
            }
        });
    }
}
