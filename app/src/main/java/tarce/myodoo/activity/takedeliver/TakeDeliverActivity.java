package tarce.myodoo.activity.takedeliver;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import greendao.DaoSession;
import greendao.SupplierBeanDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.GetGroupByListresponse;
import tarce.model.SearchSupplierResponse;
import tarce.model.greendaoBean.SupplierBean;
import tarce.model.inventory.TakeDelListBean;
import tarce.myodoo.MyApplication;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.adapter.TakeDelievAdapter;
import tarce.myodoo.adapter.takedeliver.SupplierAdapter;
import tarce.myodoo.bean.MenuBean;
import tarce.myodoo.greendaoUtils.SupplierBeanUtils;
import tarce.support.AlertAialogUtils;
import tarce.support.MyLog;
import tarce.support.ToastUtils;
import tarce.support.ViewUtils;

/**
 * Created by rose.zou  on 2017/6/21.
 */

public class TakeDeliverActivity extends BaseActivity {

    @InjectView(R.id.recycler_show_supplier)
    RecyclerView recyclerShowSupplier;
    @InjectView(R.id.tv_get_last)
    TextView tvGetLast;
    @InjectView(R.id.recycler_take_deliver)
    RecyclerView recyclerTakeDeliver;
    @InjectView(R.id.edit_search_custom)
    SearchView editSearchCustom;
    @InjectView(R.id.edit_search_order)
    SearchView editSearchOrder;
    private InventoryApi inventoryApi;
    private List<MenuBean> menuBeanList;
    private TakeDelievAdapter adapter;
    private List<GetGroupByListresponse.ResultBean.ResDataBean.StatesBean> states;
    private String picking_type_code;
    private SupplierBeanDao supplierBeanDao;
    private DaoSession daoSession;
    private SupplierBeanUtils supplierBeanUtils;
    private SupplierAdapter supplierAdapter;
    private long partnerId;
    private int picking_type_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_deliver);
        ButterKnife.inject(this);

        setTitle("收货");
        inventoryApi = RetrofitClient.getInstance(TakeDeliverActivity.this).create(InventoryApi.class);
        daoSession = MyApplication.getInstances().getDaoSession();
        supplierBeanDao = daoSession.getSupplierBeanDao();
        supplierBeanUtils = new SupplierBeanUtils();
        setRecyclerview(recyclerTakeDeliver);
        setRecyclerview(recyclerShowSupplier);
        editCustomListener();
        editOrderListener();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (menuBeanList == null) {
            initData();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (menuBeanList != null) {
            menuBeanList = null;
        }
    }

    private void initData() {
        menuBeanList = new ArrayList<>();
        menuBeanList.add(new MenuBean("待收货", 0));
        menuBeanList.add(new MenuBean("完成", 0));
        menuBeanList.add(new MenuBean("待入库", 0));
        adapter = new TakeDelievAdapter(R.layout.salesout_adapter, menuBeanList);
        recyclerTakeDeliver.setAdapter(adapter);
        initDeliever(0);
    }

    /**
     * 根据订单号搜索
     * */
    private void editOrderListener() {
        editSearchOrder.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ViewUtils.collapseSoftInputMethod(TakeDeliverActivity.this, editSearchOrder);
                searchOrder(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    /**
     * 根据订单号搜索 searchByTakeNumber
     * */
    private void searchOrder(String query) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("order_name", query);
        objectObjectHashMap.put("type", "incoming");
        Call<TakeDelListBean> getSaleListByNumberResponseCall = inventoryApi.searchByTakeNumber(objectObjectHashMap);
        showDefultProgressDialog();
        getSaleListByNumberResponseCall.enqueue(new Callback<TakeDelListBean>() {
            @Override
            public void onResponse(Call<TakeDelListBean> call, Response<TakeDelListBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)return;
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data()!=null){
                    List<TakeDelListBean.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                    if (res_data != null && res_data.size() > 0) {
                        Intent intent = new Intent(TakeDeliverActivity.this, TakeDeliveListActivity.class);
                        intent.putExtra("intent", (Serializable) res_data);
                        intent.putExtra("from","no");
                        intent.putExtra("notneed", "yes");
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<TakeDelListBean> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(TakeDeliverActivity.this, t.toString());
                MyLog.e("TakeDeliverActivity", t.toString());
            }
        });
    }

    /**
     * 搜索供应商名的监听
     * */
    private void editCustomListener() {
        editSearchCustom.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ViewUtils.collapseSoftInputMethod(TakeDeliverActivity.this, editSearchCustom);
                List<SupplierBean> supplierBeanList = supplierBeanUtils.searchByName(query);
                if (supplierBeanList != null && supplierBeanList.size() > 0) {
                    showQueryRecycler(supplierBeanList);
                } else {
                    Toast.makeText(TakeDeliverActivity.this, "在本地数据库未找到该供应商，请尝试点击“获取最新”按键后再搜索", Toast.LENGTH_LONG).show();
                    tvGetLast.setVisibility(View.VISIBLE);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == null || newText.length() == 0) {
                    if (supplierAdapter != null) {
                        supplierAdapter.getData().clear();
                        recyclerShowSupplier.setVisibility(View.GONE);
                        tvGetLast.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        });
    }

    /**
     * 显示查询结果
     *
     * @param supplierBeanList
     */
    private void showQueryRecycler(final List<SupplierBean> supplierBeanList) {
        recyclerShowSupplier.setVisibility(View.VISIBLE);
        tvGetLast.setVisibility(View.VISIBLE);
        supplierAdapter = new SupplierAdapter(R.layout.customername_textview, supplierBeanList);
        recyclerShowSupplier.setAdapter(supplierAdapter);
        supplierAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                editSearchCustom.setQuery(supplierBeanList.get(position).getName(), true);
                recyclerShowSupplier.setVisibility(View.GONE);
                tvGetLast.setVisibility(View.GONE);
                if (menuBeanList != null) {
                    menuBeanList.set(0, new MenuBean("待收货", 0));
                    menuBeanList.set(1, new MenuBean("完成", 0));
                    menuBeanList.set(2, new MenuBean("待入库", 0));
                }
                adapter.notifyDataSetChanged();
                initDeliever(supplierBeanList.get(position).getPartner_id());
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initDeliever(long partner_id) {
        showDefultProgressDialog();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("partner_id", partner_id);
        partnerId = partner_id;
        hashMap.put("groupby", "picking_type_id");
        hashMap.put("model", "stock.picking");
        Call<GetGroupByListresponse> groupsByList = inventoryApi.getGroupsByList(hashMap);
        groupsByList.enqueue(new MyCallback<GetGroupByListresponse>() {
            @Override
            public void onResponse(Call<GetGroupByListresponse> call, Response<GetGroupByListresponse> response) {
                dismissDefultProgressDialog();
                if (response.body() == null || response.body().getResult()==null) return;
                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                    List<GetGroupByListresponse.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                    states = new ArrayList<>();
                    for (int i = 0; i < res_data.size(); i++) {
                        if (res_data.get(i).getPicking_type_code().equals("incoming")) {
                            states.addAll(res_data.get(i).getStates());
                            picking_type_code = res_data.get(i).getPicking_type_code();
                            picking_type_id = res_data.get(i).getPicking_type_id();
                        }
                    }
                    int sumOne = 0;
                    int sumTwo = 0;
                    int sumThree = 0;
                    for (int i = 0; i < states.size(); i++) {
                        if (states.get(i).getState().equals("assigned")) {
                            sumOne = sumOne + states.get(i).getState_count();
                            menuBeanList.set(0, new MenuBean("待收货", sumOne));
                        } else if (states.get(i).getState().equals("done")) {
                            sumTwo = sumTwo + states.get(i).getState_count();
                            menuBeanList.set(1, new MenuBean("完成", sumTwo));
                        } else if (states.get(i).getState().equals("waiting_in")) {
                            sumThree = sumThree + states.get(i).getState_count();
                            menuBeanList.set(2, new MenuBean("待入库", sumThree));
                        }
                    }
                    adapter.notifyDataSetChanged();
                    initListener();
                }
            }

            @Override
            public void onFailure(Call<GetGroupByListresponse> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
    }

    //item点击事件
    private void initListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (menuBeanList.get(position).getNumber() <= 0) {
                    return;
                }
                Intent intent = new Intent(TakeDeliverActivity.this, TakeDeliveListActivity.class);
                if (partnerId == 0){
                    intent.putExtra("from", "no");
                    intent.putExtra("partner_id", 0);
                }else {
                    intent.putExtra("from", "yes");
                    intent.putExtra("partner_id", partnerId);
                    intent.putExtra("picking_type_id", picking_type_id);
                }
                intent.putExtra("notneed", "no");
                intent.putExtra("type_code", picking_type_code);
                switch (position) {
                    case 0:
                        intent.putExtra("state", "assigned");
                        break;
                    case 1:
                        intent.putExtra("state", "done");
                        break;
                    case 2:
                        intent.putExtra("state", "waiting_in");
                        break;
                }
                startActivity(intent);
            }
        });
    }

    /**
     * 获取最新点击事件
     */
    @OnClick(R.id.tv_get_last)
    void getLast(View view) {
        AlertAialogUtils.getCommonDialog(TakeDeliverActivity.this, "此操作涉及大量数据加载读写到本地，将会造成几秒钟的界面卡顿，请耐心等待！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getSupplier();
                    }
                }).show();
    }

    /**
     * 存储供应商信息
     */
    private void getSupplier() {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name", null);
        // type: ‘supplier’ or ‘customer’
        objectObjectHashMap.put("type", "supplier");
        Call<SearchSupplierResponse> stringCall = inventoryApi.searchSupplier(objectObjectHashMap);
        stringCall.enqueue(new Callback<SearchSupplierResponse>() {
            @Override
            public void onResponse(Call<SearchSupplierResponse> call, Response<SearchSupplierResponse> response) {
                if (response.body() == null) return;
                if (response.body().getResult() != null && response.body().getResult().getRes_data() != null) {
                    List<SearchSupplierResponse.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                    if (res_data != null && res_data.size() > 0) {
                        for (SearchSupplierResponse.ResultBean.ResDataBean resDataBean : res_data) {
                            supplierBeanDao.insertOrReplace(new SupplierBean(resDataBean.getComment(), resDataBean.getName()
                                    , resDataBean.getPartner_id(), resDataBean.getPhone(), resDataBean.getX_qq()));
                        }
                        ToastUtils.showCommonToast(TakeDeliverActivity.this, "已加载完毕");
                    }
                }
                long count = supplierBeanDao.count();
                MyLog.e("TakeDeliverActivity", "supplierBeanDao里面的数量是" + count);
            }

            @Override
            public void onFailure(Call<SearchSupplierResponse> call, Throwable t) {
                ToastUtils.showCommonToast(TakeDeliverActivity.this, t.toString());
            }
        });
    }
}
