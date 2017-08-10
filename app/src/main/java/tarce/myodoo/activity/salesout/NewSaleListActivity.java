package tarce.myodoo.activity.salesout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.GetSaleResponse;
import tarce.model.inventory.CustomerSaleBean;
import tarce.model.inventory.NewSaleListBean;
import tarce.model.inventory.SalesOutListResponse;
import tarce.myodoo.R;
import tarce.myodoo.adapter.takedeliver.NewSaleListAdapte;
import tarce.myodoo.utils.StringUtils;
import tarce.support.MyLog;
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
    @InjectView(R.id.wait_radio_dan)
    RadioButton waitRadioDan;
    @InjectView(R.id.can_radio_dan)
    RadioButton canRadioDan;
    @InjectView(R.id.parent_radio_dan)
    RadioGroup parentRadioDan;
    @InjectView(R.id.recycler_sale_list)
    RecyclerView recyclerSaleList;
    @InjectView(R.id.search_newsalelist)
    SearchView searchNewsalelist;
    private int partner_id;
    private InventoryApi inventoryApi;
    private ProgressDialog progressDialog;
    /*@InjectView(R.id.fragment_sale_list)
    FrameLayout fragmentSaleList;*/
    private NewSaleListAdapte listAdapte;
    private List<NewSaleListBean.ResultBean.ResDataBean.AbleToDataBean> res_data;
    private List<NewSaleListBean.ResultBean.ResDataBean.AbleToDataBean> allList;
    private NewSaleListBean.ResultBean.ResDataBean resDataBean;
    private String from;
    private String isUpdate = "";
    private String danhao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsalelist);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(NewSaleListActivity.this).create(InventoryApi.class);
        progressDialog = new ProgressDialog(NewSaleListActivity.this);
        progressDialog.setMessage("加载中...");
        progressDialog.setCanceledOnTouchOutside(false);
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if ("fistPage".equals(from)){
            danhao = intent.getStringExtra("danhao");
            getSearch(danhao);
        }else {
            partner_id = intent.getIntExtra("partner_id", -1);
            String partner_name = intent.getStringExtra("partner_name");
            saleTitleDan.setText(partner_name);
            getData();
        }
        recyclerSaleList.setLayoutManager(new LinearLayoutManager(NewSaleListActivity.this));
        recyclerSaleList.addItemDecoration(new DividerItemDecoration(NewSaleListActivity.this,
                DividerItemDecoration.VERTICAL));
    }

    /**
     * 根据首页的单号搜索
     * */
    private void getSearch(String danhao) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("order_name", danhao);
        Call<NewSaleListBean> getSaleListByNumberResponseCall = inventoryApi.getPickby(objectObjectHashMap);
        progressDialog.show();
        getSaleListByNumberResponseCall.enqueue(new Callback<NewSaleListBean>() {
            @Override
            public void onResponse(Call<NewSaleListBean> call, Response<NewSaleListBean> response) {
                progressDialog.dismiss();
                if (response.body() == null || response.body().getResult() == null) return;
                if (response.body().getResult().getRes_code() == 1) {
                    resDataBean = response.body().getResult().getRes_data();
                    if (resDataBean.getWaiting_data().size()==0 && resDataBean.getAble_to_data().size() == 0){
                        ToastUtils.showCommonToast(NewSaleListActivity.this, "没有找到相关数据");
                        finish();
                        return;
                    }
                    res_data = resDataBean.getWaiting_data();
                    allList = res_data;
                    listAdapte = new NewSaleListAdapte(R.layout.item_newsalelist, res_data);
                    recyclerSaleList.setAdapter(listAdapte);
                    initListner();
                    searchListener();
                    initRadio();
                }
            }

            @Override
            public void onFailure(Call<NewSaleListBean> call, Throwable t) {
                progressDialog.dismiss();
                ToastUtils.showCommonToast(NewSaleListActivity.this, t.toString());
                MyLog.e("NewSaleListActivity", t.toString());
            }
        });
    }

    /**
     * 监测radiobutton
     * */
    private void initRadio(){
        parentRadioDan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.wait_radio_dan) {
                    if (resDataBean!=null)
                    res_data = resDataBean.getWaiting_data();
                } else if (checkedRadioButtonId == R.id.can_radio_dan) {
                    if (resDataBean!=null)
                    res_data = resDataBean.getAble_to_data();
                }
                allList = res_data;
                listAdapte.setNewData(res_data);
                listAdapte.notifyDataSetChanged();
            }
        });
    }

    @OnClick(R.id.finish_this)
    void finishThis(View view){
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!StringUtils.isNullOrEmpty(isUpdate)){
            if ("fistPage".equals(from)){
                getSearch(danhao);
                waitRadioDan.setChecked(true);
            }else {
                getData();
                waitRadioDan.setChecked(true);
            }
        }
    }

    private void getData() {
        progressDialog.show();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("partner_id", partner_id);
        Call<NewSaleListBean> bypartner = inventoryApi.getBypartner(hashMap);
        bypartner.enqueue(new MyCallback<NewSaleListBean>() {
            @Override
            public void onResponse(Call<NewSaleListBean> call, Response<NewSaleListBean> response) {
                progressDialog.dismiss();
                if (response.body() == null || response.body().getResult() == null) return;
                resDataBean = response.body().getResult().getRes_data();
                res_data = resDataBean.getWaiting_data();
                allList = res_data;
                listAdapte = new NewSaleListAdapte(R.layout.item_newsalelist, res_data);
                recyclerSaleList.setAdapter(listAdapte);
                initListner();
                searchListener();
                initRadio();
            }

            @Override
            public void onFailure(Call<NewSaleListBean> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("zws", t.toString());
            }
        });
    }

    private void searchListener() {
        searchNewsalelist.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fillData(newText);
                return false;
            }
        });
    }

    //筛选数据搜索
    private void fillData(String newText) {
        if (StringUtils.isNullOrEmpty(newText)) {
            res_data = allList;
        } else {
            List<NewSaleListBean.ResultBean.ResDataBean.AbleToDataBean> filterDateList = new ArrayList<>();
            for (NewSaleListBean.ResultBean.ResDataBean.AbleToDataBean bean : allList) {
                if (bean.getOrigin().contains(newText)) {
                    filterDateList.add(bean);
                }
            }
            res_data = filterDateList;
        }
        listAdapte.setNewData(res_data);
        listAdapte.notifyDataSetChanged();
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
                        if (response.body() == null || response.body().getResult() == null) return;
                        if (response.body().getResult().getRes_data() == null)return;
                        if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
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
        isUpdate = "update";
    }
}
