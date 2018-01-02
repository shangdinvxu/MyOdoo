package tarce.myodoo.activity.inbuy;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tarce.api.OKHttpFactory;
import tarce.api.api.InventoryApi;
import tarce.model.ScanBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.uiutil.InsertFeedbackDial;
import tarce.myodoo.uiutil.TipDialog;
import tarce.support.AlertAialogUtils;
import tarce.support.ToastUtils;

/**
 * Created by zouwansheng on 2017/12/14.
 */

public class ScancodeBuyActivity extends BaseActivity {

    @InjectView(R.id.tv_state_scan)
    TextView tvStateScan;
    @InjectView(R.id.name_product_inspecdetail)
    TextView nameProductInspecdetail;
    @InjectView(R.id.location_product_inspecdetail)
    TextView locationProductInspecdetail;
    @InjectView(R.id.num_product_inspecdetail)
    TextView numProductInspecdetail;
    @InjectView(R.id.num_sample_inspecdetail)
    TextView numSampleInspecdetail;
    @InjectView(R.id.tv_rate_inspecdetail)
    TextView tvRateInspecdetail;
    @InjectView(R.id.num_rejects_inspecdetail)
    TextView numRejectsInspecdetail;
    @InjectView(R.id.num_rate_rejects)
    TextView numRateRejects;
    @InjectView(R.id.comments_of_inspecdetail)
    EditText commentsOfInspecdetail;
    @InjectView(R.id.tv_ruku)
    TextView tvRuku;
    private Retrofit retrofit;
    private ScanBean.ResultBean.FeedbackBean feedbackBean;
    private InventoryApi inventoryApi;
    private String db;
    private String feedback_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scancodebuy);
        ButterKnife.inject(this);

        Intent intent = new Intent(ScancodeBuyActivity.this, CaptureActivity.class);
        startActivityForResult(intent, 1024);
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == 1024) {
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        showDefultProgressDialog();
                        String string = bundle.getString(CodeUtils.RESULT_STRING);
                        String[] strings = string.split(";");
                        if (strings.length!=3){
                            dismissDefultProgressDialog();
                            new TipDialog(ScancodeBuyActivity.this, R.style.MyDialogStyle, "请扫描符合规则的二维码")
                                    .setTrue(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            finish();
                                        }
                                    })
                                    .show();
                            return;
                        }
                        HashMap<Object, Object> hashMap = new HashMap<>();
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("db", strings[1]);
                            jsonObject.put("feedback_id", strings[2]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        db = strings[1];
                        feedback_id = strings[2];
                        String enToStr = Base64.encodeToString(jsonObject.toString().getBytes(), Base64.DEFAULT);
                        hashMap.put("data", enToStr);
                        retrofit = new Retrofit.Builder()
                                //设置OKHttpClient
                                .client(new OKHttpFactory(ScancodeBuyActivity.this).getOkHttpClient())
                                .baseUrl(strings[0] + "/linkloving_web/")
                                //gson转化器
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                .build();
                        inventoryApi = retrofit.create(InventoryApi.class);
                        Call<ScanBean> objectCall = inventoryApi.viewFeedbackDetail(hashMap);
                        objectCall.enqueue(new Callback<ScanBean>() {
                            @Override
                            public void onResponse(Call<ScanBean> call, Response<ScanBean> response) {
                                dismissDefultProgressDialog();
                                if (response.body() == null) return;
                                if (response.body().getError() != null) {
                                    new TipDialog(ScancodeBuyActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                            .show();
                                    return;
                                }
                                if (response.body().getResult().getFeedback() != null) {
                                    feedbackBean = response.body().getResult().getFeedback();
                                    initView();
                                } else {
                                    ToastUtils.showCommonToast(ScancodeBuyActivity.this, "暂未找到相关信息");
                                }
                            }

                            @Override
                            public void onFailure(Call<ScanBean> call, Throwable t) {
                                dismissDefultProgressDialog();
                            }
                        });
                    }
                }
            }
        } else {
            if (requestCode == 1024)
                finish();
        }
    }

    private void initView() {
        setTitle(feedbackBean.getProduction_id().getDisplay_name());
        nameProductInspecdetail.setText(feedbackBean.getProduction_id().getProduct_id().getProduct_name());
        locationProductInspecdetail.setText(feedbackBean.getProduction_id().getProduct_id().getArea_name()+"");
        numProductInspecdetail.setText(feedbackBean.getQty_produced()+"");
        numSampleInspecdetail.setText(feedbackBean.getQc_test_qty()+"");
        tvRateInspecdetail.setText(feedbackBean.getQc_rate()+"%");
        numRejectsInspecdetail.setText(feedbackBean.getQc_fail_qty()+"");
        numRateRejects.setText(feedbackBean.getQc_fail_rate()+"%");
        commentsOfInspecdetail.setText(feedbackBean.getQc_note()+"");
        switch (feedbackBean.getState()){
            case "draft":
                tvStateScan.setText("等待品检");
                break;
            case "qc_ing":
                tvStateScan.setText("品检中");
                break;
            case "qc_success":
                tvStateScan.setText("等待入库");
                break;
        }
    }

    @OnClick(R.id.tv_ruku)
    void setTvRuku(View view) {
        AlertAialogUtils.getCommonDialog(ScancodeBuyActivity.this, "是否确定入库")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showDefultProgressDialog();
                        HashMap<Object, Object> hashMap = new HashMap<>();
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("db", db);
                            jsonObject.put("feedback_id", feedback_id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String enToStr = Base64.encodeToString(jsonObject.toString().getBytes(), Base64.DEFAULT);
                        hashMap.put("data", enToStr);
                        Call<ScanBean> objectCall = inventoryApi.actionTransfer(hashMap);
                        objectCall.enqueue(new Callback<ScanBean>() {
                            @Override
                            public void onResponse(Call<ScanBean> call, Response<ScanBean> response) {
                                dismissDefultProgressDialog();
                                if (response.body() == null) return;
                                if (response.body().getError() != null) {
                                    new TipDialog(ScancodeBuyActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                            .show();
                                    return;
                                }
                                if (response.body().getResult().getFeedback()!=null){
                                    ToastUtils.showCommonToast(ScancodeBuyActivity.this, "入库成功");
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<ScanBean> call, Throwable t) {
                                dismissDefultProgressDialog();
                            }
                        });
                    }
                }).show();
    }
}
