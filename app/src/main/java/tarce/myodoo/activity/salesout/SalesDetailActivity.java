package tarce.myodoo.activity.salesout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newland.me.ConnUtils;
import com.newland.me.DeviceManager;
import com.newland.mtype.ConnectionCloseEvent;
import com.newland.mtype.ModuleType;
import com.newland.mtype.event.DeviceEventListener;
import com.newland.mtype.module.common.printer.FontSettingScope;
import com.newland.mtype.module.common.printer.FontType;
import com.newland.mtype.module.common.printer.LiteralType;
import com.newland.mtype.module.common.printer.Printer;
import com.newland.mtype.module.common.printer.WordStockType;
import com.newland.mtypex.nseries.NSConnV100ConnParams;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

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
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.FindProductByConditionResponse;
import tarce.model.GetSaleResponse;
import tarce.model.inventory.DoUnreservBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.adapter.SalesDetailAdapter;
import tarce.myodoo.uiutil.DialogIsSave;
import tarce.myodoo.uiutil.FullyLinearLayoutManager;
import tarce.myodoo.uiutil.ImageUtil;
import tarce.myodoo.utils.DateTool;
import tarce.myodoo.utils.StringUtils;
import tarce.myodoo.utils.UserManager;
import tarce.support.AlertAialogUtils;
import tarce.support.BitmapUtils;
import tarce.support.MyLog;
import tarce.support.TimeUtils;
import tarce.support.ToastUtils;
import tarce.support.ViewUtils;

/**
 * Created by Daniel.Xu on 2017/4/21.
 * 销售出货详情页
 */

public class SalesDetailActivity extends BaseActivity {
    private static final String K21_DRIVER_NAME = "com.newland.me.K21Driver";
    private static String TAG = SalesDetailActivity.class.getSimpleName();
    @InjectView(R.id.partner)
    TextView partner;
    @InjectView(R.id.time)
    TextView time;
    @InjectView(R.id.states)
    TextView states;
    @InjectView(R.id.origin_documents)
    TextView originDocuments;
    @InjectView(R.id.sales_out)
    TextView salesOut;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.framelayout)
    FrameLayout framelayout;
    @InjectView(R.id.camera_buttom_linearlayout)
    LinearLayout cameraButtomLinearlayout;
    @InjectView(R.id.top_imageview)
    ImageView topImageview;
    /*@InjectView(R.id.camera_Imageview)
    ImageView cameraImageview;*/
    @InjectView(R.id.remarks)
    EditText remarks;
    @InjectView(R.id.buttom_button1)
    Button buttomButton1;
    @InjectView(R.id.buttom_button2)
    Button buttomButton2;
    @InjectView(R.id.buttom_button3)
    Button buttomButton3;
    @InjectView(R.id.buttom_button4)
    Button buttomButton4;
    @InjectView(R.id.linear_bottom)
    LinearLayout linearBottom;
    @InjectView(R.id.tv_print)
    TextView tvPrint;
    private SalesDetailAdapter salesDetailAdapter;
    private boolean isShowCamera = true;
    private InventoryApi inventoryApi;
    private GetSaleResponse.TResult.TRes_data bundle1;
    private String imgPath;//图片拍照照片的本地路径
    private String imgName;//后缀名
    private String selectedImagePath = "";
    /**
     * 回调常量之：拍照
     */
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 1;//拍照
    private String saleName;
    private String model_state = "";
    private DeviceManager deviceManager;
    private Printer printer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_detial);
        ButterKnife.inject(this);
        topImageview.setFocusableInTouchMode(true);
        topImageview.requestFocus();
        inventoryApi = RetrofitClient.getInstance(SalesDetailActivity.this).create(InventoryApi.class);
        recyclerview.setLayoutManager(new FullyLinearLayoutManager(SalesDetailActivity.this));
        recyclerview.addItemDecoration(new DividerItemDecoration(SalesDetailActivity.this,
                DividerItemDecoration.VERTICAL));
        recyclerview.setNestedScrollingEnabled(false);
        initIntent();
    }

    private void initIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("intent");
        bundle1 = (GetSaleResponse.TResult.TRes_data) bundle.getSerializable("bundle");
        saleName = bundle1.getName();
        setTitle(saleName);
        refreshView(bundle1);
    }

    private void refreshView(GetSaleResponse.TResult.TRes_data bundle1) {
        partner.setText(bundle1.getParnter_id());
        time.setText(TimeUtils.utc2Local(bundle1.getMin_date()));
        states.setText(StringUtils.switchString(bundle1.getState()));
        originDocuments.setText(bundle1.getOrigin());
        if (bundle1.getDelivery_rule() != null) {
            salesOut.setText(StringUtils.switchString((String) bundle1.getDelivery_rule()));
        } else {
            salesOut.setText("");
        }
        remarks.setText(String.valueOf(bundle1.getSale_note()));
        List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> pack_operation_product_ids = bundle1.getPack_operation_product_ids();
        salesDetailAdapter = new SalesDetailAdapter(R.layout.salesout_detail_adapter_item, pack_operation_product_ids);
        recyclerview.setAdapter(salesDetailAdapter);
        refreshButtom(bundle1.getState());
    }


    @OnClick(R.id.top_imageview)
    void setCameraState(View view) {
        if (isShowCamera) {
            showCamera();
        } else {
            dismissCamera();
        }
    }

    private void dismissCamera() {
        isShowCamera = true;
        framelayout.setVisibility(View.GONE);
        ViewUtils.ViewRotate(topImageview, 0);
    }

    private void showCamera() {
        isShowCamera = false;
        initFragment();
        framelayout.setVisibility(View.VISIBLE);
        ViewUtils.ViewRotate(topImageview, 180);
    }


    private void initListener() {
        salesDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position>adapter.getData().size()-1)return;
                final GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids obj =
                        (GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids) adapter.getData().get(position);
                if (obj.getPack_id() == -1) return;
                final EditText editText = new EditText(SalesDetailActivity.this);
                final Integer qty_available = obj.getProduct_id().getQty_available();
                Integer product_qty = obj.getProduct_qty();
                Integer qty = qty_available >= product_qty ? product_qty : qty_available;
                editText.setText(qty + "");
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setSelection(editText.getText().length());
                AlertDialog.Builder dialog = AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "输入" + obj.getProduct_id().getName() + "完成数量");
                dialog.setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Integer.parseInt(editText.getText().toString()) > qty_available) {
                                    Toast.makeText(SalesDetailActivity.this, "库存不足", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                obj.setQty_done(Integer.parseInt(editText.getText().toString()));
                                salesDetailAdapter.notifyDataSetChanged();
                            }
                        }).show();
            }
        });
    }


    /**
     * 加载扫描的fragment
     */
    private void initFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        final CaptureFragment captureFragment = new CaptureFragment();
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(new CodeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("default_code", result);
                HashMap<Object, Object> objectObjectHashMap1 = new HashMap<>();
                objectObjectHashMap1.put("condition", objectObjectHashMap);
                Call<FindProductByConditionResponse> productByCondition = inventoryApi.findProductByCondition(objectObjectHashMap1);
                showDefultProgressDialog();
                productByCondition.enqueue(new Callback<FindProductByConditionResponse>() {
                    @Override
                    public void onResponse(Call<FindProductByConditionResponse> call, Response<FindProductByConditionResponse> response) {
                        dismissDefultProgressDialog();
                        if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                            String name = response.body().getResult().getRes_data().getProduct().getProduct_name();
                            List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> data = salesDetailAdapter.getData();
                            if (data == null) return;
                            boolean isInit = false;
                            int index = -1;
                            for (int i = 0; i < data.size(); i++) {
                                if (data.get(i).getProduct_id().getName().equals(name)) {
                                    isInit = true;
                                    index = i;
                                }
                            }
                            if (isInit) {
                                final GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids obj = data.get(index);
                                final EditText editText = new EditText(SalesDetailActivity.this);
                                final Integer qty_available = obj.getProduct_id().getQty_available();
                                Integer product_qty = obj.getProduct_qty();
                                Integer qty = qty_available >= product_qty ? product_qty : qty_available;
                                editText.setText(qty + "");
                                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                                AlertDialog.Builder dialog = AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "输入" + obj.getProduct_id().getName() + "完成数量");
                                dialog.setView(editText)
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Integer.parseInt(editText.getText().toString()) > qty_available) {
                                                    Toast.makeText(SalesDetailActivity.this, "库存不足", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                                obj.setQty_done(Integer.parseInt(editText.getText().toString()));
                                                salesDetailAdapter.notifyDataSetChanged();
                                            }
                                        }).show();
                            } else {
                                ToastUtils.showCommonToast(SalesDetailActivity.this,
                                        "该产品不在单据中");
                            }


                        } else {
                            ToastUtils.showCommonToast(SalesDetailActivity.this,
                                    response.body().getResult().getRes_data().getError());
                        }
                    }

                    @Override
                    public void onFailure(Call<FindProductByConditionResponse> call, Throwable t) {
                        dismissDefultProgressDialog();

                    }
                });

            }

            @Override
            public void onAnalyzeFailed() {

            }
        });
        fragmentTransaction.replace(R.id.framelayout, captureFragment);
        fragmentTransaction.commit();
    }

    /**
     * 是否显示底部(仓库)
     */
    public void showLinThreeCang() {
        if (!UserManager.getSingleton().getGrops().contains("group_charge_warehouse")) {
            linearBottom.setVisibility(View.GONE);
        }
    }

    /**
     * 打印
     * */
    @OnClick(R.id.tv_print)
    void printTv(View view){
        AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "确定打印？(请尽量避免重复打印)")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        printTra();
                    }
                }).show();
    }

    private void refreshButtom(String s){
        switch (s) {
            case "confirmed":
            case "partially_available":
            case "assigned":
                showLinThreeCang();
                if (bundle1.getComplete_rate() == 0){
                    linearBottom.setVisibility(View.GONE);
                    return;
                }
                buttomButton1.setText("开始备货");
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "确定开始备货？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //开始备货
                                        refreshButtom("备货完成");
                                        model_state = "change";
                                        initListener();
                                    }
                                }).show();
                    }
                });
                break;
            case "done":
                buttomButton1.setText("补拍物流信息");
                buttomButton1.setVisibility(View.VISIBLE);
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "是否补拍物流信息？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //进入拍照
                                        imgName = "photo.jpg";
                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                                        startActivityForResult(intent, REQUEST_CODE_IMAGE_CAPTURE);
                                    }
                                }).show();
                    }
                });
                break;
            case "备货完成":
                buttomButton1.setVisibility(View.VISIBLE);
                buttomButton1.setText(s);
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> data = salesDetailAdapter.getData();
                        List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> subdata = new ArrayList<>();
                        for (int i = 0; i < data.size(); i++) {
                            if (data.get(i).getPack_id() == -1){
                                subdata.add(data.get(i));
                            }
                        }
                        data.removeAll(subdata);
                        int sum = 0;
                        for (int i = 0; i < data.size(); i++) {
                            sum = sum + data.get(i).getQty_done();
                        }
                        if (sum == 0) {
                            ToastUtils.showCommonToast(SalesDetailActivity.this, "请检查完成数量");
                            return;
                        }
                        AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "是否确定备货完成？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                                        bundle1.setPack_operation_product_ids(data);
                                        boolean threeOrFive = false;
                                        for (int i = 0; i < data.size(); i++) {
                                            if (data.get(i).getQty_done() > 0) {
                                                threeOrFive = false;
                                            } else {
                                                threeOrFive = true;
                                                break;
                                            }
                                        }
                                        if (threeOrFive) {
                                            objectObjectHashMap.put("state", "process");
                                            objectObjectHashMap.put("picking_id", bundle1.getPicking_id());
                                            objectObjectHashMap.put("pack_operation_product_ids", data);
                                        } else {
                                            objectObjectHashMap.put("state", "process");
                                            objectObjectHashMap.put("picking_id", bundle1.getPicking_id());
                                            objectObjectHashMap.put("pack_operation_product_ids", data);
                                            objectObjectHashMap.put("qc_img", bundle1.getQc_img());
                                            objectObjectHashMap.put("qc_note", "yes");
                                        }
                                        Call<GetSaleResponse> getSaleResponseCall = inventoryApi.changeStockPicking(objectObjectHashMap);
                                        showDefultProgressDialog();
                                        getSaleResponseCall.enqueue(new Callback<GetSaleResponse>() {
                                            @Override
                                            public void onResponse(Call<GetSaleResponse> call, final Response<GetSaleResponse> response) {
                                                dismissDefultProgressDialog();
                                                if (response.body() == null || response.body().getResult() == null) return;
                                                if (response.body().getError() != null) {
                                                    ToastUtils.showCommonToast(SalesDetailActivity.this, response.body().getError().getMessage());
                                                    return;
                                                }
                                                if (response.body().getResult().getRes_code() == 1) {
                                                    AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "备货成功,是否拍摄物流信息")
                                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    bundle1 = response.body().getResult().getRes_data();
                                                                    refreshView(bundle1);
                                                                    refreshButtom("补拍物流信息");
                                                                    //进入拍照
                                                                    imgName = "photo.jpg";
                                                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                                                                    startActivityForResult(intent, REQUEST_CODE_IMAGE_CAPTURE);
                                                                }
                                                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Toast.makeText(SalesDetailActivity.this, "由于未执行打印操作，将自动打印此单据，请等待", Toast.LENGTH_LONG).show();
                                                            for (int i = 0; i < 2; i++) {
                                                                printTra();
                                                            }
                                                            finish();
                                                        }
                                                    }).show();
                                                } else if (response.body().getResult().getRes_code() == -1) {
                                                    ToastUtils.showCommonToast(SalesDetailActivity.this, response.body().getResult().getRes_data().getError() + "");
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<GetSaleResponse> call, Throwable t) {
                                                dismissDefultProgressDialog();
                                                MyLog.e("", t.toString());
                                            }
                                        });
                                    }
                                }).show();
                    }
                });
                break;
            case "补拍物流信息":
                buttomButton1.setText("补拍物流信息");
                buttomButton1.setVisibility(View.VISIBLE);
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder dialog = AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "是否要补拍物流信息？");
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //进入拍照
                                imgName = "photo.jpg";
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                                startActivityForResult(intent, REQUEST_CODE_IMAGE_CAPTURE);
                            }
                        }).show();

                    }
                });
                break;
        }
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
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == REQUEST_CODE_IMAGE_CAPTURE) {
                selectedImagePath = getImagePath();
                if (!StringUtils.isNullOrEmpty(selectedImagePath)) {
                    HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                    objectObjectHashMap.put("state", "upload_img");
                    objectObjectHashMap.put("picking_id", bundle1.getPicking_id());
                    List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> ids = bundle1.getPack_operation_product_ids();
                    List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> sub_ids = new ArrayList<>();
                    for (int i = 0; i < ids.size(); i++) {
                        if (ids.get(i).getPack_id() == -1){
                            sub_ids.add(ids.get(i));
                        }
                    }
                    ids.removeAll(sub_ids);
                    objectObjectHashMap.put("pack_operation_product_ids", ids);
                    objectObjectHashMap.put("qc_note", bundle1.getQc_note());
                    objectObjectHashMap.put("qc_img", BitmapUtils.bitmapToBase64(ImageUtil.decodeFile(selectedImagePath)));
                    Call<GetSaleResponse> getSaleResponseCall = inventoryApi.changeStockPicking(objectObjectHashMap);
                    showDefultProgressDialog();
                    getSaleResponseCall.enqueue(new Callback<GetSaleResponse>() {
                        @Override
                        public void onResponse(Call<GetSaleResponse> call, Response<GetSaleResponse> response) {
                            dismissDefultProgressDialog();
                            if (response.body() == null || response.body().getResult() == null) return;
                            if (response.body().getResult().getRes_code() == 1) {
                                Toast.makeText(SalesDetailActivity.this, "物流信息上传成功!\n由于未操作打印，此次将自动打印笨单据，请等待！", Toast.LENGTH_LONG).show();
                                for (int i = 0; i < 2; i++) {
                                    printTra();
                                }
                            } else {
                                ToastUtils.showCommonToast(SalesDetailActivity.this, "物流信息上传成功");
                            }
                            finish();
                        }

                        @Override
                        public void onFailure(Call<GetSaleResponse> call, Throwable t) {
                            dismissDefultProgressDialog();

                        }
                    });
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == android.R.id.home) {
            boolean isSave = false;
            for (int i = 0; i < bundle1.getPack_operation_product_ids().size(); i++) {
                if (bundle1.getPack_operation_product_ids().get(i).getQty_done() > 0) {
                    isSave = true;
                    break;
                } else {
                    isSave = false;
                }
            }
            /**
             * 情况一：点进来是开始备货状态，什么都没操作，点击返回按钮默认取消保留
             * 情况二：点击了开始备货，什么也没操作，全是0，点击返回默认取消保留
             * 情况三：点击了开始备货，并且至少备了一个，则进行选择是否保留
             * 情况一和二合并
             * */

            if ("change".equals(model_state) && isSave && (bundle1.getState().equals("assigned") || bundle1.getState().equals("partially_available"))) {
                new DialogIsSave(SalesDetailActivity.this)
                        .setSave(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                saveState();
                            }
                        }).setNotSave(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cacelReserver();
                    }
                }).setCancel().show();
            } else {
                cacelReserver();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 返回按钮
     * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            boolean isSave = false;
            for (int i = 0; i < bundle1.getPack_operation_product_ids().size(); i++) {
                if (bundle1.getPack_operation_product_ids().get(i).getQty_done() > 0) {
                    isSave = true;
                    break;
                } else {
                    isSave = false;
                }
            }
            /**
             * 情况一：点进来是开始备货状态，什么都没操作，点击返回按钮默认取消保留
             * 情况二：点击了开始备货，什么也没操作，全是0，点击返回默认取消保留
             * 情况三：点击了开始备货，并且至少备了一个，则进行选择是否保留
             * 情况一和二合并
             * */

            if ("change".equals(model_state) && isSave && (bundle1.getState().equals("assigned") || bundle1.getState().equals("partially_available"))) {
                new DialogIsSave(SalesDetailActivity.this)
                        .setSave(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                saveState();
                            }
                        }).setNotSave(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cacelReserver();
                    }
                }).setCancel().show();
            } else {
                cacelReserver();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 点击返回按钮时  取消保留
     */
    private void cacelReserver() {
        showDefultProgressDialog();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("picking_id", bundle1.getPicking_id());
        Call<DoUnreservBean> getSaleResponseCall = inventoryApi.doUnreserveAction(hashMap);
        getSaleResponseCall.enqueue(new MyCallback<DoUnreservBean>() {
            @Override
            public void onResponse(Call<DoUnreservBean> call, Response<DoUnreservBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getResult().getRes_code() == 1) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<DoUnreservBean> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(SalesDetailActivity.this, t.toString());
            }
        });
    }

    /**
     * 保存
     */
    private void saveState() {
        showDefultProgressDialog();
        HashMap<Object, Object> hashMap = new HashMap<>();
        List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> ids = bundle1.getPack_operation_product_ids();
        List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> sub_ids = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            if (ids.get(i).getPack_id() == -1){
                sub_ids.add(ids.get(i));
            }
        }
        ids.removeAll(sub_ids);
        hashMap.put("pack_operation_product_ids", ids);
        hashMap.put("picking_id", bundle1.getPicking_id());
        hashMap.put("state", "prepare");
        Call<GetSaleResponse> getSaleResponseCall = inventoryApi.changeStockPicking(hashMap);
        getSaleResponseCall.enqueue(new MyCallback<GetSaleResponse>() {
            @Override
            public void onResponse(Call<GetSaleResponse> call, Response<GetSaleResponse> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError() != null) {
                    ToastUtils.showCommonToast(SalesDetailActivity.this, response.body().getError().getMessage());
                    return;
                }
                if (response.body().getResult().getRes_code() == 1) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<GetSaleResponse> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(SalesDetailActivity.this, t.toString());
            }
        });
    }

    /**
     * 打印操作
     */
    private void printTra() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDefultProgressDialog();
            }
        });
        initDevice();
        printer = (Printer) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_PRINTER);
        printer.init();
        printer.setLineSpace(2);
        printer.print("销售订单备注: "+bundle1.getSale_note()+"\n\n"+"\n出货单号：" + bundle1.getName() + "\n" + "源单据: " + bundle1.getOrigin() + "\n" + "合作伙伴： " +
                bundle1.getParnter_id() + "\n" +
                "收货人联系电话: "+bundle1.getPhone()+"\n--------------------------\n", 30, TimeUnit.SECONDS);
        printer.print("产品名称         完成数量", 30, TimeUnit.SECONDS);
        for (int i = 0; i < bundle1.getPack_operation_product_ids().size(); i++) {
            if (bundle1.getPack_operation_product_ids().get(i).getPack_id() != -1){
                printer.print(bundle1.getPack_operation_product_ids().get(i).getProduct_id().getName() + "  ---  " +
                        bundle1.getPack_operation_product_ids().get(i).getQty_done()
                        + "\n", 30, TimeUnit.SECONDS);
            }
        }
        printer.print("\n", 30, TimeUnit.SECONDS);
       // Bitmap mBitmap = CodeUtils.createImage(bundle1.getName()+"&stock.picking&"+bundle1.getPicking_id(), 300, 300, null);
        Bitmap mBitmap = CodeUtils.createImage(bundle1.getName(), 200, 200, null);
        printer.print(0, mBitmap, 30, TimeUnit.SECONDS);
        printer.print("\n"+"打印时间："+ DateTool.getDateTime(), 30, TimeUnit.SECONDS);
        printer.print("\n\n\n\n\n\n", 30, TimeUnit.SECONDS);
        dismissDefultProgressDialog();
    }

    /**
     * 连接设备打印机
     */
    private void initDevice() {
        deviceManager = ConnUtils.getDeviceManager();
        try {
            deviceManager.init(SalesDetailActivity.this, K21_DRIVER_NAME, new NSConnV100ConnParams(), new DeviceEventListener<ConnectionCloseEvent>() {
                @Override
                public void onEvent(ConnectionCloseEvent connectionCloseEvent, Handler handler) {
                    if (connectionCloseEvent.isSuccess()) {
                        ToastUtils.showCommonToast(SalesDetailActivity.this, "设备被客户主动断开！");
                    }
                    if (connectionCloseEvent.isFailed()) {
                        ToastUtils.showCommonToast(SalesDetailActivity.this, "设备链接异常断开！");
                    }
                }

                @Override
                public Handler getUIHandler() {
                    return null;
                }
            });
            deviceManager.connect();
            MyLog.e("ProductingActivity", "连接成功");
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showCommonToast(SalesDetailActivity.this, "链接异常,请检查设备或重新连接.." + e);
        }
    }
}
