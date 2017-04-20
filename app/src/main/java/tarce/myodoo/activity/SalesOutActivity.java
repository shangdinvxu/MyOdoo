package tarce.myodoo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.SearchView;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import greendao.ContactsBeanDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.SearchSupplierResponse;
import tarce.model.greendaoBean.ContactsBean;
import tarce.myodoo.R;
import tarce.myodoo.greendaoUtils.GreenDaoManager;
import tarce.support.ToolBarActivity;

/**
 * Created by Daniel.Xu on 2017/4/20.
 */

public class SalesOutActivity extends ToolBarActivity {
    @InjectView(R.id.search_customer)
    SearchView searchCustomer;
    @InjectView(R.id.search_sales_number)
    SearchView searchSalesNumber;
    private InventoryApi inventoryApi;
    private ContactsBeanDao contactsBeanDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_out);
        ButterKnife.inject(this);
        initListener();
        inventoryApi = RetrofitClient.getInstance(SalesOutActivity.this).create(InventoryApi.class);
        contactsBeanDao = GreenDaoManager.getInstance().getmDaoSession().getContactsBeanDao();
        setTitle("销售出库");
    }

    private void initListener() {
        searchCustomer.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                initSearchCustomer(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void initSearchCustomer(String s) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name", null);
        // type: ‘supplier’ or ‘customer’
        objectObjectHashMap.put("type", "supplier");
        Call<SearchSupplierResponse> stringCall = inventoryApi.searchSupplier(objectObjectHashMap);
        stringCall.enqueue(new Callback<SearchSupplierResponse>() {
            @Override
            public void onResponse(Call<SearchSupplierResponse> call, Response<SearchSupplierResponse> response) {
                if (response.body().getResult() != null) {
                    List<SearchSupplierResponse.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                    if (res_data != null && res_data.size() > 0) {
                        for (SearchSupplierResponse.ResultBean.ResDataBean resDataBean : res_data) {
                            contactsBeanDao.insertOrReplace(new ContactsBean(resDataBean.getComment(), resDataBean.getPhone()
                                    , resDataBean.getPartner_id(), resDataBean.getName(), resDataBean.getX_qq()));
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<SearchSupplierResponse> call, Throwable t) {
            }
        });

    }
}
