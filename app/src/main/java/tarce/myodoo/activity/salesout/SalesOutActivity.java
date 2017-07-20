package tarce.myodoo.activity.salesout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import greendao.ContactsBeanDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.OutgoingStockpickingBean;
import tarce.model.SearchSupplierResponse;
import tarce.model.greendaoBean.ContactsBean;
import tarce.model.inventory.SalesOutListResponse;
import tarce.myodoo.MyApplication;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.adapter.CustomerAdapter;
import tarce.myodoo.adapter.SalesStatesAdapter;
import tarce.myodoo.bean.AvailabilityBean;
import tarce.myodoo.greendaoUtils.ContactBeanUtils;
import tarce.support.AlertAialogUtils;
import tarce.support.MyLog;
import tarce.support.ToastUtils;

/**
 * Created by Daniel.Xu on 2017/4/20.
 */

public class SalesOutActivity extends BaseActivity {
    private final static String TAG = SalesOutActivity.class.getSimpleName();
    @InjectView(R.id.search_customer)
    SearchView searchCustomer;
    @InjectView(R.id.search_sales_number)
    SearchView searchSalesNumber;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.recyclerviewStates)
    RecyclerView recyclerviewStates;
    @InjectView(R.id.tv_get_last)
    TextView tvGetLast;
    private InventoryApi inventoryApi;
    private ContactsBeanDao contactsBeanDao;
    private ContactBeanUtils contactBeanUtils;
    private CustomerAdapter customerAdapter;
    private List<AvailabilityBean> availabilityBeen;
    private SalesStatesAdapter salesStatesAdapter;
    private long parterId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_out);
        ButterKnife.inject(this);
        initListener();
        inventoryApi = RetrofitClient.getInstance(SalesOutActivity.this).create(InventoryApi.class);
        contactsBeanDao = MyApplication.getInstances().getDaoSession().getContactsBeanDao();
        contactBeanUtils = new ContactBeanUtils();
        setTitle("销售出货");
        setRecyclerview(recyclerview);
        setRecyclerview(recyclerviewStates);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (availabilityBeen == null){
            initData();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (availabilityBeen != null){
            availabilityBeen = null;
        }
    }

    private void initData() {
        availabilityBeen = new ArrayList<>();
        availabilityBeen.add(new AvailabilityBean("可用率 100%",0,0));
        availabilityBeen.add(new AvailabilityBean("可用率 1%-99%",0,0));
        availabilityBeen.add(new AvailabilityBean("可用率 0%",0,0));
        availabilityBeen.add(new AvailabilityBean("完成",0,0));
        salesStatesAdapter = new SalesStatesAdapter(R.layout.salesout_adapter, availabilityBeen);
        recyclerviewStates.setAdapter(salesStatesAdapter);

        initSearchCustomerFromNet(0);
    }

    /**
     * 可用率列表的点击事件
     * */
    private void initSalesStatesListener(final SalesStatesAdapter salesStatesAdapter) {
        salesStatesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
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
                Intent intent = new Intent(SalesOutActivity.this, SalesListActivity.class);
                intent.putExtra("complete_rate", complete_rate);
                intent.putExtra("state", state);
                intent.putExtra("deliver","no");
                intent.putExtra("partner_id", parterId);
                startActivity(intent);
            }
        });

    }

    /**
     * 根据客户简称进行搜索
     * */
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
                        tvGetLast.setVisibility(View.GONE);
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

    /**
     * 如果数据库没有  就搜索服务器
     * */
    private void initSearchFormDB(String s){

        List<ContactsBean> contactsBeen = contactBeanUtils.searchByName(s);
        if (contactsBeen != null && contactsBeen.size() > 0) {
            showInRecyclerView(contactsBeen);
        } else {
            ToastUtils.showCommonToast(SalesOutActivity.this, "本地数据库未找到，请尝试点击“获取最新”按键后再搜索");
            recyclerview.setVisibility(View.GONE);
            tvGetLast.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 根据输入的订单号搜索订单
     * */
    private void initSearchSalesListFromNet(final String name) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("order_name", name);
        objectObjectHashMap.put("type", "outgoing");
        Call<SalesOutListResponse> getSaleListByNumberResponseCall = inventoryApi.searchBySalesNumber(objectObjectHashMap);
        showDefultProgressDialog();
        getSaleListByNumberResponseCall.enqueue(new Callback<SalesOutListResponse>() {
            @Override
            public void onResponse(Call<SalesOutListResponse> call, Response<SalesOutListResponse> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)return;
                if (response.body().getResult().getRes_code() == 1){
                    List<SalesOutListResponse.TResult.TRes_data> res_data = response.body().getResult().getRes_data();
                    if (res_data != null && res_data.size() > 0) {
                        Intent intent = new Intent(SalesOutActivity.this, SalesListActivity.class);
                        intent.putExtra("intent", (Serializable) res_data);
                        intent.putExtra("deliver","yes");
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<SalesOutListResponse> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(SalesOutActivity.this, t.toString());
                MyLog.e(TAG, t.toString());
            }
        });
    }

    /**
     * 显示客户全称  点击事件
     * */
    private void showInRecyclerView(final List<ContactsBean> contactsBeen) {
        tvGetLast.setVisibility(View.VISIBLE);
        customerAdapter = new CustomerAdapter(R.layout.customername_textview, contactsBeen);
        recyclerview.setVisibility(View.VISIBLE);
        recyclerview.setAdapter(customerAdapter);
        customerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (availabilityBeen != null){
                    availabilityBeen.set(2,new AvailabilityBean("可用率 0%", 0, 0));
                    availabilityBeen.set(0,new AvailabilityBean("可用率 100%", 100, 0));
                    availabilityBeen.set(1,new AvailabilityBean("可用率 1%-99%", 99, 0));
                    availabilityBeen.set(3, new AvailabilityBean("完成", 1000, 0));
                }
                salesStatesAdapter.notifyDataSetChanged();
                searchCustomer.setQuery(contactsBeen.get(position).getName(), true);
                initSearchCustomerFromNet(contactsBeen.get(position).getPartner_id());
            }
        });
    }

    /**
     * 获取客户信息并保存至本地
     * */
    @OnClick(R.id.tv_get_last)
    void getlastCustom(View view){
        AlertAialogUtils.getCommonDialog(SalesOutActivity.this, "此操作涉及大量数据加载读写到本地，将会造成几秒钟的界面卡顿，请耐心等待！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                   //     objectObjectHashMap.put("name", null);
                        // type: ‘supplier’ or ‘customer’
                        objectObjectHashMap.put("type", "customer");
                        Call<SearchSupplierResponse> stringCall = inventoryApi.searchSupplier(objectObjectHashMap);
                        stringCall.enqueue(new Callback<SearchSupplierResponse>() {
                            @Override
                            public void onResponse(Call<SearchSupplierResponse> call, Response<SearchSupplierResponse> response) {
                                if (response.body() == null || response.body().getResult() == null)return;
                                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code()==1) {
                                    List<SearchSupplierResponse.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                                    if (res_data != null && res_data.size() > 0) {
                                        for (SearchSupplierResponse.ResultBean.ResDataBean resDataBean : res_data) {
                                            contactsBeanDao.insertOrReplace(new ContactsBean(resDataBean.getComment(), resDataBean.getPhone()
                                                    , resDataBean.getPartner_id(), resDataBean.getName(), resDataBean.getX_qq()));
                                        }
                                    }
                                }
                                long count = contactsBeanDao.count();
                                MyLog.e(TAG,"contactsBeanDao里面的数量是"+count);
                                ToastUtils.showCommonToast(SalesOutActivity.this, "已加载完毕");
                            }
                            @Override
                            public void onFailure(Call<SearchSupplierResponse> call, Throwable t) {
                                ToastUtils.showCommonToast(SalesOutActivity.this, t.toString());
                            }
                        });
                    }
                }).show();
    }


    private void initSearchCustomerFromNet(final long id) {
        showDefultProgressDialog();
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("partner_id", id);
        parterId = id;
        Call<OutgoingStockpickingBean> stringCall = inventoryApi.searchByCustomName(objectObjectHashMap);
        stringCall.enqueue(new Callback<OutgoingStockpickingBean>() {
            @Override
            public void onResponse(Call<OutgoingStockpickingBean> call, Response<OutgoingStockpickingBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null || response.body().getResult() == null)return;
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data()!=null){
                    List<OutgoingStockpickingBean.ResultBean.ResDataBean.CompleteRateBean> complete_rate = response.body().getResult().getRes_data().getComplete_rate();
                    for (int i = 0; i < complete_rate.size(); i++) {
                        if (complete_rate.get(i).getComplete_rate() == 0){
                            availabilityBeen.set(2,new AvailabilityBean("可用率 0%", 0, complete_rate.get(i).getComplete_rate_count()));
                        }else if (complete_rate.get(i).getComplete_rate() == 100){
                            availabilityBeen.set(0,new AvailabilityBean("可用率 100%", 100, complete_rate.get(i).getComplete_rate_count()));
                        }else if (complete_rate.get(i).getComplete_rate() == 99){
                            availabilityBeen.set(1,new AvailabilityBean("可用率 1%-99%", 99, complete_rate.get(i).getComplete_rate_count()));
                        }
                    }
                    if (response.body().getResult().getRes_data().getState()!=null){
                        availabilityBeen.set(3, new AvailabilityBean("完成", 1000, response.body().getResult().getRes_data().getState().getState_count()));
                    }
                    recyclerview.setVisibility(View.GONE);
                    tvGetLast.setVisibility(View.GONE);
                    salesStatesAdapter.notifyDataSetChanged();
                    initSalesStatesListener(salesStatesAdapter);
                }
            }

            @Override
            public void onFailure(Call<OutgoingStockpickingBean> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(SalesOutActivity.this, t.toString());
            }
        });
    }
}
