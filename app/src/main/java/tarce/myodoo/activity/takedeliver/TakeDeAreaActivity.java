package tarce.myodoo.activity.takedeliver;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import tarce.model.inventory.TakeDeAreaBean;
import tarce.model.inventory.TakeDelListBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.adapter.processproduct.AreaMessageAdapter;
import tarce.myodoo.uiutil.ImageUtil;
import tarce.myodoo.utils.StringUtils;
import tarce.support.AlertAialogUtils;
import tarce.support.BitmapUtils;
import tarce.support.ToastUtils;
import tarce.support.ViewUtils;

/**
 * Created by zouzou on 2017/6/23.
 * 收货里面拍摄位置信息
 */

public class TakeDeAreaActivity extends BaseActivity {

    @InjectView(R.id.tv_one)
    TextView tvOne;
    @InjectView(R.id.image_show_photo)
    ImageView imageShowPhoto;
    @InjectView(R.id.tv_take_photo)
    TextView tvTakePhoto;
    @InjectView(R.id.edit_area_message)
    EditText editAreaMessage;
    @InjectView(R.id.relative_click_use)
    RelativeLayout relativeClickUse;
    @InjectView(R.id.recycler_area)
    RecyclerView recyclerArea;
    @InjectView(R.id.tv_finish_order)
    TextView tvFinishOrder;
    private InventoryApi inventoryApi;
    private List<AreaMessageBean.ResultBean.ResDataBean>  res_data;
    private AreaMessageAdapter adapter;
    private String selectedImagePath = "";
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 1;//拍照

    private String imgPath;//图片拍照照片的本地路径
    private String imgName;//后缀名
    private List<TakeDelListBean.ResultBean.ResDataBean.PackOperationProductIdsBean> data;
    private TakeDelListBean.ResultBean.ResDataBean resDataBean;
    private ArrayList<Integer> intArr;
    private String type_code;
    private String state;
    private String notneed;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_area);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        type_code = intent.getStringExtra("type_code");
        state = intent.getStringExtra("state");
        intArr = intent.getIntegerArrayListExtra("intArr");
        from = intent.getStringExtra("from");
        notneed = intent.getStringExtra("notneed");
        resDataBean = (TakeDelListBean.ResultBean.ResDataBean) intent.getSerializableExtra("bean");
        recyclerArea.setLayoutManager(new LinearLayoutManager(TakeDeAreaActivity.this));
        recyclerArea.addItemDecoration(new DividerItemDecoration(TakeDeAreaActivity.this,
                DividerItemDecoration.VERTICAL));
        inventoryApi = RetrofitClient.getInstance(TakeDeAreaActivity.this).create(InventoryApi.class);
        editListener();
    }

    @OnClick(R.id.image_show_photo)
    void takePhoto(View view) {
        imgName = "photo.jpg";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
        startActivityForResult(intent, REQUEST_CODE_IMAGE_CAPTURE);
    }

    @OnClick(R.id.tv_finish_order)
    void commitKu(View view){
        if (StringUtils.isNullOrEmpty(editAreaMessage.getText().toString())) {
            ToastUtils.showCommonToast(TakeDeAreaActivity.this, "请填写位置信息");
            return;
        }
        if (StringUtils.isNullOrEmpty(selectedImagePath)) {
            ToastUtils.showCommonToast(TakeDeAreaActivity.this, "请拍照");
            return;
        }
        AlertAialogUtils.getCommonDialog(TakeDeAreaActivity.this, "是否确认提交物料信息")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showDefultProgressDialog();
                        HashMap<Object, Object> hashMap = new HashMap<>();
                        hashMap.put("picking_id",resDataBean.getPicking_id());
                        hashMap.put("post_area_name",editAreaMessage.getText().toString());
                        hashMap.put("state","post");
                        hashMap.put("post_img", BitmapUtils.bitmapToBase64(ImageUtil.decodeFile(selectedImagePath)));
                        List<TakeDelListBean.ResultBean.ResDataBean.PackOperationProductIdsBean> ids = resDataBean.getPack_operation_product_ids();
                        List<TakeDelListBean.ResultBean.ResDataBean.PackOperationProductIdsBean> sub_ids = new ArrayList<>();
                        for (int i = 0; i < ids.size(); i++) {
                            if (ids.get(i).getPack_id() == -1){
                                sub_ids.add(ids.get(i));
                            }
                        }
                        ids.removeAll(sub_ids);
                        int size = ids.size();
                        Map[] maps = new Map[size];
                        for (int i = 0; i < size; i++) {
                            Map<Object, Object> map = new HashMap<>();
                            map.put("pack_id", ids.get(i).getPack_id());
                            map.put("qty_done", intArr.get(i));
                            maps[i] = map;
                        }
                        hashMap.put("pack_operation_product_ids", maps);
                        Call<TakeDeAreaBean> objectCall = inventoryApi.commitRuku(hashMap);
                        objectCall.enqueue(new MyCallback<TakeDeAreaBean>() {
                            @Override
                            public void onResponse(Call<TakeDeAreaBean> call, Response<TakeDeAreaBean> response) {
                                dismissDefultProgressDialog();
                                if (response.body() == null || response.body().getResult() == null)return;
                                if (response.body().getResult().getRes_data()!=null && response.body().getResult().getRes_code() == 1){
                                    AlertAialogUtils.getCommonDialog(TakeDeAreaActivity.this, "提交物料信息成功，等待入库品检")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(TakeDeAreaActivity.this, TakeDeliveListActivity.class);
                                                    intent.putExtra("type_code", type_code);
                                                    intent.putExtra("state",state);
                                                    intent.putExtra("from",from);
                                                    if (from.equals("no")){
                                                        intent.putExtra("notneed",notneed);
                                                    }
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(TakeDeAreaActivity.this, TakeDeliveListActivity.class);
                                            intent.putExtra("type_code", type_code);
                                            intent.putExtra("state",state);
                                            intent.putExtra("from",from);
                                            if (from.equals("no")){
                                                intent.putExtra("notneed",notneed);
                                            }
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<TakeDeAreaBean> call, Throwable t) {
                                dismissDefultProgressDialog();
                            }
                        });
                    }
                }).show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        ViewUtils.collapseSoftInputMethod(TakeDeAreaActivity.this, editAreaMessage);
        return super.dispatchTouchEvent(ev);
    }

    /**
     * edittext的搜索监听
     */
    private void editListener() {
        editAreaMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    ViewUtils.collapseSoftInputMethod(TakeDeAreaActivity.this, editAreaMessage);
                    HashMap<Object, Object> hashMap = new HashMap<>();
                    hashMap.put("condition", editAreaMessage.getText().toString());
                    Call<AreaMessageBean> areaMessage = inventoryApi.getAreaMessage(hashMap);
                    areaMessage.enqueue(new MyCallback<AreaMessageBean>() {
                        @Override
                        public void onResponse(Call<AreaMessageBean> call, Response<AreaMessageBean> response) {
                            if (response.body() == null || response.body().getResult() == null) return;
                            if (response.body().getResult().getRes_data()!=null && response.body().getResult().getRes_code() == 1) {
                                res_data = response.body().getResult().getRes_data();
                                adapter = new AreaMessageAdapter(R.layout.adapter_area_message, res_data);
                                recyclerArea.setAdapter(adapter);
                                if (res_data == null) return;
                                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        editAreaMessage.setText(res_data.get(position).getArea_name());
                                        ViewUtils.collapseSoftInputMethod(TakeDeAreaActivity.this, editAreaMessage);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<AreaMessageBean> call, Throwable t) {
                            ToastUtils.showCommonToast(TakeDeAreaActivity.this, t.toString());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == REQUEST_CODE_IMAGE_CAPTURE) {
                selectedImagePath = getImagePath();
                Glide.with(TakeDeAreaActivity.this).load(new File(selectedImagePath)).into(imageShowPhoto);
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

    }
}
