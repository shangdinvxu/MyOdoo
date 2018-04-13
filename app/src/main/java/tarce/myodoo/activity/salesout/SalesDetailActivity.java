package tarce.myodoo.activity.salesout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.DrmInitData;
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
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newland.mtype.ModuleType;
import com.newland.mtype.module.common.printer.Printer;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tarce.api.MyCallback;
import tarce.api.OKHttpFactory;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.FindProductByConditionResponse;
import tarce.model.GetSaleResponse;
import tarce.model.LoginResponse;
import tarce.model.TimeSheetBean;
import tarce.model.WorkTypeBean;
import tarce.model.inventory.CommonBean;
import tarce.model.inventory.DoUnreservBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.activity.product.AllEmployeesActivity;
import tarce.myodoo.adapter.SalesDetailAdapter;
import tarce.myodoo.uiutil.FullyLinearLayoutManager;
import tarce.myodoo.uiutil.ImageUtil;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.uiutil.WorkTimeDialog;
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
 * Created by zws on 2017/4/21.
 * 销售出货详情页
 */

public class SalesDetailActivity extends BaseActivity {
    private static String TAG = SalesDetailActivity.class.getSimpleName();
    private static final int SECOND_OPERATION = 0x10;
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
    @InjectView(R.id.tv_auto_num)
    TextView tvAutoNum;
    @InjectView(R.id.is_secondary)
    TextView isSecondary;
    @InjectView(R.id.is_secondary_message)
    TextView isSecondaryMessage;
    @InjectView(R.id.tv_fen_name)
    TextView tvFenName;
    @InjectView(R.id.tv_control_name)
    TextView tvControlName;
    @InjectView(R.id.work_channel)
    TextView workChannel;
    @InjectView(R.id.work_time)
    TextView workTime;
    @InjectView(R.id.secondary_linear)
    LinearLayout secondaryLinear;
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
    private Printer printer;
    private Retrofit retrofit;
    private TimeSheetBean.ResultBean.ResDataBean dataBean;
    private int sheet_id = 0;
    private LoginResponse userInfoBean;
    private boolean company_main;
    private int picking_id;
    private boolean isRefesh = false;
    private boolean isBeihuoOk = false;//标示是否备货完成

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
        Intent intent = getIntent();
        picking_id = intent.getIntExtra("picking_id", 0);
        initData();
    }

    private void initData() {
        showDefultProgressDialog();
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("picking_id", picking_id);
        Call<GetSaleResponse> stringCall = inventoryApi.checkIsCanUse(objectObjectHashMap);
        stringCall.enqueue(new MyCallback<GetSaleResponse>() {
            @Override
            public void onResponse(Call<GetSaleResponse> call, Response<GetSaleResponse> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError() != null) {
                    new TipDialog(SalesDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_data() == null) return;
                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                    GetSaleResponse.TResult result = response.body().getResult();
                    bundle1 = result.getRes_data();
                    saleName = bundle1.getName();
                    setTitle(saleName);
                    refreshView(bundle1);
                } else {
                    ToastUtils.showCommonToast(SalesDetailActivity.this, "加载失败，请稍后重试");
                }
            }

            @Override
            public void onFailure(Call<GetSaleResponse> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(SalesDetailActivity.this, t.toString());
                Log.e("zws", t.toString());
            }
        })           ;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRefesh){
            initData();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRefesh = true;
    }

    private void refreshView(GetSaleResponse.TResult.TRes_data bundle1) {
        if (bundle1.isSecondary_operation()) {//二次生产显示的信息
            isSecondary.setText("是");
            isSecondaryMessage.setVisibility(View.VISIBLE);
            secondaryLinear.setVisibility(View.VISIBLE);
            if (bundle1.getTimesheet_order_id().size() > 0) {
                tvControlName.setText(bundle1.getTimesheet_order_id().get(0).getTo_partner().getName());
                tvFenName.setText(bundle1.getTimesheet_order_id().get(0).getFrom_partner().getName());
                workChannel.setText(bundle1.getTimesheet_order_id().get(0).getWork_type_id().getName());
                workTime.setText(bundle1.getTimesheet_order_id().get(0).getHour_spent() + "");
            } else {
                tvControlName.setText("暂无");
                tvFenName.setText("暂无");
                workChannel.setText("暂无");
                workTime.setText("暂无");
            }

        } else {
            isSecondary.setText("否");
            isSecondaryMessage.setVisibility(View.GONE);
            secondaryLinear.setVisibility(View.GONE);
        }

        partner.setText(bundle1.getParnter_id());
        if (!StringUtils.isNullOrEmpty(bundle1.getMin_date() + "")) {
            time.setText(TimeUtils.utc2Local(bundle1.getMin_date() + ""));
        } else {
            time.setText("false");
        }
        states.setText(StringUtils.switchString(bundle1.getState()));
        originDocuments.setText(bundle1.getOrigin());
        if (bundle1.getDelivery_rule() != null) {
            salesOut.setText(StringUtils.switchString((String) bundle1.getDelivery_rule()));
        } else {
            salesOut.setText("");
        }
        remarks.setText(String.valueOf(bundle1.getSale_note()));
        List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> pack_operation_product_ids = bundle1.getPack_operation_product_ids();
        salesDetailAdapter = new SalesDetailAdapter(R.layout.salesout_detail_adapter_item, pack_operation_product_ids, SalesDetailActivity.this);
        /*if (bundle1.getState().equals("done") || bundle1.getState().equals("cancel")) {
            salesDetailAdapter.setState("two");
        } else {
            salesDetailAdapter.setState("");
        }*/
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

    /**
     * 批量自动填写
     */
    @OnClick(R.id.tv_auto_num)
    void autoWrite(View view) {
        for (int i = 0; i < bundle1.getPack_operation_product_ids().size(); i++) {
            GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids productIds = bundle1.getPack_operation_product_ids().get(i);
            double keyong = productIds.getProduct_id().getQty_available() - productIds.getReserved_qty();
            if (productIds.getPack_id() != -1) {
                double qty = keyong >= productIds.getOrigin_qty() ? productIds.getOrigin_qty() : keyong;
                if (qty < 0) {
                    productIds.setQty_done(0);
                } else {
                    productIds.setQty_done(qty);
                }
//                salesDetailAdapter.notifyDataSetChanged();
            }
        }

        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("picking_id", bundle1.getPicking_id());
        objectObjectHashMap.put("pack_operation_product_ids", bundle1.getPack_operation_product_ids());
        Call<GetSaleResponse> getSaleResponseCall = inventoryApi.changeStockPicking(objectObjectHashMap);
        showDefultProgressDialog();
        getSaleResponseCall.enqueue(new Callback<GetSaleResponse>() {
            @Override
            public void onResponse(Call<GetSaleResponse> call, final Response<GetSaleResponse> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)
                    return;
                if (response.body().getError() != null) {
                    new TipDialog(SalesDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1) {
                    salesDetailAdapter.notifyDataSetChanged();
                } else if (response.body().getResult().getRes_code() == -1) {
                    ToastUtils.showCommonToast(SalesDetailActivity.this, response.body().getResult().getRes_data().getError() + "");
                }
            }

            @Override
            public void onFailure(Call<GetSaleResponse> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(SalesDetailActivity.this, t.toString());
                MyLog.e("", t.toString());
            }
        });
    }

    private void initListener() {
        salesDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position > adapter.getData().size() - 1) return;
                final GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids obj =
                        (GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids) adapter.getData().get(position);
                if (obj.getPack_id() == -1) return;
                final EditText editText = new EditText(SalesDetailActivity.this);
                // TODO: 2018/3/16 根据主公司子公司
                userInfoBean = UserManager.getSingleton().getUserInfoBean();
                if (userInfoBean != null) {
                    company_main = userInfoBean.getResult().getRes_data().is_company_main();
                }
                final double qty_available = obj.getProduct_id().getQty_available() - obj.getReserved_qty();
                double product_qty = obj.getOrigin_qty();
                final double qty = qty_available >= product_qty ? product_qty : qty_available;
                editText.setText(qty + "");
                if (qty < 0) {
                    editText.setText("0");
                }
                //editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setSelection(editText.getText().length());
                AlertDialog.Builder dialog = AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "输入" + obj.getProduct_id().getName() + "完成数量");
                dialog.setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                double keyong;
//                                if (bundle1.getState().equals("done") || bundle1.getState().equals("cancel")) {
//                                    keyong = obj.getProduct_id().getQty_available();
//                                } else {
//                                    keyong = obj.getProduct_id().getQty_available()-obj.getQty_done();
//                                }
//                                if (Double.parseDouble(editText.getText().toString()) > keyong)     {
//                                    Toast.makeText(SalesDetailActivity.this, "库存不足", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
                                if (company_main) {//主公司
                                    if (Double.parseDouble(editText.getText().toString()) > qty) {
                                        Toast.makeText(SalesDetailActivity.this, "库存不足或超过初始需求", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                if (!company_main) {//子公司
                                    // TODO: 2018/3/14 小于库存就好了
                                    if (Double.parseDouble(editText.getText().toString()) > qty_available) {
                                        Toast.makeText(SalesDetailActivity.this, "库存不足", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                // TODO: 2018/4/11 点一条出一条
                                obj.setQty_done(Double.parseDouble(editText.getText().toString()));
                                List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> list
                                        = new ArrayList<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids>();
                                list.add(obj);
                                HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                                objectObjectHashMap.put("picking_id", bundle1.getPicking_id());
                                objectObjectHashMap.put("pack_operation_product_ids", list);
                                Call<GetSaleResponse> getSaleResponseCall = inventoryApi.changeStockPicking(objectObjectHashMap);
                                showDefultProgressDialog();
                                getSaleResponseCall.enqueue(new Callback<GetSaleResponse>() {
                                    @Override
                                    public void onResponse(Call<GetSaleResponse> call, final Response<GetSaleResponse> response) {
                                        dismissDefultProgressDialog();
                                        if (response.body() == null)
                                            return;
                                        if (response.body().getError() != null) {
                                            new TipDialog(SalesDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                                    .show();
                                            return;
                                        }
                                        if (response.body().getResult().getRes_code() == 1) {
                                            salesDetailAdapter.notifyDataSetChanged();
                                        } else if (response.body().getResult().getRes_code() == -1) {
                                            ToastUtils.showCommonToast(SalesDetailActivity.this, response.body().getResult().getRes_data().getError() + "");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<GetSaleResponse> call, Throwable t) {
                                        dismissDefultProgressDialog();
                                        ToastUtils.showCommonToast(SalesDetailActivity.this, t.toString());
                                        MyLog.e("", t.toString());
                                    }
                                });

                                // obj.setQty_done(Double.parseDouble(editText.getText().toString()));
                                // salesDetailAdapter.notifyDataSetChanged();
                            }
                        }).show();
            }
        });
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
     */
    @OnClick(R.id.tv_print)
    void printTv(View view) {
        AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "此次将打印默认数量")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (bundle1.getState().equals("done")) {
                            printTra("留底单");
                        } else {
                            printDefaultNum();
                        }
                    }
                }).show();
    }


    /**
     * 打印默认数量
     */
    private void printDefaultNum() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDefultProgressDialog();
            }
        });
        initDevice();
        printer = (Printer) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_PRINTER);
        printer.init();
        printer.setLineSpace(1);
        printer.print("              留底\n      --------------------\n" + "销售订单备注: " + bundle1.getSale_note() + "\n\n\n" + "出货单号：" + bundle1.getName() + "\n" + "源单据: " + bundle1.getOrigin() + "\n" + "合作伙伴： " +
                bundle1.getParnter_id() + "\n" +
                "收货人联系电话: " + bundle1.getPhone() + "\n--------------------------\n", 30, TimeUnit.SECONDS);
        printer.print("产品名称       完成数量", 30, TimeUnit.SECONDS);

        for (int i = 0; i < bundle1.getPack_operation_product_ids().size(); i++) {
            GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids productIds = bundle1.getPack_operation_product_ids().get(i);
            if (productIds.getPack_id() != -1) {
                double qty = productIds.getProduct_id().getQty_available() >= productIds.getProduct_qty() ? productIds.getProduct_qty() : productIds.getProduct_id().getQty_available();
                String name = productIds.getProduct_id().getName();
                if (name.length() > 17) {
                    printer.print(name.substring(0, 15) + "       " +
                            qty
                            + "\n" + name.substring(15, name.length()) + "\n\n", 30, TimeUnit.SECONDS);
                } else {
                    printer.print(name + "      " +
                            qty
                            + "\n\n", 30, TimeUnit.SECONDS);
                }
            }
        }
        printer.print("打印时间：" + DateTool.getDateTime() + "\n\n\n\n\n", 30, TimeUnit.SECONDS);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissDefultProgressDialog();
            }
        });
    }

    private void refreshButtom(String s) {
        switch (s) {
            case "waiting_out":
                showLinThreeCang();
                buttomButton1.setText("继续备货");
                buttomButton2.setText("出库完成");
                buttomButton2.setVisibility(View.VISIBLE);
                buttomButton1.setBackgroundResource(R.color.height_yellow);
                buttomButton2.setBackgroundResource(R.color.color_5693f8);
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                        objectObjectHashMap.put("state", "cancel_stock");
                        objectObjectHashMap.put("picking_id", bundle1.getPicking_id());
                        objectObjectHashMap.put("qc_img", bundle1.getQc_img());
                        Call<GetSaleResponse> getSaleResponseCall = inventoryApi.changeStockPicking(objectObjectHashMap);
                        showDefultProgressDialog();
                        getSaleResponseCall.enqueue(new Callback<GetSaleResponse>() {
                            @Override
                            public void onResponse(Call<GetSaleResponse> call, final Response<GetSaleResponse> response) {
                                dismissDefultProgressDialog();
                                if (response.body() == null)
                                    return;
                                if (response.body().getError() != null) {
                                    new TipDialog(SalesDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                            .show();
                                    return;
                                }
                                if (response.body().getResult().getRes_code() == 1) {
                                    isBeihuoOk = true;
                                    Toast.makeText(SalesDetailActivity.this, "继续备货", Toast.LENGTH_LONG).show();
                                    bundle1 = response.body().getResult().getRes_data();
                                    refreshView(bundle1);
                                    initListener();
                                } else if (response.body().getResult().getRes_code() == -1) {
                                    ToastUtils.showCommonToast(SalesDetailActivity.this, response.body().getResult().getRes_data().getError() + "");
                                }
                            }

                            @Override
                            public void onFailure(Call<GetSaleResponse> call, Throwable t) {
                                dismissDefultProgressDialog();
                                ToastUtils.showCommonToast(SalesDetailActivity.this, t.toString());
                                MyLog.e("", t.toString());
                            }
                        });
                    }
                });
                buttomButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> data = salesDetailAdapter.getData();
                        List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> subdata = new ArrayList<>();
                        for (int i = 0; i < data.size(); i++) {
                            if (data.get(i).getPack_id() == -1) {
                                subdata.add(data.get(i));
                            }
                        }
                        data.removeAll(subdata);
                        double sum = 0;
                        for (int i = 0; i < data.size(); i++) {
                            sum = sum + data.get(i).getQty_done();
                        }
                        if (sum == 0) {
                            ToastUtils.showCommonToast(SalesDetailActivity.this, "请检查完成数量");
                            return;
                        }
                        AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "是否确定出库？")
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
                                        // TODO: 2018/1/17 需要把完成数量是0的都删除掉不传给后台
                                        List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> subTwodata = new ArrayList<>();
                                        for (int i = 0; i < data.size(); i++) {
                                            if (data.get(i).getQty_done() > 0) {
                                                subTwodata.add(data.get(i));
                                            }
                                        }
                                     //   data.removeAll(subTwodata);
                                        if (threeOrFive) {
                                            objectObjectHashMap.put("state", "process");
                                            objectObjectHashMap.put("picking_id", bundle1.getPicking_id());
                                            objectObjectHashMap.put("pack_operation_product_ids", subTwodata);
                                        } else {
                                            objectObjectHashMap.put("state", "process");
                                            objectObjectHashMap.put("picking_id", bundle1.getPicking_id());
                                            objectObjectHashMap.put("pack_operation_product_ids", subTwodata);
                                            objectObjectHashMap.put("qc_img", bundle1.getQc_img());
                                            // TODO: 2018/1/17 一次性出货规则的订单 需要看完成数量是否都等于了初始需求
                                            if (String.valueOf(bundle1.getDelivery_rule()).equals("delivery_once")) {
                                                boolean isYes = false;
                                                for (int i = 0; i < data.size(); i++) {
                                                    if (data.get(i).getQty_done() >= data.get(i).getOrigin_qty()) {
                                                        isYes = true;
                                                    } else {
                                                        isYes = false;
                                                        break;
                                                    }
                                                }
                                                if (isYes) {
                                                    objectObjectHashMap.put("qc_note", "yes");
                                                } else {
                                                    objectObjectHashMap.put("qc_note", "no");
                                                }
                                            } else {
                                                objectObjectHashMap.put("qc_note", "yes");
                                            }
                                        }
                                        Call<GetSaleResponse> getSaleResponseCall = inventoryApi.changeStockPicking(objectObjectHashMap);
                                        showDefultProgressDialog();
                                        getSaleResponseCall.enqueue(new Callback<GetSaleResponse>() {
                                            @Override
                                            public void onResponse(Call<GetSaleResponse> call, final Response<GetSaleResponse> response) {
                                                dismissDefultProgressDialog();
                                                if (response.body() == null)
                                                    return;
                                                if (response.body().getError() != null) {
                                                    new TipDialog(SalesDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                                            .show();
                                                    return;
                                                }
                                                if (response.body().getResult().getRes_code() == 1) {
                                                    AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "出库完成,是否拍摄物流信息")
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
                                                            Toast.makeText(SalesDetailActivity.this, "将自动打印此单据，请等待", Toast.LENGTH_LONG).show();
                                                            for (int i = 0; i < 2; i++) {
                                                                if (i == 0) {
                                                                    printTra("客户联");
                                                                } else if (i == 1) {
                                                                    printTra("物流联");
                                                                }
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
                                                ToastUtils.showCommonToast(SalesDetailActivity.this, t.toString());
                                                MyLog.e("", t.toString());
                                            }
                                        });
                                    }
                                }).show();
                    }
                });
                break;
            case "confirmed":
            case "partially_available":
            case "assigned":
                if (isBeihuoOk){
                    refreshButtom("备货完成");
                    initListener();
                }else {
                    showLinThreeCang();
                    buttomButton1.setText("开始备货");
                    buttomButton2.setVisibility(View.GONE);
                    buttomButton1.setBackgroundResource(R.color.violet);
                    buttomButton1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "确定开始备货？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //开始备货
                                            if (bundle1.isSecondary_operation()) {
                                                refreshButtom("分配二次加工负责人");
                                                initListener();
                                            } else {
                                                isBeihuoOk = true;
                                                refreshButtom("备货完成");
                                                model_state = "change";
                                                initListener();
                                            }
                                        }
                                    }).show();
                        }
                    });
                }
                break;
            case "done":
                buttomButton1.setText("补拍物流信息");
                buttomButton1.setVisibility(View.VISIBLE);
                buttomButton2.setVisibility(View.GONE);
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
            case "stock_ready":
            case "secondary_operation_done":
            case "备货完成":
//                initListener();
                tvAutoNum.setVisibility(View.VISIBLE);
                buttomButton1.setVisibility(View.VISIBLE);
                buttomButton2.setVisibility(View.GONE);
                buttomButton1.setText("备货完成");
                buttomButton1.setBackgroundResource(R.color.color_5693f8);
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> data = salesDetailAdapter.getData();
                        List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> subdata = new ArrayList<>();
                        for (int i = 0; i < data.size(); i++) {
                            if (data.get(i).getPack_id() == -1) {
                                subdata.add(data.get(i));
                            }
                        }
                        data.removeAll(subdata);
                        double sum = 0;
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
                                        objectObjectHashMap.put("state", "stock_ready");
                                        objectObjectHashMap.put("picking_id", bundle1.getPicking_id());
                                        // objectObjectHashMap.put("pack_operation_product_ids", subTwodata);
                                        objectObjectHashMap.put("qc_img", bundle1.getQc_img());
                                        Call<GetSaleResponse> getSaleResponseCall = inventoryApi.changeStockPicking(objectObjectHashMap);
                                        showDefultProgressDialog();
                                        getSaleResponseCall.enqueue(new Callback<GetSaleResponse>() {
                                            @Override
                                            public void onResponse(Call<GetSaleResponse> call, final Response<GetSaleResponse> response) {
                                                dismissDefultProgressDialog();
                                                if (response.body() == null)
                                                    return;
                                                if (response.body().getError() != null) {
                                                    new TipDialog(SalesDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                                            .show();
                                                    return;
                                                }
                                                if (response.body().getResult().getRes_code() == 1) {
                                                    Toast.makeText(SalesDetailActivity.this, "备货完成", Toast.LENGTH_LONG).show();
                                                    bundle1 = response.body().getResult().getRes_data();
                                                    refreshView(bundle1);
                                                } else if (response.body().getResult().getRes_code() == -1) {
                                                    ToastUtils.showCommonToast(SalesDetailActivity.this, response.body().getResult().getRes_data().getError() + "");
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<GetSaleResponse> call, Throwable t) {
                                                dismissDefultProgressDialog();
                                                ToastUtils.showCommonToast(SalesDetailActivity.this, t.toString());
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
                buttomButton2.setVisibility(View.GONE);
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
            case "cancel":
                buttomButton1.setText("已取消");
                break;
            case "分配二次加工负责人":
                buttomButton2.setVisibility(View.GONE);
                buttomButton1.setText("分配二次加工负责人");
                buttomButton1.setVisibility(View.VISIBLE);
                buttomButton1.setBackgroundResource(R.color.color_5693f8);
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SalesDetailActivity.this, AllEmployeesActivity.class);
                        intent.putExtra("picking_id", bundle1.getPicking_id());
                        startActivityForResult(intent, SECOND_OPERATION);
                    }
                });
                break;
            case "secondary_operation":
            case "加工完成":
                buttomButton1.setText("加工完成");
                buttomButton1.setVisibility(View.VISIBLE);
                buttomButton2.setVisibility(View.GONE);
                buttomButton1.setBackgroundResource(R.color.color_5693f8);
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        retrofit = new Retrofit.Builder()
                                .client(new OKHttpFactory(SalesDetailActivity.this).getOkHttpClient())
                                .baseUrl(RetrofitClient.Url + "/linkloving_timesheets/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                .build();
                        InventoryApi inventoryApi1 = retrofit.create(InventoryApi.class);
                        showDefultProgressDialog();
                        Call<WorkTypeBean> workType = inventoryApi1.getWorkType(new HashMap());
                        workType.enqueue(new Callback<WorkTypeBean>() {
                            @Override
                            public void onResponse(Call<WorkTypeBean> call, Response<WorkTypeBean> response) {
                                dismissDefultProgressDialog();
                                if (response.body() == null) return;
                                if (response.body().getError() != null) {
                                    new TipDialog(SalesDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                            .show();
                                    return;
                                }
                                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                                    WorkTimeDialog timeDialog = new WorkTimeDialog(SalesDetailActivity.this, R.style.MyDialogStyle,
                                            new WorkTimeDialog.OnSendCommonClickListener() {
                                                @Override
                                                public void OnSendCommonClick(double num, int type_id) {
                                                    //ToastUtils.showCommonToast(SalesDetailActivity.this, "num="+num+"gongzhong="+type_id);
                                                    retrofit = new Retrofit.Builder()
                                                            .client(new OKHttpFactory(SalesDetailActivity.this).getOkHttpClient())
                                                            .baseUrl(RetrofitClient.Url + "/linkloving_timesheets/")
                                                            .addConverterFactory(GsonConverterFactory.create())
                                                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                                            .build();
                                                    InventoryApi inventoryApi2 = retrofit.create(InventoryApi.class);
                                                    showDefultProgressDialog();
                                                    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                                                    hashMap.put("picking_id", bundle1.getPicking_id());
                                                    if (bundle1.getTimesheet_order_id().size() > 0) {
                                                        hashMap.put("sheet_id", bundle1.getTimesheet_order_id().get(0).getId());
                                                    } else {
                                                        hashMap.put("sheet_id", sheet_id);
                                                    }
                                                    hashMap.put("work_type_id", type_id);
                                                    hashMap.put("hour_spent", num);
                                                    Call<TimeSheetBean> commonBeanCall = inventoryApi2.actionAssinHour(hashMap);
                                                    commonBeanCall.enqueue(new Callback<TimeSheetBean>() {
                                                        @Override
                                                        public void onResponse(Call<TimeSheetBean> call, Response<TimeSheetBean> response) {
                                                            dismissDefultProgressDialog();
                                                            if (response.body() == null) return;
                                                            if (response.body().getError() != null) {
                                                                new TipDialog(SalesDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                                                        .show();
                                                                return;
                                                            }
                                                            if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                                                                TimeSheetBean.ResultBean.ResDataBean res_data = response.body().getResult().getRes_data().get(0);
                                                                tvControlName.setText(res_data.getTo_partner().getName());
                                                                tvFenName.setText(res_data.getFrom_partner().getName());
                                                                workChannel.setText(res_data.getWork_type_id().getName());
                                                                workTime.setText(res_data.getHour_spent() + "");
                                                              //  bundle1 = res_data;
                                                                refreshButtom("备货完成");
                                                                initListener();
//                                                                initData();
                                                            }
                                                        }
                                                        @Override
                                                        public void onFailure(Call<TimeSheetBean> call, Throwable t) {
                                                            dismissDefultProgressDialog();
                                                        }
                                                    });
                                                }
                                            }, response.body().getResult().getRes_data(), tvFenName.getText().toString(), tvControlName.getText().toString());
                                    timeDialog.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<WorkTypeBean> call, Throwable t) {
                                dismissDefultProgressDialog();
                            }
                        });
                    }
                });
                break;
        }
    }

    public Uri setImageUri() {
        // Store image in dcim
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "imageLogistical" + new Date().getTime() + ".png");
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
                    showDefultProgressDialog();
                    HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                    objectObjectHashMap.put("state", "upload_img");
                    objectObjectHashMap.put("picking_id", bundle1.getPicking_id());
                    List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> ids = bundle1.getPack_operation_product_ids();
                    List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> sub_ids = new ArrayList<>();
                    for (int i = 0; i < ids.size(); i++) {
                        if (ids.get(i).getPack_id() == -1) {
                            sub_ids.add(ids.get(i));
                        }
                    }
                    ids.removeAll(sub_ids);
                    objectObjectHashMap.put("pack_operation_product_ids", ids);
                    objectObjectHashMap.put("qc_note", bundle1.getQc_note());
                    objectObjectHashMap.put("qc_img", BitmapUtils.bitmapToBase64(ImageUtil.decodeFile(selectedImagePath)));
                    Call<GetSaleResponse> getSaleResponseCall = inventoryApi.changeStockPicking(objectObjectHashMap);
                    getSaleResponseCall.enqueue(new Callback<GetSaleResponse>() {
                        @Override
                        public void onResponse(Call<GetSaleResponse> call, Response<GetSaleResponse> response) {
                            dismissDefultProgressDialog();
                            if (response.body() == null)
                                return;
                            if (response.body().getError() != null) {
                                new TipDialog(SalesDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                        .show();
                                return;
                            }
                            if (response.body().getResult().getRes_code() == 1) {
                                Toast.makeText(SalesDetailActivity.this, "物流信息上传成功!", Toast.LENGTH_LONG).show();
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
            } else if (requestCode == SECOND_OPERATION) {
                if (resultCode == 1) {
                    if (data != null) {
                        dataBean = (TimeSheetBean.ResultBean.ResDataBean) data.getSerializableExtra("data");
//                        TimeSheetBean.ResultBean.ResDataBean.ToPartnerBean to_partner = (TimeSheetBean.ResultBean.ResDataBean.ToPartnerBean) data.getSerializableExtra("to_partner");
//                        TimeSheetBean.ResultBean.ResDataBean.FromPartnerBean fromPartnerBean = (TimeSheetBean.ResultBean.ResDataBean.FromPartnerBean) data.getSerializableExtra("from_partner");
                        if (dataBean != null) {
                            sheet_id = dataBean.getId();
                            tvFenName.setText(dataBean.getFrom_partner().getName());
                            tvControlName.setText(dataBean.getTo_partner().getName());
                        }
                        refreshButtom("加工完成");
                    }
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // TODO Auto-generated method stub
//        if (item.getItemId() == android.R.id.home) {
//            boolean isSave = false;
//            for (int i = 0; i < bundle1.getPack_operation_product_ids().size(); i++) {
//                if (bundle1.getPack_operation_product_ids().get(i).getQty_done() > 0) {
//                    isSave = true;
//                    break;
//                } else {
//                    isSave = false;
//                }
//            }
//            /**
//             * 情况一：点进来是开始备货状态，什么都没操作，点击返回按钮默认取消保留
//             * 情况二：点击了开始备货，什么也没操作，全是0，点击返回默认取消保留
//             * 情况三：点击了开始备货，并且至少备了一个，则进行选择是否保留
//             * 情况一和二合并
//             * */
//
//            if ("change".equals(model_state) && isSave && (bundle1.getState().equals("assigned") || bundle1.getState().equals("partially_available"))) {
//                new DialogIsSave(SalesDetailActivity.this)
//                        .setSave(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                saveState();
//                            }
//                        }).setNotSave(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        cacelReserver();
//                    }
//                }).setCancel().show();
//            } else if (isSave && (bundle1.getState().equals("assigned") || bundle1.getState().equals("partially_available"))) {
//                new DialogIsSave(SalesDetailActivity.this)
//                        .setSave(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                saveState();
//                            }
//                        }).setNotSave(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        cacelReserver();
//                    }
//                }).setCancel().show();
//            } else {
//                cacelReserver();
//            }
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * 返回按钮
     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
////            cacelReserver();
////            boolean isSave = false;
////            for (int i = 0; i < bundle1.getPack_operation_product_ids().size(); i++) {
////                if (bundle1.getPack_operation_product_ids().get(i).getQty_done() > 0) {
////                    isSave = true;
////                    break;
////                } else {
////                    isSave = false;
////                }
////            }
////            /**
////             * 情况一：点进来是开始备货状态，什么都没操作，点击返回按钮默认取消保留
////             * 情况二：点击了开始备货，什么也没操作，全是0，点击返回默认取消保留
////             * 情况三：点击了开始备货，并且至少备了一个，则进行选择是否保留
////             * 情况一和二合并
////             * */
////
////            if ("change".equals(model_state) && isSave && (bundle1.getState().equals("assigned") || bundle1.getState().equals("partially_available"))) {
////                new DialogIsSave(SalesDetailActivity.this)
////                        .setSave(new View.OnClickListener() {
////                            @Override
////                            public void onClick(View v) {
////                                saveState();
////                            }
////                        }).setNotSave(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        cacelReserver();
////                    }
////                }).setCancel().show();
////            } else if (isSave && (bundle1.getState().equals("assigned") || bundle1.getState().equals("partially_available"))) {
////                new DialogIsSave(SalesDetailActivity.this)
////                        .setSave(new View.OnClickListener() {
////                            @Override
////                            public void onClick(View v) {
////                                saveState();
////                            }
////                        }).setNotSave(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        cacelReserver();
////                    }
////                }).setCancel().show();
////            } else {
////                cacelReserver();
////            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

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
                if (response.body().getError() != null) {
                    new TipDialog(SalesDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getMessage())
                            .show();
                    return;
                }
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
            if (ids.get(i).getPack_id() == -1) {
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
                    new TipDialog(SalesDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getMessage())
                            .show();
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
    private void printTra(String type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDefultProgressDialog();
            }
        });
        initDevice();
        printer = (Printer) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_PRINTER);
        printer.init();
        printer.setLineSpace(1);
        printer.print("              " + type + "\n      ----------------\n" + "销售订单备注: " + bundle1.getSale_note() + "\n\n" + "出货单号：" + bundle1.getName() + "\n" + "源单据: " + bundle1.getOrigin() + "\n" + "合作伙伴： " +
                bundle1.getParnter_id() + "\n" +
                "收货人联系电话: " + bundle1.getPhone() + "\n--------------------------\n", 30, TimeUnit.SECONDS);
        printer.print("产品名称         完成数量", 30, TimeUnit.SECONDS);

        for (int i = 0; i < bundle1.getPack_operation_product_ids().size(); i++) {
            if (bundle1.getPack_operation_product_ids().get(i).getPack_id() != -1) {
                String name = bundle1.getPack_operation_product_ids().get(i).getProduct_id().getName();
                if (name.length() > 17) {
                    printer.print(name.substring(0, 15) + "      " +
                            bundle1.getPack_operation_product_ids().get(i).getQty_done()
                            + "\n" + name.substring(15, name.length()) + "\n\n", 30, TimeUnit.SECONDS);
                } else {
                    printer.print(name + "      " +
                            bundle1.getPack_operation_product_ids().get(i).getQty_done()
                            + "\n\n", 30, TimeUnit.SECONDS);
                }
            }
        }
        // printer.print("\n", 30, TimeUnit.SECONDS);
        // Bitmap mBitmap = CodeUtils.createImage(bundle1.getName()+"&stock.picking&"+bundle1.getPicking_id(), 300, 300, null);
        Bitmap mBitmap = CodeUtils.createImage(bundle1.getName(), 180, 180, null);
        printer.print(0, mBitmap, 30, TimeUnit.SECONDS);
        printer.print("\n" + "打印时间：" + DateTool.getDateTime(), 30, TimeUnit.SECONDS);
        printer.print("\n\n\n\n\n\n", 30, TimeUnit.SECONDS);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissDefultProgressDialog();
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
                                final Integer qty_available = StringUtils.doubleToInt(obj.getProduct_id().getQty_available());
                                double product_qty = obj.getProduct_qty();
                                double qty = qty_available >= product_qty ? product_qty : qty_available;
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
}
