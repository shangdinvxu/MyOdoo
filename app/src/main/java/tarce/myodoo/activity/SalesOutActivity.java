package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.Serializable;
import java.util.ArrayList;
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
import tarce.model.GetSaleListResponse;
import tarce.model.SearchSupplierResponse;
import tarce.model.greendaoBean.ContactsBean;
import tarce.myodoo.MyApplication;
import tarce.myodoo.R;
import tarce.myodoo.adapter.CustomerAdapter;
import tarce.myodoo.adapter.SalesStatesAdapter;
import tarce.myodoo.greendaoUtils.ContactBeanUtils;
import tarce.support.MyLog;
import tarce.support.ToolBarActivity;

/**
 * Created by Daniel.Xu on 2017/4/20.
 */

public class SalesOutActivity extends ToolBarActivity {
    private final  static  String TAG = SalesOutActivity.class.getSimpleName();
    @InjectView(R.id.search_customer)
    SearchView searchCustomer;
    @InjectView(R.id.search_sales_number)
    SearchView searchSalesNumber;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.recyclerviewStates)
    RecyclerView recyclerviewStates;
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
        setRecyclerview(recyclerview);
        setRecyclerview(recyclerviewStates);
        initData();

    }

    private void initData() {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("groupby", "picking_type_id");
        objectObjectHashMap.put("model", "stock.picking");
        objectObjectHashMap.put("partner_id", 0);
        Call<GetGroupByListresponse> groupsByList = inventoryApi.getGroupsByList(objectObjectHashMap);
        groupsByList.enqueue(new Callback<GetGroupByListresponse>() {
            @Override
            public void onResponse(Call<GetGroupByListresponse> call, Response<GetGroupByListresponse> response) {
                String s = response.body().toString();
                MyLog.e(TAG,s);
                List<GetGroupByListresponse.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                for (GetGroupByListresponse.ResultBean.ResDataBean bean : res_data) {
                    if (bean.getPicking_type_code().equals("outgoing")) {
                        MyLog.e(TAG,"执行了的次数");
                        ArrayList<GetGroupByListresponse.ResultBean.ResDataBean.StatesBean> statesBeanList = new ArrayList<>();
                        List<GetGroupByListresponse.ResultBean.ResDataBean.StatesBean> states = bean.getStates();
                        for (GetGroupByListresponse.ResultBean.ResDataBean.StatesBean statesBean :states){
                            if (statesBean.getState().equals("partially_available")||statesBean.getState().equals("done")
                                    || statesBean.getState().equals("confirmed")||statesBean.getState().equals("assigned")){
                                statesBeanList.add(statesBean);
                            }
                        }
                        SalesStatesAdapter salesStatesAdapter = new SalesStatesAdapter(R.layout.salesout_adapter, statesBeanList);
                        initSalesStatesListener(salesStatesAdapter,statesBeanList);
                        recyclerviewStates.setAdapter(salesStatesAdapter);
                    }
                }

            }
            @Override
            public void onFailure(Call<GetGroupByListresponse> call, Throwable t) {
                MyLog.e(TAG,t.toString());
            }
        });


    }

    private void initSalesStatesListener(SalesStatesAdapter salesStatesAdapter,
                                         final ArrayList<GetGroupByListresponse.ResultBean.ResDataBean.StatesBean> statesBeanList) {
        salesStatesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("state",statesBeanList.get(position).getState());
                objectObjectHashMap.put("limit",80);
                objectObjectHashMap.put("offset",0);
                objectObjectHashMap.put("picking_type_code","outgoing");
                Call<GetSaleListResponse> inComingOutgoingList = inventoryApi.getInComingOutgoingList(objectObjectHashMap);
                inComingOutgoingList.enqueue(new Callback<GetSaleListResponse>() {
                    @Override
                    public void onResponse(Call<GetSaleListResponse> call, Response<GetSaleListResponse> response) {
                        List<GetSaleListResponse.TResult.TRes_data> res_data = response.body().getResult().getRes_data();
                        Intent intent = new Intent(SalesOutActivity.this, SalesListActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("bundle", (Serializable) res_data);
                        intent.putExtra("intent",bundle);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<GetSaleListResponse> call, Throwable t) {
                            MyLog.e(TAG,t.toString());
                    }
                });



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
        } else {
            recyclerview.setVisibility(View.GONE);
            initSearchCustomerFromNet(s);
        }
    }

    private void initSearchSalesListFromNet(final String name) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("order_name", name);
//        objectObjectHashMap.put("type", "supplier");
        Call<GetSaleListResponse> getSaleListByNumberResponseCall = inventoryApi.searchBySalesNumber(objectObjectHashMap);
        getSaleListByNumberResponseCall.enqueue(new Callback<GetSaleListResponse>() {
            @Override
            public void onResponse(Call<GetSaleListResponse> call, Response<GetSaleListResponse> response) {
                List<GetSaleListResponse.TResult.TRes_data> res_data = response.body().getResult().getRes_data();
                if (res_data != null && res_data.size() > 0) {
                    Intent intent = new Intent(SalesOutActivity.this, SalesListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bundle", (Serializable) res_data);
                    intent.putExtra("intent", bundle);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<GetSaleListResponse> call, Throwable t) {
                MyLog.e(TAG,t.toString());
            }
        });


    }


    private void showInRecyclerView(List<ContactsBean> contactsBeen) {
        recyclerview.setVisibility(View.VISIBLE);
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
