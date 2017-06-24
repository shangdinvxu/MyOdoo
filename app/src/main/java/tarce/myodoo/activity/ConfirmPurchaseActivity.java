package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;

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
import tarce.model.GetGroupByListresponse;
import tarce.myodoo.R;
import tarce.myodoo.adapter.TakeDelievAdapter;
import tarce.myodoo.bean.MenuBean;
import tarce.myodoo.utils.UserManager;

/**
 * Created by zouzou on 2017/6/24.
 * 采购确认页面
 */

public class ConfirmPurchaseActivity extends BaseActivity {
    @InjectView(R.id.edit_confirm_search)
    EditText editConfirmSearch;
    @InjectView(R.id.recycler_confirm)
    RecyclerView recyclerConfirm;
    private InventoryApi inventoryApi;
    private List<MenuBean> menuBeanList;
    private TakeDelievAdapter adapter;
    private List<GetGroupByListresponse.ResultBean.ResDataBean.StatesBean> states;
    private String picking_type_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_purchase);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(ConfirmPurchaseActivity.this).create(InventoryApi.class);
        setRecyclerview(recyclerConfirm);
        initData();
        showLinThreeGou();
    }

    private void initData() {
        menuBeanList = new ArrayList<>();
        menuBeanList.add(new MenuBean("等待采购检验",0));
        menuBeanList.add(new MenuBean("完成",0));
        adapter = new TakeDelievAdapter(R.layout.salesout_adapter, menuBeanList);
        recyclerConfirm.setAdapter(adapter);
        initDeliever();
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
     *初始化数据
     * */
    private void initDeliever(){
        showDefultProgressDialog();
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
                if (response.body().getResult().getRes_data()!=null && response.body().getResult().getRes_code() == 1){
                    List<GetGroupByListresponse.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                    states = new ArrayList<>();
                    for (int i = 0; i < res_data.size(); i++) {
                        if (res_data.get(i).getPicking_type_code().equals("incoming")){
                            states = res_data.get(i).getStates();
                            picking_type_code = res_data.get(i).getPicking_type_code();
                        }
                    }
                    for (int i = 0; i < states.size(); i++) {
                        if (states.get(i).getState().equals("validate")){
                            menuBeanList.set(0, new MenuBean("等待采购检验", states.get(i).getState_count()));
                        }else if (states.get(i).getState().equals("done")){
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
            }
        });
    }

    /**
     * item点击事件
     * */
    private void initListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (menuBeanList.get(position).getNumber()<=0){
                    return;
                }
                Intent intent = new Intent(ConfirmPurchaseActivity.this, TakeDeliveListActivity.class);
                intent.putExtra("from", "no");
                intent.putExtra("type_code", picking_type_code);
                switch (position){
                    case 0:
                        intent.putExtra("state","validate");
                        break;
                    case 1:
                        intent.putExtra("state","done");
                        break;
                }
                startActivity(intent);
            }
        });
    }
}
