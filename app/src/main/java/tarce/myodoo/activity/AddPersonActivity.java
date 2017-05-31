package tarce.myodoo.activity;

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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.AddworkBean;
import tarce.model.inventory.FreeWorkBean;
import tarce.model.inventory.WorkingWorkerBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.processproduct.AreaMessageAdapter;
import tarce.myodoo.adapter.product.WorkPersonAdapter;
import tarce.myodoo.adapter.product.WorkingPersonAdapter;
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
    @InjectView(R.id.icon_to_left)
    ImageView iconToLeft;
    @InjectView(R.id.icon_to_right)
    ImageView iconToRight;
    @InjectView(R.id.recycler_person_work)
    RecyclerView recyclerPersonWork;
    @InjectView(R.id.tv_add_true)
    TextView tvAddTrue;
    private InventoryApi inventoryApi;
    private int order_id;
    private WorkPersonAdapter adapter;
    private List<FreeWorkBean.ResultBean.ResDataBean> res_data;
    private WorkingPersonAdapter personAdapter;
    private List<String> res_data_working;
    private List<String> add_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        ButterKnife.inject(this);
        setTitle("添加员工");

        setRecyclerview(recyclerPersonWait);
        setRecyclerview(recyclerPersonWork);

        initFragment();
        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 1);
        inventoryApi = RetrofitClient.getInstance(AddPersonActivity.this).create(InventoryApi.class);
        getFreeWork();
        getWorking();
    }

    /**
     * 工作中员工的数据请求
     */
    private void getWorking() {
        res_data_working = new ArrayList<>();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("order_id", order_id);
        Call<WorkingWorkerBean> working = inventoryApi.getWorking(hashMap);
        working.enqueue(new MyCallback<WorkingWorkerBean>() {
            @Override
            public void onResponse(Call<WorkingWorkerBean> call, Response<WorkingWorkerBean> response) {
                if (response.body() == null) return;
                if (response.body().getResult().getRes_code() == 1) {
                    List<WorkingWorkerBean.ResultBean.ResDataBean> res_dataTwo = response.body().getResult().getRes_data();
                    for (int i = 0; i < res_dataTwo.size(); i++) {
                        res_data_working.add(res_dataTwo.get(i).getWorker().getName());
                    }
                    personAdapter = new WorkingPersonAdapter(R.layout.adapte_working_person, res_data_working);
                    recyclerPersonWork.setAdapter(personAdapter);
                }
            }

            @Override
            public void onFailure(Call<WorkingWorkerBean> call, Throwable t) {
                super.onFailure(call, t);
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
                if (response.body() == null) return;
                if (response.body().getResult().getRes_code() == 1) {
                    res_data = response.body().getResult().getRes_data();
                    adapter = new WorkPersonAdapter(res_data, AddPersonActivity.this);
                    recyclerPersonWait.setAdapter(adapter);
                }
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
                ToastUtils.showCommonToast(AddPersonActivity.this, result);
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
        add_name = new ArrayList<>();
        for (int i = 0; i < adapter.getSelected().size(); i++) {
            add_name.add(adapter.getSelected().get(i).getName());
        }
        res_data_working.addAll(add_name);
        personAdapter.notifyDataSetChanged();
    }

    /**
     * 添加完成的点击事件
     */
    @OnClick(R.id.tv_add_true)
    void add(View view) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("is_add", 1);
        hashMap.put("order_id", order_id);
        int[] work_id = new int[adapter.getSelected().size()];
        for (int i = 0; i < adapter.getSelected().size(); i++) {
            work_id[i] = adapter.getSelected().get(i).getWorker_id();
        }
        hashMap.put("worker_ids", work_id);
        Call<AddworkBean> objectCall = inventoryApi.addWork_id(hashMap);
        objectCall.enqueue(new MyCallback<AddworkBean>() {
            @Override
            public void onResponse(Call<AddworkBean> call, Response<AddworkBean> response) {
                if (response.body() == null) return;
                if (response.body().getResult().getRes_code() == 1) {

                }
            }

            @Override
            public void onFailure(Call<AddworkBean> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
}
