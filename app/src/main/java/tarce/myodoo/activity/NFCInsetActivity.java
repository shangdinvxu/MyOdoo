package tarce.myodoo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zaihuishou.expandablerecycleradapter.adapter.BaseExpandableAdapter;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractAdapterItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tarce.api.MyCallback;
import tarce.api.OKHttpFactory;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.inventory.BomFramworkBean;
import tarce.model.inventory.BomSubBean;
import tarce.model.inventory.NFCWorkerBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.ComponyOne.BottomThree;
import tarce.myodoo.activity.ComponyOne.ComponyOne;
import tarce.myodoo.activity.ComponyOne.EmployeeTwo;
import tarce.myodoo.adapter.expand.CompanyItem;
import tarce.myodoo.adapter.expand.DepartmentItem;
import tarce.myodoo.adapter.expand.EmployeeItem;
import tarce.myodoo.adapter.expand.LastItem;
import tarce.myodoo.adapter.expand.SixItem;
import tarce.myodoo.adapter.expand.WorkerItem;
import tarce.myodoo.adapter.takedeliver.WorkerAllAdapter;
import tarce.support.MyLog;
import tarce.support.ToastUtils;

/**
 * Created by zouzou on 2017/6/28.
 * NFC录入页面
 */

public class NFCInsetActivity extends BaseActivity {
    @InjectView(R.id.recycler_insert_nfc)
    RecyclerView recyclerInsertNfc;
    private InventoryApi inventoryApi;
    private Retrofit retrofit;
    private List<NFCWorkerBean.ResultBean.ResDataBean> res_data;
    private List<Object> mCompanylist;
    private final int ITEM_TYPE_COMPANY = 1;
    private final int ITEM_TYPE_DEPARTMENT = 2;
    private final int ITEM_TYPE_SIX = 3;
    private BaseExpandableAdapter adapter;
    private NFCWorkerBean.ResultBean company;
    private boolean isHaveHeader = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_insert);
        ButterKnife.inject(this);
        setRecyclerview(recyclerInsertNfc);
        setTitle("员工NFC录入");

        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(new OKHttpFactory(NFCInsetActivity.this).getOkHttpClient())

                //baseUrl
//                .baseUrl("http://192.168.2.111:8069/linkloving_app_api/")
                .baseUrl(RetrofitClient.Url+"/linkloving_user_auth/")
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        inventoryApi = retrofit.create(InventoryApi.class);

        initData();
    }

    /**
     * 初始化数据
     * */
    private void initData() {
        //HashMap<Object, Object> hashMap = new HashMap<>();
       // hashMap.put("parent_id",parent_id);
        Call<NFCWorkerBean> depart = inventoryApi.getDepart(new HashMap());
        depart.enqueue(new MyCallback<NFCWorkerBean>() {
            @Override
            public void onResponse(Call<NFCWorkerBean> call, Response<NFCWorkerBean> response) {
                if (response.body() == null) return;
                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1){
                    final NFCWorkerBean.ResultBean result = response.body().getResult();
                    mCompanylist = new ArrayList<>();
                    company = createCompany(result, false);
                    mCompanylist.add(company);
                    adapter = new BaseExpandableAdapter(mCompanylist){

                        @Override
                        public AbstractAdapterItem<Object> getItemView(Object type) {
                            int itemType = (int) type;
                            switch (itemType) {
                                case ITEM_TYPE_COMPANY:
                                    return new ComponyOne(isHaveHeader);
                                case ITEM_TYPE_DEPARTMENT:
                                    return new EmployeeTwo();
                                case ITEM_TYPE_SIX:
                                    return new BottomThree();
                            }
                            return null;
                        }

                        @Override
                        public Object getItemViewType(Object t){
                            if (t instanceof NFCWorkerBean.ResultBean)
                                return ITEM_TYPE_COMPANY;
                             else if (t instanceof NFCWorkerBean.ResultBean.ResDataBean)
                                return ITEM_TYPE_DEPARTMENT;
                             else if (t instanceof NFCWorkerBean.ResultBean.ResDataBean.EmployeesBean)
                                return ITEM_TYPE_SIX;
                            return -1;
                        }
                    };
                    recyclerInsertNfc.setAdapter(adapter);
                    initListener(result);
                }
            }
            @Override
            public void onFailure(Call<NFCWorkerBean> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(NFCInsetActivity.this, t.toString());
            }
        });
    }

    //item点击事件
    private void initListener(final NFCWorkerBean.ResultBean resultBean){
        adapter.setExpandCollapseListener(new BaseExpandableAdapter.ExpandCollapseListener() {
            @Override
            public void onListItemExpanded(final int position) {
                    ToastUtils.showCommonToast(NFCInsetActivity.this, "position = "+position);
                    if (position == 0)return;
                    HashMap<Object, Object> hashMap = new HashMap<>();
                    hashMap.put("parent_id", resultBean.getRes_data().get(position-1).getDepartment_id());
                    Log.i("zouwansheng",resultBean.getRes_data().get(position-1).getDepartment_id()+""+resultBean.getRes_data().get(position-1));
                    Call<NFCWorkerBean> depart1 = inventoryApi.getDepart(hashMap);
                    MyLog.e("TEST",adapter.getDataList()+"");
                    depart1.enqueue(new MyCallback<NFCWorkerBean>() {
                        @Override
                        public void onResponse(Call<NFCWorkerBean> call, Response<NFCWorkerBean> response) {
                            if (response.body()==null)return;
                            if (response.body().getResult().getRes_data()!=null && response.body().getResult().getRes_code()==1){
                                // mCompanylist.set(0,response.body().getResult());
//                                mCompanylist.set(position, createCompany(response.body().getResult(), true));
                                isHaveHeader = true;
                                company.setRes_data(response.body().getResult().getRes_data());
                            //    company.getRes_data().add(position,response.body().getResult().getRes_data().get(position));
                                mCompanylist.add(1,company);
                                adapter.updateData(mCompanylist);
                                initListener(response.body().getResult());
                            }
                        }

                        @Override
                        public void onFailure(Call<NFCWorkerBean> call, Throwable t) {
                            super.onFailure(call, t);
                        }
                    });
            }

            @Override
            public void onListItemCollapsed(int position) {
                /*if (position == 0){
                    adapter.collapseAllParents();
                };*/
            }
        });
    }
    private NFCWorkerBean.ResultBean createCompany(NFCWorkerBean.ResultBean result, boolean isExpandDefault) {
        NFCWorkerBean.ResultBean firstCompany = new NFCWorkerBean.ResultBean();
       // firstCompany.res_data = result.getRes_data();
        List<NFCWorkerBean.ResultBean.ResDataBean> departments = new ArrayList<>();
        for (int i = 0; i < result.getRes_data().size(); i++) {
            NFCWorkerBean.ResultBean.ResDataBean department = new NFCWorkerBean.ResultBean.ResDataBean();
            NFCWorkerBean.ResultBean.ResDataBean bomIdsBeanX = result.getRes_data().get(i);
            department.employees = bomIdsBeanX.getEmployees();
            department.name = bomIdsBeanX.getName();
            department.department_id = bomIdsBeanX.getDepartment_id();
            department.parent_id = bomIdsBeanX.getParent_id();
            if (bomIdsBeanX.getEmployees().size() > 0) {
                department.setExpanded(false);
                List<NFCWorkerBean.ResultBean.ResDataBean.EmployeesBean> employeeList = new ArrayList<>();
                for (int j = 0; j < bomIdsBeanX.getEmployees().size(); j++) {
                    NFCWorkerBean.ResultBean.ResDataBean.EmployeesBean employee = new NFCWorkerBean.ResultBean.ResDataBean.EmployeesBean();
                    NFCWorkerBean.ResultBean.ResDataBean.EmployeesBean bomIdsBean = bomIdsBeanX.getEmployees().get(j);
                    employee.name = bomIdsBean.getName();
                    employee.employee_id = bomIdsBean.getEmployee_id();
                    employee.work_email = employee.getWork_email();
                    employeeList.add(employee);
                }
                department.employees = employeeList;
            }
            departments.add(department);
        }
        firstCompany.res_data = departments;
        firstCompany.mExpanded = isExpandDefault;
        return firstCompany;
    }
}
