package tarce.myodoo.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.inventory.QcFeedbaskBean;
import tarce.model.inventory.RejectResultBean;
import tarce.model.inventory.RukuBean;
import tarce.model.inventory.StartInspectBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;
import tarce.support.AlertAialogUtils;
import tarce.support.BitmapUtils;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/6/2.
 * 品检的Mo单详情页面
 */

public class InspectMoDetailActivity extends ToolBarActivity {

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
    private QcFeedbaskBean.ResultBean.ResDataBean dataBean;
    private InventoryApi inventoryApi;
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
                float num = input / chouyang_num *100;
                numRateRejects.setText(num + "%");
            }
        }
    };
    private String selectedImagePath = "";
    private String imgPath;//图片拍照照片的本地路径

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspec_detail);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(InspectMoDetailActivity.this).create(InventoryApi.class);
        Intent intent = getIntent();
        dataBean = (QcFeedbaskBean.ResultBean.ResDataBean) intent.getSerializableExtra("data");
        initView();
    }

    private void initView() {
        setTitle(dataBean.getProduction_id().getDisplay_name());
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
                break;
            case "qc_ing":
                stateInspectDetail.setText("品检中");
                tvPassOr.setVisibility(View.VISIBLE);
                tvClickFinish.setText("品检通过");
                numRateRejects.setText("");
                tvRateInspecdetail.setText("");
                imgTakePhoto.setVisibility(View.VISIBLE);
                break;
            case "qc_success":
                stateInspectDetail.setText("等待入库");
                numRateRejects.setText(Math.rint(dataBean.getQc_fail_rate())+"%");
                tvRateInspecdetail.setText(Math.rint(dataBean.getQc_rate())+"%");
                numRejectsInspecdetail.setFocusable(false);
                numRejectsInspecdetail.setText(StringUtils.doubleToString(dataBean.getQc_fail_qty()));
                numSampleInspecdetail.setFocusable(false);
                numSampleInspecdetail.setText(StringUtils.doubleToString(dataBean.getQc_test_qty()));
                commentsOfInspecdetail.setFocusable(false);
                commentsOfInspecdetail.setText(dataBean.getQc_note());
                if (dataBean.getQc_img().size()>0){
                    imgTakePhoto.setVisibility(View.VISIBLE);
                    Glide.with(InspectMoDetailActivity.this).load(dataBean.getQc_img().get(0)).into(imgTakePhoto);
                    imgTakePhoto.setClickable(false);
                }
                tvClickFinish.setText("入库");
                break;
            case "qc_fail":
                stateInspectDetail.setText("品检失败");
                numRateRejects.setText(Math.rint(dataBean.getQc_fail_rate())+"%");
                tvRateInspecdetail.setText(Math.rint(dataBean.getQc_rate())+"%");
                numRejectsInspecdetail.setFocusable(false);
                numRejectsInspecdetail.setText(StringUtils.doubleToString(dataBean.getQc_fail_qty()));
                numSampleInspecdetail.setFocusable(false);
                numSampleInspecdetail.setText(StringUtils.doubleToString(dataBean.getQc_test_qty()));
                commentsOfInspecdetail.setFocusable(false);
                commentsOfInspecdetail.setText(dataBean.getQc_note());
                if (dataBean.getQc_img().size()>0){
                    imgTakePhoto.setVisibility(View.VISIBLE);
                    Glide.with(InspectMoDetailActivity.this).load(dataBean.getQc_img().get(0)).into(imgTakePhoto);
                    imgTakePhoto.setClickable(false);
                }
                tvClickFinish.setText("开始返工");
                break;
        }
        numProductInspecdetail.setText(StringUtils.doubleToString(dataBean.getQty_produced()));
        numSampleInspecdetail.addTextChangedListener(mEditnumSample);
        numRejectsInspecdetail.addTextChangedListener(mEditnumRejects);
    }

    /**
     * 点击事件
     */
    @OnClick(R.id.tv_click_finish)
    void clickFinish(View view) {
        showDefultProgressDialog();
        switch (tvClickFinish.getText().toString()) {
            case "开始品检":
                HashMap<Object, Object> hashMap = new HashMap<>();
                hashMap.put("feedback_id", dataBean.getFeedback_id());
                hashMap.put("order_id", dataBean.getProduction_id().getOrder_id());
                Call<StartInspectBean> objectCall = inventoryApi.startInspection(hashMap);
                objectCall.enqueue(new MyCallback<StartInspectBean>() {
                    @Override
                    public void onResponse(Call<StartInspectBean> call, Response<StartInspectBean> response) {
                        dismissDefultProgressDialog();
                        if (response.body() == null) return;
                        if (response.body().getResult().getRes_code() == 1) {
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
                        }
                    }

                    @Override
                    public void onFailure(Call<StartInspectBean> call, Throwable t) {
                        dismissDefultProgressDialog();
                    }
                });
                break;
            case "品检通过":
                if (StringUtils.isNullOrEmpty(numRejectsInspecdetail.getText().toString())){
                    ToastUtils.showCommonToast(InspectMoDetailActivity.this, "请填写品检数量");
                    return;
                }
                if (StringUtils.isNullOrEmpty(numSampleInspecdetail.getText().toString())){
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
                HashMap<Object, Object> doneHashmap = new HashMap<>();
                doneHashmap.put("feedback_id",dataBean.getFeedback_id());
                Call<RukuBean> objectCall1 = inventoryApi.produceDone(doneHashmap);
                objectCall1.enqueue(new MyCallback<RukuBean>() {
                    @Override
                    public void onResponse(Call<RukuBean> call, Response<RukuBean> response) {
                        dismissDefultProgressDialog();
                        if (response.body() == null)return;
                        if (response.body().getResult().getRes_code() == 1){
                            AlertAialogUtils.getCommonDialog(InspectMoDetailActivity.this, "")
                                    .setMessage("入库成功")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(InspectMoDetailActivity.this, InspectionSubActivity.class);
                                            intent.putExtra("state", "qc_success");
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RukuBean> call, Throwable t) {
                        dismissDefultProgressDialog();
                        super.onFailure(call, t);
                    }
                });
                break;
            case "开始返工":

                break;
        }
    }
    @OnClick(R.id.img_take_photo)
    void takePhoto(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
        startActivityForResult(intent, REQUEST_CODE_IMAGE_CAPTURE);
    }

    /**
     * 品检不通过
     * */
    @OnClick(R.id.tv_pass_or)
    void resultFalse(View view){
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
     * */
    private void checkResult(final int result){
        HashMap<Object, Object> resultMap = new HashMap<>();
        resultMap.put("feedback_id", dataBean.getFeedback_id());
        resultMap.put("result", result);
        resultMap.put("qc_test_qty", Integer.parseInt(numSampleInspecdetail.getText().toString()));
        resultMap.put("qc_fail_qty", Integer.parseInt(numRejectsInspecdetail.getText().toString()));
        resultMap.put("qc_note", commentsOfInspecdetail.getText().toString());
        if (StringUtils.isNullOrEmpty(selectedImagePath)){
            String[] img_strNull = {""};
            resultMap.put("qc_img", img_strNull);
        }else {
            String[] img_str = {BitmapUtils.bitmapToBase64(decodeFile(selectedImagePath))};
            resultMap.put("qc_img", img_str);
        }
        Call<RejectResultBean> objectCall1 = inventoryApi.resultInspec(resultMap);
        objectCall1.enqueue(new MyCallback<RejectResultBean>() {
            @Override
            public void onResponse(Call<RejectResultBean> call, Response<RejectResultBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)return;
                if (response.body().getResult().getRes_code() == 1){
                    if (result == 1){
                        AlertAialogUtils.getCommonDialog(InspectMoDetailActivity.this,"品检成功，等待入库")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(InspectMoDetailActivity.this, InspectionSubActivity.class);
                                        intent.putExtra("state", "qc_ing");
                                        startActivity(intent);
                                        finish();
                                    }
                                }).show();
                    }else {
                        AlertAialogUtils.getCommonDialog(InspectMoDetailActivity.this,"品检不通过，等待返工")
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
        if (resultCode != Activity.RESULT_CANCELED){
            if (requestCode == REQUEST_CODE_IMAGE_CAPTURE) {
                selectedImagePath = getImagePath();
                Glide.with(InspectMoDetailActivity.this).load(new File(selectedImagePath)).into(imgTakePhoto);
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
        String[] projection = { MediaStore.MediaColumns.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }
}
