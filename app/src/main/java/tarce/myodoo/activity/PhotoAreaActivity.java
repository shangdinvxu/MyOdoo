package tarce.myodoo.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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
import com.newland.me.ConnUtils;
import com.newland.me.DeviceManager;
import com.newland.mtype.ConnectionCloseEvent;
import com.newland.mtype.ModuleType;
import com.newland.mtype.event.DeviceEventListener;
import com.newland.mtype.module.common.printer.Printer;
import com.newland.mtype.module.common.printer.WordStockType;
import com.newland.mtypex.nseries.NSConnV100ConnParams;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.DateTool;
import tarce.myodoo.utils.StringUtils;
import tarce.support.AlertAialogUtils;
import tarce.support.MyLog;
import tarce.support.TimeUtils;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;
import tarce.support.ViewUtils;
import tarce.support.BitmapUtils;

/**
 * Created by rose.zou on 2017/5/25.
 * 用于产品的位置信息，拍照
 */

public class PhotoAreaActivity extends ToolBarActivity {
    private static final String K21_DRIVER_NAME = "com.newland.me.K21Driver";
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
    private List<AreaMessageBean.ResultBean.ResDataBean> res_data = new ArrayList<>();

    private String selectedImagePath = "";
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 1;//拍照

    private String imgPath;//图片拍照照片的本地路径
    private String imgName;//后缀名
    private String type;
    private int order_id;
    private int limit;
    private String delay_state;
    private int process_id;
    private boolean change;
    private OrderDetailBean.ResultBean.ResDataBean resDataBean;
    private Printer printer;
    private DeviceManager deviceManager;
    private String typeString;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    printer.setLineSpace(1);
                    printer.print("MO单号：" + resDataBean.getDisplay_name() + "\n" + "产品: " + resDataBean.getProduct_name() + "\n" + "时间： " + TimeUtils.utc2Local(resDataBean.getDate_planned_start()) + "\n" +
                            "负责人: " + resDataBean.getIn_charge_name() + "\n" + "生产数量：" + resDataBean.getQty_produced() + "\n" + "需求数量：" + resDataBean.getProduct_qty()
                            + "\n" + "规格：" + resDataBean.getProduct_id().getProduct_specs() + "\n" + "工序：" + resDataBean.getProcess_id().getName() + "\n" + "类型：" + typeString
                            + "\n" + "MO单备注：" + resDataBean.getRemark() + "\n" + "销售单备注：" + resDataBean.getSale_remark() + "\n" + "仓库备注：\n\n" + "品检备注：\n\n", 30, TimeUnit.SECONDS);
                    Bitmap mBitmap = CodeUtils.createImage(resDataBean.getDisplay_name(), 150, 150, null);
                    printer.print(0, mBitmap, 30, TimeUnit.SECONDS);
                    printer.print("\n" + "打印时间：" + DateTool.getDateTime(), 30, TimeUnit.SECONDS);
                    printer.print("\n\n\n\n\n\n\n", 30, TimeUnit.SECONDS);
                    break;
                case 2:
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private int production_line_id;

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
        if (change) {
            tvFinishOrder.setText("提交产品位置信息");
        }
        if (type.equals("progress")){
            production_line_id = intent.getIntExtra("production_line_id", 100);
        }
        setTitle("物料位置信息");
        switch (String.valueOf(resDataBean.getProduction_order_type())) {
            case "stockup":
                typeString = "备货制";
                break;
            case "ordering":
                typeString = "订单制";
                break;
        }
        recyclerArea.setLayoutManager(new LinearLayoutManager(PhotoAreaActivity.this));
        recyclerArea.addItemDecoration(new DividerItemDecoration(PhotoAreaActivity.this,
                DividerItemDecoration.VERTICAL));
        inventoryApi = RetrofitClient.getInstance(PhotoAreaActivity.this).create(InventoryApi.class);
        editListener();

        initDevice();
        printer = (Printer) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_PRINTER);
        printer.init();
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
                            if (response.body().getError() != null) {
                                new TipDialog(PhotoAreaActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                        .show();
                                return;
                            }
                            if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                                res_data = response.body().getResult().getRes_data();
                                adapter = new AreaMessageAdapter(R.layout.adapter_area_message, res_data);
                                recyclerArea.setAdapter(adapter);
                                if (res_data == null) return;
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
     */
    @OnClick(R.id.tv_finish_order)
    void commitMessage(View view) {
        showDefultProgressDialog();
        if (StringUtils.isNullOrEmpty(editAreaMessage.getText().toString())) {
            dismissDefultProgressDialog();
            ToastUtils.showCommonToast(PhotoAreaActivity.this, "请填写位置信息");
            return;
        } else if (StringUtils.isNullOrEmpty(selectedImagePath)) {
            dismissDefultProgressDialog();
            ToastUtils.showCommonToast(PhotoAreaActivity.this, "请拍照");
            return;
        }
        tvFinishOrder.setEnabled(false);
        if (change) {
            dismissDefultProgressDialog();
            AlertAialogUtils.getCommonDialog(PhotoAreaActivity.this, "是否确定提交产品位置信息")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showDefultProgressDialog();
                            HashMap<Object, Object> hashMap = new HashMap<>();
                            hashMap.put("order_id", order_id);
                            hashMap.put("area_name", editAreaMessage.getText().toString());
                            hashMap.put("procure_img", BitmapUtils.bitmapToBase64(ImageUtil.decodeFile(selectedImagePath)));
                            Call<UpdateMessageBean> objectCall = inventoryApi.uploadProductArea(hashMap);
                            objectCall.enqueue(new MyCallback<UpdateMessageBean>() {
                                @Override
                                public void onResponse(Call<UpdateMessageBean> call, Response<UpdateMessageBean> response) {
                                    tvFinishOrder.setEnabled(true);
                                    dismissDefultProgressDialog();
                                    if (response.body() == null) return;
                                    if (response.body().getError()!=null){
                                        new TipDialog(PhotoAreaActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                               .show();
                                        return;
                                    }
                                    if (response.body().getResult().getRes_code() == 1) {
//                                        Intent intent = new Intent(PhotoAreaActivity.this, ProductLineListActivity.class);
//                                        intent.putExtra("name_activity", "生产中");
//                                        intent.putExtra("state_product", "progress");
//                                        intent.putExtra("process_id", process_id);
//                                        intent.putExtra("production_line_id", production_line_id);
//                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<UpdateMessageBean> call, Throwable t) {
                                    tvFinishOrder.setEnabled(true);
                                    dismissDefultProgressDialog();
//                                    ToastUtils.showCommonToast(PhotoAreaActivity.this, t.toString());
                                    Log.e("PhotoAreaActivity", t.toString());
                                }
                            });
                        }
                    }).show();
        } else {
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
                    dismissDefultProgressDialog();
                    if (response.body() == null) return;
                    if (response.body().getError()!=null){
                        new TipDialog(PhotoAreaActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                .show();
                        return;
                    }
                    if (response.body().getResult().getRes_code() == 1) {
                        new TipDialog(PhotoAreaActivity.this, R.style.MyDialogStyle, "提交物料信息成功，点击返回")
                                .setTrue(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        finish();
                                    }
                                }).show();
                       // showDefultProgressDialog();
                        //提交备料
                       // commitBeiliao();
                    } else if (response.body().getResult().getRes_code() == -1 && response.body().getResult().getRes_data() != null) {
                        ToastUtils.showCommonToast(PhotoAreaActivity.this, response.body().getResult().getRes_data().getError());
                    }
                }

                @Override
                public void onFailure(Call<UpdateMessageBean> call, Throwable t) {
                    tvFinishOrder.setEnabled(true);
                    dismissDefultProgressDialog();
                    Log.e("PhotoAreaActivity", t.toString());
                }
            });
        }
    }

    /**
     * 连接设备打印机
     */
    private void initDevice() {
        deviceManager = ConnUtils.getDeviceManager();
        try {
            deviceManager.init(PhotoAreaActivity.this, K21_DRIVER_NAME, new NSConnV100ConnParams(), new DeviceEventListener<ConnectionCloseEvent>() {
                @Override
                public void onEvent(ConnectionCloseEvent connectionCloseEvent, Handler handler) {
                    if (connectionCloseEvent.isSuccess()) {
                        ToastUtils.showCommonToast(PhotoAreaActivity.this, "设备被客户主动断开！");
                    }
                    if (connectionCloseEvent.isFailed()) {
                        ToastUtils.showCommonToast(PhotoAreaActivity.this, "设备链接异常断开！");
                    }
                }

                @Override
                public Handler getUIHandler() {
                    return null;
                }
            });
            deviceManager.connect();
            MyLog.e("PhotoAreaActivity", "连接成功");
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showCommonToast(PhotoAreaActivity.this, "链接异常,请检查设备或重新连接.." + e);
        }
    }

    private void commitBeiliao() {
        HashMap<Object, Object> hashMap = new HashMap();
        hashMap.put("order_id", order_id);
        /*Map[] maps = new Map[resDataBean.getStock_move_lines().size()];
        for (int i = 0; i < resDataBean.getStock_move_lines().size(); i++) {
            Map<Object, Object> mapSmall = new HashMap<>();
            mapSmall.put("stock_move_lines_id", resDataBean.getStock_move_lines().get(i).getId());
            mapSmall.put("quantity_ready", resDataBean.getStock_move_lines().get(i).getQuantity_ready());
            mapSmall.put("order_id", resDataBean.getStock_move_lines().get(i).getOrder_id());
            maps[i] = mapSmall;
        }
        hashMap.put("stock_moves", maps);*/
        Call<FinishPrepareMaBean> objectCall = inventoryApi.newfinishPrepareMa(hashMap);
        objectCall.enqueue(new MyCallback<FinishPrepareMaBean>() {
            @Override
            public void onResponse(Call<FinishPrepareMaBean> call, Response<FinishPrepareMaBean> response) {
                tvFinishOrder.setEnabled(true);
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError()!=null){
                    new TipDialog(PhotoAreaActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                    new TipDialog(PhotoAreaActivity.this, R.style.MyDialogStyle, "提交位置信息成功,点击确定将打印MO单，请等待")
                            .setTrue(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Message message = new Message();
                                    message.what = 1;
                                    handler.sendMessage(message);

                                    Intent intent = new Intent(PhotoAreaActivity.this, MaterialDetailActivity.class);
                                    intent.putExtra("limit", limit);
                                    intent.putExtra("process_id", process_id);
                                    intent.putExtra("state", delay_state);
                                    startActivity(intent);
                                    finish();
                                }
                            }).show();
                }
            }

            @Override
            public void onFailure(Call<FinishPrepareMaBean> call, Throwable t) {
                tvFinishOrder.setEnabled(true);
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(PhotoAreaActivity.this, t.toString());
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
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
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
}
