package tarce.myodoo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newland.me.ConnUtils;
import com.newland.me.DeviceManager;
import com.newland.mtype.ConnectionCloseEvent;
import com.newland.mtype.ModuleType;
import com.newland.mtype.event.DeviceEventListener;
import com.newland.mtype.module.common.rfcard.RFCardModule;
import com.newland.mtype.module.common.rfcard.RFResult;
import com.newland.mtype.util.ISOUtils;
import com.newland.mtypex.nseries.NSConnV100ConnParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tarce.api.MyCallback;
import tarce.api.OKHttpFactory;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.BuLlBean;
import tarce.model.inventory.NfcOrderBean;
import tarce.model.inventory.OrderDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.product.BuGetLiaoAdapter;
import tarce.myodoo.device.Const;
import tarce.myodoo.uiutil.FullyLinearLayoutManager;
import tarce.myodoo.uiutil.InsertNumDialog;
import tarce.myodoo.uiutil.NFCdialog;
import tarce.support.AlertAialogUtils;
import tarce.support.MyLog;
import tarce.support.ToastUtils;

import static tarce.api.RetrofitClient.Url;

/**
 * Created by rose.zou on 2017/5/31.
 * 补领料页面
 */

public class BuGetLiaoActivity extends BaseActivity {
    private static final String K21_DRIVER_NAME = "com.newland.me.K21Driver";
    @InjectView(R.id.recycler_bu_getliao)
    RecyclerView recyclerBuGetliao;
    @InjectView(R.id.tv_bottom_bu)
    TextView tvBottomBu;
    @InjectView(R.id.recycler_banchengp)
    RecyclerView recyclerBanchengp;
    @InjectView(R.id.recycler_liuzhuanpin)
    RecyclerView recyclerLiuzhuanpin;

    private OrderDetailBean.ResultBean.ResDataBean resDataBean;
    private BuGetLiaoAdapter adapter;
    private BuGetLiaoAdapter adapter_two;
    private BuGetLiaoAdapter adapter_three;
    private int order_id;
    private DeviceManager deviceManager;
    private RFCardModule rfCardModule;
    private NFCdialog nfCdialog;
    private Retrofit retrofit;
    private InventoryApi inventoryApi;
    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list_one;
    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list_two;
    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list_three;
    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list_all = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bu_getliao);
        ButterKnife.inject(this);

        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(new OKHttpFactory(BuGetLiaoActivity.this).getOkHttpClient())
                .baseUrl(Url + "/linkloving_user_auth/")
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        Url = RetrofitClient.Url;
        inventoryApi = RetrofitClient.getInstance(BuGetLiaoActivity.this).create(InventoryApi.class);
        setTitle("补料完成");
        recyclerBuGetliao.setLayoutManager(new FullyLinearLayoutManager(BuGetLiaoActivity.this));
        recyclerBuGetliao.addItemDecoration(new DividerItemDecoration(BuGetLiaoActivity.this,
                DividerItemDecoration.VERTICAL));
        recyclerBanchengp.setLayoutManager(new FullyLinearLayoutManager(BuGetLiaoActivity.this));
        recyclerBanchengp.addItemDecoration(new DividerItemDecoration(BuGetLiaoActivity.this,
                DividerItemDecoration.VERTICAL));
        recyclerLiuzhuanpin.setLayoutManager(new FullyLinearLayoutManager(BuGetLiaoActivity.this));
        recyclerLiuzhuanpin.addItemDecoration(new DividerItemDecoration(BuGetLiaoActivity.this,
                DividerItemDecoration.VERTICAL));
        recyclerBuGetliao.setNestedScrollingEnabled(false);
        recyclerBanchengp.setNestedScrollingEnabled(false);
        recyclerLiuzhuanpin.setNestedScrollingEnabled(false);
        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 1);
        resDataBean = (OrderDetailBean.ResultBean.ResDataBean) intent.getSerializableExtra("value");
        String state = intent.getStringExtra("state");
        List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> stock_move_lines = resDataBean.getStock_move_lines();
        list_one = new ArrayList<>();
        list_two = new ArrayList<>();
        list_three = new ArrayList<>();
        for (int i = 0; i < stock_move_lines.size(); i++) {
            String s = String.valueOf(stock_move_lines.get(i).getProduct_type());
            if (s.equals("material")){
                list_one.add(stock_move_lines.get(i));
            }else if (s.equals("real_semi_finished")){
                list_two.add(stock_move_lines.get(i));
            }else if (s.equals("semi-finished")){
                list_three.add(stock_move_lines.get(i));
            }
        }
        adapter = new BuGetLiaoAdapter(R.layout.bu_ll_adapter, list_one, state);
        recyclerBuGetliao.setAdapter(adapter);
        adapter_two = new BuGetLiaoAdapter(R.layout.bu_ll_adapter, list_two, state);
        recyclerBanchengp.setAdapter(adapter_two);
        adapter_three = new BuGetLiaoAdapter(R.layout.bu_ll_adapter, list_three, state);
        recyclerLiuzhuanpin.setAdapter(adapter_three);

        initAdapter(adapter);
        initAdapter(adapter_two);
        initAdapter(adapter_three);
    }

    /**
     * adapter点击事件
     * */
    private void initAdapter(final BuGetLiaoAdapter adapter_type) {
        adapter_type.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final BaseQuickAdapter adapter, View view, final int position) {
                final List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> data =
                        (List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean>) adapter_type.getData();
                new InsertNumDialog(BuGetLiaoActivity.this, R.style.MyDialogStyle, new InsertNumDialog.OnSendCommonClickListener() {
                    @Override
                    public void OnSendCommonClick(final int num) {
                        if (num > data.get(position).getQty_available()) {
                            ToastUtils.showCommonToast(BuGetLiaoActivity.this, "库存不足");
                        } else {
                            final String product_type = data.get(position).getProduct_type();
                            if (product_type.equals("material") || product_type.equals("real_semi_finished")) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        initDevice();
                                        processingLock();
                                        showNfcDialog();
                                        try {
                                            final RFResult qPResult = rfCardModule.powerOn(null, 6, TimeUnit.SECONDS);
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (qPResult.getCardSerialNo() == null) {
                                                        ToastUtils.showCommonToast(BuGetLiaoActivity.this, "不能识别序列号：" + Const.MessageTag.DATA);
                                                    } else {
                                                        showDefultProgressDialog();
                                                        String NFC_Number = ISOUtils.hexString(qPResult.getCardSerialNo());
                                                        InventoryApi inventory = retrofit.create(InventoryApi.class);
                                                        HashMap<Object, Object> hashMap = new HashMap<>();
                                                        hashMap.put("card_num", NFC_Number);
                                                        Call<NfcOrderBean> objectCall = inventory.authWarehouse(hashMap);
                                                        objectCall.enqueue(new Callback<NfcOrderBean>() {
                                                            @Override
                                                            public void onResponse(Call<NfcOrderBean> call, Response<NfcOrderBean> response) {
                                                                dismissDefultProgressDialog();
                                                                if (response.body() == null) return;
                                                                if (response.body().getError() != null) {
                                                                    nfCdialog.setHeaderImage(R.drawable.warning)
                                                                            .setTip(response.body().getError().getData().getMessage())
                                                                            .setCancelVisi().show();
                                                                    threadDismiss(nfCdialog);
                                                                } else if (response.body().getResult() != null && response.body().getResult().getRes_code() == -1) {
                                                                    nfCdialog.setHeaderImage(R.drawable.warning)
                                                                            .setTip(response.body().getResult().getRes_data().getErrorX())
                                                                            .setCancelVisi().show();
                                                                    threadDismiss(nfCdialog);
                                                                } else if (response.body().getResult() != null && response.body().getResult().getRes_code() == 1) {
                                                                    final NfcOrderBean.ResultBean.ResDataBean res_data = response.body().getResult().getRes_data();
                                                                    nfCdialog.setHeaderImage(R.drawable.defaultimage)
                                                                            .setTip(res_data.getName() + res_data.getEmployee_id() + "\n" + res_data.getWork_email()
                                                                                    + "\n\n" + "打卡成功")
                                                                            .setCancelVisi().show();
                                                                    threadDismiss(nfCdialog);
                                                                    showDefultProgressDialog();
                                                                    HashMap<Object, Object> hashMap = new HashMap<>();
                                                                    hashMap.put("order_id", order_id);
                                                                    hashMap.put("employee_id",res_data.getEmployee_id());
                                                                    Map<Object, Object> mapSmall = new HashMap<>();
                                                                    mapSmall.put("stock_move_lines_id", data.get(position).getId());
                                                                    mapSmall.put("quantity_ready", num);
                                                                    mapSmall.put("order_id", data.get(position).getOrder_id());
                                                                    hashMap.put("stock_move", mapSmall);
                                                                    Call<OrderDetailBean> objectCall = inventoryApi.newPrepareMater(hashMap);
                                                                    objectCall.enqueue(new Callback<OrderDetailBean>() {
                                                                        @Override
                                                                        public void onResponse(Call<OrderDetailBean> call, final Response<OrderDetailBean> response) {
                                                                            dismissDefultProgressDialog();
                                                                            if (response.body() == null || response.body().getResult() == null)
                                                                                return;
                                                                            if (response.body().getResult().getRes_code() == 1) {
                                                                                runOnUiThread(new Runnable() {
                                                                                    @Override
                                                                                    public void run() {
                                                                                        resDataBean = response.body().getResult().getRes_data();
                                                                                        list_one = new ArrayList<>();
                                                                                        list_two = new ArrayList<>();
                                                                                        for (int i = 0; i < resDataBean.getStock_move_lines().size(); i++) {
                                                                                            if (resDataBean.getStock_move_lines().get(i).getProduct_type().equals("material")) {
                                                                                                list_one.add(resDataBean.getStock_move_lines().get(i));
                                                                                            } else if (resDataBean.getStock_move_lines().get(i).getProduct_type().equals("real_semi_finished")) {
                                                                                                list_two.add(resDataBean.getStock_move_lines().get(i));
                                                                                            }
                                                                                        }
                                                                                        if (product_type.equals("material")){
                                                                                            data.get(position).setOver_picking_qty(num);
                                                                                            data.get(position).setQuantity_done(list_one.get(position).getQuantity_done());
                                                                                            data.get(position).setQty_available(list_one.get(position).getQty_available());
                                                                                            data.get(position).setBlue(true);
                                                                                        }else if (product_type.equals("real_semi_finished")){
                                                                                            data.get(position).setOver_picking_qty(num);
                                                                                            data.get(position).setQuantity_done(list_two.get(position).getQuantity_done());
                                                                                            data.get(position).setQty_available(list_two.get(position).getQty_available());
                                                                                            data.get(position).setBlue(true);
                                                                                        }
                                                                                        adapter_type.notifyDataSetChanged();
                                                                                    }
                                                                                });
                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<OrderDetailBean> call, Throwable t) {
                                                                            dismissDefultProgressDialog();
                                                                        }
                                                                    });
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<NfcOrderBean> call, Throwable t) {
                                                                dismissDefultProgressDialog();
                                                                Log.e("zws", t.toString());
                                                            }
                                                        });
                                                    }
                                                    processingUnLock();
                                                }
                                            });
                                        } catch (final Exception e) {
                                            e.fillInStackTrace();
                                            if (e.getMessage().equals("device invoke timeout!7")) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            Thread.sleep(1000);
                                                            //ToastUtils.showCommonToast(BuGetLiaoActivity.this, "读卡时间超过10秒");
                                                            nfCdialog.dismiss();
                                                        } catch (InterruptedException e1) {
                                                            e1.printStackTrace();
                                                        }
                                                    }
                                                });
                                            }
                                            processingUnLock();
                                        }
                                    }
                                }).start();
                            } else {
                                showDefultProgressDialog();
                                HashMap<Object, Object> hashMap = new HashMap<>();
                                hashMap.put("order_id", order_id);
                                Map<Object, Object> mapSmall = new HashMap<>();
                                mapSmall.put("stock_move_lines_id", data.get(position).getId());
                                mapSmall.put("quantity_ready", num);
                                mapSmall.put("order_id", data.get(position).getOrder_id());
                                hashMap.put("stock_move", mapSmall);
                                Call<OrderDetailBean> objectCall = inventoryApi.newPrepareMater(hashMap);
                                objectCall.enqueue(new Callback<OrderDetailBean>() {
                                    @Override
                                    public void onResponse(Call<OrderDetailBean> call, final Response<OrderDetailBean> response) {
                                        dismissDefultProgressDialog();
                                        if (response.body() == null || response.body().getResult() == null)
                                            return;
                                        if (response.body().getResult().getRes_code() == 1) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    resDataBean = response.body().getResult().getRes_data();
                                                    list_three = new ArrayList<>();
                                                    for (int i = 0; i < resDataBean.getStock_move_lines().size(); i++) {
                                                        String product_type = resDataBean.getStock_move_lines().get(i).getProduct_type();
                                                        if (product_type.equals("semi-finished")){
                                                            list_three.add(resDataBean.getStock_move_lines().get(i));
                                                        }
                                                    }
                                                    data.get(position).setOver_picking_qty(num);
                                                    data.get(position).setQuantity_done(list_three.get(position).getQuantity_done());
                                                    data.get(position).setQty_available(list_three.get(position).getQty_available());
                                                    data.get(position).setBlue(true);
                                                    adapter_type.notifyDataSetChanged();
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<OrderDetailBean> call, Throwable t) {
                                        dismissDefultProgressDialog();
                                    }
                                });
                            }
                        }
                    }
                }, data.get(position).getProduct_id())
                        .changeTitle("填写 " + data.get(position).getProduct_id() + "的补领料数量")
                        .dismissTip().show();
            }
        });
    }

    @OnClick(R.id.tv_bottom_bu)
    void buLl(View view) {
        AlertAialogUtils.getCommonDialog(BuGetLiaoActivity.this, "")
                .setMessage("是否确定")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                        /*showDefultProgressDialog();
                        HashMap<Object, Object> hashMap = new HashMap<>();
                        hashMap.put("order_id", order_id);
                        list_all.addAll(adapter.getData());
                        list_all.addAll(adapter_two.getData());
                        list_all.addAll(adapter_three.getData());
                        Map[] maps = new Map[list_all.size()];
                        for (int i = 0; i < list_all.size(); i++) {
                            Map<Object, Object> objectMap = new HashMap<>();
                            objectMap.put("order_id", list_all.get(i).getOrder_id());
                            objectMap.put("over_picking_qty", list_all.get(i).getOver_picking_qty());
                            objectMap.put("stock_move_lines_id", list_all.get(i).getId());
                            maps[i] = objectMap;
                        }
                        hashMap.put("stock_moves", maps);
                        Call<BuLlBean> objectCall = inventoryApi.buLl(hashMap);
                        objectCall.enqueue(new MyCallback<BuLlBean>() {
                            @Override
                            public void onResponse(Call<BuLlBean> call, Response<BuLlBean> response) {
                                dismissDefultProgressDialog();
                                if (response.body() == null || response.body().getResult() == null)
                                    return;
                                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<BuLlBean> call, Throwable t) {
                                dismissDefultProgressDialog();
                                ToastUtils.showCommonToast(BuGetLiaoActivity.this, t.toString());
                            }
                        });
                    }*/
                }).show();
    }

    //显示nfc的dialog
    private void showNfcDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nfCdialog = new NFCdialog(BuGetLiaoActivity.this);
                nfCdialog.setCancel(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        processingUnLock();
                        nfCdialog.dismiss();
                        return;
                    }
                }).show();
            }
        });
    }

    //关闭dialog
    private void threadDismiss(final NFCdialog dialog) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    dialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 连接设备打印机
     */
    private void initDevice() {
        deviceManager = ConnUtils.getDeviceManager();
        try {
            deviceManager.init(BuGetLiaoActivity.this, K21_DRIVER_NAME, new NSConnV100ConnParams(), new DeviceEventListener<ConnectionCloseEvent>() {
                @Override
                public void onEvent(ConnectionCloseEvent connectionCloseEvent, Handler handler) {
                    if (connectionCloseEvent.isSuccess()) {
                        ToastUtils.showCommonToast(BuGetLiaoActivity.this, "设备被客户主动断开！");
                    }
                    if (connectionCloseEvent.isFailed()) {
                        ToastUtils.showCommonToast(BuGetLiaoActivity.this, "设备链接异常断开！");
                    }
                }

                @Override
                public Handler getUIHandler() {
                    return null;
                }
            });
            deviceManager.connect();
            MyLog.e("OrderDetailActivity", "连接成功");
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showCommonToast(BuGetLiaoActivity.this, "链接异常,请检查设备或重新连接.." + e);
        }
        try {
            rfCardModule = (RFCardModule) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_RFCARDREADER);
        }catch (Exception e){
            Log.e("zws", "error");
        }
    }

    public void processingLock() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                SharedPreferences setting = getSharedPreferences("setting", 0);
                SharedPreferences.Editor editor = setting.edit();
                editor.putBoolean("PBOC_LOCK", true);
                editor.commit();
            }
        });

    }

    public void processingUnLock() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences setting = getSharedPreferences("setting", 0);
                SharedPreferences.Editor editor = setting.edit();
                editor.putBoolean("PBOC_LOCK", false);
                editor.commit();
            }
        });

    }
}
