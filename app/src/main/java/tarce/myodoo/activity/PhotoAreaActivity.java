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
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.inventory.AreaMessageBean;
import tarce.model.inventory.UpdateMessageBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.processproduct.AreaMessageAdapter;
import tarce.myodoo.uiutil.ImageUtil;
import tarce.myodoo.uiutil.TakePhotoDialog;
import tarce.myodoo.utils.StringUtils;
import tarce.support.AlertAialogUtils;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;
import tarce.support.ViewUtils;
import tarce.support.BitmapUtils;

/**
 * Created by rose.zou on 2017/5/25.
 * 用于产品的位置信息，拍照
 */

public class PhotoAreaActivity extends ToolBarActivity {

    @InjectView(R.id.tv_one)
    TextView tvOne;
    @InjectView(R.id.tv_take_photo)
    TextView tvTakePhoto;
    @InjectView(R.id.edit_area_message)
    EditText editAreaMessage;
    @InjectView(R.id.recycler_area)
    RecyclerView recyclerArea;
    @InjectView(R.id.image_show_photo)
    ImageView imageShowPhoto;
    @InjectView(R.id.relative_click_use)
    RelativeLayout relativeClickUse;
    @InjectView(R.id.tv_finish_order)
    TextView tvFinishOrder;
    private InventoryApi inventoryApi;
    private AreaMessageAdapter adapter;
    private List<AreaMessageBean.ResultBean.ResDataBean> res_data;
    private String selectedImagePath = "";
    private static final int REQUEST_CODE_PICK_IMAGE = 0;//相册
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 1;//拍照

    private String imgPath;//图片拍照照片的本地路径
    private String imgName;//后缀名
    private Intent mIntentPic;
    private String type;
    private int order_id;
    private int limit;
    private String delay_state;
    private int process_id;
    private boolean change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_area);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        order_id = intent.getIntExtra("order_id", 1);
        limit = intent.getIntExtra("limit", 1);
        delay_state = intent.getStringExtra("delay_state");
        process_id = intent.getIntExtra("process_id", 1);
        change = intent.getBooleanExtra("change", true);
        if (change){
            tvFinishOrder.setText("提交产品位置信息");
        }
        setTitle("物料位置信息");
        // setRecyclerview(recyclerArea);
        recyclerArea.setLayoutManager(new LinearLayoutManager(PhotoAreaActivity.this));
        recyclerArea.addItemDecoration(new DividerItemDecoration(PhotoAreaActivity.this,
                DividerItemDecoration.VERTICAL));
        editListener();

    }

    /**
     * edittext的搜索监听
     */
    private void editListener() {
        editAreaMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    res_data = new ArrayList<>();
                    inventoryApi = RetrofitClient.getInstance(PhotoAreaActivity.this).create(InventoryApi.class);
                    HashMap<Object, Object> hashMap = new HashMap<>();
                    hashMap.put("condition", editAreaMessage.getText().toString());
                    Call<AreaMessageBean> areaMessage = inventoryApi.getAreaMessage(hashMap);
                    areaMessage.enqueue(new MyCallback<AreaMessageBean>() {
                        @Override
                        public void onResponse(Call<AreaMessageBean> call, Response<AreaMessageBean> response) {
                            if (response.body() == null) return;
                            res_data = response.body().getResult().getRes_data();
                            adapter = new AreaMessageAdapter(R.layout.adapter_area_message, res_data);
                            recyclerArea.setAdapter(adapter);
                            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    editAreaMessage.setText(res_data.get(position).getArea_name());
                                    ViewUtils.collapseSoftInputMethod(PhotoAreaActivity.this, editAreaMessage);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<AreaMessageBean> call, Throwable t) {
                            super.onFailure(call, t);
                        }
                    });
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.image_show_photo)
    void takePhoto(View view) {
        imgName = "photo.jpg";
        new TakePhotoDialog(PhotoAreaActivity.this)
                .setTakephoto(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*mIntentPic = ImageUtil.takeBigPicture();
                        startActivityForResult(mIntentPic, REQUEST_CODE_IMAGE_CAPTURE);*/
                        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                        startActivityForResult(intent, REQUEST_CODE_IMAGE_CAPTURE);
                    }
                })
                .setSelectalbum(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*mIntentPic = ImageUtil.choosePicture2();
                        startActivityForResult(mIntentPic, REQUEST_CODE_PICK_IMAGE);*/
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, ""), REQUEST_CODE_PICK_IMAGE);
                    }
                })
                .setCancel()
                .show();

    }

    /**
     * relative点击事件
     */
    @OnClick(R.id.recycler_area)
    void clickRela(View view) {
        res_data.clear();
        adapter.notifyDataSetChanged();
        ViewUtils.collapseSoftInputMethod(PhotoAreaActivity.this, editAreaMessage);
    }

    /**
     * 提交物料信息
     * */
    @OnClick(R.id.tv_finish_order)
    void commitMessage(View view){
        if (StringUtils.isNullOrEmpty(editAreaMessage.getText().toString())){
            ToastUtils.showCommonToast(PhotoAreaActivity.this,"请填写位置信息");
            return;
        }
        if (change){
            AlertAialogUtils.getCommonDialog(PhotoAreaActivity.this, "")
                    .setMessage("是否确定提交产品位置信息")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HashMap<Object,Object> hashMap = new HashMap<>();
                            hashMap.put("order_id",order_id);
                            hashMap.put("area_name",editAreaMessage.getText().toString());
                            hashMap.put("procure_img", BitmapUtils.bitmapToBase64(BitmapFactory.decodeFile(selectedImagePath)));
                            Call<UpdateMessageBean> objectCall = inventoryApi.uploadProductArea(hashMap);
                            objectCall.enqueue(new MyCallback<UpdateMessageBean>() {
                                @Override
                                public void onResponse(Call<UpdateMessageBean> call, Response<UpdateMessageBean> response) {
                                    if (response.body() == null)return;
                                    if (response.body().getResult().getRes_code() == 1){
                                        Intent intent = new Intent(PhotoAreaActivity.this, ProductLlActivity.class);
                                        intent.putExtra("name_activity","生产中");
                                        intent.putExtra("state_product","progress");
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<UpdateMessageBean> call, Throwable t) {
                                    super.onFailure(call, t);
                                }
                            });
                        }
                    }).show();
        }else {
            HashMap<Object, Object> hashMap = new HashMap<>();
            hashMap.put("type", type);
            hashMap.put("order_id", order_id);
            hashMap.put("area_name", editAreaMessage.getText().toString());
            hashMap.put("img", BitmapUtils.bitmapToBase64(BitmapFactory.decodeFile(selectedImagePath)));
            Call<UpdateMessageBean> objectCall = inventoryApi.commitMessage(hashMap);
            objectCall.enqueue(new MyCallback<UpdateMessageBean>() {
                @Override
                public void onResponse(Call<UpdateMessageBean> call, Response<UpdateMessageBean> response) {
                    if (response.body() == null)return;
                    /*if (response.body().getError()!=null){
                        ToastUtils.showCommonToast(PhotoAreaActivity.this, response.body().getError().getMessage());
                    }else if (response.body().getResult()!=null){
                        Intent intent = new Intent(PhotoAreaActivity.this, MaterialDetailActivity.class);
                        intent.putExtra("limit",limit);
                        intent.putExtra("process_id", process_id);
                        intent.putExtra("state",delay_state);
                        startActivity(intent);
                        PhotoAreaActivity.this.finish();
                    }*/
                    if (response.body().getResult().getRes_code() == 1){
                        Intent intent = new Intent(PhotoAreaActivity.this, MaterialDetailActivity.class);
                        intent.putExtra("limit",limit);
                        intent.putExtra("process_id", process_id);
                        intent.putExtra("state",delay_state);
                        startActivity(intent);
                        PhotoAreaActivity.this.finish();
                    }
                }

                @Override
                public void onFailure(Call<UpdateMessageBean> call, Throwable t) {
                    super.onFailure(call, t);
                }
            });
        }
    }
    /**
     * 压缩图片
     *//*
    private void startPhotoZoom(String sourcePath) {
        imgPath = ImageUtil.saveMyBitmap(sourcePath);

        //上传
        if (!StringUtils.isNullOrEmpty(imgPath)) {
            Glide.with(PhotoAreaActivity.this).load(new File(imgPath)).into(imageShowPhoto);
        }
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_IMAGE_CAPTURE:
            case REQUEST_CODE_PICK_IMAGE:
                String sourcePath = ImageUtil.retrievePath(PhotoAreaActivity.this, mIntentPic, data);
                if (StringUtils.isNullOrEmpty(sourcePath)) {
                    return;
                }
                startPhotoZoom(sourcePath);
                break;

        }
    }*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
            if (requestCode == REQUEST_CODE_PICK_IMAGE) {
                selectedImagePath = getAbsolutePath(data.getData());
                Glide.with(PhotoAreaActivity.this).load(new File(selectedImagePath)).into(imageShowPhoto);
                //imgUser.setImageBitmap(decodeFile(selectedImagePath));
            } else if (requestCode == REQUEST_CODE_IMAGE_CAPTURE) {
                selectedImagePath = getImagePath();
                Glide.with(PhotoAreaActivity.this).load(new File(selectedImagePath)).into(imageShowPhoto);
                //imgUser.setImageBitmap(decodeFile(selectedImagePath));
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
