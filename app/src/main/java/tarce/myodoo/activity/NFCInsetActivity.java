package tarce.myodoo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zaihuishou.expandablerecycleradapter.adapter.BaseExpandableAdapter;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractAdapterItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tarce.api.MyCallback;
import tarce.api.OKHttpFactory;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.inventory.BomFramworkBean;
import tarce.model.inventory.BomSubBean;
import tarce.model.inventory.NFCWorkerBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.expand.CompanyItem;
import tarce.myodoo.adapter.expand.DepartmentItem;
import tarce.myodoo.adapter.expand.EmployeeItem;
import tarce.myodoo.adapter.expand.LastItem;
import tarce.myodoo.adapter.expand.SixItem;
import tarce.myodoo.adapter.expand.WorkerItem;
import tarce.myodoo.adapter.takedeliver.WorkerAllAdapter;

/**
 * Created by zouzou on 2017/6/28.
 * NFC录入页面
 */

public class NFCInsetActivity extends BaseActivity {
    @InjectView(R.id.recycler_insert_nfc)
    RecyclerView recyclerInsertNfc;
    private InventoryApi inventoryApi;
    private Retrofit retrofit;
    private List<NFCWorkerBean.ResultBean.ResDataBean> res_data;
    private List<Object> mCompanylist;
    private final int ITEM_TYPE_COMPANY = 1;
    private final int ITEM_TYPE_SIX = 6;
    private BaseExpandableAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_insert);
        ButterKnife.inject(this);
        setRecyclerview(recyclerInsertNfc);
        setTitle("员工NFC录入");

        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(new OKHttpFactory(NFCInsetActivity.this).getOkHttpClient())

                //baseUrl
//                .baseUrl("http://192.168.2.111:8069/linkloving_app_api/")
                .baseUrl(RetrofitClient.Url+"/linkloving_user_auth/")
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        inventoryApi = retrofit.create(InventoryApi.class);

        initData();
    }

    /**
     * 初始化数据
     * */
    private void initData() {
        //HashMap<Object, Object> hashMap = new HashMap<>();
       // hashMap.put("parent_id",parent_id);
        Call<NFCWorkerBean> depart = inventoryApi.getDepart(new HashMap());
        depart.enqueue(new MyCallback<NFCWorkerBean>() {
            @Override
            public void onResponse(Call<NFCWorkerBean> call, Response<NFCWorkerBean> response) {
                if (response.body() == null) return;
                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1){
                    NFCWorkerBean.ResultBean result = response.body().getResult();
                    mCompanylist = new ArrayList<>();
                    mCompanylist.add(createCompany(result, false));
                    adapter = new BaseExpandableAdapter(mCompanylist){

                        @Override
                        public AbstractAdapterItem<Object> getItemView(Object type) {
                            int itemType = (int) type;
                            switch (itemType) {
                                case ITEM_TYPE_COMPANY:
                                    return new CompanyItem();
                                case ITEM_TYPE_SIX:
                                    return new SixItem();
                            }
                            return null;
                        }

                        @Override
                        public Object getItemViewType(Object t){
                            if (t instanceof NFCWorkerBean.ResultBean) {
                                return ITEM_TYPE_COMPANY;
                            } else if (t instanceof NFCWorkerBean.ResultBean.ResDataBean) {
                                return ITEM_TYPE_SIX;
                            }
                            return -1;
                        }
                    };
                    recyclerInsertNfc.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<NFCWorkerBean> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    //item点击事件
    private void initListener() {

    }
    private NFCWorkerBean.ResultBean createCompany(NFCWorkerBean.ResultBean result, boolean isExpandDefault) {

        return result;
    }
}
