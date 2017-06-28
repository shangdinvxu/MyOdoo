package tarce.myodoo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zaihuishou.expandablerecycleradapter.adapter.BaseExpandableAdapter;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractAdapterItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import tarce.myodoo.adapter.expand.CompanyItem;
import tarce.myodoo.adapter.expand.DepartmentItem;
import tarce.myodoo.adapter.expand.EmployeeItem;
import tarce.myodoo.adapter.expand.LastItem;
import tarce.myodoo.adapter.expand.SixItem;
import tarce.myodoo.adapter.expand.WorkerItem;
import tarce.myodoo.adapter.takedeliver.WorkerAllAdapter;

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
    private final int ITEM_TYPE_SIX = 6;
    private BaseExpandableAdapter adapter;


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
                    NFCWorkerBean.ResultBean result = response.body().getResult();
                    mCompanylist = new ArrayList<>();
                    mCompanylist.add(createCompany(result, false));
                    adapter = new BaseExpandableAdapter(mCompanylist){

                        @Override
                        public AbstractAdapterItem<Object> getItemView(Object type) {
                            int itemType = (int) type;
                            switch (itemType) {
                                case ITEM_TYPE_COMPANY:
                                    return new CompanyItem();
                                case ITEM_TYPE_SIX:
                                    return new SixItem();
                            }
                            return null;
                        }

                        @Override
                        public Object getItemViewType(Object t){
                            if (t instanceof NFCWorkerBean.ResultBean) {
                                return ITEM_TYPE_COMPANY;
                            } else if (t instanceof NFCWorkerBean.ResultBean.ResDataBean) {
                                return ITEM_TYPE_SIX;
                            }
                            return -1;
                        }
                    };
                    recyclerInsertNfc.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<NFCWorkerBean> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    //item点击事件
    private void initListener() {

    }
    private NFCWorkerBean.ResultBean createCompany(NFCWorkerBean.ResultBean result, boolean isExpandDefault) {
        NFCWorkerBean.ResultBean firstCompany = new NFCWorkerBean.ResultBean();
        firstCompany.res_data = result.getRes_data();
        List<NFCWorkerBean.ResultBean.ResDataBean> departments = new ArrayList<>();
        for (int i = 0; i < result.getRes_data().size(); i++){
            NFCWorkerBean.ResultBean.ResDataBean department = new NFCWorkerBean.ResultBean.ResDataBean();
            NFCWorkerBean.ResultBean.ResDataBean bomIdsBeanX = result.getRes_data().get(i);
            department.employees = bomIdsBeanX.getEmployees();
            department.name = bomIdsBeanX.getName();
            department.department_id = bomIdsBeanX.getDepartment_id();
            department.parent_id = bomIdsBeanX.getParent_id();
            if (bomIdsBeanX.getBom_ids().size()>0) {
                department.setExpanded(false);
                List<BomFramworkBean.ResultBean.ResDataBean.BomIdsBeanX.BomIdsBean> employeeList = new ArrayList<>();
                for (int j = 0; j < bomIdsBeanX.getBom_ids().size(); j++) {
                    BomFramworkBean.ResultBean.ResDataBean.BomIdsBeanX.BomIdsBean employee = new BomFramworkBean.ResultBean.ResDataBean.BomIdsBeanX.BomIdsBean();
                    BomFramworkBean.ResultBean.ResDataBean.BomIdsBeanX.BomIdsBean bomIdsBean = bomIdsBeanX.getBom_ids().get(j);
                    employee.name = bomIdsBean.getName();
                    employee.code = bomIdsBean.getCode();
                    employee.product_specs = bomIdsBean.getProduct_specs();
                    employee.process_id = bomIdsBean.getProcess_id();
                    if (bomIdsBean.getBom_ids().size()>0){
                        employee.setExpanded(false);
                        List<BomSubBean> lastList = new ArrayList<>();
                        for (int k = 0; k < bomIdsBean.getBom_ids().size(); k++){
                            BomSubBean last = new BomSubBean();
                            BomSubBean bomSubBean = bomIdsBean.getBom_ids().get(k);
                            last.name = bomSubBean.getName();
                            last.code = bomSubBean.getCode();
                            last.product_specs = bomSubBean.getProduct_specs();
                            last.process_id = bomSubBean.getProcess_id();
                            if (bomSubBean.getBom_ids().size()>0){
                                last.setExpanded(false);
                                List<BomSubBean.BomBottomBean> bottomBeanList = new ArrayList<>();
                                for (int l = 0; l < bomSubBean.getBom_ids().size(); l++) {
                                    BomSubBean.BomBottomBean bottomBean = new BomSubBean.BomBottomBean();
                                    BomSubBean.BomBottomBean bottomBean1 = bomSubBean.getBom_ids().get(l);
                                    bottomBean.name = bottomBean1.getName();
                                    bottomBean.code = bottomBean1.getCode();
                                    bottomBean.product_specs = bottomBean1.getProduct_specs();
                                    bottomBean.process_id = bottomBean1.getProcess_id();
                                    if (bottomBean1.getBom_ids().size()>0){
                                        bottomBean.setExpanded(false);
                                        List<BomSubBean.BomBottomBean.SixBomBottomBean> sixBomBottomBeen = new ArrayList<>();
                                        for (int m = 0; m < bottomBean1.getBom_ids().size(); m++) {
                                            BomSubBean.BomBottomBean.SixBomBottomBean bean = new BomSubBean.BomBottomBean.SixBomBottomBean();
                                            BomSubBean.BomBottomBean.SixBomBottomBean bean1 = bottomBean1.getBom_ids().get(m);
                                            bean.name = bean1.getName();
                                            bean.code = bean1.getCode();
                                            bean.product_specs = bean1.getProduct_specs();
                                            bean.process_id = bean1.getProcess_id();
                                            sixBomBottomBeen.add(bean);
                                        }
                                        bottomBean.bom_ids = sixBomBottomBeen;
                                    }
                                    bottomBeanList.add(bottomBean);
                                }
                                last.bom_ids = bottomBeanList;
                            }
                            lastList.add(last);
                        }
                        employee.bom_ids = lastList;
                    }
                    employeeList.add(employee);
                }
                department.bom_ids = employeeList;
            }
            departments.add(department);
        }
        firstCompany.bom_ids = departments;
        firstCompany.mExpanded = isExpandDefault;
        return firstCompany;
    }
}
