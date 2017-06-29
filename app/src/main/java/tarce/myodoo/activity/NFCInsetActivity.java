package tarce.myodoo.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.zaihuishou.expandablerecycleradapter.adapter.BaseExpandableAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
import tarce.model.inventory.NFCWorkerBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.expand.MyTreeHolder;
import tarce.myodoo.adapter.expand.NeedExpandHolder;
import tarce.support.MyLog;
import tarce.support.ToastUtils;

/**
 * Created by zouzou on 2017/6/28.
 * NFC录入页面
 */

public class NFCInsetActivity extends BaseActivity {
    @InjectView(R.id.container)
    RelativeLayout container;
    private InventoryApi inventoryApi;
    private Retrofit retrofit;
    private List<NFCWorkerBean.ResultBean.ResDataBean> res_data;
    private MyTreeHolder holder;
    private NeedExpandHolder expandHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_insert);
        ButterKnife.inject(this);
        setTitle("员工NFC录入");
        holder = new MyTreeHolder(NFCInsetActivity.this);
        expandHolder = new NeedExpandHolder(NFCInsetActivity.this);
        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(new OKHttpFactory(NFCInsetActivity.this).getOkHttpClient())

                //baseUrl
//                .baseUrl("http://192.168.2.111:8069/linkloving_app_api/")
                .baseUrl(RetrofitClient.Url + "/linkloving_user_auth/")
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        inventoryApi = retrofit.create(InventoryApi.class);

        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //HashMap<Object, Object> hashMap = new HashMap<>();
        // hashMap.put("parent_id",parent_id);
        Call<NFCWorkerBean> depart = inventoryApi.getDepart(new HashMap());
        depart.enqueue(new MyCallback<NFCWorkerBean>() {
            @Override
            public void onResponse(Call<NFCWorkerBean> call, Response<NFCWorkerBean> response) {
                if (response.body() == null) return;
                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                    TreeNode root = TreeNode.root();
                    NFCWorkerBean.ResultBean.ResDataBean resDataBean = response.body().getResult().getRes_data().get(0);
                    MyTreeHolder.ParentBean first = new MyTreeHolder.ParentBean(resDataBean.getDepartment_id(),"linkloving&robotime");
                    //创建节点item
                    MyTreeHolder.ParentBean nodeItem = new MyTreeHolder.ParentBean(resDataBean.getDepartment_id(), resDataBean.getName());
                    MyTreeHolder.ParentBean nodeItem2 = new MyTreeHolder.ParentBean(resDataBean.getDepartment_id(),resDataBean.getEmployees().get(0).getName());
                  //  MyTreeHolder.ParentBean nodeItem3 = new MyTreeHolder.ParentBean(resDataBean.getDepartment_id(), "我的");
                    //创建一般节点
                    TreeNode device = new TreeNode(nodeItem).setViewHolder(new MyTreeHolder(NFCInsetActivity.this));;
                    TreeNode firstNode = new TreeNode(first).setViewHolder(new MyTreeHolder(NFCInsetActivity.this));;
                    TreeNode fold = new TreeNode(nodeItem2).setViewHolder(new MyTreeHolder(NFCInsetActivity.this));;
                 //   TreeNode file = new TreeNode(nodeItem3).setViewHolder(new MyTreeHolder(NFCInsetActivity.this));;
                    //添加子节点
                 //   device.addChild(file);
                    firstNode.addChildren(fold,device);
                    root.addChild(firstNode);
                    //创建树形视图
                    AndroidTreeView tView = new AndroidTreeView(NFCInsetActivity.this, root);
                    //设置树形视图开启默认动画
                    tView.setDefaultAnimation(true);
                    //设置树形视图默认的样式
                    tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
                    tView.setDefaultNodeClickListener(nodeClickListener);
                    //设置树形视图默认的ViewHolder
                    tView.setDefaultViewHolder(MyTreeHolder.class);
                    //将树形视图添加到layout中
                    container.addView(tView.getView());
                }
            }

            @Override
            public void onFailure(Call<NFCWorkerBean> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(NFCInsetActivity.this, t.toString());
            }
        });
    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            if (value instanceof MyTreeHolder.ParentBean){
                MyTreeHolder.ParentBean item = (MyTreeHolder.ParentBean) value;
                HashMap<Object, Object> hashMap = new HashMap<>();
                hashMap.put("parent_id",((MyTreeHolder.ParentBean) value).getDepart_id());
                Call<NFCWorkerBean> depart = inventoryApi.getDepart(hashMap);
                depart.enqueue(new MyCallback<NFCWorkerBean>() {
                    @Override
                    public void onResponse(Call<NFCWorkerBean> call, Response<NFCWorkerBean> response) {
                        if (response.body() == null)return;

                    }

                    @Override
                    public void onFailure(Call<NFCWorkerBean> call, Throwable t) {
                        super.onFailure(call, t);
                    }
                });
                ToastUtils.showCommonToast(NFCInsetActivity.this, "ererer");
            }
        }
    };
}
