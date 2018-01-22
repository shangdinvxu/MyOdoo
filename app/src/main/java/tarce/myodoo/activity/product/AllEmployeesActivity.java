package tarce.myodoo.activity.product;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tarce.api.OKHttpFactory;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.AllEmployeeBean;
import tarce.model.TimeSheetBean;
import tarce.model.inventory.CommonBean;
import tarce.model.inventory.NFCUserBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.activity.NFCReadingActivity;
import tarce.myodoo.adapter.product.AllEmployeeAdapter;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.StringUtils;
import tarce.support.AlertAialogUtils;
import tarce.support.ToastUtils;

import static android.R.attr.data;

/**
 * Created by zouwansheng on 2018/1/16.
 */

public class AllEmployeesActivity extends BaseActivity {
    @InjectView(R.id.search_name)
    SearchView searchName;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.btn_true)
    Button btnTrue;
    private Retrofit retrofit;
    private InventoryApi inventoryApi;
    private AllEmployeeAdapter employeeAdapter;
    private double user_id;
    private int picking_id;
    private String user_name;
    private Retrofit retrofit2;
    private List<AllEmployeeBean.ResultBean.ResDataBean> res_data = new ArrayList<>();
    private List<AllEmployeeBean.ResultBean.ResDataBean> allList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_reading);
        ButterKnife.inject(this);

        setRecyclerview(recyclerview);
        btnTrue.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        //searchName.clearFocus();
        picking_id = intent.getIntExtra("picking_id", 1);
        initData();
    }

    private void initData() {
        retrofit = new Retrofit.Builder()
                .client(new OKHttpFactory(AllEmployeesActivity.this).getOkHttpClient())
                .baseUrl(RetrofitClient.Url + "/linkloving_oa_api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        inventoryApi = retrofit.create(InventoryApi.class);
        showDefultProgressDialog();
        final Call<AllEmployeeBean> allEmployees = inventoryApi.getAllEmployees(new HashMap());
        allEmployees.enqueue(new Callback<AllEmployeeBean>() {
            @Override
            public void onResponse(Call<AllEmployeeBean> call, Response<AllEmployeeBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError() != null) {
                    new TipDialog(AllEmployeesActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                    res_data = response.body().getResult().getRes_data();
                    allList = res_data;
                    employeeAdapter = new AllEmployeeAdapter(R.layout.adapter_nfcread_detail, res_data);
                    recyclerview.setAdapter(employeeAdapter);
                    initListner();
                    setSearchName();
                }
            }

            @Override
            public void onFailure(Call<AllEmployeeBean> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
    }

    private void initListner() {
        employeeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<AllEmployeeBean.ResultBean.ResDataBean> data = employeeAdapter.getData();
                data.get(position).setPosition(position);
                employeeAdapter.notifyDataSetChanged();
                user_id = (double) data.get(position).getPartner_id();
                user_name = data.get(position).getName();
                if (user_id==0){
                    new TipDialog(AllEmployeesActivity.this, R.style.MyDialogStyle, "该用户没有partner_id,请重新选择")
                            .show();
                    return;
                }
                ToastUtils.showCommonToast(AllEmployeesActivity.this, "选择:"+user_name);
            }
        });
    }

    @OnClick(R.id.btn_true)
    void setBtnTrue(View view){
        AlertAialogUtils.getCommonDialog(AllEmployeesActivity.this, "提示")
                .setMessage("分配负责人后将开始二次加工")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        retrofit2 = new Retrofit.Builder()
                                .client(new OKHttpFactory(AllEmployeesActivity.this).getOkHttpClient())
                                .baseUrl(RetrofitClient.Url + "/linkloving_timesheets/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                .build();
                        inventoryApi = retrofit2.create(InventoryApi.class);
                        showDefultProgressDialog();
                        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                        if (user_id==0){
                            ToastUtils.showCommonToast(AllEmployeesActivity.this, "选择人员未有partner_id, 请重新选择");
                            return;
                        }
                        hashMap.put("to_partner", user_id);
                        hashMap.put("picking_id", picking_id);
                        Call<TimeSheetBean> objectCall = inventoryApi.action_partner(hashMap);
                        objectCall.enqueue(new Callback<TimeSheetBean>() {
                            @Override
                            public void onResponse(Call<TimeSheetBean> call, Response<TimeSheetBean> response) {
                                dismissDefultProgressDialog();
                                if (response.body()==null)return;
                                if (response.body().getError()!=null){
                                    new TipDialog(AllEmployeesActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                            .show();
                                    return;
                                }
                                if (response.body().getResult().getRes_data()!=null && response.body().getResult().getRes_code()==1){
                                    TimeSheetBean.ResultBean.ResDataBean res_data = response.body().getResult().getRes_data().get(0);
                                    Intent intent = new Intent();
                                    intent.putExtra("data", res_data);
//                                    intent.putExtra("to_partner", res_data.getTo_partner());
//                                    intent.putExtra("from_partner", res_data.getFrom_partner());
                                    AllEmployeesActivity.this.setResult(1, intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<TimeSheetBean> call, Throwable t) {
                                dismissDefultProgressDialog();
                            }
                        });
                    }
                }).show();
    }

    private void setSearchName() {
        searchName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                ToastUtils.showCommonToast(AllEmployeesActivity.this, "可以了？");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (StringUtils.isNullOrEmpty(s)){
                    res_data = allList;
                }
                ArrayList<AllEmployeeBean.ResultBean.ResDataBean> searchList = new ArrayList<>();
                for (AllEmployeeBean.ResultBean.ResDataBean bean : allList) {
                    if (bean.getName().contains(s)) {
                        searchList.add(bean);
                    }
                }
                res_data = searchList;
                employeeAdapter.setNewData(res_data);
                employeeAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }
}
