package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import greendao.ContactsBeanDao;
import greendao.DaoSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.GetGroupByListresponse;
import tarce.model.SearchSupplierResponse;
import tarce.myodoo.MyApplication;
import tarce.myodoo.R;
import tarce.myodoo.adapter.TakeDelievAdapter;
import tarce.myodoo.bean.MenuBean;
import tarce.support.ViewUtils;

/**
 * Created by rose.zou  on 2017/6/21.
 */

public class TakeDeliverActivity extends BaseActivity {
    @InjectView(R.id.edit_search_custom)
    EditText editSearchCustom;
    @InjectView(R.id.edit_search_order)
    EditText editSearchOrder;
    @InjectView(R.id.recycler_show_supplier)
    RecyclerView recyclerShowSupplier;
    @InjectView(R.id.tv_get_last)
    TextView tvGetLast;
    @InjectView(R.id.recycler_take_deliver)
    RecyclerView recyclerTakeDeliver;
    private InventoryApi inventoryApi;
    private List<MenuBean> menuBeanList;
    private TakeDelievAdapter adapter;
    private List<GetGroupByListresponse.ResultBean.ResDataBean.StatesBean> states;
    private String picking_type_code;
    private ContactsBeanDao contactsBeanDao;
    private DaoSession daoSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_deliver);
        ButterKnife.inject(this);

        daoSession = MyApplication.getInstances().getDaoSession();
        contactsBeanDao =  daoSession.getContactsBeanDao();
        setRecyclerview(recyclerTakeDeliver);
        editCustomListener();
        editOrderListener();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (menuBeanList == null){
            initData();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (menuBeanList != null){
            menuBeanList = null;
        }
    }

    private void initData() {
        menuBeanList = new ArrayList<>();
        menuBeanList.add(new MenuBean("待收货",0));
        menuBeanList.add(new MenuBean("完成",0));
        menuBeanList.add(new MenuBean("待入库",0));
        adapter = new TakeDelievAdapter(R.layout.salesout_adapter, menuBeanList);
        recyclerTakeDeliver.setAdapter(adapter);
        initDeliever();
    }

    private void editOrderListener() {
        editSearchCustom.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    ViewUtils.collapseSoftInputMethod(TakeDeliverActivity.this, editSearchCustom);
                }
                return false;
            }
        });
    }

    private void editCustomListener() {
        editSearchOrder.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    ViewUtils.collapseSoftInputMethod(TakeDeliverActivity.this, editSearchOrder);
                }
                return false;
            }
        });
    }

    /**
     *初始化数据
     * */
    private void initDeliever(){
        showDefultProgressDialog();
        inventoryApi = RetrofitClient.getInstance(TakeDeliverActivity.this).create(InventoryApi.class);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("partner_id",0);
        hashMap.put("groupby","picking_type_id");
        hashMap.put("model","stock.picking");
        Call<GetGroupByListresponse> groupsByList = inventoryApi.getGroupsByList(hashMap);
        groupsByList.enqueue(new MyCallback<GetGroupByListresponse>() {
            @Override
            public void onResponse(Call<GetGroupByListresponse> call, Response<GetGroupByListresponse> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)return;
                if (response.body().getResult().getRes_code() == 1){
                    List<GetGroupByListresponse.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                    states = new ArrayList<>();
                    for (int i = 0; i < res_data.size(); i++) {
                        if (res_data.get(i).getPicking_type_code().equals("incoming")){
                            states = res_data.get(i).getStates();
                            picking_type_code = res_data.get(i).getPicking_type_code();
                        }
                    }
                    for (int i = 0; i < states.size(); i++) {
                        if (states.get(i).getState().equals("assigned")){
                            menuBeanList.set(0, new MenuBean("待收货", states.get(i).getState_count()));
                        }else if (states.get(i).getState().equals("done")){
                            menuBeanList.set(1, new MenuBean("完成", states.get(i).getState_count()));
                        }else if (states.get(i).getState().equals("waiting_in")){
                            menuBeanList.set(2, new MenuBean("待入库", states.get(i).getState_count()));
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
                if (menuBeanList.get(position).getNumber()<=0){
                    return;
                }
                Intent intent = new Intent(TakeDeliverActivity.this, TakeDeliveListActivity.class);
                intent.putExtra("from", "no");
                intent.putExtra("type_code", picking_type_code);
                switch (position){
                    case 0:
                        intent.putExtra("state","assigned");
                        break;
                    case 1:
                        intent.putExtra("state","done");
                        break;
                    case 2:
                        intent.putExtra("state","waiting_in");
                        break;
                }
                startActivity(intent);
            }
        });
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
                /*if (response.body().getResult() != null) {
                    List<SearchSupplierResponse.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                    if (res_data != null && res_data.size() > 0) {
                        for (SearchSupplierResponse.ResultBean.ResDataBean resDataBean : res_data) {
                            contactsBeanDao.insertOrReplace(new ContactsBean(resDataBean.getComment(), resDataBean.getPhone()
                                    , resDataBean.getPartner_id(), resDataBean.getName(), resDataBean.getX_qq()));
                        }
                    }
                }
                long count = contactsBeanDao.count();
                MyLog.e(TAG,"contactsBeanDao里面的数量是"+count);*/
            }

            @Override
            public void onFailure(Call<SearchSupplierResponse> call, Throwable t) {
            }
        });
    }
}
