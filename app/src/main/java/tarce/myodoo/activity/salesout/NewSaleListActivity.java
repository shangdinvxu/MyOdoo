package tarce.myodoo.activity.salesout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.GetSaleResponse;
import tarce.model.inventory.SalesOutListResponse;
import tarce.myodoo.R;
import tarce.myodoo.adapter.takedeliver.NewSaleListAdapte;
import tarce.support.ToastUtils;

/**
 * Created by zws on 2017/8/9.
 */

public class NewSaleListActivity extends Activity {
    @InjectView(R.id.finish_this)
    ImageView finishThis;
    @InjectView(R.id.sale_title_dan)
    TextView saleTitleDan;
    @InjectView(R.id.right_detail)
    ImageView rightDetail;
    /*@InjectView(R.id.wait_radio_dan)
    RadioButton waitRadioDan;
    @InjectView(R.id.can_radio_dan)
    RadioButton canRadioDan;
    @InjectView(R.id.parent_radio_dan)
    RadioGroup parentRadioDan;*/
    @InjectView(R.id.recycler_sale_list)
    RecyclerView recyclerSaleList;
    private int partner_id;
    private InventoryApi inventoryApi;
    private ProgressDialog progressDialog;
    /*@InjectView(R.id.fragment_sale_list)
    FrameLayout fragmentSaleList;*/
    private NewSaleListAdapte listAdapte;
    private List<SalesOutListResponse.TResult.TRes_data> res_data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsalelist);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(NewSaleListActivity.this).create(InventoryApi.class);
        Intent intent = getIntent();
        partner_id = intent.getIntExtra("partner_id", -1);
        String partner_name = intent.getStringExtra("partner_name");
        saleTitleDan.setText(partner_name);
        recyclerSaleList.setLayoutManager(new LinearLayoutManager(NewSaleListActivity.this));
        recyclerSaleList.addItemDecoration(new DividerItemDecoration(NewSaleListActivity.this,
                DividerItemDecoration.VERTICAL));
        progressDialog = new ProgressDialog(NewSaleListActivity.this);
        progressDialog.setMessage("加载中...");
        getData();
    }

    @OnClick(R.id.finish_this)
    void finishThis(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (res_data == null){
            getData();
        }
    }

    private void getData() {
        progressDialog.show();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("partner_id", partner_id);
        Call<SalesOutListResponse> bypartner = inventoryApi.getBypartner(hashMap);
        bypartner.enqueue(new MyCallback<SalesOutListResponse>() {
            @Override
            public void onResponse(Call<SalesOutListResponse> call, Response<SalesOutListResponse> response) {
                progressDialog.dismiss();
                if (response.body() == null) return;
                res_data = response.body().getResult().getRes_data();
                listAdapte = new NewSaleListAdapte(R.layout.item_newsalelist, res_data);
                recyclerSaleList.setAdapter(listAdapte);
                initListner();
            }

            @Override
            public void onFailure(Call<SalesOutListResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void initListner() {
            listAdapte.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    progressDialog.show();
                    HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                    objectObjectHashMap.put("picking_id", listAdapte.getData().get(position).getPicking_id());
                    Call<GetSaleResponse> stringCall = inventoryApi.checkIsCanUse(objectObjectHashMap);
                    stringCall.enqueue(new MyCallback<GetSaleResponse>() {
                        @Override
                        public void onResponse(Call<GetSaleResponse> call, Response<GetSaleResponse> response) {
                            progressDialog.dismiss();
                            if (response.body() == null)return;
                            if (response.body().getResult().getRes_code()==1 && response.body().getResult().getRes_data()!=null){
                                GetSaleResponse.TResult result = response.body().getResult();
                                Intent intent = new Intent(NewSaleListActivity.this, SalesDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("bundle", result.getRes_data());
                                intent.putExtra("intent", bundle);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<GetSaleResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            ToastUtils.showCommonToast(NewSaleListActivity.this, t.toString());
                        }
                    });
                }
            });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (res_data!=null){
            res_data = null;
        }
    }
}
