package tarce.myodoo.activity.moreproduce;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newland.mtype.module.common.rfcard.RFResult;
import com.newland.mtype.util.ISOUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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
import tarce.model.inventory.NfcOrderBean;
import tarce.model.inventory.QcFeedbaskBean;
import tarce.model.inventory.RejectResultBean;
import tarce.model.inventory.RukuBean;
import tarce.model.inventory.StartInspectBean;
import tarce.model.inventory.StartReworkBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.activity.OrderDetailActivity;
import tarce.myodoo.activity.inspect.InspectMoDetailActivity;
import tarce.myodoo.activity.inspect.InspectionSubActivity;
import tarce.myodoo.adapter.moreproduce.MorePAdapter;
import tarce.myodoo.device.Const;
import tarce.myodoo.uiutil.ImageUtil;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.StringUtils;
import tarce.support.AlertAialogUtils;
import tarce.support.BitmapUtils;
import tarce.support.ToastUtils;

import static tarce.api.RetrofitClient.Url;
import static tarce.myodoo.R.string.num;

/**
 * Created by zouwansheng on 2017/9/25.
 */

public class InquiriessMorePActivity extends BaseActivity {
    private static final int QC_ING = 1;
    @InjectView(R.id.more_recycler)
    RecyclerView moreRecycler;
    @InjectView(R.id.btn_start_more)
    Button btnStartMore;
    @InjectView(R.id.btn_start_pass)
    Button btnStartPass;
    @InjectView(R.id.state_inspect_detail)
    TextView stateInspectDetail;
    @InjectView(R.id.tv_look_bom)
    TextView tvLookBom;

    private QcFeedbaskBean.ResultBean.ResDataBean dataBean;
    private MorePAdapter morePAdapter;
    private InventoryApi inventoryApi;
    private int position_click;
    private Retrofit retrofit;
    private int handlerPosition;
    private int handlerNum;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                morePAdapter.getData().get(handlerPosition).setBlue(true);
               // morePAdapter.getData().get(handlerPosition).setQty_produced(handlerNum);
                morePAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquirmorep);
        ButterKnife.inject(this);

        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(new OKHttpFactory(InquiriessMorePActivity.this).getOkHttpClient())
                .baseUrl(Url + "/linkloving_user_auth/")
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        Url = RetrofitClient.Url;
        inventoryApi = RetrofitClient.getInstance(InquiriessMorePActivity.this).create(InventoryApi.class);
        setRecyclerview(moreRecycler);
        Intent intent = getIntent();
        dataBean = (QcFeedbaskBean.ResultBean.ResDataBean) intent.getSerializableExtra("data");
        initView();
        initDevice();
    }

    private void initView() {
        if (dataBean.getLine_ids().size() == 0){
            ToastUtils.showCommonToast(InquiriessMorePActivity.this, "暂时没有品检单");
        }else {
            morePAdapter = new MorePAdapter(R.layout.item_done_adapter, (List<QcFeedbaskBean.ResultBean.ResDataBean.LinesBean>) dataBean.getLine_ids());
            moreRecycler.setAdapter(morePAdapter);
        }
        switch (dataBean.getState()){
            case "draft":
                stateInspectDetail.setText("等待品检");
                break;
            case "qc_ing":
                stateInspectDetail.setText("品检中");
                btnStartMore.setText("品检通过");
                btnStartPass.setVisibility(View.VISIBLE);
                initListener();
                break;
            case "qc_success":
                stateInspectDetail.setText("品检通过");
                btnStartMore.setText("入库");
                initShowDialog();
                break;
            case "qc_fail":
                stateInspectDetail.setText("品检失败");
                btnStartMore.setText("开始返工");
                initListener();
                break;
        }
    }

    //开始品检  或者 品检通过
    @OnClick(R.id.btn_start_more)
    void startInquir(View view) {
        switch (btnStartMore.getText().toString()){
            case "开始品检":
                showDefultProgressDialog();
                HashMap<Object, Object> hashMap = new HashMap<>();
                hashMap.put("feedback_id", dataBean.getFeedback_id());
                hashMap.put("order_id", dataBean.getProduction_id().getOrder_id());
                Call<StartInspectBean> objectCall = inventoryApi.startInspection(hashMap);
                objectCall.enqueue(new MyCallback<StartInspectBean>() {
                    @Override
                    public void onResponse(Call<StartInspectBean> call, Response<StartInspectBean> response) {
                        dismissDefultProgressDialog();
                        if (response.body() == null) return;
                        if (response.body().getError()!=null){
                            new TipDialog(InquiriessMorePActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                    .show();
                            return;
                        }
                        if (response.body().getResult().getRes_data() !=null && response.body().getResult().getRes_code() == 1) {
                            AlertAialogUtils.getCommonDialog(InquiriessMorePActivity.this, "")
                                    .setMessage("该单据状态变为品检中")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(InquiriessMorePActivity.this, InspectionSubActivity.class);
                                            intent.putExtra("state", "draft");
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).show();
                        }else {
                            ToastUtils.showCommonToast(InquiriessMorePActivity.this,  "出现错误，请联系开发人员调试");
                        }
                    }

                    @Override
                    public void onFailure(Call<StartInspectBean> call, Throwable t) {
                        dismissDefultProgressDialog();
                        Log.e("zws", t.toString());
                        ToastUtils.showCommonToast(InquiriessMorePActivity.this, t.toString());
                    }
                });
                break;
            case "品检通过":
                if (passOrnot()){
                    ToastUtils.showCommonToast(InquiriessMorePActivity.this, "有未抽样品检产品，请品检");
                    return;
                }
                AlertAialogUtils.getCommonDialog(InquiriessMorePActivity.this, "")
                        .setMessage("是否确定品检通过")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkResult(1);
                            }
                        }).show();
                break;
            case "开始返工":
                HashMap<Object, Object> hashMap1 = new HashMap<>();
                hashMap1.put("feedback_id", dataBean.getFeedback_id());
                //hashMap1.put("feedback_id", 1);
                Call<StartReworkBean> objectCall2 = inventoryApi.startRework(hashMap1);
                objectCall2.enqueue(new MyCallback<StartReworkBean>() {
                    @Override
                    public void onResponse(Call<StartReworkBean> call, Response<StartReworkBean> response) {
                        if (response.body() == null) return;
                        if (response.body().getError()!=null){
                            new TipDialog(InquiriessMorePActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                    .show();
                            return;
                        }
                        if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data()!=null) {
                            AlertAialogUtils.getCommonDialog(InquiriessMorePActivity.this, "该单据开始返工")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(InquiriessMorePActivity.this, InspectionSubActivity.class);
                                            intent.putExtra("state", "qc_fail");
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).show();
                        } else if (response.body().getResult().getRes_data()!=null && response.body().getResult().getRes_code() == -1) {
                            ToastUtils.showCommonToast(InquiriessMorePActivity.this, response.body().getResult().getRes_data().getError());
                        }
                    }

                    @Override
                    public void onFailure(Call<StartReworkBean> call, Throwable t) {

                    }
                });
                break;
            case "入库":
                boolean isPass = false;
                for (int i = 0; i < morePAdapter.getData().size(); i++) {
                    if (!morePAdapter.getData().get(i).isBlue()){
                        isPass = true;
                        break;
                    }
                }
                if (isPass){
                    ToastUtils.showCommonToast(InquiriessMorePActivity.this, "请点击产品单确认生产数量！");
                    return;
                }
                showDefultProgressDialog();
                HashMap<Object, Object> doneHashmap = new HashMap<>();
                doneHashmap.put("feedback_id", dataBean.getFeedback_id());
                Call<RukuBean> objectCall1 = inventoryApi.produceDone(doneHashmap);
                objectCall1.enqueue(new MyCallback<RukuBean>() {
                    @Override
                    public void onResponse(Call<RukuBean> call, Response<RukuBean> response) {
                        dismissDefultProgressDialog();
                        if (response.body() == null) return;
                        if (response.body().getError()!=null){
                            new TipDialog(InquiriessMorePActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                    .show();
                            return;
                        }
                        if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                            new TipDialog(InquiriessMorePActivity.this, R.style.MyDialogStyle, "入库成功")
                                    .setTrue(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(InquiriessMorePActivity.this, InspectionSubActivity.class);
                                            intent.putExtra("state", "qc_success");
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).show();
                        }else if (response.body().getResult().getRes_code() == -1 && response.body().getResult().getRes_data() != null){
                            new TipDialog(InquiriessMorePActivity.this, R.style.MyDialogStyle, response.body().getResult().getRes_data().getError())
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RukuBean> call, Throwable t) {
                        dismissDefultProgressDialog();
                        new TipDialog(InquiriessMorePActivity.this, R.style.MyDialogStyle, t.toString())
                                .show();
                        //  Log.e("zws", t.toString());
                    }
                });
                break;
        }
    }

    //品检不通过
    @OnClick(R.id.btn_start_pass)
    void notPass(View view) {
        if (passOrnot()){
            ToastUtils.showCommonToast(InquiriessMorePActivity.this, "有未抽样品检产品，请品检");
            return;
        }
        AlertAialogUtils.getCommonDialog(InquiriessMorePActivity.this, "")
                .setMessage("是否确定品检不通过")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkResult(0);
                    }
                }).show();
    }

    //查看物料关系
    @OnClick(R.id.tv_look_bom)
    void lookLink(View view) {
        Intent intent = new Intent(InquiriessMorePActivity.this, MaterialRelationActivity.class);
        intent.putExtra("rule_id", dataBean.getRule_id());
        startActivity(intent);
    }

    /**
     * 另一个点击事件
     * */
    private void initShowDialog(){
        morePAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                TextView textView = new TextView(InquiriessMorePActivity.this);
                textView.setText("生产数量： "+morePAdapter.getData().get(position).getQty_produced()+"");
                textView.setTextColor(Color.RED);
                textView.setPadding(25,20,0,0);
//                final EditText editText = new EditText(InquiriessMorePActivity.this);
//                editText.setText(morePAdapter.getData().get(position).getQty_produced()+"");
//                editText.setSelection(editText.getText().length());
//                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                AlertDialog.Builder dialog = AlertAialogUtils.getCommonDialog(InquiriessMorePActivity.this, "请确认" + morePAdapter.getData().get(position).getProduct_name() + "完成数量");
                dialog.setView(textView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                if (StringUtils.isNullOrEmpty(editText.getText().toString())){
//                                    ToastUtils.showCommonToast(InquiriessMorePActivity.this, "未输入数量");
//                                    return;
//                                }
//                                if (Integer.parseInt(editText.getText().toString()) == 0) {
//                                    return;
//                                }
                                handlerPosition = position;
                             //   handlerNum = Integer.parseInt(editText.getText().toString());
                                //handlerType = type;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        processingLock();
                                        showNfcDialog();
                                        try {
                                            final RFResult qPResult = rfCardModule.powerOn(null, 8, TimeUnit.SECONDS);
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (qPResult.getCardSerialNo() == null) {
                                                        ToastUtils.showCommonToast(InquiriessMorePActivity.this, "不能识别序列号：" + Const.MessageTag.DATA);
                                                    } else {
                                                        // showDefultProgressDialog();
                                                        String NFC_Number = ISOUtils.hexString(qPResult.getCardSerialNo());
                                                        InventoryApi inventory = retrofit.create(InventoryApi.class);
                                                        HashMap<Object, Object> hashMap = new HashMap<>();
                                                        hashMap.put("card_num", NFC_Number);
                                                        Call<NfcOrderBean> objectCall = inventory.authWarehouse(hashMap);
                                                        objectCall.enqueue(new Callback<NfcOrderBean>() {
                                                            @Override
                                                            public void onResponse(Call<NfcOrderBean> call, Response<NfcOrderBean> response) {
                                                                // dismissDefultProgressDialog();
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
                                                                    final NfcOrderBean.ResultBean.ResDataBean res_data = response.body().getResult().getRes_data();
                                                                    nfCdialog.setHeaderImage(R.drawable.defaultimage)
                                                                            .setTip(res_data.getName() + res_data.getEmployee_id() + "\n" + res_data.getWork_email()
                                                                                    + "\n\n" + "打卡成功")
                                                                            .setCancelVisi().show();
                                                                    new Handler().postDelayed(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            nfCdialog.dismiss();
                                                                        }
                                                                    }, 500);
                                                                    //employee_id = res_data.getEmployee_id();
                                                                    Message message = new Message();
                                                                    message.what = 1;
                                                                    mHandler.sendMessage(message);
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
                                                            // ToastUtils.showCommonToast(OrderDetailActivity.this, e.getMessage() + "  " + Const.MessageTag.ERROR);
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
     * recycler 点击事件
     * */
    private void initListener(){
        morePAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (dataBean.getState().equals("qc_ing")){
                        Intent intent = new Intent(InquiriessMorePActivity.this, InspectMoDetailActivity.class);
                        intent.putExtra("data", dataBean);
                        intent.putExtra("position", position);
                        position_click = position;
                        startActivityForResult(intent, QC_ING);
                    }else if (dataBean.getState().equals("qc_fail")){
                        Intent intent = new Intent(InquiriessMorePActivity.this, InspectMoDetailActivity.class);
                        intent.putExtra("data", dataBean);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 111){
            if (requestCode == QC_ING){
                if (data==null){
                    return;
                }
                dataBean.getLine_ids().get(position_click).setQc_test_qty(data.getIntExtra("qc_test_qty", 1));
                dataBean.getLine_ids().get(position_click).setQc_fail_qty(data.getIntExtra("qc_fail_qty", 1));
                dataBean.getLine_ids().get(position_click).setQc_note(data.getStringExtra("qc_note"));
            }
        }
    }

    /**
     * 品检之前的判断
     * */
    private boolean passOrnot(){
        boolean isPass = false;
        List<QcFeedbaskBean.ResultBean.ResDataBean.LinesBean> line_ids = dataBean.getLine_ids();
        for (int i = 0; i < line_ids.size(); i++) {
            if (line_ids.get(i).getQc_test_qty()<=0){
                isPass = true;
                break;
            }
        }
        return isPass;
    }
    /**
     * 品检通过或者不通过接口
     */
    private void checkResult(final int result) {
        showDefultProgressDialog();
        HashMap<Object, Object> resultMap = new HashMap<>();
        resultMap.put("feedback_id", dataBean.getFeedback_id());
        resultMap.put("result", result);
        resultMap.put("line_ids", dataBean.getLine_ids());

        Call<StartInspectBean> objectCall1 = inventoryApi.resultInspec(resultMap);
        objectCall1.enqueue(new MyCallback<StartInspectBean>() {
            @Override
            public void onResponse(Call<StartInspectBean> call, Response<StartInspectBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError()!=null){
                    new TipDialog(InquiriessMorePActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data()!=null) {
                    if (result == 1) {
                        AlertAialogUtils.getCommonDialog(InquiriessMorePActivity.this, "品检成功，等待入库")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        Intent intent = new Intent(InquiriessMorePActivity.this, InspectionSubActivity.class);
//                                        intent.putExtra("state", "qc_ing");
//                                        startActivity(intent);
                                        finish();
                                    }
                                }).show();
                    } else {
                        AlertAialogUtils.getCommonDialog(InquiriessMorePActivity.this, "品检不通过，等待返工")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        Intent intent = new Intent(InquiriessMorePActivity.this, InspectionSubActivity.class);
//                                        intent.putExtra("state", "qc_ing");
//                                        startActivity(intent);
                                        finish();
                                    }
                                }).show();
                    }
                } else if (response.body().getResult().getRes_code() == -1) {
                    ToastUtils.showCommonToast(InquiriessMorePActivity.this, response.body().getResult().getRes_data().getError());
                }
            }

            @Override
            public void onFailure(Call<StartInspectBean> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
    }
}
