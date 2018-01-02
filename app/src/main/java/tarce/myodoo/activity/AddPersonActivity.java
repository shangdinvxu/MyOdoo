package tarce.myodoo.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newland.me.ConnUtils;
import com.newland.me.DeviceManager;
import com.newland.mtype.ConnectionCloseEvent;
import com.newland.mtype.ModuleType;
import com.newland.mtype.event.DeviceEventListener;
import com.newland.mtype.module.common.rfcard.RFCardModule;
import com.newland.mtype.module.common.rfcard.RFResult;
import com.newland.mtype.util.ISOUtils;
import com.newland.mtypex.nseries.NSConnV100ConnParams;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
import tarce.myodoo.activity.newproduct.NewProductDateActivity;
import tarce.myodoo.adapter.product.WorkPersonAdapter;
import tarce.myodoo.adapter.product.WorkingPersonAdapter;
import tarce.myodoo.bean.WorkingStateBean;
import tarce.myodoo.device.Const;
import tarce.myodoo.uiutil.TipDialog;
import tarce.support.AlertAialogUtils;
import tarce.support.MyLog;
import tarce.support.ToastUtils;

/**
 * Created by rose.zou on 2017/5/27.
 * 添加员工生产页面
 */

public class AddPersonActivity extends BaseActivity {
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
    @InjectView(R.id.tv_controll_scan)
    TextView tvControllScan;
    @InjectView(R.id.tv_nfc_open)
    TextView tvNfcOpen;
    @InjectView(R.id.time_tv)
    TextView timeTv;
    @InjectView(R.id.linear_seven)
    RelativeLayout linearSeven;
    private InventoryApi inventoryApi;
    private int order_id;
    private WorkPersonAdapter adapter;
    private List<FreeWorkBean.ResultBean.ResDataBean> res_data = new ArrayList<>();
    private WorkingPersonAdapter personAdapter;
    private List<String> res_data_working;
    private List<WorkingStateBean> add_name = new ArrayList<>();
    private List<WorkingWorkerBean.ResultBean.ResDataBean> res_dataTwo;
    private List<WorkingStateBean> adapterList = new ArrayList<>();
    private Map<String, Integer> map;//存放work的name和id
    private Map<String, String> map_nfc;//存放work的name和id
    private String state_activity;
    private String name_activity;
    private boolean close;
    private boolean showScan = false;
    private Dialog alertDialog;
    private TimeCount time;//时间计时类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        ButterKnife.inject(this);
        setTitle("员工");

        setRecyclerview(recyclerPersonWait);
        setRecyclerview(recyclerPersonWork);

        //   initFragment();
        showDefultProgressDialog();
        Intent intent = getIntent();
        close = intent.getBooleanExtra("close", true);
        order_id = intent.getIntExtra("order_id", 1);
        state_activity = intent.getStringExtra("state_activity");
        name_activity = intent.getStringExtra("name_activity");
        inventoryApi = RetrofitClient.getInstance(AddPersonActivity.this).create(InventoryApi.class);
        getFreeWork();
        getWorking();
        time = new TimeCount(10000,1000);
        if (close) {
            tvAddTrue.setVisibility(View.GONE);
        }
        initDevice();
    }

    @OnClick(R.id.linear_seven)
    void openNFC(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                time.start();
                try {
                    processingLock();
                    final RFResult qPResult = rfCardModule.powerOn(null, 10, TimeUnit.SECONDS);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (qPResult.getCardSerialNo() == null) {
                                ToastUtils.showCommonToast(AddPersonActivity.this, "非接卡序列号null: " + "" + Const.MessageTag.DATA);
                            } else {
                                String NFC_Number = ISOUtils.hexString(qPResult.getCardSerialNo());
                                boolean isHaveWork = false;
                                int index = -1;
                                for (int i = 0; i < res_data.size(); i++) {
                                    if (NFC_Number.equals(res_data.get(i).getCard_num())) {
                                        isHaveWork = true;
                                        index = i;
                                        break;
                                    } else {
                                        isHaveWork = false;
                                    }
                                }
                                if (isHaveWork) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(AddPersonActivity.this);
                                    View inflate = LayoutInflater.from(AddPersonActivity.this).inflate(R.layout.dialog_nfc_addwork, null);
                                    TextView textviewName = (TextView) inflate.findViewById(R.id.nfc_work_name);
                                    textviewName.setText(res_data.get(index).getName());
                                    TextView textNfcNum = (TextView) inflate.findViewById(R.id.tv_nfc_num);
                                    textNfcNum.setText(NFC_Number);
                                    ImageView imageHeader = (ImageView) inflate.findViewById(R.id.image_nfc_addwork);
                                    Glide.with(AddPersonActivity.this).load(res_data.get(index).getImage()).into(imageHeader);
                                    TextView textCancel = (TextView) inflate.findViewById(R.id.tv_cancel_addnfc);
                                    TextView textTrue = (TextView) inflate.findViewById(R.id.tv_true_addnfc);
                                    builder.setView(inflate);
                                    alertDialog = builder.create();
                                    alertDialog.show();
                                    textCancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            alertDialog.dismiss();
                                            time.onFinish();
                                        }
                                    });
                                    final int finalIndex = index;
                                    textTrue.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            time.onFinish();
                                            rfCardModule.powerOff(2);
                                            alertDialog.dismiss();
                                            adapter.notifyItemRemoved(finalIndex);
                                            Integer[] workId = {res_data.get(finalIndex).getWorker_id()};
                                            addWorkToClient(workId);
                                        }
                                    });
                                } else {
                                    time.onFinish();
                                    ToastUtils.showCommonToast(AddPersonActivity.this, "没有匹配到该序列号所属员工信息");
                                }
                            }
                            processingUnLock();
                        }
                    });

                } catch (final Exception e) {
                    e.fillInStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showCommonToast(AddPersonActivity.this, "非接卡序列号为空: " + "" + Const.MessageTag.DATA);
                        }
                    });
                    processingUnLock();
                }
            }
        }).start();
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
                if (response.body() == null)
                    return;
                if (response.body().getError()!=null){
                    new TipDialog(AddPersonActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                    res_dataTwo = response.body().getResult().getRes_data();
                    for (int i = 0; i < res_dataTwo.size(); i++) {
                        res_data_working.add(res_dataTwo.get(i).getWorker().getName());
                        adapterList.add(new WorkingStateBean(res_dataTwo.get(i).getWorker().getName(), res_dataTwo.get(i).getLine_state()));
                    }
                }
                personAdapter = new WorkingPersonAdapter(adapterList, AddPersonActivity.this, close);
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
                if(response.body().getError()!=null){
                    new TipDialog(AddPersonActivity.this, R.style.MyDialogStyle,response.body().getError().getData().getMessage())
                    .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                    res_data = response.body().getResult().getRes_data();
                    map = new HashMap<>();
                    for (int i = 0; i < res_data.size(); i++) {
                        map.put(res_data.get(i).getName(), res_data.get(i).getWorker_id());
                    }
                    adapter = new WorkPersonAdapter(res_data, AddPersonActivity.this);
                    recyclerPersonWait.setAdapter(adapter);
                } else {
                    Log.e("AddpersonActivity", "error");
                }
            }

            @Override
            public void onFailure(Call<FreeWorkBean> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(AddPersonActivity.this, t.toString());
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
                        if (response.body().getError() != null) {
                            ToastUtils.showCommonToast(AddPersonActivity.this, "不在员工列表中？");
                            return;
                        }
                        if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                            if (res_data_working.contains(response.body().getResult().getRes_data().getName())) {
                                ToastUtils.showCommonToast(AddPersonActivity.this, "已经添加该员工");
                            } else {
                                res_data_working.add(response.body().getResult().getRes_data().getName());
                                personAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.e("AddPersonActivity", "error");
                        }
                    }

                    @Override
                    public void onFailure(Call<AutoAddworkBean> call, Throwable t) {
                        ToastUtils.showCommonToast(AddPersonActivity.this, t.toString());
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
     * 联系服务器上传数据
     */
    @OnClick(R.id.icon_to_right)
    void addToRight(View view) {
        add_name.clear();
        for (int i = 0; i < adapter.getSelected().size(); i++) {
            if (!res_data_working.contains(adapter.getSelected().get(i).getName())) {
                add_name.add(new WorkingStateBean(adapter.getSelected().get(i).getName(), ""));
                res_data_working.add(adapter.getSelected().get(i).getName());
            } else {
              //  adapter.getSelected().remove(adapter.getSelected().get(i));
                ToastUtils.showCommonToast(AddPersonActivity.this, "已经添加该员工");
            }
        }
        res_data.removeAll(adapter.getSelected());
        adapter.notifyDataSetChanged();

        Integer[] work_id;
        if (add_name.size() == 0) {
            work_id = new Integer[res_dataTwo.size()];
            for (int i = 0; i < res_dataTwo.size(); i++) {
                work_id[i] = res_dataTwo.get(i).getWorker().getWorker_id();
            }
        } else {
            work_id = new Integer[res_data_working.size()];
            for (int i = 0; i < res_data_working.size(); i++) {
                work_id[i] = map.get(res_data_working.get(i));
            }
        }
        addWorkToClient(work_id);
    }

    /**
     * 向服务器添加员工
     */
    private void addWorkToClient(Integer[] work_id) {
        showDefultProgressDialog();
        final HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("is_add", 1);
        hashMap.put("order_id", order_id);
        hashMap.put("worker_ids", work_id);
        Call<AddworkBean> objectCall = inventoryApi.addWork_id(hashMap);
        objectCall.enqueue(new MyCallback<AddworkBean>() {
            @Override
            public void onResponse(Call<AddworkBean> call, Response<AddworkBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError()!=null){
                    new TipDialog(AddPersonActivity.this, R.style.MyDialogStyle,response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                    if (close) {
                        adapterList.clear();
                        for (int i = 0; i < response.body().getResult().getRes_data().size(); i++) {
                            adapterList.add(new WorkingStateBean(response.body().getResult().getRes_data().get(i).getWorker().getName(), response.body().getResult().getRes_data().get(i).getLine_state()));
                        }
                        personAdapter.setClose(true);
                        personAdapter.notifyDataSetChanged();
                    } else {
                        adapterList.addAll(add_name);
                        personAdapter.notifyDataSetChanged();
                    }
                } else {
                    //ToastUtils.showCommonToast(AddPersonActivity.this, "some error,please try again");
                    Log.e("zws", "数据异常");
                }
            }

            @Override
            public void onFailure(Call<AddworkBean> call, Throwable t) {
                ToastUtils.showCommonToast(AddPersonActivity.this, t.toString());
            }
        });
    }

    /**
     * 添加完成的点击事件
     */
    @OnClick(R.id.tv_add_true)
    void add(View view) {
        AlertAialogUtils.getCommonDialog(AddPersonActivity.this, "").setMessage("员工添加完成，确定开始生产？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showDefultProgressDialog();
                        HashMap<Object, Object> hashMap1 = new HashMap<>();
                        hashMap1.put("order_id", order_id);
                        Call<StartProductBean> objectCall1 = inventoryApi.startProduct(hashMap1);
                        objectCall1.enqueue(new MyCallback<StartProductBean>() {
                            @Override
                            public void onResponse(Call<StartProductBean> call, Response<StartProductBean> response) {
                                dismissDefultProgressDialog();
                                if (response.body() == null) return;
                                if (response.body().getError()!=null){
                                    new TipDialog(AddPersonActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                            .show();
                                    return;
                                }
                                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                                    Intent intent = new Intent(AddPersonActivity.this, NewProductDateActivity.class);
                                   // intent.putExtra("state_delay", name_activity);
                                    startActivity(intent);
                                    finish();
                                } else {
                                   // ToastUtils.showCommonToast(AddPersonActivity.this, "出现错误，请联系开发人员调试");
                                    Log.e("zws", "数据异常");
                                }
                            }

                            @Override
                            public void onFailure(Call<StartProductBean> call, Throwable t) {
                                dismissDefultProgressDialog();
                                ToastUtils.showCommonToast(AddPersonActivity.this, t.toString());
                            }
                        });
                    }
                }).show();
    }

    /**
     * 改变员工状态
     */
    private void changeState(final int position, final String state) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("state", state);
        if (map.get(res_data_working.get(position)) == null) {
            hashMap.put("worker_line_id", res_dataTwo.get(position).getWorker().getWorker_id());
        } else {
            hashMap.put("worker_line_id", map.get(res_data_working.get(position)));
        }
        Call<ChangeStateBean> objectCall = inventoryApi.changeState(hashMap);
        objectCall.enqueue(new MyCallback<ChangeStateBean>() {
            @Override
            public void onResponse(Call<ChangeStateBean> call, Response<ChangeStateBean> response) {
                if (response.body() == null) return;
                if (response.body().getError()!=null){
                    new TipDialog(AddPersonActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                    adapterList.set(position, new WorkingStateBean(adapterList.get(position).getName(), state));
                    personAdapter.notifyItemChanged(position);
                } else {
                   // ToastUtils.showCommonToast(AddPersonActivity.this, "出现错误，请联系开发人员调试");
                    Log.e("zws", "数据异常");
                }
            }

            @Override
            public void onFailure(Call<ChangeStateBean> call, Throwable t) {
                ToastUtils.showCommonToast(AddPersonActivity.this, t.toString());
            }
        });
    }

    @OnClick(R.id.tv_controll_scan)
    void controllScan(View view) {
        if (showScan) {
            fragmentScan.setVisibility(View.GONE);
            tvControllScan.setText("打开扫描");
            showScan = false;
        } else {
            initFragment();
            fragmentScan.setVisibility(View.VISIBLE);
            tvControllScan.setText("关闭扫描");
            showScan = true;
        }
    }

    /**
     * 获取验证码倒计时实现类
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            timeTv.setVisibility(View.GONE);
            tvNfcOpen.setText("打开NFC");
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            final long time = millisUntilFinished / 1000;
            tvNfcOpen.setText("请将卡靠近NFC感应区");
            timeTv.setVisibility(View.VISIBLE);
            timeTv.setText("("+time+")");
        }
    }
}
