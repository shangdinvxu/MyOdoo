package tarce.myodoo.activity.inspect;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.newland.me.ConnUtils;
import com.newland.me.DeviceManager;
import com.newland.mtype.ConnectionCloseEvent;
import com.newland.mtype.ModuleType;
import com.newland.mtype.event.DeviceEventListener;
import com.newland.mtype.module.common.rfcard.RFCardModule;
import com.newland.mtype.module.common.rfcard.RFResult;
import com.newland.mtype.util.ISOUtils;
import com.newland.mtypex.nseries.NSConnV100ConnParams;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
import tarce.myodoo.activity.BomFramworkActivity;
import tarce.myodoo.activity.WriteFeedMateriActivity;
import tarce.myodoo.adapter.product.ImgRecycAdapter;
import tarce.myodoo.device.Const;
import tarce.myodoo.uiutil.FullyGridLayoutManager;
import tarce.myodoo.uiutil.ImageUtil;
import tarce.myodoo.uiutil.NFCdialog;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.StringUtils;
import tarce.myodoo.utils.UserManager;
import tarce.support.AlertAialogUtils;
import tarce.support.BitmapUtils;
import tarce.support.MyLog;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;
import tarce.support.ViewUtils;

import static tarce.api.RetrofitClient.Url;

/**
 * Created by rose.zou on 2017/6/2.
 * 品检的Mo单详情页面
 */

public class InspectMoDetailActivity extends BaseActivity {
    private static final String K21_DRIVER_NAME = "com.newland.me.K21Driver";
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 1;//拍照
    @InjectView(R.id.state_inspect_detail)
    TextView stateInspectDetail;
    @InjectView(R.id.num_product_inspecdetail)
    TextView numProductInspecdetail;
    @InjectView(R.id.num_sample_inspecdetail)
    EditText numSampleInspecdetail;
    @InjectView(R.id.tv_rate_inspecdetail)
    TextView tvRateInspecdetail;
    @InjectView(R.id.num_rejects_inspecdetail)
    EditText numRejectsInspecdetail;
    @InjectView(R.id.num_rate_rejects)
    TextView numRateRejects;
    @InjectView(R.id.comments_of_inspecdetail)
    EditText commentsOfInspecdetail;
    @InjectView(R.id.tv_click_finish)
    TextView tvClickFinish;
    @InjectView(R.id.tv_pass_or)
    TextView tvPassOr;
    @InjectView(R.id.img_take_photo)
    ImageView imgTakePhoto;
    @InjectView(R.id.tv_look_bom)
    TextView tvLookBom;
    @InjectView(R.id.linear_three)
    LinearLayout linearThree;
    /*@InjectView(R.id.relative_img_take)
    RelativeLayout relativeImgTake;*/
    @InjectView(R.id.img_grid_recycler)
    RecyclerView imgGridRecycler;
    private QcFeedbaskBean.ResultBean.ResDataBean dataBean;
    private InventoryApi inventoryApi;
    private DeviceManager deviceManager;
    private RFCardModule rfCardModule;
    private NFCdialog nfCdialog;
    private Retrofit retrofit;
    private TextWatcher mEditnumSample = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (temp.length() > 0) {
                long input = Integer.parseInt(temp.toString());
                float num = (float) (input / dataBean.getQty_produced() * 100);
                tvRateInspecdetail.setText(num + "%");
            }
        }
    };

    private TextWatcher mEditnumRejects = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (temp.length() > 0) {
                float input = Float.parseFloat(temp.toString());
                //     long input = Integer.parseInt(temp.toString());
                float chouyang_num = Float.parseFloat(numSampleInspecdetail.getText().toString());
                //long chouyang_num = Integer.parseInt(numSampleInspecdetail.getText().toString());
                float num = input / chouyang_num * 100;
                numRateRejects.setText(num + "%");
            }
        }
    };
    private String selectedImagePath = "";
    private String imgPath;//图片拍照照片的本地路径
    private List<String> imgList = new ArrayList<>();
    private ImgRecycAdapter imgRecycAdapter;
    private InventoryApi inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspec_detail);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(InspectMoDetailActivity.this).create(InventoryApi.class);
        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(new OKHttpFactory(InspectMoDetailActivity.this).getOkHttpClient())
                .baseUrl(Url+"/linkloving_user_auth/")
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        Url = RetrofitClient.Url;
        Intent intent = getIntent();
        dataBean = (QcFeedbaskBean.ResultBean.ResDataBean) intent.getSerializableExtra("data");
        initRecycler();
        initView();
    }

    private void initRecycler() {
        FullyGridLayoutManager layoutManager = new FullyGridLayoutManager(InspectMoDetailActivity.this, 4);
        layoutManager.setAutoMeasureEnabled(true);
        imgGridRecycler.setLayoutManager(layoutManager);
        imgGridRecycler.setNestedScrollingEnabled(false);
        imgGridRecycler.setHasFixedSize(true);
    }

    private void initView() {
        setTitle(String.valueOf(dataBean.getProduction_id().getDisplay_name()));
        switch (dataBean.getState()) {
            case "draft":
                stateInspectDetail.setText("等待品检");
                numRateRejects.setText("0%");
                tvRateInspecdetail.setText("0%");
                numRejectsInspecdetail.setFocusable(false);
                numRejectsInspecdetail.setText("0");
                numSampleInspecdetail.setFocusable(false);
                numSampleInspecdetail.setText("0");
                commentsOfInspecdetail.setFocusable(false);
                showLinThreePin();
                break;
            case "qc_ing":
                stateInspectDetail.setText("品检中");
                tvPassOr.setVisibility(View.VISIBLE);
                tvClickFinish.setText("品检通过");
                numRateRejects.setText("");
                tvRateInspecdetail.setText("");
                imgTakePhoto.setVisibility(View.VISIBLE);
                imgRecycAdapter = new ImgRecycAdapter(InspectMoDetailActivity.this, R.layout.adapter_img_recyc, imgList);
                imgGridRecycler.setAdapter(imgRecycAdapter);
                showLinThreePin();
                break;
            case "qc_success":
                stateInspectDetail.setText("等待入库");
                numRateRejects.setText(Math.rint(dataBean.getQc_fail_rate()) + "%");
                tvRateInspecdetail.setText(Math.rint(dataBean.getQc_rate()) + "%");
                numRejectsInspecdetail.setFocusable(false);
                numRejectsInspecdetail.setText(StringUtils.doubleToString(dataBean.getQc_fail_qty()));
                numSampleInspecdetail.setFocusable(false);
                numSampleInspecdetail.setText(StringUtils.doubleToString(dataBean.getQc_test_qty()));
                commentsOfInspecdetail.setFocusable(false);
                commentsOfInspecdetail.setText(dataBean.getQc_note());
                imgRecycAdapter = new ImgRecycAdapter(InspectMoDetailActivity.this, R.layout.adapter_img_recyc, (List<String>) dataBean.getQc_img());
                imgGridRecycler.setAdapter(imgRecycAdapter);
                tvClickFinish.setText("入库");
             //   showLinThreeCang();
                showLinThreePro();
                break;
            case "qc_fail":
                stateInspectDetail.setText("品检失败");
                numRateRejects.setText(Math.rint(dataBean.getQc_fail_rate()) + "%");
                tvRateInspecdetail.setText(Math.rint(dataBean.getQc_rate()) + "%");
                numRejectsInspecdetail.setFocusable(false);
                numRejectsInspecdetail.setText(StringUtils.doubleToString(dataBean.getQc_fail_qty()));
                numSampleInspecdetail.setFocusable(false);
                numSampleInspecdetail.setText(StringUtils.doubleToString(dataBean.getQc_test_qty()));
                commentsOfInspecdetail.setFocusable(false);
                commentsOfInspecdetail.setText(dataBean.getQc_note());
                if (dataBean.getQc_img().size() > 0) {
                    imgRecycAdapter = new ImgRecycAdapter(InspectMoDetailActivity.this, R.layout.adapter_img_recyc, (List<String>) dataBean.getQc_img());
                    imgGridRecycler.setAdapter(imgRecycAdapter);
                }
                tvClickFinish.setText("开始返工");
                showLinThreePro();
                break;
        }
        numProductInspecdetail.setText(StringUtils.doubleToString(dataBean.getQty_produced()));
        numSampleInspecdetail.addTextChangedListener(mEditnumSample);
        numRejectsInspecdetail.addTextChangedListener(mEditnumRejects);
    }

    /**
     * 是否显示底部(仓库)
     */
    public void showLinThreeCang() {
        if (!UserManager.getSingleton().getGrops().contains("group_charge_warehouse")) {
            linearThree.setVisibility(View.GONE);
        }
    }

    /**
     * 是否显示底部（生产）
     */
    public void showLinThreePro() {
        if (!UserManager.getSingleton().getGrops().contains("group_charge_produce")) {
            linearThree.setVisibility(View.GONE);
        }
    }

    /**
     * 是否显示底部（品检）
     */
    public void showLinThreePin() {
        if (!UserManager.getSingleton().getGrops().contains("group_charge_inspection")) {
            linearThree.setVisibility(View.GONE);
        }
    }

    /**
     * 点击事件
     */
    @OnClick(R.id.tv_click_finish)
    void clickFinish(View view) {
        switch (tvClickFinish.getText().toString()) {
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
                        if (response.body() == null || response.body().getResult() == null) return;
                        if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() !=null) {
                            AlertAialogUtils.getCommonDialog(InspectMoDetailActivity.this, "")
                                    .setMessage("该单据状态变为品检中")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(InspectMoDetailActivity.this, InspectionSubActivity.class);
                                            intent.putExtra("state", "draft");
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).show();
                        }else {
                            ToastUtils.showCommonToast(InspectMoDetailActivity.this,  "出现错误，请联系开发人员调试");
                        }
                    }

                    @Override
                    public void onFailure(Call<StartInspectBean> call, Throwable t) {
                        dismissDefultProgressDialog();
                        ToastUtils.showCommonToast(InspectMoDetailActivity.this, t.toString());
                    }
                });
                break;
            case "品检通过":
                if (StringUtils.isNullOrEmpty(numRejectsInspecdetail.getText().toString())) {
                    ToastUtils.showCommonToast(InspectMoDetailActivity.this, "品检数量不能为0");
                    return;
                }
                if (StringUtils.isNullOrEmpty(numSampleInspecdetail.getText().toString())) {
                    ToastUtils.showCommonToast(InspectMoDetailActivity.this, "请填写品检通过数量");
                    return;
                }
                AlertAialogUtils.getCommonDialog(InspectMoDetailActivity.this, "")
                        .setMessage("是否确实品检通过")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkResult(1);
                            }
                        }).show();
                break;
            case "入库":
                if (nfCdialog!=null && nfCdialog.isShowing()){
                    tvClickFinish.setClickable(false);
                    return;
                }
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
                                        ToastUtils.showCommonToast(InspectMoDetailActivity.this, "不能识别序列号：" + Const.MessageTag.DATA);
                                        tvClickFinish.setClickable(true);
                                    } else {
                                        showDefultProgressDialog();
                                        String NFC_Number = ISOUtils.hexString(qPResult.getCardSerialNo());
                                        if (Url!=null){
                                            inventory = retrofit.create(InventoryApi.class);
                                        }else {
                                            Url = RetrofitClient.Url;
                                            inventory = retrofit.create(InventoryApi.class);
                                        }
                                        HashMap<Object, Object> hashMap = new HashMap<>();
                                        hashMap.put("card_num", NFC_Number);
                                        Call<NfcOrderBean> objectCall = inventory.authWarehouse(hashMap);
                                        objectCall.enqueue(new Callback<NfcOrderBean>() {
                                            @Override
                                            public void onResponse(Call<NfcOrderBean> call, Response<NfcOrderBean> response) {
                                                dismissDefultProgressDialog();
                                                tvClickFinish.setClickable(true);
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
                                                    final NfcOrderBean.ResultBean.ResDataBean res_dataNfc = response.body().getResult().getRes_data();
                                                    nfCdialog.setHeaderImage(R.drawable.defaultimage)
                                                            .setTip(res_dataNfc.getName() + res_dataNfc.getEmployee_id() + "\n" + res_dataNfc.getWork_email()
                                                                    + "\n\n" + "打卡成功")
                                                            .setCancelVisi().show();
                                                    threadDismiss(nfCdialog);
                                                    showDefultProgressDialog();
                                                    HashMap<Object, Object> doneHashmap = new HashMap<>();
                                                    doneHashmap.put("feedback_id", dataBean.getFeedback_id());
                                                    doneHashmap.put("employee_id",res_dataNfc.getEmployee_id());
                                                    Call<RukuBean> objectCall1 = inventoryApi.produceDone(doneHashmap);
                                                    objectCall1.enqueue(new MyCallback<RukuBean>() {
                                                        @Override
                                                        public void onResponse(Call<RukuBean> call, Response<RukuBean> response) {
                                                            dismissDefultProgressDialog();
                                                            if (response.body() == null || response.body().getResult() == null) return;
                                                            if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                                                                new TipDialog(InspectMoDetailActivity.this, R.style.MyDialogStyle, "入库成功")
                                                                        .setTrue(new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View v) {
                                                                                Intent intent = new Intent(InspectMoDetailActivity.this, InspectionSubActivity.class);
                                                                                intent.putExtra("state", "qc_success");
                                                                                startActivity(intent);
                                                                                finish();
                                                                            }
                                                                        }).show();
                                                            }else if (response.body().getResult().getRes_code() == -1 && response.body().getResult().getRes_data() != null){
                                                                ToastUtils.showCommonToast(InspectMoDetailActivity.this,  response.body().getResult().getRes_data().getError());
                                                            } else {
                                                               // ToastUtils.showCommonToast(InspectMoDetailActivity.this, "some error");
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<RukuBean> call, Throwable t) {
                                                            dismissDefultProgressDialog();
                                                            ToastUtils.showCommonToast(InspectMoDetailActivity.this, t.toString());
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
                                            ToastUtils.showCommonToast(InspectMoDetailActivity.this, "读卡超时");
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
                break;
            case "品检不通过":
                if (StringUtils.isNullOrEmpty(numRejectsInspecdetail.getText().toString())) {
                    ToastUtils.showCommonToast(InspectMoDetailActivity.this, "品检数量不能为0");
                    return;
                }
                if (StringUtils.isNullOrEmpty(numSampleInspecdetail.getText().toString())) {
                    ToastUtils.showCommonToast(InspectMoDetailActivity.this, "请填写品检通过数量");
                    return;
                }
                AlertAialogUtils.getCommonDialog(InspectMoDetailActivity.this, "")
                        .setMessage("是否确实品检不通过")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkResult(0);
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
                        if (response.body() == null || response.body().getResult()==null) return;
                        if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data()!=null) {
                            AlertAialogUtils.getCommonDialog(InspectMoDetailActivity.this, "该单据开始返工")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(InspectMoDetailActivity.this, InspectionSubActivity.class);
                                            intent.putExtra("state", "qc_fail");
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).show();
                        } else if (response.body().getResult().getRes_code() == -1) {
                            ToastUtils.showCommonToast(InspectMoDetailActivity.this, response.body().getResult().getRes_data().getError());
                        }
                    }

                    @Override
                    public void onFailure(Call<StartReworkBean> call, Throwable t) {

                    }
                });
                break;
        }
    }

    //显示nfc的dialog
    private void showNfcDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nfCdialog = new NFCdialog(InspectMoDetailActivity.this);
                nfCdialog.setCancel(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nfCdialog.dismiss();
                        tvClickFinish.setClickable(true);
                    }
                }).show();
            }
        });
    }

    //关闭dialog
    private void threadDismiss(final NFCdialog dialog) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1000);
    }
    /**
     * 查看bom结构
     */
    @OnClick(R.id.tv_look_bom)
    void lookBom(View view) {
        Intent intent = new Intent(InspectMoDetailActivity.this, BomFramworkActivity.class);
        intent.putExtra("order_id", (int)dataBean.getProduction_id().getOrder_id());
        startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        ViewUtils.collapseSoftInputMethod(InspectMoDetailActivity.this, numSampleInspecdetail);
        ViewUtils.collapseSoftInputMethod(InspectMoDetailActivity.this, numRejectsInspecdetail);
        ViewUtils.collapseSoftInputMethod(InspectMoDetailActivity.this, commentsOfInspecdetail);
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 拍照
     */
    @OnClick(R.id.img_take_photo)
    void takePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
        startActivityForResult(intent, REQUEST_CODE_IMAGE_CAPTURE);
    }

    /**
     * 品检不通过
     */
    @OnClick(R.id.tv_pass_or)
    void resultFalse(View view) {
        AlertAialogUtils.getCommonDialog(InspectMoDetailActivity.this, "")
                .setMessage("是否确定品检不通过")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkResult(0);
                    }
                }).show();
    }

    /**
     * 品检通过或者不通过接口
     */
    private void checkResult(final int result) {
        showDefultProgressDialog();
        HashMap<Object, Object> resultMap = new HashMap<>();
        resultMap.put("feedback_id", dataBean.getFeedback_id());
        resultMap.put("result", result);
        if (StringUtils.isNullOrEmpty(numRejectsInspecdetail.getText().toString())) {
            resultMap.put("qc_fail_qty", 0);
        } else {
            resultMap.put("qc_fail_qty", Integer.parseInt(numRejectsInspecdetail.getText().toString()));
        }
        if (StringUtils.isNullOrEmpty(numSampleInspecdetail.getText().toString())) {
            resultMap.put("qc_test_qty", 0);
        } else {
            resultMap.put("qc_test_qty", Integer.parseInt(numSampleInspecdetail.getText().toString()));
        }
        resultMap.put("qc_note", commentsOfInspecdetail.getText().toString());
        if (StringUtils.isNullOrEmpty(selectedImagePath)) {
            String[] img_strNull = {""};
            resultMap.put("qc_img", img_strNull);
        } else {
            String[] img_str = new String[imgList.size()];
            for (int i = 0; i < imgList.size(); i++) {
                img_str[i] = BitmapUtils.bitmapToBase64(ImageUtil.decodeFile(imgList.get(i)));
            }
               //     String[] img_str = {BitmapUtils.bitmapToBase64(decodeFile(selectedImagePath))};
            resultMap.put("qc_img", img_str);
        }
        Call<RejectResultBean> objectCall1 = inventoryApi.resultInspec(resultMap);
        objectCall1.enqueue(new MyCallback<RejectResultBean>() {
            @Override
            public void onResponse(Call<RejectResultBean> call, Response<RejectResultBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError()!=null){
                    ToastUtils.showCommonToast(InspectMoDetailActivity.this, "访问出错,或许有安全限制，请联系管理员");
                    return;
                }
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data()!=null) {
                    if (result == 1) {
                        AlertAialogUtils.getCommonDialog(InspectMoDetailActivity.this, "品检成功，等待入库")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(InspectMoDetailActivity.this, InspectionSubActivity.class);
                                        intent.putExtra("state", "qc_ing");
                                        startActivity(intent);
                                        finish();
                                    }
                                }).show();
                    } else {
                        AlertAialogUtils.getCommonDialog(InspectMoDetailActivity.this, "品检不通过，等待返工")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(InspectMoDetailActivity.this, InspectionSubActivity.class);
                                        intent.putExtra("state", "qc_ing");
                                        startActivity(intent);
                                        finish();
                                    }
                                }).show();
                    }
                } else if (response.body().getResult().getRes_code() == -1) {
                    ToastUtils.showCommonToast(InspectMoDetailActivity.this, response.body().getResult().getRes_data().getError());
                }
            }

            @Override
            public void onFailure(Call<RejectResultBean> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
    }

    public Uri setImageUri() {
        // Store image in dcim
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
        Uri imgUri = Uri.fromFile(file);
        this.imgPath = file.getAbsolutePath();
        return imgUri;
    }


    public String getImagePath() {
        return imgPath;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == REQUEST_CODE_IMAGE_CAPTURE) {
                selectedImagePath = getImagePath();
                imgList.add(selectedImagePath);
                imgRecycAdapter.notifyDataSetChanged();
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

    }


    public Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getAbsolutePath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }
    /**
     * 连接设备打印机
     */
    private void initDevice() {
        deviceManager = ConnUtils.getDeviceManager();
        try {
            deviceManager.init(InspectMoDetailActivity.this, K21_DRIVER_NAME, new NSConnV100ConnParams(), new DeviceEventListener<ConnectionCloseEvent>() {
                @Override
                public void onEvent(ConnectionCloseEvent connectionCloseEvent, Handler handler) {
                    if (connectionCloseEvent.isSuccess()) {
                        ToastUtils.showCommonToast(InspectMoDetailActivity.this, "设备被客户主动断开！");
                    }
                    if (connectionCloseEvent.isFailed()) {
                        ToastUtils.showCommonToast(InspectMoDetailActivity.this, "设备链接异常断开！");
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
            MyLog.e("OrderDetailActivity", "链接异常,请检查设备或重新连接..");
            //ToastUtils.showCommonToast(InspectMoDetailActivity.this, "链接异常,请检查设备或重新连接.." + e);
            return;
        }
        if (deviceManager!=null)
        rfCardModule = (RFCardModule) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_RFCARDREADER);
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
