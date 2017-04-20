package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;

import java.io.Serializable;
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
import tarce.model.GetGroupByListresponse;
import tarce.model.GetSaleListByNumberResponse;
import tarce.model.SearchSupplierResponse;
import tarce.model.greendaoBean.ContactsBean;
import tarce.myodoo.MyApplication;
import tarce.myodoo.R;
import tarce.myodoo.adapter.CustomerAdapter;
import tarce.myodoo.greendaoUtils.ContactBeanUtils;
import tarce.support.ToolBarActivity;

/**
 * Created by Daniel.Xu on 2017/4/20.
 */

public class SalesOutActivity extends ToolBarActivity {
    @InjectView(R.id.search_customer)
    SearchView searchCustomer;
    @InjectView(R.id.search_sales_number)
    SearchView searchSalesNumber;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    private InventoryApi inventoryApi;
    private ContactsBeanDao contactsBeanDao;
    private ContactBeanUtils contactBeanUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_out);
        ButterKnife.inject(this);
        initListener();
        inventoryApi = RetrofitClient.getInstance(SalesOutActivity.this).create(InventoryApi.class);
        contactsBeanDao = MyApplication.getInstances().getDaoSession().getContactsBeanDao();
        contactBeanUtils = new ContactBeanUtils();
        setTitle("销售出库");
        makeDefultRecycler();
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(dividerItemDecoration);
        initData();

    }

    private void initData() {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("groupby","picking_type_id");
        objectObjectHashMap.put("model","stock.picking");
        objectObjectHashMap.put("partner_id",0);
        Call<GetGroupByListresponse> groupsByList = inventoryApi.getGroupsByList(objectObjectHashMap);
        groupsByList.enqueue(new Callback<GetGroupByListresponse>() {
            @Override
            public void onResponse(Call<GetGroupByListresponse> call, Response<GetGroupByListresponse> response) {
                List<GetGroupByListresponse.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                for (GetGroupByListresponse.ResultBean.ResDataBean bean :res_data){
                    if (bean.getPicking_type_code().equals("outgoing")){
                        List<GetGroupByListresponse.ResultBean.ResDataBean.StatesBean> states = bean.getStates();
                        for (GetGroupByListresponse.ResultBean.ResDataBean.StatesBean statesBean :states){
//                            可用
                            if (statesBean.getState().equals("assigned")){

//                                等待可用
                            }else if (statesBean.getState().equals("confirmed")){

                            }else if (statesBean.getState().equals("done")){

                            }else if (statesBean.getState().equals("assigned")){

                            }
                        }
                    };
                }
            }

            @Override
            public void onFailure(Call<GetGroupByListresponse> call, Throwable t) {

            }
        });



    }

    private void initListener() {
        /**客户简称*/
        searchCustomer.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                initSearchFormDB(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        /**订单号*/
        searchSalesNumber.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                initSearchSalesListFromNet(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void initSearchFormDB(String s) {

        List<ContactsBean> contactsBeen = contactBeanUtils.searchByName(s);
        if (contactsBeen != null && contactsBeen.size() > 0) {
            showInRecyclerView(contactsBeen);
        }else {
            initSearchCustomerFromNet(s);
        }
    }
    private void initSearchSalesListFromNet(final String name) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("order_name", name);
//        objectObjectHashMap.put("type", "supplier");
        Call<GetSaleListByNumberResponse> getSaleListByNumberResponseCall = inventoryApi.searchBySalesNumber(objectObjectHashMap);
        getSaleListByNumberResponseCall.enqueue(new Callback<GetSaleListByNumberResponse>() {
            @Override
            public void onResponse(Call<GetSaleListByNumberResponse> call, Response<GetSaleListByNumberResponse> response) {
                List<GetSaleListByNumberResponse.TResult.TRes_data> res_data = response.body().getResult().getRes_data();
                if (res_data!=null&&res_data.size()>0){
                    Intent intent = new Intent(SalesOutActivity.this, SalesListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bundle", (Serializable) res_data);
                    intent.putExtra("intent",bundle);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<GetSaleListByNumberResponse> call, Throwable t) {

            }
        });


    }


    private void showInRecyclerView(List<ContactsBean> contactsBeen) {
        CustomerAdapter customerAdapter = new CustomerAdapter(R.layout.customername_textview, contactsBeen);
        recyclerview.setAdapter(customerAdapter);
    }


    private void initSearchCustomerFromNet(final String name) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name", name);
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
                        showInRecyclerView(contactBeanUtils.searchByName(name));
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchSupplierResponse> call, Throwable t) {
            }
        });
    }
}
