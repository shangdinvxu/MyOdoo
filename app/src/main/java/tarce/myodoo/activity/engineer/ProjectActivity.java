package tarce.myodoo.activity.engineer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.GetSaleResponse;
import tarce.model.inventory.NewSaleListBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.activity.inspect.InspectMoDetailActivity;
import tarce.myodoo.activity.salesout.NewSaleListActivity;
import tarce.myodoo.activity.salesout.SalesDetailActivity;
import tarce.myodoo.adapter.projectpick.ProjectPickAdapter;
import tarce.myodoo.adapter.takedeliver.NewSaleListAdapte;
import tarce.myodoo.uiutil.TipDialog;
import tarce.support.ToastUtils;

/**
 * Created by zouzou on 2017/8/28.
 */

public class ProjectActivity extends BaseActivity {

    @InjectView(R.id.wait_radio)
    RadioButton waitRadio;
    @InjectView(R.id.can_radio)
    RadioButton canRadio;
    @InjectView(R.id.done_radio)
    RadioButton doneRadio;
    @InjectView(R.id.radioproject)
    RadioGroup radioproject;
    @InjectView(R.id.search_name)
    SearchView searchName;
    @InjectView(R.id.search_num)
    SearchView searchNum;
    @InjectView(R.id.recycler_project)
    RecyclerView recyclerProject;
    private String type;
    private InventoryApi inventoryApi;
    private ProjectPickAdapter projectPickAdapter;
    private NewSaleListBean.ResultBean.ResDataBean resDataBean;
    private List<NewSaleListBean.ResultBean.ResDataBean.AbleToDataBean> res_data;
    private List<NewSaleListBean.ResultBean.ResDataBean.AbleToDataBean> allList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        ButterKnife.inject(this);
        setTitle("物流发料");

        inventoryApi = RetrofitClient.getInstance(ProjectActivity.this).create(InventoryApi.class);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        setRecyclerview(recyclerProject);
        initdata();
    }

    //初始化数据
    private void initdata() {
        showDefultProgressDialog();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("picking_type", type);
        Call<NewSaleListBean> bypartner = inventoryApi.getPickrequest(hashMap);
        bypartner.enqueue(new MyCallback<NewSaleListBean>() {
            @Override
            public void onResponse(Call<NewSaleListBean> call, Response<NewSaleListBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError()!=null){
                    new TipDialog(ProjectActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_data()!=null && response.body().getResult().getRes_code() == 1){
                    resDataBean = response.body().getResult().getRes_data();
                    res_data = resDataBean.getWaiting_data();
                    projectPickAdapter = new ProjectPickAdapter(R.layout.item_project_pick, res_data);
                    recyclerProject.setAdapter(projectPickAdapter);
                    initListenerRadio();
                    initListener();
                }
            }

            @Override
            public void onFailure(Call<NewSaleListBean> call, Throwable t) {
                dismissDefultProgressDialog();
                Log.e("zws", t.toString());
            }
        });
    }

    //item点击事件
    private void initListener() {
        projectPickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showDefultProgressDialog();
                HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("picking_id", projectPickAdapter.getData().get(position).getPicking_id());
                Call<GetSaleResponse> stringCall = inventoryApi.checkIsCanUse(objectObjectHashMap);
                stringCall.enqueue(new MyCallback<GetSaleResponse>() {
                    @Override
                    public void onResponse(Call<GetSaleResponse> call, Response<GetSaleResponse> response) {
                        dismissDefultProgressDialog();
                        if (response.body() == null) return;
                        if (response.body().getError()!=null){
                            new TipDialog(ProjectActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                    .show();
                            return;
                        }
                        if (response.body().getResult().getRes_data() == null) return;
                        if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                            GetSaleResponse.TResult result = response.body().getResult();
                            Intent intent = new Intent(ProjectActivity.this, ProjectDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("bundle", result.getRes_data());
                            intent.putExtra("intent", bundle);
                            startActivity(intent);
                        } else {
                            ToastUtils.showCommonToast(ProjectActivity.this, "加载失败，请稍后重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<GetSaleResponse> call, Throwable t) {
                        dismissDefultProgressDialog();
                        ToastUtils.showCommonToast(ProjectActivity.this, t.toString());
                        Log.e("zws", t.toString());
                    }
                });
            }
        });
    }

    //radiobutton的监听
    private void initListenerRadio() {
        radioproject.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.wait_radio){
                    if (resDataBean != null)
                    res_data = resDataBean.getWaiting_data();
                    allList = res_data;
                    projectPickAdapter.setNewData(res_data);
                }else if (checkedRadioButtonId == R.id.can_radio){
                    if (resDataBean != null)
                        res_data = resDataBean.getAble_to_data();
                    allList = res_data;
                    projectPickAdapter.setNewData(res_data);
                }else if (checkedRadioButtonId == R.id.done_radio){

                }
            }
        });
    }
}
