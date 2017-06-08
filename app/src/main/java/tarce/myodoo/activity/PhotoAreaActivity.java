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
import android.view.MotionEvent;
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
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.inventory.AreaMessageBean;
import tarce.model.inventory.FinishPrepareMaBean;
import tarce.model.inventory.OrderDetailBean;
import tarce.model.inventory.UpdateMessageBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.processproduct.AreaMessageAdapter;
import tarce.myodoo.uiutil.ImageUtil;
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
    private List<AreaMessageBean.ResultBean.ResDataBean> res_data = new ArrayList<>();;
    private String selectedImagePath = "";
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
    private OrderDetailBean.ResultBean.ResDataBean resDataBean;

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
        resDataBean = (OrderDetailBean.ResultBean.ResDataBean) intent.getSerializableExtra("bean");
        if (change){
            tvFinishOrder.setText("提交产品位置信息");
        }
        setTitle("物料位置信息");
        // setRecyclerview(recyclerArea);
        recyclerArea.setLayoutManager(new LinearLayoutManager(PhotoAreaActivity.this));
        recyclerArea.addItemDecoration(new DividerItemDecoration(PhotoAreaActivity.this,
                DividerItemDecoration.VERTICAL));
        inventoryApi = RetrofitClient.getInstance(PhotoAreaActivity.this).create(InventoryApi.class);
        editListener();

    }

    /**
     * edittext的搜索监听
     */
    private void editListener() {
        editAreaMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    ViewUtils.collapseSoftInputMethod(PhotoAreaActivity.this, editAreaMessage);
                    HashMap<Object, Object> hashMap = new HashMap<>();
                    hashMap.put("condition", editAreaMessage.getText().toString());
                    Call<AreaMessageBean> areaMessage = inventoryApi.getAreaMessage(hashMap);
                    areaMessage.enqueue(new MyCallback<AreaMessageBean>() {
                        @Override
                        public void onResponse(Call<AreaMessageBean> call, Response<AreaMessageBean> response) {
                            if (response.body() == null) return;
                            if (response.body().getResult().getRes_code() == 1){
                                res_data = response.body().getResult().getRes_data();
                                adapter = new AreaMessageAdapter(R.layout.adapter_area_message, res_data);
                                recyclerArea.setAdapter(adapter);
                                if (res_data == null)return;
                                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        editAreaMessage.setText(res_data.get(position).getArea_name());
                                        ViewUtils.collapseSoftInputMethod(PhotoAreaActivity.this, editAreaMessage);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<AreaMessageBean> call, Throwable t) {
                            ToastUtils.showCommonToast(PhotoAreaActivity.this, t.toString());
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
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
        startActivityForResult(intent, REQUEST_CODE_IMAGE_CAPTURE);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        ViewUtils.collapseSoftInputMethod(PhotoAreaActivity.this, editAreaMessage);
        return super.dispatchTouchEvent(ev);
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
        if (StringUtils.isNullOrEmpty(selectedImagePath)){
            ToastUtils.showCommonToast(PhotoAreaActivity.this,"请拍照");
            return;
        }
        if (change){
            AlertAialogUtils.getCommonDialog(PhotoAreaActivity.this, "")
                    .setMessage("是否确定提交产品位置信息")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showDefultProgressDialog();
                            HashMap<Object,Object> hashMap = new HashMap<>();
                            hashMap.put("order_id",order_id);
                            hashMap.put("area_name",editAreaMessage.getText().toString());
                            hashMap.put("procure_img", BitmapUtils.bitmapToBase64(ImageUtil.decodeFile(selectedImagePath)));
                            Call<UpdateMessageBean> objectCall = inventoryApi.uploadProductArea(hashMap);
                            objectCall.enqueue(new MyCallback<UpdateMessageBean>() {
                                @Override
                                public void onResponse(Call<UpdateMessageBean> call, Response<UpdateMessageBean> response) {
                                    dismissDefultProgressDialog();
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
                                    Log.e("zouwansheng", "t = "+t.toString());
                                    dismissDefultProgressDialog();
                                }
                            });
                        }
                    }).show();
        }else {
            showDefultProgressDialog();
            HashMap<Object, Object> hashMap = new HashMap<>();
            hashMap.put("type", type);
            hashMap.put("order_id", order_id);
            hashMap.put("area_name", editAreaMessage.getText().toString());
            hashMap.put("img", BitmapUtils.bitmapToBase64(ImageUtil.decodeFile(selectedImagePath)));
            Call<UpdateMessageBean> objectCall = inventoryApi.commitMessage(hashMap);
            objectCall.enqueue(new MyCallback<UpdateMessageBean>() {
                @Override
                public void onResponse(Call<UpdateMessageBean> call, Response<UpdateMessageBean> response) {
                    if (response.body() == null)return;

                    if (response.body().getResult().getRes_code() == 1){
                        //提交备料
                        commitBeiliao();
                    }
                }

                @Override
                public void onFailure(Call<UpdateMessageBean> call, Throwable t) {
                    dismissDefultProgressDialog();
                }
            });
        }
    }

    private void commitBeiliao() {

        HashMap<Object, Object> hashMap = new HashMap();
        hashMap.put("order_id", order_id);
        Map[] maps = new Map[resDataBean.getStock_move_lines().size()];
        for (int i = 0; i < resDataBean.getStock_move_lines().size(); i++) {
            Map<Object, Object> mapSmall = new HashMap<>();
            mapSmall.put("stock_move_lines_id", resDataBean.getStock_move_lines().get(i).getId());
            mapSmall.put("quantity_ready", resDataBean.getStock_move_lines().get(i).getQuantity_ready());
            mapSmall.put("order_id", resDataBean.getStock_move_lines().get(i).getOrder_id());
            maps[i] = mapSmall;
        }
        hashMap.put("stock_moves", maps);
        Call<FinishPrepareMaBean> objectCall = inventoryApi.finishPrepareMa(hashMap);
        objectCall.enqueue(new MyCallback<FinishPrepareMaBean>(){
            @Override
            public void onResponse(Call<FinishPrepareMaBean> call, Response<FinishPrepareMaBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)return;
                if (response.body().getResult().getRes_code() == 1){
                  AlertAialogUtils.getCommonDialog(PhotoAreaActivity.this, "提交位置信息成功")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(PhotoAreaActivity.this, MaterialDetailActivity.class);
                                    intent.putExtra("limit",limit);
                                    intent.putExtra("process_id", process_id);
                                    intent.putExtra("state",delay_state);
                                    startActivity(intent);
                                    finish();
                                }
                            }).show();
                }
            }

            @Override
            public void onFailure(Call<FinishPrepareMaBean> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
    }

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
            if (requestCode == REQUEST_CODE_IMAGE_CAPTURE) {
                selectedImagePath = getImagePath();
                Glide.with(PhotoAreaActivity.this).load(new File(selectedImagePath)).into(imageShowPhoto);
                //imgUser.setImageBitmap(decodeFile(selectedImagePath));
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

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
