package tarce.myodoo.activity.engineer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.stetho.inspector.protocol.module.Network;
import com.newland.me.ConnUtils;
import com.newland.me.DeviceManager;
import com.newland.mtype.ConnectionCloseEvent;
import com.newland.mtype.ModuleType;
import com.newland.mtype.event.DeviceEventListener;
import com.newland.mtype.module.common.printer.Printer;
import com.newland.mtype.module.common.rfcard.RFCardModule;
import com.newland.mtype.module.common.rfcard.RFResult;
import com.newland.mtype.util.ISOUtils;
import com.newland.mtypex.nseries.NSConnV100ConnParams;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
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
import tarce.model.ChangeProjectBean;
import tarce.model.GetSaleResponse;
import tarce.model.ProjectDetailBean;
import tarce.model.inventory.NfcOrderBean;
import tarce.model.inventory.OrderDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.activity.BuGetLiaoActivity;
import tarce.myodoo.adapter.SalesDetailAdapter;
import tarce.myodoo.adapter.projectpick.ProjectDetailAdapter;
import tarce.myodoo.device.Const;
import tarce.myodoo.uiutil.FullyLinearLayoutManager;
import tarce.myodoo.uiutil.NFCdialog;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.DateTool;
import tarce.myodoo.utils.StringUtils;
import tarce.support.AlertAialogUtils;
import tarce.support.MyLog;
import tarce.support.TimeUtils;
import tarce.support.ToastUtils;

import static tarce.api.RetrofitClient.Url;
import static tarce.myodoo.R.string.num;

/**
 * Created by zouwansheng on 2017/9/6.
 */

public class ProjectDetailActivity extends BaseActivity {
    @InjectView(R.id.tv_auto_num)
    TextView tvAutoNum;
    @InjectView(R.id.top_imageview)
    ImageView topImageview;
    @InjectView(R.id.tv_print)
    TextView tvPrint;
    @InjectView(R.id.partner)
    TextView partner;
    @InjectView(R.id.time)
    TextView time;
    @InjectView(R.id.jiaohuo_date)
    TextView jiaohuoDate;
    @InjectView(R.id.lingliao_yuanyin)
    TextView lingliaoYuanyin;
    @InjectView(R.id.remarks)
    EditText remarks;
    @InjectView(R.id.framelayout)
    FrameLayout framelayout;
    @InjectView(R.id.camera_buttom_linearlayout)
    LinearLayout cameraButtomLinearlayout;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.buttom_button1)
    Button buttomButton1;
    @InjectView(R.id.buttom_button2)
    Button buttomButton2;
    @InjectView(R.id.buttom_button3)
    Button buttomButton3;
    @InjectView(R.id.buttom_button4)
    Button buttomButton4;
    @InjectView(R.id.linear_bottom)
    LinearLayout linearBottom;
    private InventoryApi inventoryApi;
    private GetSaleResponse.TResult.TRes_data bundle1;
    private String saleName;
    private SalesDetailAdapter salesDetailAdapter;
    private String model_state = "";
    private ProjectDetailBean.ResultBean.ResDataBean res_data;
    private int material_id;
    private ProjectDetailAdapter detailAdapter;
    private List<ProjectDetailBean.ResultBean.ResDataBean.LineIdsBean> line_ids;
    private String state;
    private Retrofit retrofit;
    private int pos;
    private double numGet;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                detailAdapter.getData().get(pos).setQuantity_done(numGet);
                detailAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private String type;
    private Printer printer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_projectdetail);
        ButterKnife.inject(this);

        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(new OKHttpFactory(ProjectDetailActivity.this).getOkHttpClient())
                .baseUrl(Url + "/linkloving_user_auth/")
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        Url = RetrofitClient.Url;
        topImageview.setFocusableInTouchMode(true);
        topImageview.requestFocus();
        inventoryApi = RetrofitClient.getInstance(ProjectDetailActivity.this).create(InventoryApi.class);
        recyclerview.setLayoutManager(new FullyLinearLayoutManager(ProjectDetailActivity.this));
        recyclerview.addItemDecoration(new DividerItemDecoration(ProjectDetailActivity.this,
                DividerItemDecoration.VERTICAL));
        recyclerview.setNestedScrollingEnabled(false);
        initIntent();
        initDevice();
    }

    private void initIntent() {
        Intent intent = getIntent();
        material_id = intent.getIntExtra("material_id", 1);
        saleName = intent.getStringExtra("name");
        state = intent.getStringExtra("state");
        type = intent.getStringExtra("type");
        setTitle(saleName);
        initData();
        // refreshView(bundle1);
    }

    private void initData() {
        showDefultProgressDialog();
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("material_id", material_id);
        Call<ProjectDetailBean> stringCall = inventoryApi.getOneMaterShow(objectObjectHashMap);
        stringCall.enqueue(new MyCallback<ProjectDetailBean>() {
            @Override
            public void onResponse(Call<ProjectDetailBean> call, Response<ProjectDetailBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError() != null) {
                    new TipDialog(ProjectDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                    res_data = response.body().getResult().getRes_data();
                    refreshView();
                } else {
//                    new TipDialog(ProjectDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
//                            .show();
//                    ToastUtils.showCommonToast(ProjectDetailActivity.this, "加载失败，请稍后重试");
                }
            }

            @Override
            public void onFailure(Call<ProjectDetailBean> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(ProjectDetailActivity.this, t.toString());
                Log.e("zws", t.toString());
            }
        });
    }

    private void refreshView() {
        partner.setText(res_data.getCreate_uid());
        time.setText(res_data.getCreate_date());
        jiaohuoDate.setText(res_data.getDelivery_date());
        lingliaoYuanyin.setText(res_data.getPicking_cause());
        remarks.setText(res_data.getRemark()+"");

        line_ids = res_data.getLine_ids();
        detailAdapter = new ProjectDetailAdapter(R.layout.salesout_detail_adapter_item, line_ids);
        recyclerview.setAdapter(detailAdapter);

        switch (state){
            case "finish_pick":
                buttomButton1.setText("已完成");
                break;
            case "approved_finish":
            buttomButton1.setText("开始备料");
            buttomButton1.setBackgroundResource(R.color.violet);
            buttomButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertAialogUtils.getCommonDialog(ProjectDetailActivity.this, "确定开始备料？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //开始备货
                                    refreshButtom("备料完成");
                                    model_state = "change";
                                    initListener();
                                }
                            }).show();
                }
            });
                break;
        }
    }

    private void refreshButtom(String state) {
        switch (state) {
            case "备料完成":
                buttomButton1.setVisibility(View.VISIBLE);
                buttomButton1.setText("备料完成");
                buttomButton1.setBackgroundResource(R.color.color_5693f8);
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isContinue = false;
                        int index = 0;
                        for (int i = 0; i < detailAdapter.getData().size(); i++) {
                            if (detailAdapter.getData().get(i).getQuantity_done()<1){
                                isContinue = true;
                                index = i;
                                break;
                            }
                        }
                        if (isContinue){
                            ToastUtils.showCommonToast(ProjectDetailActivity.this, "请输入"+detailAdapter.getData().get(index).getName()+"的领料数量");
                            return;
                        }
                        AlertAialogUtils.getCommonDialog(ProjectDetailActivity.this, "是否确定备料完成？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                                        objectObjectHashMap.put("material_id", material_id);
                                        List<Map<Object, Object>> mapList = new ArrayList<Map<Object, Object>>();
                                        for (int i = 0; i < detailAdapter.getData().size(); i++) {
                                            Map<Object, Object> map = new HashMap<>();
                                            map.put("quantity_done", detailAdapter.getData().get(i).getQuantity_done());
                                            map.put("id", detailAdapter.getData().get(i).getId());
                                            mapList.add(map);
                                         }
                                        objectObjectHashMap.put("pack_operation_product_ids", mapList);
                                        Call<ChangeProjectBean> objectCall = inventoryApi.packOperIds(objectObjectHashMap);
                                        showDefultProgressDialog();
                                        objectCall.enqueue(new Callback<ChangeProjectBean>() {
                                            @Override
                                            public void onResponse(Call<ChangeProjectBean> call, Response<ChangeProjectBean> response) {
                                                dismissDefultProgressDialog();
                                                if (response.body() == null)return;
                                                if (response.body().getError()!=null){
                                                    new TipDialog(ProjectDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                                            .show();
                                                    return;
                                                }
                                                if (response.body().getResult()!=null && response.body().getResult().getRes_code() == 1){
                                                    ToastUtils.showCommonToast(ProjectDetailActivity.this, "上传成功");
                                                    printTra();
                                                    finish();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ChangeProjectBean> call, Throwable t) {
                                                dismissDefultProgressDialog();
                                            }
                                        });
                                    }
                                }).show();
                    }
                });
                break;
        }
    }

    private void initListener() {
        detailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                if (position > adapter.getData().size() - 1) return;
                final EditText editText = new EditText(ProjectDetailActivity.this);
                final Integer qty_available = StringUtils.doubleToInt(detailAdapter.getData().get(position).getProduct_qty());
//                final int keyong1 = StringUtils.doubleToInt(detailAdapter.getData().get(position).getQuantity_available());
//                final int num = keyong1 > qty_available?qty_available:keyong1;//可用数量和需求数量比较   谁小默认显示谁
                // TODO: 2017/12/25 改为小于库存数量
                final double qty_product = detailAdapter.getData().get(position).getQty_product();
                editText.setText(qty_available + "");
             //   editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setSelection(editText.getText().length());
                AlertDialog.Builder dialog = AlertAialogUtils.getCommonDialog(ProjectDetailActivity.this, "输入" + detailAdapter.getData().get(position).getName() + "备料数量");
                pos = position;
                dialog.setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (Double.parseDouble(editText.getText().toString()) > qty_product) {
                                    Toast.makeText(ProjectDetailActivity.this, "超过了库存数量，请查看", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                numGet = Double.parseDouble(editText.getText().toString());
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        processingLock();
                                        showNfcDialog();
                                        try {
                                            final RFResult qPResult = rfCardModule.powerOn(null, 6, TimeUnit.SECONDS);
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (qPResult.getCardSerialNo() == null) {
                                                        ToastUtils.showCommonToast(ProjectDetailActivity.this, "不能识别序列号：" + Const.MessageTag.DATA);
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
                                                                    return;
                                                                }
                                                                if (response.body().getResult() != null && response.body().getResult().getRes_code() == -1) {
                                                                    nfCdialog.setHeaderImage(R.drawable.warning)
                                                                            .setTip(response.body().getResult().getRes_data().getErrorX())
                                                                            .setCancelVisi().show();
                                                                    threadDismiss(nfCdialog);
                                                                } else if (response.body().getResult() != null && response.body().getResult().getRes_code() == 1) {
                                                                    NfcOrderBean.ResultBean.ResDataBean res_data = response.body().getResult().getRes_data();
                                                                    nfCdialog.setHeaderImage(R.drawable.defaultimage)
                                                                            .setTip(res_data.getName() + res_data.getEmployee_id() + "\n" + res_data.getWork_email()
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
                            }
                        }).show();
            }
        });
    }

    /**
     * 打印操作
     */
    private void printTra() {
        initDevice();
        printer = (Printer) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_PRINTER);
        printer.init();
        printer.setLineSpace(1);
        printer.print(type+"\n单号：" + saleName + "\n申请人: "+res_data.getCreate_uid() + "\n领料原因: " + res_data.getPicking_cause(), 30, TimeUnit.SECONDS);
        printer.print("产品名称         领料数量", 30, TimeUnit.SECONDS);
        for (int i = 0; i < detailAdapter.getData().size(); i++) {
                String name = detailAdapter.getData().get(i).getName();
                if (name.length()>17){
                    printer.print(name.substring(0, 15) + "     " +
                            detailAdapter.getData().get(i).getQuantity_done()
                            + "\n"+name.substring(15, name.length())+"\n\n", 30, TimeUnit.SECONDS);
                }else {
                    printer.print(name+ "     " +
                            detailAdapter.getData().get(i).getQuantity_done()
                            + "\n\n", 30, TimeUnit.SECONDS);
                }
        }
        printer.print("\n" + "打印时间：" + DateTool.getDateTime(), 30, TimeUnit.SECONDS);
        printer.print("\n\n\n\n\n\n\n", 30, TimeUnit.SECONDS);
    }
}
