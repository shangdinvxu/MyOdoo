package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.zaihuishou.expandablerecycleradapter.adapter.BaseExpandableAdapter;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractAdapterItem;

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
import tarce.model.inventory.BomFramworkBean;
import tarce.model.inventory.BomSubBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.expand.CompanyItem;
import tarce.myodoo.adapter.expand.DepartmentItem;
import tarce.myodoo.adapter.expand.EmployeeItem;
import tarce.myodoo.adapter.expand.LastItem;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/6/6.
 * Bom结构表
 */

public class BomFramworkActivity extends ToolBarActivity {
    @InjectView(R.id.recycler_rom_framwork)
    RecyclerView recyclerRomFramwork;
    private InventoryApi inventoryApi;
    private int order_id;
    private BaseExpandableAdapter adapter;
    private final int ITEM_TYPE_COMPANY = 1;
    private final int ITEM_TYPE_DEPARTMENT = 2;
    private final int ITEM_TYPE_EMPLOYEE = 3;
    private final int ITEM_TYPE_LAST = 4;
    private List<Object> mCompanylist;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bom_framwork);
        ButterKnife.inject(this);

        setRecyclerview(recyclerRomFramwork);
        setTitle("查看BOM结构");

        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 1);
        getData();
    }


    private void getData() {
        inventoryApi = RetrofitClient.getInstance(BomFramworkActivity.this).create(InventoryApi.class);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("order_id", order_id);
        Call<BomFramworkBean> bomDetail = inventoryApi.getBomDetail(hashMap);
        bomDetail.enqueue(new MyCallback<BomFramworkBean>(){
            @Override
            public void onResponse(Call<BomFramworkBean> call, Response<BomFramworkBean> response) {
                if (response.body() == null)return;
                if (response.body().getResult().getRes_code() == 1){
                    BomFramworkBean.ResultBean.ResDataBean res_data = response.body().getResult().getRes_data();
                    mCompanylist = new ArrayList<>();
                    mCompanylist.add(createCompany(res_data, false));
                    adapter = new BaseExpandableAdapter(mCompanylist){

                        @Override
                        public AbstractAdapterItem<Object> getItemView(Object type) {
                            int itemType = (int) type;
                            switch (itemType) {
                                case ITEM_TYPE_COMPANY:
                                    return new CompanyItem();
                                case ITEM_TYPE_DEPARTMENT:
                                    return new DepartmentItem();
                                case ITEM_TYPE_EMPLOYEE:
                                    return new EmployeeItem();
                                case ITEM_TYPE_LAST:
                                    return new LastItem();
                            }
                            return null;
                        }

                        @Override
                        public Object getItemViewType(Object t){
                            if (t instanceof BomFramworkBean.ResultBean.ResDataBean) {
                                return ITEM_TYPE_COMPANY;
                            } else if (t instanceof BomFramworkBean.ResultBean.ResDataBean.BomIdsBeanX)
                                return ITEM_TYPE_DEPARTMENT;
                            else if (t instanceof BomFramworkBean.ResultBean.ResDataBean.BomIdsBeanX.BomIdsBean)
                                return ITEM_TYPE_EMPLOYEE;
                            else if (t instanceof BomSubBean)
                                return ITEM_TYPE_LAST;
                            return -1;
                        }
                    };
                    recyclerRomFramwork.setAdapter(adapter);
                }
            }
        });
    }

    private BomFramworkBean.ResultBean.ResDataBean createCompany(BomFramworkBean.ResultBean.ResDataBean resDataBean, boolean isExpandDefault) {
        BomFramworkBean.ResultBean.ResDataBean firstCompany = new BomFramworkBean.ResultBean.ResDataBean();
        firstCompany.name = resDataBean.getName();
        firstCompany.process_id = resDataBean.getProcess_id();
        List<BomFramworkBean.ResultBean.ResDataBean.BomIdsBeanX> departments = new ArrayList<>();
        for (int i = 0; i < resDataBean.getBom_ids().size(); i++){
            BomFramworkBean.ResultBean.ResDataBean.BomIdsBeanX department = new BomFramworkBean.ResultBean.ResDataBean.BomIdsBeanX();
            department.name = resDataBean.getBom_ids().get(i).getName();
            department.product_specs = resDataBean.getBom_ids().get(i).getProduct_specs();
            if (i == 0) {
                department.setExpanded(false);
                List<BomFramworkBean.ResultBean.ResDataBean.BomIdsBeanX.BomIdsBean> employeeList = new ArrayList<>();
                for (int j = 0; j < resDataBean.getBom_ids().get(i).getBom_ids().size(); j++) {
                    BomFramworkBean.ResultBean.ResDataBean.BomIdsBeanX.BomIdsBean employee = new BomFramworkBean.ResultBean.ResDataBean.BomIdsBeanX.BomIdsBean();
                    employee.name = resDataBean.getBom_ids().get(i).getBom_ids().get(j).getName();
                    if (j == 0){
                        employee.setExpanded(false);
                        List<BomSubBean> lastList = new ArrayList<>();
                        for (int k = 0; k < resDataBean.getBom_ids().get(i).getBom_ids().get(j).getBom_ids().size(); k++) {
                            BomSubBean last = new BomSubBean();
                            last.name = resDataBean.getBom_ids().get(i).getBom_ids().get(j).getBom_ids().get(k).getName();
                        }
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
