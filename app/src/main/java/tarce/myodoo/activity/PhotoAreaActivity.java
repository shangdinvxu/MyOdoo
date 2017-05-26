package tarce.myodoo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
                        mIntentPic = ImageUtil.takeBigPicture();
                        startActivityForResult(mIntentPic, REQUEST_CODE_IMAGE_CAPTURE);
                    }
                })
                .setSelectalbum(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mIntentPic = ImageUtil.choosePicture2();
                        startActivityForResult(mIntentPic, REQUEST_CODE_PICK_IMAGE);
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
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("type", type);
        hashMap.put("order_id", order_id);
        hashMap.put("area_name", editAreaMessage.getText().toString());
        hashMap.put("img", BitmapUtils.bitmapToBase64(BitmapFactory.decodeFile(imgPath)));
        Call<UpdateMessageBean> objectCall = inventoryApi.commitMessage(hashMap);
        objectCall.enqueue(new MyCallback<UpdateMessageBean>() {
            @Override
            public void onResponse(Call<UpdateMessageBean> call, Response<UpdateMessageBean> response) {
                if (response.body() == null)return;
                if (response.body().getError()!=null){
                    ToastUtils.showCommonToast(PhotoAreaActivity.this, response.body().getError().getMessage());
                }else if (response.body().getResult()!=null){
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
    /**
     * 压缩图片
     */
    private void startPhotoZoom(String sourcePath) {
        imgPath = ImageUtil.saveMyBitmap(sourcePath);

        //上传
        if (!StringUtils.isNullOrEmpty(imgPath)) {
            Glide.with(PhotoAreaActivity.this).load(new File(imgPath)).into(imageShowPhoto);
        }
    }

    @Override
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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
