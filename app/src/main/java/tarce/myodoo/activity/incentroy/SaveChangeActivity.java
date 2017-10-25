package tarce.myodoo.activity.incentroy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.FindProductByConditionResponse;
import tarce.model.inventory.AreaMessageBean;
import tarce.model.inventory.DiyListBean;
import tarce.myodoo.R;
import tarce.myodoo.uiutil.Areardialog;
import tarce.myodoo.uiutil.ImageUtil;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.StringUtils;
import tarce.support.BitmapUtils;
import tarce.support.ToastUtils;
import tarce.support.ViewUtils;

/**
 * Created by zouwansheng on 2017/8/29.
 */

public class SaveChangeActivity extends AppCompatActivity {
    private static final int ADDKUCUN = 100;
    @InjectView(R.id.tv_takephoto)
    TextView tvTakephoto;
    @InjectView(R.id.product_weight)
    EditText productWeight;
    private String selectedImagePath = "";
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 1;//拍照
    private String imgPath;//图片拍照照片的本地路径
    private String imgName;//后缀名
    @InjectView(R.id.back_left)
    ImageView backLeft;
    @InjectView(R.id.save_tv)
    TextView saveTv;
    @InjectView(R.id.image_invent_detail)
    ImageView imageInventDetail;
    @InjectView(R.id.liaohao)
    TextView liaohao;
    @InjectView(R.id.area)
    EditText area;
    @InjectView(R.id.lilun_num)
    TextView lilunNum;
    @InjectView(R.id.actrul_num)
    EditText actrulNum;
    @InjectView(R.id.product_guige)
    TextView productGuige;
    private InventoryApi inventoryApi;
    private ProgressDialog dialog;
    private FindProductByConditionResponse.ResultBean.ResDataBean res_data;
    private int area_id;
    private String scan;
    private DiyListBean diyListBean;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savechange);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(SaveChangeActivity.this).create(InventoryApi.class);
        Intent intent = getIntent();
        scan = intent.getStringExtra("scan");
        if (scan.equals("no")) {
            diyListBean = (DiyListBean) intent.getSerializableExtra("bean");
            position = intent.getIntExtra("position", 1);
            initViewDiy();
        } else {
            Intent intent1 = new Intent(SaveChangeActivity.this, CaptureActivity.class);
            startActivityForResult(intent1, ADDKUCUN);
        }
        dialog = new ProgressDialog(SaveChangeActivity.this);
        dialog.setMessage("加载中。。。");
    }

    /**
     * diy的库存条目view初始化
     */
    private void initViewDiy() {
        Glide.with(SaveChangeActivity.this).load(diyListBean.getProduct().getImage_medium()).into(imageInventDetail);
        liaohao.setText(diyListBean.getProduct().getProduct_name());
        area.setText(diyListBean.getProduct().getArea().getArea_name());
        area.setSelection(area.getText().length());
        area_id = diyListBean.getProduct().getArea().getArea_id();
        lilunNum.setText(diyListBean.getTheoretical_qty() + "");
        actrulNum.setText(diyListBean.getProduct_qty() + "");
        actrulNum.setSelection(actrulNum.getText().length());
        productGuige.setText(diyListBean.getProduct().getProduct_spec());
        productWeight.setText(diyListBean.getProduct().getWeight() + "");
        initEzdit();
    }

    @OnClick(R.id.back_left)
    void backLeft(View view) {
        finish();
    }

    @OnClick(R.id.save_tv)
    void setSaveTv(View view) {
        Intent intent = new Intent();
        if (scan.equals("no")) {
            if (!StringUtils.isNullOrEmpty(selectedImagePath)) {
                diyListBean.product.image_medium = BitmapUtils.bitmapToBase64(ImageUtil.decodeFile(selectedImagePath));
            }
            diyListBean.theoretical_qty = diyListBean.getTheoretical_qty();
            diyListBean.product_qty = Double.valueOf(actrulNum.getText().toString());
            diyListBean.product.weight = productWeight.getText().toString();
            diyListBean.product.area.area_name = area.getText().toString();
            diyListBean.product.area.area_id = area_id;
            intent.putExtra("bean", diyListBean);
            intent.putExtra("position", position);
        } else {
            if (StringUtils.isNullOrEmpty(actrulNum.getText().toString())) {
                ToastUtils.showCommonToast(SaveChangeActivity.this, "请输入数量");
                return;
            }
            DiyListBean bean = new DiyListBean();
            final DiyListBean.ProductBean productBean = new DiyListBean.ProductBean();
            DiyListBean.ProductBean.ArearBean arearBean = new DiyListBean.ProductBean.ArearBean();
            arearBean.area_id = area_id;
            arearBean.area_name = area.getText().toString();
            productBean.area = arearBean;
            if (!StringUtils.isNullOrEmpty(selectedImagePath)) {
                productBean.image_medium = BitmapUtils.bitmapToBase64(ImageUtil.decodeFile(selectedImagePath));
            } else {
                productBean.image_medium = res_data.getProduct().getImage_medium();
            }
            productBean.product_id = res_data.getProduct().getProduct_id();
            productBean.product_name = res_data.getProduct().getProduct_name();
            productBean.product_spec = res_data.getProduct().getProduct_spec();
            productBean.weight = productWeight.getText().toString();
            bean.product = productBean;
            bean.product_qty = Integer.parseInt(actrulNum.getText().toString());
            bean.theoretical_qty = res_data.getTheoretical_qty();
            intent.putExtra("bean", bean);
        }
        setResult(AddCuActivity.SETRESULT, intent);
        finish();
    }

    @OnClick(R.id.image_invent_detail)
    void setImageInventDetail(View view) {
        imgName = "photo.jpg";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
        startActivityForResult(intent, REQUEST_CODE_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == ADDKUCUN) {
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String string = bundle.getString(CodeUtils.RESULT_STRING);
                        Log.e("zws", "string = " + string);
                        dialog.show();
                        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                        objectObjectHashMap.put("default_code", string);
                        HashMap<Object, Object> objectObjectHashMap1 = new HashMap<>();
                        objectObjectHashMap1.put("condition", objectObjectHashMap);
                        Call<FindProductByConditionResponse> productByCondition = inventoryApi.findProductByCondition(objectObjectHashMap1);
                        productByCondition.enqueue(new Callback<FindProductByConditionResponse>() {
                            @Override
                            public void onResponse(Call<FindProductByConditionResponse> call, Response<FindProductByConditionResponse> response) {
                                dialog.dismiss();
                                if (response.body() == null) return;
                                if (response.body().getError() != null) {
                                    new TipDialog(SaveChangeActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                            .show();
                                    return;
                                }
                                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                                    res_data = response.body().getResult().getRes_data();
                                    initView();
                                } else if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == -1) {
                                    ToastUtils.showCommonToast(SaveChangeActivity.this, response.body().getResult().getRes_data().getError());
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<FindProductByConditionResponse> call, Throwable t) {
                                dialog.dismiss();
                            }
                        });
                    }
                }
            } else if (requestCode == REQUEST_CODE_IMAGE_CAPTURE) {
                selectedImagePath = getImagePath();
                Glide.with(SaveChangeActivity.this).load(new File(selectedImagePath)).into(imageInventDetail);
            }
        } else {
            if (requestCode == ADDKUCUN)
                finish();
        }
    }


    private void initView() {
        if (StringUtils.isNullOrEmpty(res_data.getProduct().getImage_medium())) {
            tvTakephoto.setVisibility(View.VISIBLE);
        } else {
           // Log.e("zws", "img = "+res_data.getProduct().getImage_medium());
            Glide.with(SaveChangeActivity.this)
                    .load(res_data.getProduct().getImage_medium())
                    .placeholder(R.drawable.person_camera_icon)
                    .crossFade()
                    .into(imageInventDetail);
        }
        liaohao.setText(res_data.getProduct().getProduct_name());
        if (res_data.getProduct().getArea().getArea_name() == null) {
            area.setText("");
        } else {
            area.setText(res_data.getProduct().getArea().getArea_name() + "");
        }
        area.setSelection(area.getText().length());
        if (res_data.getProduct().getArea().getArea_id() != null) {
            area_id = (int) res_data.getProduct().getArea().getArea_id();
        } else {
            area_id = 0;
        }
        lilunNum.setText(res_data.getTheoretical_qty() + "");
        actrulNum.setText(res_data.getProduct_qty() + "");
        actrulNum.setSelection(actrulNum.getText().length());
        productGuige.setText(res_data.getProduct().getProduct_spec());
        if (res_data.getProduct().getWeight() == 0){
            productWeight.setText("0");
        }else {
            productWeight.setText(res_data.getProduct().getWeight() + "");
        }
        initEzdit();
    }

    //监听edittext
    private void initEzdit() {
        area.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    ViewUtils.collapseSoftInputMethod(SaveChangeActivity.this, area);
                    dialog.show();
                    HashMap<Object, Object> hashMap = new HashMap<>();
                    hashMap.put("condition", area.getText().toString());
                    Call<AreaMessageBean> areaMessage = inventoryApi.getAreaMessage(hashMap);
                    areaMessage.enqueue(new MyCallback<AreaMessageBean>() {
                        @Override
                        public void onResponse(Call<AreaMessageBean> call, Response<AreaMessageBean> response) {
                            dialog.dismiss();
                            if (response.body() == null) return;
                            if (response.body().getResult() == null) {
                                ToastUtils.showCommonToast(SaveChangeActivity.this, "没有返回数据");
                                return;
                            }
                            if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                                List<AreaMessageBean.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                                Areardialog areardialog = new Areardialog(SaveChangeActivity.this, R.style.MyDialogStyle, res_data);
                                areardialog.sendNameCompany(new Areardialog.GetCompany() {
                                    @Override
                                    public void getCompanyName(String name, int company_id) {
                                        area.setText(name);
                                        area_id = company_id;
                                    }
                                });
                                areardialog.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AreaMessageBean> call, Throwable t) {
                            dialog.dismiss();
//                            ToastUtils.showCommonToast(PhotoAreaActivity.this, t.toString());
                            Log.d("PhotoAreaActivity", t.toString());
                        }
                    });
                    return true;
                }
                return false;
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

}
