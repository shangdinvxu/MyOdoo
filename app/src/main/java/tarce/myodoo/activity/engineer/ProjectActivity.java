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
import tarce.model.GetSaleResponse;
import tarce.model.ProjectBean;
import tarce.model.inventory.DiyListBean;
import tarce.model.inventory.NewSaleListBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.activity.inspect.InspectMoDetailActivity;
import tarce.myodoo.activity.salesout.NewSaleListActivity;
import tarce.myodoo.activity.salesout.SalesDetailActivity;
import tarce.myodoo.adapter.projectpick.ProjectPickAdapter;
import tarce.myodoo.adapter.takedeliver.NewSaleListAdapte;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.StringUtils;
import tarce.support.ToastUtils;

/**
 * Created by zouzou on 2017/8/28.
 */

public class ProjectActivity extends BaseActivity {

    @InjectView(R.id.wait_radio)
    RadioButton waitRadio;
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
    private ProjectBean.ResultBean.ResDataBean resDataBean;
    private List<ProjectBean.ResultBean.ResDataBean.WaitingDataBean> res_data;
    private List<ProjectBean.ResultBean.ResDataBean.WaitingDataBean> allList = new ArrayList<>();
    private String isUpdate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(ProjectActivity.this).create(InventoryApi.class);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if (type.equals("pick_type")){
            setTitle("产线领用");
        }else {
            setTitle("工程领用");
        }
        setRecyclerview(recyclerProject);
        showDefultProgressDialog();
        initdata();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!StringUtils.isNullOrEmpty(isUpdate)){
            initdata();
            waitRadio.setChecked(true);
        }
    }

    //初始化数据
    private void initdata() {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("picking_type", type);
        Call<ProjectBean> bypartner = inventoryApi.getPickrequest(hashMap);
        bypartner.enqueue(new MyCallback<ProjectBean>() {
            @Override
            public void onResponse(Call<ProjectBean> call, Response<ProjectBean> response) {
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
                    allList = res_data;
                    projectPickAdapter = new ProjectPickAdapter(R.layout.item_project_pick, res_data);
                    recyclerProject.setAdapter(projectPickAdapter);
                    initListenerRadio();
                    initlistEdittext();
                    initListener();
                }
            }

            @Override
            public void onFailure(Call<ProjectBean> call, Throwable t) {
                dismissDefultProgressDialog();
                Log.e("zws", t.toString());
            }
        });
    }

    /**
     * edittext搜索效果
     * */
    private void initlistEdittext() {
        searchNum.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                fillData(s, 1);
                return false;
            }
        });
        searchName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                fillData(s, 2);
                return false;
            }
        });
    }
    //筛选数据搜索
    private void fillData(String newText, int type) {
        if (StringUtils.isNullOrEmpty(newText)) {
            res_data = allList;
        } else {
            List<ProjectBean.ResultBean.ResDataBean.WaitingDataBean> filterDateList = new ArrayList<>();
            for (ProjectBean.ResultBean.ResDataBean.WaitingDataBean bean : allList) {
                if (type == 1){
                    if (bean.getName().contains(newText)) {
                        filterDateList.add(bean);
                    }
                }else if (type == 2){
                    if (bean.getCreate_uid().contains(newText)) {
                        filterDateList.add(bean);
                    }
                }
            }
            res_data = filterDateList;
        }
        projectPickAdapter.setNewData(res_data);
        projectPickAdapter.notifyDataSetChanged();
    }

    //item点击事件
    private void initListener() {
        projectPickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ProjectActivity.this, ProjectDetailActivity.class);
                intent.putExtra("material_id", projectPickAdapter.getData().get(position).getId());
                intent.putExtra("name", projectPickAdapter.getData().get(position).getName());
                intent.putExtra("state", projectPickAdapter.getData().get(position).getPicking_state());
                if (type.equals("pick_type")){
                    intent.putExtra("type", "产线领用");
                }else {
                    intent.putExtra("type", "工程领用");
                }
                startActivity(intent);
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
                }else if (checkedRadioButtonId == R.id.done_radio){
                    if (resDataBean != null)
                        res_data = resDataBean.getFinish_data();
                    allList = res_data;
                    projectPickAdapter.setNewData(res_data);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        isUpdate = "update";
    }
}
