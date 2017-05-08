package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
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
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.api.dialogrequest.ProgressSubscriber;
import tarce.api.dialogrequest.SubscriberOnNextListener;
import tarce.model.GetGroupByListresponse;
import tarce.model.GetSaleListResponse;
import tarce.model.OutgoingStockpickingBean;
import tarce.model.SearchSupplierResponse;
import tarce.model.greendaoBean.ContactsBean;
import tarce.model.inventory.SalesOutListResponse;
import tarce.myodoo.MyApplication;
import tarce.myodoo.R;
import tarce.myodoo.adapter.CustomerAdapter;
import tarce.myodoo.adapter.SalesStatesAdapter;
import tarce.myodoo.bean.AvailabilityBean;
import tarce.myodoo.greendaoUtils.ContactBeanUtils;
import tarce.support.MyLog;
import tarce.support.ToolBarActivity;

/**
 * Created by Daniel.Xu on 2017/4/20.
 */

public class SalesOutActivity extends ToolBarActivity {
    private final static String TAG = SalesOutActivity.class.getSimpleName();
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
    private CustomerAdapter customerAdapter;

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
        objectObjectHashMap.put("partner_id", 0);
        Observable<OutgoingStockpickingBean> outgoingStockpicking = inventoryApi.getOutgoingStockpicking(objectObjectHashMap);
        outgoingStockpicking
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<>(new SubscriberOnNextListener<OutgoingStockpickingBean>() {
                    @Override
                    public void onNext(OutgoingStockpickingBean bean) {
                        ArrayList<AvailabilityBean> statesBeanList = new ArrayList<>();
                        List<OutgoingStockpickingBean.ResultBean.ResDataBean.CompleteRateBean> complete_rate = bean.getResult().getRes_data().getComplete_rate();
                        for (OutgoingStockpickingBean.ResultBean.ResDataBean.CompleteRateBean completeRateBean : complete_rate) {
                            statesBeanList.add(new AvailabilityBean(completeRateBean.getComplete_rate(),
                                    completeRateBean.getComplete_rate_count()));
                        }
                        OutgoingStockpickingBean.ResultBean.ResDataBean.StateBean state = bean.getResult().getRes_data().getState();
                        //完成的给的1000
                        statesBeanList.add(new AvailabilityBean(1000, state.getState_count()));
                        SalesStatesAdapter salesStatesAdapter = new SalesStatesAdapter(R.layout.salesout_adapter, statesBeanList);
                        recyclerviewStates.setAdapter(salesStatesAdapter);
                        initSalesStatesListener(salesStatesAdapter);
                    }
                }, SalesOutActivity.this));

    }

    private void initSalesStatesListener(final SalesStatesAdapter salesStatesAdapter) {
        salesStatesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                final List<AvailabilityBean> data = salesStatesAdapter.getData();
                String state = null;
                int complete_rate = 0 ;
                int per = data.get(position).getPer();
                switch (per){
                    case 100:
                        complete_rate = 100 ;
                        state = "";
                        break;
                    case 99:
                        complete_rate = 99 ;
                        state = "";
                        break;
                    case 0:
                        complete_rate = 0 ;
                        state = "";
                        break;
                    case 1000:
                        complete_rate = 0 ;
                        state = "done";
                        break;
                }
                objectObjectHashMap.put("complete_rate", complete_rate);
                objectObjectHashMap.put("limit", 80);
                objectObjectHashMap.put("offset", 0);
                objectObjectHashMap.put("state", state);
                Observable<SalesOutListResponse> inComingOutgoingList = inventoryApi.getOutgoingStockpickingList(objectObjectHashMap);
                final int finalComplete_rate = complete_rate;
                final String finalState = state;
                inComingOutgoingList.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ProgressSubscriber<>(new SubscriberOnNextListener<SalesOutListResponse>() {
                            @Override
                            public void onNext(SalesOutListResponse bean) {
                                List<SalesOutListResponse.TResult.TRes_data> res_data = bean.getResult().getRes_data();
                                Intent intent = new Intent(SalesOutActivity.this, SalesListActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("count",data.get(position).getNumber());
                                bundle.putSerializable("bundle", (Serializable) res_data);
                                bundle.putString("state",finalState);
                                bundle.putInt("complete_rate", finalComplete_rate);
                                intent.putExtra("intent", bundle);
                                startActivity(intent);
                            }
                        },SalesOutActivity.this));
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
                if (s == null || s.length() == 0) {
                    if (customerAdapter != null) {
                        customerAdapter.getData().clear();
                        recyclerview.setVisibility(View.GONE);
                    }
                }

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
        showDefultProgressDialog();
        getSaleListByNumberResponseCall.enqueue(new Callback<GetSaleListResponse>() {
            @Override
            public void onResponse(Call<GetSaleListResponse> call, Response<GetSaleListResponse> response) {
                dismissDefultProgressDialog();
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
                dismissDefultProgressDialog();
                MyLog.e(TAG, t.toString());
            }
        });


    }


    private void showInRecyclerView(final List<ContactsBean> contactsBeen) {
        recyclerview.setVisibility(View.VISIBLE);
        customerAdapter = new CustomerAdapter(R.layout.customername_textview, contactsBeen);
        recyclerview.setVisibility(View.VISIBLE);
        recyclerview.setAdapter(customerAdapter);
        customerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }


    private void initSearchCustomerFromNet(final String name) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name", name);
        // type: ‘supplier’ or ‘customer’
        objectObjectHashMap.put("type", "supplier");
        Call<SearchSupplierResponse> stringCall = inventoryApi.searchSupplier(objectObjectHashMap);
        showDefultProgressDialog();
        stringCall.enqueue(new Callback<SearchSupplierResponse>() {
            @Override
            public void onResponse(Call<SearchSupplierResponse> call, Response<SearchSupplierResponse> response) {
                dismissDefultProgressDialog();
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
                dismissDefultProgressDialog();
            }
        });
    }
}
