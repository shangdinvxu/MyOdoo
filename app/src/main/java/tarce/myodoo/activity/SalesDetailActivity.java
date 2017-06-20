package tarce.myodoo.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.io.IOException;
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
import tarce.model.GetSaleResponse;
import tarce.model.inventory.DoUnreservBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.SalesDetailAdapter;
import tarce.myodoo.uiutil.DialogIsSave;
import tarce.myodoo.uiutil.FullyLinearLayoutManager;
import tarce.myodoo.uiutil.ImageUtil;
import tarce.myodoo.utils.StringUtils;
import tarce.support.AlertAialogUtils;
import tarce.support.AvatarHelper;
import tarce.support.BitmapUtils;
import tarce.support.MyLog;
import tarce.support.TimeUtils;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;
import tarce.support.ViewUtils;

/**
 * Created by Daniel.Xu on 2017/4/21.
 */

public class SalesDetailActivity extends ToolBarActivity {
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
    @InjectView(R.id.print_tv)
    TextView C;
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
        salesOut.setText(StringUtils.switchString(bundle1.getDelivery_rule()));
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
        //     cameraImageview.setVisibility(View.GONE);
        isShowCamera = true;
        framelayout.setVisibility(View.GONE);
        ViewUtils.ViewRotate(topImageview, 0);
    }

    private void showCamera() {
        isShowCamera = false;
        initFragment();
        framelayout.setVisibility(View.VISIBLE);
        //    cameraImageview.setVisibility(View.VISIBLE);
        ViewUtils.ViewRotate(topImageview, 180);
    }


    private void initListener() {
        salesDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids obj =
                        (GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids) adapter.getData().get(position);
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
                        if (response.body().getResult().getRes_code() == 1) {
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
     *//*
    public void showLinThreeCang() {
        if (!UserManager.getSingleton().getGrops().contains("group_charge_warehouse")) {
            linearThree.setVisibility(View.GONE);
        }
    }*/
    private void refreshButtom(String s) {
        switch (s) {
            case "assigned":
                buttomButton1.setText("开始备货");
                buttomButton1.setVisibility(View.VISIBLE);
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "确定开始备货？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //开始备货
                                        refreshButtom("备货完成");
                                        initListener();
                                       // showCamera();
                                    }
                                }).show();
                    }
                });
                buttomButton2.setVisibility(View.GONE);
                buttomButton3.setVisibility(View.GONE);
                buttomButton4.setVisibility(View.GONE);
                break;
            case "confirmed":
                buttomButton1.setVisibility(View.GONE);
                buttomButton2.setVisibility(View.GONE);
                buttomButton3.setVisibility(View.GONE);
                buttomButton4.setVisibility(View.GONE);
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
                buttomButton2.setVisibility(View.GONE);
                buttomButton3.setVisibility(View.GONE);
                buttomButton4.setVisibility(View.GONE);
                break;
            case "partially_available":
                buttomButton1.setVisibility(View.GONE);
                buttomButton2.setVisibility(View.GONE);
                buttomButton3.setVisibility(View.GONE);
                buttomButton4.setVisibility(View.GONE);
                break;
            case "备货完成":
                buttomButton1.setVisibility(View.VISIBLE);
                buttomButton1.setText(s);
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "是否确定备货完成？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> data = salesDetailAdapter.getData();
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
                                            public void onResponse(Call<GetSaleResponse> call, Response<GetSaleResponse> response) {
                                                dismissDefultProgressDialog();
                                                if (response.body() == null)return;
                                                if (response.body().getError() != null){
                                                    ToastUtils.showCommonToast(SalesDetailActivity.this, response.body().getError().getMessage());
                                                    return;
                                                }
                                                if (response.body().getResult().getRes_code() == 1) {
                                                    bundle1 = response.body().getResult().getRes_data();
                                                    refreshView(bundle1);
                                                    refreshButtom("补拍物流信息");
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
                buttomButton2.setVisibility(View.GONE);
                buttomButton3.setVisibility(View.GONE);
                buttomButton4.setVisibility(View.GONE);
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
                buttomButton2.setVisibility(View.GONE);
                buttomButton3.setVisibility(View.GONE);
                buttomButton4.setVisibility(View.GONE);
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
                    objectObjectHashMap.put("pack_operation_product_ids", bundle1.getPack_operation_product_ids());
                    objectObjectHashMap.put("qc_note", bundle1.getQc_note());
                    objectObjectHashMap.put("qc_img", BitmapUtils.bitmapToBase64(ImageUtil.decodeFile(selectedImagePath)));
                    Call<GetSaleResponse> getSaleResponseCall = inventoryApi.changeStockPicking(objectObjectHashMap);
                    showDefultProgressDialog();
                    getSaleResponseCall.enqueue(new Callback<GetSaleResponse>() {
                        @Override
                        public void onResponse(Call<GetSaleResponse> call, Response<GetSaleResponse> response) {
                            dismissDefultProgressDialog();
                            if (response.body() == null)return;
                            if (response.body().getResult().getRes_code() == 1){
                                ToastUtils.showCommonToast(SalesDetailActivity.this, "物流信息上传成功");
                                finish();
                            }
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
            new DialogIsSave(SalesDetailActivity.this)
                    .setSave(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).setNotSave(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).setCancel().show();
            cacelReserver();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 点击返回按钮时  取消保留
     */
    private void cacelReserver() {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("picking_id", bundle1.getPicking_id());
        Call<DoUnreservBean> getSaleResponseCall = inventoryApi.doUnreserveAction(hashMap);
        getSaleResponseCall.enqueue(new MyCallback<DoUnreservBean>() {
            @Override
            public void onResponse(Call<DoUnreservBean> call, Response<DoUnreservBean> response) {
                if (response.body() == null) return;
                if (response.body().getResult().getRes_code() == 1) {
                    finish();
                }
            }
        });
    }
}
