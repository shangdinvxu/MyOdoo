package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import greendao.DaoSession;
import greendao.SupplierBeanDao;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.GetGroupByListresponse;
import tarce.model.greendaoBean.SupplierBean;
import tarce.myodoo.MyApplication;
import tarce.myodoo.R;
import tarce.myodoo.activity.takedeliver.TakeDeliveListActivity;
import tarce.myodoo.activity.takedeliver.TakeDeliverActivity;
import tarce.myodoo.adapter.TakeDelievAdapter;
import tarce.myodoo.adapter.takedeliver.SupplierAdapter;
import tarce.myodoo.bean.MenuBean;
import tarce.myodoo.greendaoUtils.SupplierBeanUtils;
import tarce.myodoo.utils.UserManager;
import tarce.support.ToastUtils;
import tarce.support.ViewUtils;

/**
 * Created by zouzou on 2017/6/24.
 * 采购确认页面
 */

public class ConfirmPurchaseActivity extends BaseActivity {
    @InjectView(R.id.recycler_confirm)
    RecyclerView recyclerConfirm;
    @InjectView(R.id.edit_confirm_search)
    SearchView editConfirmSearch;
    @InjectView(R.id.recycler_show_supplier)
    RecyclerView recyclerShowSupplier;
    @InjectView(R.id.tv_get_last)
    TextView tvGetLast;
    private InventoryApi inventoryApi;
    private List<MenuBean> menuBeanList;
    private TakeDelievAdapter adapter;
    private List<GetGroupByListresponse.ResultBean.ResDataBean.StatesBean> states;
    private String picking_type_code;
    private DaoSession daoSession;
    private SupplierBeanDao supplierBeanDao;
    private SupplierBeanUtils supplierBeanUtils;
    private SupplierAdapter supplierAdapter;
    private List<GetGroupByListresponse.ResultBean.ResDataBean> res_data;
    private long partnerId;
    private int picking_type_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_purchase);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(ConfirmPurchaseActivity.this).create(InventoryApi.class);
        daoSession = MyApplication.getInstances().getDaoSession();
        supplierBeanDao = daoSession.getSupplierBeanDao();
        supplierBeanUtils = new SupplierBeanUtils();
        setRecyclerview(recyclerConfirm);
        setRecyclerview(recyclerShowSupplier);

        editCustomListener();
        initData();
        showLinThreeGou();
    }

    private void initData() {
        menuBeanList = new ArrayList<>();
        menuBeanList.add(new MenuBean("等待采购检验", 0));
        menuBeanList.add(new MenuBean("完成", 0));
        adapter = new TakeDelievAdapter(R.layout.salesout_adapter, menuBeanList);
        recyclerConfirm.setAdapter(adapter);
        initDeliever(0);
    }

    /**
     * 是否显示底部（采购）
     */
    public void showLinThreeGou() {
        if (!UserManager.getSingleton().getGrops().contains("group_purchase_manager") && !UserManager.getSingleton().getGrops().contains("group_purchase_user")) {
            recyclerConfirm.setVisibility(View.GONE);
        }
    }

    /**
     * 搜索供应商名的监听
     * */
    private void editCustomListener() {
        editConfirmSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ViewUtils.collapseSoftInputMethod(ConfirmPurchaseActivity.this, editConfirmSearch);
                List<SupplierBean> supplierBeanList = supplierBeanUtils.searchByName(query);
                if (supplierBeanList != null && supplierBeanList.size() > 0) {
                    showQueryRecycler(supplierBeanList);
                } else {
                    Toast.makeText(ConfirmPurchaseActivity.this, "在本地数据库未找到该供应商，请尝试点击“获取最新”按键后再搜索", Toast.LENGTH_LONG).show();
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

    //显示查询结果
    private void showQueryRecycler(final List<SupplierBean> supplierBeanList) {
        recyclerShowSupplier.setVisibility(View.VISIBLE);
        tvGetLast.setVisibility(View.VISIBLE);
        supplierAdapter = new SupplierAdapter(R.layout.customername_textview, supplierBeanList);
        recyclerShowSupplier.setAdapter(supplierAdapter);
        supplierAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                editConfirmSearch.setQuery(supplierBeanList.get(position).getName(), true);
                recyclerShowSupplier.setVisibility(View.GONE);
                tvGetLast.setVisibility(View.GONE);
                if (menuBeanList != null) {
                    menuBeanList.set(0, new MenuBean("等待采购检验", 0));
                    menuBeanList.set(1, new MenuBean("完成", 0));
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
                if (response.body() == null || response.body().getResult() == null) return;
                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                    res_data = response.body().getResult().getRes_data();
                    states = new ArrayList<>();
                    for (int i = 0; i < res_data.size(); i++) {
                        if (res_data.get(i).getPicking_type_code().equals("incoming")) {
                            states = res_data.get(i).getStates();
                            picking_type_code = res_data.get(i).getPicking_type_code();
                            picking_type_id = res_data.get(i).getPicking_type_id();
                        }
                    }
                    for (int i = 0; i < states.size(); i++) {
                        if (states.get(i).getState().equals("validate")) {
                            menuBeanList.set(0, new MenuBean("等待采购检验", states.get(i).getState_count()));
                        } else if (states.get(i).getState().equals("done")) {
                            menuBeanList.set(1, new MenuBean("完成", states.get(i).getState_count()));
                        }
                    }
                    adapter.notifyDataSetChanged();
                    initListener();
                }
            }

            @Override
            public void onFailure(Call<GetGroupByListresponse> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(ConfirmPurchaseActivity.this, t.toString());
            }
        });
    }

    /**
     * item点击事件
     */
    private void initListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (menuBeanList.get(position).getNumber() <= 0) {
                    return;
                }
                Intent intent = new Intent(ConfirmPurchaseActivity.this, TakeDeliveListActivity.class);
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
                        intent.putExtra("state", "validate");
                        break;
                    case 1:
                        intent.putExtra("state", "done");
                        break;
                }
                startActivity(intent);
            }
        });
    }
}
