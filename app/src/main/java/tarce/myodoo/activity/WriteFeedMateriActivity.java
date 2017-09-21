package tarce.myodoo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
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
import tarce.model.inventory.GetReturnMaterBean;
import tarce.model.inventory.NfcOrderBean;
import tarce.model.inventory.OrderDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.product.WriteFeedAdapter;
import tarce.myodoo.adapter.product.WriteFeedbackNumAdapter;
import tarce.myodoo.device.Const;
import tarce.myodoo.uiutil.FullyLinearLayoutManager;
import tarce.myodoo.uiutil.InsertNumDialog;
import tarce.myodoo.uiutil.NFCdialog;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.StringUtils;
import tarce.support.AlertAialogUtils;
import tarce.support.MyLog;
import tarce.support.ToastUtils;

import static tarce.api.RetrofitClient.Url;

/**
 * Created by rose.zou on 2017/6/5.
 * 填写退料页面
 */

public class WriteFeedMateriActivity extends BaseActivity {
    @InjectView(R.id.recycler_feed_material)
    RecyclerView recyclerFeedMaterial;
    @InjectView(R.id.tv_commit_feednum)
    TextView tvCommitFeednum;
    @InjectView(R.id.tv_yuancailiao)
    TextView tvYuancailiao;
    @InjectView(R.id.tv_banchengpin)
    TextView tvBanchengpin;
    @InjectView(R.id.recycler_semematerial)
    RecyclerView recyclerSemematerial;
    @InjectView(R.id.tv_liuzhuanpin)
    TextView tvLiuzhuanpin;
    @InjectView(R.id.recycler_liuzhuan)
    RecyclerView recyclerLiuzhuan;
    private OrderDetailBean.ResultBean.ResDataBean resDataBean;
    private WriteFeedbackNumAdapter adapter;
    private WriteFeedbackNumAdapter adapter_two;
    private WriteFeedbackNumAdapter adapter_three;
    private InsertNumDialog insertNumDialog;
    private InventoryApi inventoryApi;
    private int order_id;
    private String from;
    private WriteFeedAdapter feedAdapter;
    private WriteFeedAdapter feedAdapter_two;
    private WriteFeedAdapter feedAdapter_three;
    private List<GetReturnMaterBean.ResultBean.ResDataBean> res_data;
    private List<GetReturnMaterBean.ResultBean.ResDataBean> list_one;
    private List<GetReturnMaterBean.ResultBean.ResDataBean> list_two;
    private List<GetReturnMaterBean.ResultBean.ResDataBean> list_three;
    private Retrofit retrofit;
    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list_first = new ArrayList<>();
    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list_second = new ArrayList<>();
    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list_third = new ArrayList<>();
    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> listAll = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if (handlerType.equals("material")) {
                        list_one.get(handlerPosition).setReturn_qty(handlerNum);
                        list_one.get(handlerPosition).setNfc(true);
                    } else if (handlerType.equals("real_semi_finished")) {
                        list_two.get(handlerPosition).setReturn_qty(handlerNum);
                        list_two.get(handlerPosition).setNfc(true);
                    }
                    handlerAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private String handlerType;
    private int handlerNum;
    private int handlerPosition;
    private WriteFeedbackNumAdapter handlerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_feedmater);
        ButterKnife.inject(this);

        recyclerFeedMaterial.setLayoutManager(new FullyLinearLayoutManager(WriteFeedMateriActivity.this));
        recyclerFeedMaterial.addItemDecoration(new DividerItemDecoration(WriteFeedMateriActivity.this,
                DividerItemDecoration.VERTICAL));
        recyclerSemematerial.setLayoutManager(new FullyLinearLayoutManager(WriteFeedMateriActivity.this));
        recyclerSemematerial.addItemDecoration(new DividerItemDecoration(WriteFeedMateriActivity.this,
                DividerItemDecoration.VERTICAL));
        recyclerLiuzhuan.setLayoutManager(new FullyLinearLayoutManager(WriteFeedMateriActivity.this));
        recyclerLiuzhuan.addItemDecoration(new DividerItemDecoration(WriteFeedMateriActivity.this,
                DividerItemDecoration.VERTICAL));
        recyclerFeedMaterial.setNestedScrollingEnabled(false);
        recyclerSemematerial.setNestedScrollingEnabled(false);
        recyclerLiuzhuan.setNestedScrollingEnabled(false);
        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(new OKHttpFactory(WriteFeedMateriActivity.this).getOkHttpClient())
                .baseUrl(Url + "/linkloving_user_auth/")
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        Url = RetrofitClient.Url;
        Intent intent = getIntent();
        resDataBean = (OrderDetailBean.ResultBean.ResDataBean) intent.getSerializableExtra("recycler_data");
        order_id = intent.getIntExtra("order_id", 1);
        from = intent.getStringExtra("from");
        if (from.equals("look")) {
            tvCommitFeednum.setText("确认退料数量");
        }
        inventoryApi = RetrofitClient.getInstance(WriteFeedMateriActivity.this).create(InventoryApi.class);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (from.equals("write") || from.equals("check")) {
            List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> stock_move_lines = resDataBean.getStock_move_lines();
            for (int i = 0; i < stock_move_lines.size(); i++) {
                String s = String.valueOf(stock_move_lines.get(i).getProduct_type());
                if (s.equals("material")) {
                    list_first.add(stock_move_lines.get(i));
                } else if (s.equals("real_semi_finished")) {
                    list_second.add(stock_move_lines.get(i));
                } else if (s.equals("semi-finished")) {
                    list_third.add(stock_move_lines.get(i));
                }
            }
            feedAdapter = new WriteFeedAdapter(R.layout.adapter_write_feednum, list_first);
            recyclerFeedMaterial.setAdapter(feedAdapter);
            feedAdapter_two = new WriteFeedAdapter(R.layout.adapter_write_feednum, list_second);
            recyclerSemematerial.setAdapter(feedAdapter_two);
            feedAdapter_three = new WriteFeedAdapter(R.layout.adapter_write_feednum, list_third);
            recyclerLiuzhuan.setAdapter(feedAdapter_three);
            initClickAdapter(feedAdapter);
            initClickAdapter(feedAdapter_two);
            initClickAdapter(feedAdapter_three);
        } else {
            showDefultProgressDialog();
            HashMap<Object, Object> hashMap = new HashMap();
            hashMap.put("order_id", order_id);
            Call<GetReturnMaterBean> returnMater = inventoryApi.getReturnMater(hashMap);
            returnMater.enqueue(new MyCallback<GetReturnMaterBean>() {
                @Override
                public void onResponse(Call<GetReturnMaterBean> call, Response<GetReturnMaterBean> response) {
                    dismissDefultProgressDialog();
                    if (response.body() == null) return;
                    if (response.body().getError()!=null){
                        new TipDialog(WriteFeedMateriActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                .show();
                        return;
                    }
                    if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                        res_data = response.body().getResult().getRes_data();
                        for (int i = 0; i < res_data.size(); i++) {
                            if (res_data.get(i).getReturn_qty() == 0) {
                                res_data.get(i).setNfc(true);
                            }
                        }
                        list_one = new ArrayList<>();
                        list_two = new ArrayList<>();
                        list_three = new ArrayList<>();
                        for (int i = 0; i < res_data.size(); i++) {
                            String s = String.valueOf(res_data.get(i).getProduct_type());
                            if (s.equals("material")) {
                                list_one.add(res_data.get(i));
                            } else if (s.equals("real_semi_finished")) {
                                list_two.add(res_data.get(i));
                            } else if (s.equals("semi-finished")) {
                                list_three.add(res_data.get(i));
                            }
                        }
                        adapter = new WriteFeedbackNumAdapter(R.layout.adapter_write_feednum, list_one);
                        adapter_two = new WriteFeedbackNumAdapter(R.layout.adapter_write_feednum, list_two);
                        adapter_three = new WriteFeedbackNumAdapter(R.layout.adapter_write_feednum, list_three);
                        recyclerFeedMaterial.setAdapter(adapter);
                        recyclerSemematerial.setAdapter(adapter_two);
                        recyclerLiuzhuan.setAdapter(adapter_three);
                        initRecyc(adapter);
                        initRecyc(adapter_two);
                        initRecyc(adapter_three);
                    }
                }

                @Override
                public void onFailure(Call<GetReturnMaterBean> call, Throwable t) {
                    dismissDefultProgressDialog();
                    ToastUtils.showCommonToast(WriteFeedMateriActivity.this, t.toString());
                }
            });
        }
    }

    //写  check时候的点击事件
    private void initClickAdapter(final WriteFeedAdapter feeadapter) {
        feeadapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final BaseQuickAdapter adapter, View view, final int position) {
                final List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> data = (List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean>) adapter.getData();
                double beiNum;
                if (resDataBean.getState().equals("waiting_material")
                        || resDataBean.getState().equals("prepare_material_ing")
                        || resDataBean.getState().equals("finish_prepare_material")) {
                    beiNum = data.get(position).getQuantity_ready() + data.get(position).getQuantity_done();
                } else {
                    beiNum = data.get(position).getQuantity_done();
                }
                // TODO: 2017/6/8 生产num/需求num*item的需求num
                double v = resDataBean.getQty_produced() / resDataBean.getProduct_qty() * data.get(position).getProduct_uom_qty();
                Log.e("zws", "备料数量"+(beiNum-v));
                insertNumDialog = new InsertNumDialog(WriteFeedMateriActivity.this, R.style.MyDialogStyle,
                        new InsertNumDialog.OnSendCommonClickListener() {
                            public double beiNum;//备料数量

                            @Override
                            public void OnSendCommonClick(int num) {
                                if (resDataBean.getState().equals("waiting_material")
                                        || resDataBean.getState().equals("prepare_material_ing")
                                        || resDataBean.getState().equals("finish_prepare_material")) {
                                    beiNum = data.get(position).getQuantity_ready() + data.get(position).getQuantity_done();
                                } else {
                                    beiNum = data.get(position).getQuantity_done();
                                }
                                // TODO: 2017/6/8 生产num/需求num*item的需求num
                                double v = resDataBean.getQty_produced() / resDataBean.getProduct_qty() * data.get(position).getProduct_uom_qty();
                                if (num <= (beiNum - v)) {
                                    data.get(position).setReturn_qty(num);
                                    feeadapter.notifyDataSetChanged();
                                } else {
                                    ToastUtils.showCommonToast(WriteFeedMateriActivity.this, "退料过多");
                                }
                            }
                        }, data.get(position).getProduct_id(), (beiNum-v))
                        .changeTitle("输入 " + data.get(position).getProduct_id() + " 的退料数量");
                insertNumDialog.show();
            }
        });
    }

    /**
     * 设置recycler
     */
    private void initRecyc(final WriteFeedbackNumAdapter adapter_type) {

        adapter_type.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final BaseQuickAdapter adapter, View view, final int position) {
                insertNumDialog = new InsertNumDialog(WriteFeedMateriActivity.this, R.style.MyDialogStyle,
                        new InsertNumDialog.OnSendCommonClickListener() {
                            @Override
                            public void OnSendCommonClick(final int num) {
                                if (num > 0) {
                                    final String product_type = (String) adapter_type.getData().get(position).getProduct_type();
                                    if (product_type.equals("material") || product_type.equals("real_semi_finished")) {
                                        handlerType = product_type;
                                        handlerNum = num;
                                        handlerPosition = position;
                                        handlerAdapter = adapter_type;
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                initDevice();
                                                processingLock();
                                                showNfcDialog();
                                                try {
                                                    final RFResult qPResult = rfCardModule.powerOn(null, 10, TimeUnit.SECONDS);
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (qPResult.getCardSerialNo() == null) {
                                                                ToastUtils.showCommonToast(WriteFeedMateriActivity.this, "不能识别序列号：" + Const.MessageTag.DATA);
                                                            } else {
                                                             //   showDefultProgressDialog();
                                                                String NFC_Number = ISOUtils.hexString(qPResult.getCardSerialNo());
                                                                InventoryApi inventory = retrofit.create(InventoryApi.class);
                                                                HashMap<Object, Object> hashMap = new HashMap<>();
                                                                hashMap.put("card_num", NFC_Number);
                                                                Call<NfcOrderBean> objectCall = inventory.authWarehouse(hashMap);
                                                                objectCall.enqueue(new Callback<NfcOrderBean>() {
                                                                    @Override
                                                                    public void onResponse(Call<NfcOrderBean> call, Response<NfcOrderBean> response) {
                                                                       // dismissDefultProgressDialog();
                                                                        if (response.body() == null)
                                                                            return;
                                                                        if (response.body().getError() != null) {
                                                                            nfCdialog.setHeaderImage(R.drawable.warning)
                                                                                    .setTip(response.body().getError().getData().getMessage())
                                                                                    .setCancelVisi().show();
                                                                            threadDismiss(nfCdialog);
                                                                            return;
                                                                        }
                                                                        if (response.body().getResult() != null && response.body().getResult().getRes_code() == -1) {
                                                                            nfCdialog.setHeaderImage(R.drawable.warning)
                                                                                    .setTip(response.body().getResult().getRes_data().getErrorX())
                                                                                    .setCancelVisi().show();
                                                                            threadDismiss(nfCdialog);
                                                                        } else if (response.body().getResult() != null && response.body().getResult().getRes_code() == 1) {
                                                                            final NfcOrderBean.ResultBean.ResDataBean res_dataNfc = response.body().getResult().getRes_data();
                                                                            nfCdialog.setHeaderImage(R.drawable.defaultimage)
                                                                                    .setTip(res_dataNfc.getName() + res_dataNfc.getEmployee_id() + "\n" + res_dataNfc.getWork_email()
                                                                                            + "\n\n" + "打卡成功")
                                                                                    .setCancelVisi().show();
                                                                            threadDismiss(nfCdialog);
                                                                            Message message = new Message();
                                                                            message.what = 1;
                                                                            handler.sendMessage(message);
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<NfcOrderBean> call, Throwable t) {
                                                                       // dismissDefultProgressDialog();
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
                                                                    ToastUtils.showCommonToast(WriteFeedMateriActivity.this, e.getMessage() + "  " + Const.MessageTag.ERROR);
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
                                        list_three.get(position).setReturn_qty(num);
                                        list_three.get(position).setNfc(true);
                                        adapter_type.notifyDataSetChanged();
                                    }
                                } else {
                                    ToastUtils.showCommonToast(WriteFeedMateriActivity.this, "退料数量必须大于0");
                                }
                            }
                        }, adapter_type.getData().get(position).getProduct_id(), adapter_type.getData().get(position).getReturn_qty())
                        .changeTitle("确认 " + adapter_type.getData().get(position).getProduct_id() + " 的退料数量")
                .changeTip("输入退料数量");
                insertNumDialog.show();
            }
        });
    }


    @OnClick(R.id.tv_commit_feednum)
    void commitNum(View view) {
        if (from.equals("check") || from.equals("write")) {
            AlertAialogUtils.getCommonDialog(WriteFeedMateriActivity.this, "确定提交？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showDefultProgressDialog();
                            HashMap<Object, Object> hashMap = new HashMap();
                            hashMap.put("order_id", order_id);
                            hashMap.put("is_check", 0);
                            listAll.addAll(feedAdapter.getData());
                            listAll.addAll(feedAdapter_two.getData());
                            listAll.addAll(feedAdapter_three.getData());
                            Map[] maps = new Map[listAll.size()];
                            for (int i = 0; i < listAll.size(); i++) {
                                Map<Object, Object> smallMap = new HashMap<>();
                                smallMap.put("order_id", listAll.get(i).getOrder_id());
                                smallMap.put("product_tmpl_id", listAll.get(i).getProduct_tmpl_id());
                                smallMap.put("return_qty", listAll.get(i).getReturn_qty());
                                maps[i] = smallMap;
                            }
                            hashMap.put("stock_moves", maps);
                            Call<OrderDetailBean> objectCall = inventoryApi.semiCommitReturn(hashMap);
                            objectCall.enqueue(new MyCallback<OrderDetailBean>() {
                                @Override
                                public void onResponse(Call<OrderDetailBean> call, Response<OrderDetailBean> response) {
                                    dismissDefultProgressDialog();
                                    if (response.body() == null || response.body().getResult() == null)
                                        return;
                                    if (response.body().getError() != null) {
                                        new TipDialog(WriteFeedMateriActivity.this, R.style.MyDialogStyle, response.body().getError().getMessage())
                                                .show();
                                        return;
                                    }
                                    if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                                        ToastUtils.showCommonToast(WriteFeedMateriActivity.this, "提交退料成功");
                                        Intent intent = new Intent(WriteFeedMateriActivity.this, ProductLlActivity.class);
                                        intent.putExtra("name_activity", "生产退料");
                                        if (from.equals("check")) {
                                            intent.putExtra("state_product", "done");
                                        } else if (from.equals("write")) {
                                            intent.putExtra("state_product", "waiting_inventory_material");
                                        }
                                        startActivity(intent);
                                        finish();
                                    } else if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == -1) {
                                        ToastUtils.showCommonToast(WriteFeedMateriActivity.this, response.body().getResult().getRes_data().getError());
                                    } else {
                                        //ToastUtils.showCommonToast(WriteFeedMateriActivity.this, "数据错误");
                                        Log.e("zws", "数据异常");
                                    }
                                }

                                @Override
                                public void onFailure(Call<OrderDetailBean> call, Throwable t) {
                                    dismissDefultProgressDialog();
//                                    new TipDialog(WriteFeedMateriActivity.this, R.style.MyDialogStyle, t.toString())
//                                    .show();
                                   // ToastUtils.showCommonToast(WriteFeedMateriActivity.this, t.toString());
                                }
                            });
                        }
                    }).show();
        } else {
            boolean pass = false;
            for (int i = 0; i < res_data.size(); i++) {
                if (!res_data.get(i).isNfc() && res_data.get(i).getReturn_qty() != 0) {
                    pass = true;
                    break;
                }
            }
            if (pass) {
                ToastUtils.showCommonToast(WriteFeedMateriActivity.this, "有未确认的退料");
                return;
            }
            AlertAialogUtils.getCommonDialog(WriteFeedMateriActivity.this, "是否确定提交")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showDefultProgressDialog();
                            HashMap<Object, Object> hashMap = new HashMap();
                            hashMap.put("order_id", order_id);
                            if (from.equals("look")) {
                                hashMap.put("is_check", 1);
                            } else {
                                hashMap.put("is_check", 0);
                            }
                            Map[] maps = new Map[res_data.size()];
                            for (int i = 0; i < res_data.size(); i++) {
                                Map<Object, Object> smallMap = new HashMap<>();
                                smallMap.put("product_tmpl_id", res_data.get(i).getProduct_tmpl_id());
                                smallMap.put("return_qty", res_data.get(i).getReturn_qty());
                                maps[i] = smallMap;
                            }
                            hashMap.put("stock_moves", maps);
                            Call<OrderDetailBean> objectCall = inventoryApi.commitFeedNum(hashMap);
                            objectCall.enqueue(new MyCallback<OrderDetailBean>() {
                                @Override
                                public void onResponse(Call<OrderDetailBean> call, Response<OrderDetailBean> response) {
                                    dismissDefultProgressDialog();
                                    if (response.body() == null || response.body().getResult() == null)
                                        return;
                                    if (response.body().getError() != null) {
                                        new TipDialog(WriteFeedMateriActivity.this, R.style.MyDialogStyle, response.body().getError().getMessage())
                                                .show();
                                        return;
                                    }
                                    if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                                        Intent intent = new Intent(WriteFeedMateriActivity.this, ProductLlActivity.class);
                                        ToastUtils.showCommonToast(WriteFeedMateriActivity.this, "退料完成");
                                        intent.putExtra("name_activity", "生产退料");
                                        intent.putExtra("state_product", "waiting_warehouse_inspection");
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        //ToastUtils.showCommonToast(WriteFeedMateriActivity.this, "数据错误");
                                        Log.e("zws", "数据异常");
                                    }
                                }

                                @Override
                                public void onFailure(Call<OrderDetailBean> call, Throwable t) {
                                    dismissDefultProgressDialog();
                                    ToastUtils.showCommonToast(WriteFeedMateriActivity.this, t.toString());
                                }
                            });
                        }
                    }).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (from.equals("look") && res_data != null) {
            if (item.getItemId() == android.R.id.home) {
                boolean isBack = false;
                for (int i = 0; i < res_data.size(); i++) {
                    if (res_data.get(i).isNfc()) {
                        isBack = true;
                        break;
                    }
                }
                if (isBack) {
                    isBack = false;
                    AlertAialogUtils.getCommonDialog(WriteFeedMateriActivity.this, "已经有确认过的退料了，是否确认返回？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).show();
                } else {
                    finish();
                }
                return isBack;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 返回按钮
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (from.equals("look") && res_data != null) {
            if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
                boolean isBack = false;
                for (int i = 0; i < res_data.size(); i++) {
                    if (res_data.get(i).isNfc()) {
                        isBack = true;
                        break;
                    }
                }
                if (isBack) {
                    isBack = false;
                    AlertAialogUtils.getCommonDialog(WriteFeedMateriActivity.this, "已经有确认过的退料了，是否确认返回？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).show();
                } else {
                    finish();
                }
                return isBack;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
