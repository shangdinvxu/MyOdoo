package tarce.myodoo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.AddworkBean;
import tarce.model.ChangeStateBean;
import tarce.model.inventory.AutoAddworkBean;
import tarce.model.inventory.FreeWorkBean;
import tarce.model.inventory.StartProductBean;
import tarce.model.inventory.WorkingWorkerBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.processproduct.AreaMessageAdapter;
import tarce.myodoo.adapter.product.WorkPersonAdapter;
import tarce.myodoo.adapter.product.WorkingPersonAdapter;
import tarce.myodoo.bean.WorkingStateBean;
import tarce.support.AlertAialogUtils;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/5/27.
 * 添加员工生产页面
 */

public class AddPersonActivity extends ToolBarActivity {
    private static final String TAG = "AddPersonActivity";
    @InjectView(R.id.fragment_scan)
    FrameLayout fragmentScan;
    @InjectView(R.id.linear_title)
    LinearLayout linearTitle;
    @InjectView(R.id.recycler_person_wait)
    RecyclerView recyclerPersonWait;
    /*@InjectView(R.id.icon_to_left)
    ImageView iconToLeft;*/
    @InjectView(R.id.icon_to_right)
    ImageView iconToRight;
    @InjectView(R.id.recycler_person_work)
    RecyclerView recyclerPersonWork;
    @InjectView(R.id.tv_add_true)
    TextView tvAddTrue;
    private InventoryApi inventoryApi;
    private int order_id;
    private WorkPersonAdapter adapter;
    private List<FreeWorkBean.ResultBean.ResDataBean> res_data = new ArrayList<>();
    private WorkingPersonAdapter personAdapter;
    private List<String> res_data_working;
    private List<WorkingStateBean> add_name = new ArrayList<>();
    ;
    private List<WorkingWorkerBean.ResultBean.ResDataBean> res_dataTwo;
    private List<WorkingStateBean> adapterList = new ArrayList<>();
    private Map<String, Integer> map;//存放work的name和id
    private String state_activity;
    private String name_activity;
    private boolean close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        ButterKnife.inject(this);
        setTitle("添加员工");

        setRecyclerview(recyclerPersonWait);
        setRecyclerview(recyclerPersonWork);

        initFragment();
        showDefultProgressDialog();
        Intent intent = getIntent();
        close = intent.getBooleanExtra("close", true);
        order_id = intent.getIntExtra("order_id", 1);
        state_activity = intent.getStringExtra("state_activity");
        name_activity = intent.getStringExtra("name_activity");
        inventoryApi = RetrofitClient.getInstance(AddPersonActivity.this).create(InventoryApi.class);
        getFreeWork();
        getWorking();
        if (close) {
            tvAddTrue.setVisibility(View.GONE);
        }
    }

    /**
     * 工作中员工的数据请求
     */
    private void getWorking() {
        res_data_working = new ArrayList<>();
        res_dataTwo = new ArrayList<>();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("order_id", order_id);
        Call<WorkingWorkerBean> working = inventoryApi.getWorking(hashMap);
        working.enqueue(new MyCallback<WorkingWorkerBean>() {
            @Override
            public void onResponse(Call<WorkingWorkerBean> call, Response<WorkingWorkerBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null && response.body().getResult().getRes_data() == null)
                    return;
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                    res_dataTwo = response.body().getResult().getRes_data();
                    for (int i = 0; i < res_dataTwo.size(); i++) {
                        res_data_working.add(res_dataTwo.get(i).getWorker().getName());
                        adapterList.add(new WorkingStateBean(res_dataTwo.get(i).getWorker().getName(), res_dataTwo.get(i).getLine_state()));
                    }
                }
                personAdapter = new WorkingPersonAdapter(adapterList, AddPersonActivity.this, false);
                recyclerPersonWork.setAdapter(personAdapter);
                personAdapter.setOnSwipeListener(new WorkingPersonAdapter.onSwipeListener() {
                    @Override
                    public void onOffline(int position) {
                        changeState(position, "offline");
                    }

                    @Override
                    public void onOnline(int position) {
                        changeState(position, "online");
                    }

                    @Override
                    public void onOutline(int position) {
                        changeState(position, "outline");
                    }
                });

            }

            @Override
            public void onFailure(Call<WorkingWorkerBean> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
    }

    /**
     * 等待员工的数据请求
     */
    private void getFreeWork() {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("order_id", order_id);
        Call<FreeWorkBean> freeWorkers = inventoryApi.getFreeWorkers(hashMap);
        freeWorkers.enqueue(new MyCallback<FreeWorkBean>() {
            @Override
            public void onResponse(Call<FreeWorkBean> call, Response<FreeWorkBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                    res_data = response.body().getResult().getRes_data();
                    map = new HashMap<>();
                    for (int i = 0; i < res_data.size(); i++) {
                        map.put(res_data.get(i).getName(), res_data.get(i).getWorker_id());
                    }
                    adapter = new WorkPersonAdapter(res_data, AddPersonActivity.this);
                    recyclerPersonWait.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<FreeWorkBean> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
    }

    /**
     * 初始化扫描模块, 扫描之后自动添加
     */
    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CaptureFragment captureFragment = new CaptureFragment();
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        // TODO: 2017/5/24 扫描的回调
        captureFragment.setAnalyzeCallback(new CodeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                Log.i(TAG, "result = " + result);
                HashMap<Object, Object> hashMap = new HashMap<>();
                hashMap.put("order_id", order_id);
                hashMap.put("is_add", 0);
                hashMap.put("barcode", result);
                Call<AutoAddworkBean> objectCall = inventoryApi.addWorker(hashMap);
                objectCall.enqueue(new MyCallback<AutoAddworkBean>() {
                    @Override
                    public void onResponse(Call<AutoAddworkBean> call, Response<AutoAddworkBean> response) {
                        if (response.body() == null) return;
                        if (response.body().getResult().getRes_code() == 1) {
                            if (res_data_working.contains(response.body().getResult().getRes_data().getName())) {
                                ToastUtils.showCommonToast(AddPersonActivity.this, "已经添加该员工");
                            } else {
                                res_data_working.add(response.body().getResult().getRes_data().getName());
                                personAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AutoAddworkBean> call, Throwable t) {
                        super.onFailure(call, t);
                    }
                });
            }

            @Override
            public void onAnalyzeFailed() {

            }
        });
        fragmentTransaction.replace(R.id.fragment_scan, captureFragment);
        fragmentTransaction.commit();
    }

    /**
     * 点击向右的按钮添加
     * 只是实现界面效果，并不联系服务器添加
     */
    @OnClick(R.id.icon_to_right)
    void addToRight(View view) {
        add_name.clear();
        for (int i = 0; i < adapter.getSelected().size(); i++) {
            if (!res_data_working.contains(adapter.getSelected().get(i).getName())) {
                add_name.add(new WorkingStateBean(adapter.getSelected().get(i).getName(), ""));
                res_data_working.add(adapter.getSelected().get(i).getName());
            } /*else {
                ToastUtils.showCommonToast(AddPersonActivity.this, "已经添加该员工");
            }*/
        }
        adapterList.addAll(add_name);
        personAdapter.notifyDataSetChanged();
        res_data.removeAll(adapter.getSelected());
        adapter.notifyDataSetChanged();
    }

    /**
     * 添加完成的点击事件
     */
    @OnClick(R.id.tv_add_true)
    void add(View view) {
        showDefultProgressDialog();
        final HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("is_add", 1);
        hashMap.put("order_id", order_id);
        Integer[] work_id;
        if (add_name.size() == 0){
            work_id = new Integer[res_dataTwo.size()];
            for (int i = 0; i < res_dataTwo.size(); i++) {
                work_id[i] = res_dataTwo.get(i).getWorker().getWorker_id();
            }
        }else {
            work_id = new Integer[res_data_working.size()];
            for (int i = 0; i < res_data_working.size(); i++) {
                work_id[i] = map.get(res_data_working.get(i));
            }
        }
        hashMap.put("worker_ids", work_id);
        Call<AddworkBean> objectCall = inventoryApi.addWork_id(hashMap);
        objectCall.enqueue(new MyCallback<AddworkBean>() {
            @Override
            public void onResponse(Call<AddworkBean> call, Response<AddworkBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getResult().getRes_code() == 1) {
                    AlertAialogUtils.getCommonDialog(AddPersonActivity.this, "").setMessage("员工添加完成，确定开始生产？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    showDefultProgressDialog();
                                    HashMap<Object, Object> hashMap1 = new HashMap<Object, Object>();
                                    hashMap1.put("order_id", order_id);
                                    Call<StartProductBean> objectCall1 = inventoryApi.startProduct(hashMap1);
                                    objectCall1.enqueue(new MyCallback<StartProductBean>() {
                                        @Override
                                        public void onResponse(Call<StartProductBean> call, Response<StartProductBean> response) {
                                            dismissDefultProgressDialog();
                                            if (response.body() == null) return;
                                            if (response.body().getResult().getRes_code() == 1){
                                                Intent intent = new Intent(AddPersonActivity.this, ProductLlActivity.class);
                                                intent.putExtra("name_activity", name_activity);
                                                intent.putExtra("state_product", state_activity);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<StartProductBean> call, Throwable t) {
                                            dismissDefultProgressDialog();
                                        }
                                    });
                                }
                            }).show();
                }
            }

            @Override
            public void onFailure(Call<AddworkBean> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    /**
     * 改变员工状态
     */
    private void changeState(final int position, final String state) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("state", state);
        hashMap.put("worker_line_id", map.get(res_data_working.get(position)));
        Call<ChangeStateBean> objectCall = inventoryApi.changeState(hashMap);
        objectCall.enqueue(new MyCallback<ChangeStateBean>() {
            @Override
            public void onResponse(Call<ChangeStateBean> call, Response<ChangeStateBean> response) {
                if (response.body() == null) return;
                if (response.body().getResult().getRes_code() == 1) {
                    adapterList.set(position, new WorkingStateBean(adapterList.get(position).getName(), state));
                    personAdapter.notifyItemChanged(position);
                }
            }

            @Override
            public void onFailure(Call<ChangeStateBean> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    /*@Override
    protected void onDestroy() {
        dismissDefultProgressDialog();
        super.onDestroy();
    }*/
}
