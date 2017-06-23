package tarce.myodoo.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.POST;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.inventory.TakeDeAreaBean;
import tarce.model.inventory.TakeDelListBean;
import tarce.myodoo.R;
import tarce.myodoo.uiutil.ImageUtil;
import tarce.myodoo.utils.StringUtils;
import tarce.support.BitmapUtils;
import tarce.support.ToastUtils;

/**
 * Created by zouzou on 2017/6/23.
 * 填写品检信息页面
 */

public class WriteCheckMessaActivity extends BaseActivity {
    @InjectView(R.id.tv_pizhu)
    TextView tvPizhu;
    @InjectView(R.id.edit_write_pizhu)
    EditText editWritePizhu;
    @InjectView(R.id.img_take_photo)
    ImageView imgTakePhoto;
    @InjectView(R.id.pass_tv)
    TextView passTv;
    @InjectView(R.id.not_pass_tv)
    TextView notPassTv;
    private String selectedImagePath = "";
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 1;//拍照

    private String imgPath;//图片拍照照片的本地路径
    private String imgName;//后缀名
    private InventoryApi inventoryApi;
    private TakeDelListBean.ResultBean.ResDataBean resDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writecheck_messa);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        resDataBean = (TakeDelListBean.ResultBean.ResDataBean) intent.getSerializableExtra("bean");
        inventoryApi = RetrofitClient.getInstance(WriteCheckMessaActivity.this).create(InventoryApi.class);
    }
    @OnClick(R.id.img_take_photo)
    void takePhoto(View view){
        imgName = "photo.jpg";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
        startActivityForResult(intent, REQUEST_CODE_IMAGE_CAPTURE);
    }
    @OnClick(R.id.pass_tv)
    void passTv(View view){
        if (StringUtils.isNullOrEmpty(editWritePizhu.getText().toString())){
            ToastUtils.showCommonToast(WriteCheckMessaActivity.this, "请填写批注");
            return;
        }
        commitState("qc_ok");
    }
    @OnClick(R.id.not_pass_tv)
    void passNottv(View view){
        if (StringUtils.isNullOrEmpty(editWritePizhu.getText().toString())){
            ToastUtils.showCommonToast(WriteCheckMessaActivity.this, "请填写批注");
            return;
        }
        commitState("qc_failed");
    }

    private void commitState(String state){
        showDefultProgressDialog();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("picking_id",resDataBean.getPicking_id());
        hashMap.put("qc_note",editWritePizhu.getText().toString());
        hashMap.put("state","qc_ok");
        hashMap.put("qc_img", BitmapUtils.bitmapToBase64(ImageUtil.decodeFile(selectedImagePath)));
        int size = resDataBean.getPack_operation_product_ids().size();
        Map[] maps = new Map[size];
        for (int i = 0; i < size; i++) {
            Map<Object, Object> map = new HashMap<>();
            map.put("pack_id",resDataBean.getPack_operation_product_ids().get(i).getPack_id());
            map.put("qty_done", StringUtils.doubleToInt(resDataBean.getPack_operation_product_ids().get(i).getQty_done()));
            maps[i] = map;
        }
        hashMap.put("pack_operation_product_ids",maps);
        Call<TakeDeAreaBean> takeDeAreaBeanCall = inventoryApi.commitRuku(hashMap);
        takeDeAreaBeanCall.enqueue(new MyCallback<TakeDeAreaBean>() {
            @Override
            public void onResponse(Call<TakeDeAreaBean> call, Response<TakeDeAreaBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)return;
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
                Glide.with(WriteCheckMessaActivity.this).load(new File(selectedImagePath)).into(imgTakePhoto);
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

    }
}
