package tarce.myodoo.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newland.me.ConnUtils;
import com.newland.me.DeviceManager;
import com.newland.mtype.ConnectionCloseEvent;
import com.newland.mtype.ModuleType;
import com.newland.mtype.event.DeviceEventListener;
import com.newland.mtype.module.common.printer.Printer;
import com.newland.mtype.module.common.rfcard.RFCardModule;
import com.newland.mtype.module.common.rfcard.RFResult;
import com.newland.mtype.util.ISOUtils;
import com.newland.mtypex.nseries.NSConnV100ConnParams;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.camera.CameraManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import tarce.model.inventory.CommonBean;
import tarce.model.inventory.FinishPrepareMaBean;
import tarce.model.inventory.GetFactroyRemarkBean;
import tarce.model.inventory.NfcOrderBean;
import tarce.model.inventory.OrderDetailBean;
import tarce.model.inventory.UpdateMessageBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.moreproduce.MaterialRelationActivity;
import tarce.myodoo.activity.newproduct.NewDateActivity;
import tarce.myodoo.adapter.DoneAdapter;
import tarce.myodoo.adapter.product.OrderDetailAdapter;
import tarce.myodoo.device.Const;
import tarce.myodoo.uiutil.DialogForOrder;
import tarce.myodoo.uiutil.FullyLinearLayoutManager;
import tarce.myodoo.uiutil.InsertFeedbackDial;
import tarce.myodoo.uiutil.InsertNumDialog;
import tarce.myodoo.uiutil.NFCdialog;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.FileUtil;
import tarce.myodoo.utils.StringUtils;
import tarce.myodoo.utils.UserManager;
import tarce.support.AlertAialogUtils;
import tarce.support.MyLog;
import tarce.support.SharePreferenceUtils;
import tarce.support.TimeUtils;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;

import static tarce.api.RetrofitClient.Url;

/**
 * Created by rose.zou on 2017/5/22.
 * 生产订单详情页面
 */

public class OrderDetailActivity extends ToolBarActivity {
    // 下载失败
    public static final int DOWNLOAD_ERROR = 4;
    // 下载成功
    public static final int DOWNLOAD_SUCCESS = 3;
    private static final String K21_DRIVER_NAME = "com.newland.me.K21Driver";
    private static final String TAG = "OrderDetailActivity";
    @InjectView(R.id.img_up_down)
    ImageView imgUpDown;
    @InjectView(R.id.linear_colum)
    LinearLayout linearColum;
    @InjectView(R.id.tv_name_product)
    TextView tvNameProduct;
    @InjectView(R.id.tv_time_product)
    TextView tvTimeProduct;
    @InjectView(R.id.tv_rework_product)
    TextView tvReworkProduct;
    @InjectView(R.id.tv_num_product)
    TextView tvNumProduct;
    @InjectView(R.id.tv_need_num)
    TextView tvNeedNum;
    @InjectView(R.id.tv_gongxu_product)
    TextView tvGongxuProduct;
    @InjectView(R.id.tv_type_product)
    TextView tvTypeProduct;
    @InjectView(R.id.relative_order_show)
    RelativeLayout relativeOrderShow;
    @InjectView(R.id.recycler_order_detail)
    RecyclerView recyclerOrderDetail;
    @InjectView(R.id.recycler2_order_detail)
    RecyclerView recycler2OrderDetail;
    @InjectView(R.id.recycler3_order_detail)
    RecyclerView recycler3OrderDetail;
    @InjectView(R.id.tv_state_order)
    TextView tvStateOrder;
    @InjectView(R.id.tv_start_produce)
    TextView tvStartProduce;
    @InjectView(R.id.framelayout_product)
    FrameLayout framelayoutProduct;
    @InjectView(R.id.tv_check_state)
    TextView tvCheckState;
    @InjectView(R.id.tv_string_guige)
    TextView tvStringGuige;
    private static final int STATE_WAIT_WATERIAL = 1;
    private static final int STATE_START_PRODUCT = 2;
    private static final int STATE_REQUSIT_RIGISTER = 3;//领料登记
    private static final int STATE_ALREADY_PICKING = 4;//开始生产
    private static final int WRITE_WATERIAL_OUT = 5;//填写退料
    private static final int LOOK_MESSAGE_FEEDBACK = 6;//仓库查看退料信息
    private static final int CHECK_MATERIAL_RETURN = 7;//清点退料
    private static final int FINISH_FEEDBACK = 8;//清点退料
    private static final int LOOK_FORCE_FEEDBACK = 9;//强制取消mo的查看退料
    private static final int WRITE_WATERIAL_OUT_FORCE = 10;//强制取消mo的查看退料
    @InjectView(R.id.tv_area_look)
    TextView tvAreaLook;
    @InjectView(R.id.linear_three)
    LinearLayout linearThree;
    @InjectView(R.id.tv_show_code)
    TextView tvShowCode;
    @InjectView(R.id.eidt_mo_note)
    EditText eidtMoNote;
    @InjectView(R.id.edit_sale_note)
    EditText editSaleNote;
    @InjectView(R.id.recycler_done)
    RecyclerView recyclerDone;
    @InjectView(R.id.product_detail)
    TextView productDetail;
    @InjectView(R.id.produce_line_id)
    TextView produceLineId;
    @InjectView(R.id.tv_second_product)
    TextView tvSecondProduct;
    @InjectView(R.id.tv_product_finish)
    TextView tvProductFinish;
    @InjectView(R.id.tv_add_product)
    TextView tvAddProduct;
    @InjectView(R.id.tv_feed_material)
    TextView tvFeedMaterial;
    /*@InjectView(R.id.tv_print)
    TextView tvPrint;*/
    private int click_check;//用于底部的点击事件  根据状态加载不同的点击事件后续
    private InventoryApi inventoryApi;
    private int order_id;
    private boolean up_or_down = true;//判断是收起还是展开,默认展开
    private boolean camera_or_relative = true;//判断是否显示camera,true为显示
    private OrderDetailBean.ResultBean.ResDataBean resDataBean;
    private OrderDetailAdapter adapter;
    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list_one;
    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list_two;
    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list_three;
    private OrderDetailAdapter adapter_two;
    private OrderDetailAdapter adapter_three;
    private FullyLinearLayoutManager fullyLinearLayoutManager;
    private String state;//用于区分加载不同布局
    private DialogForOrder dialogForOrder;
    private int limit;
    private String delay_state;
    private int process_id;
    private OrderDetailBean.ResultBean.ResDataBean.PrepareMaterialAreaIdBean prepare_material_area_id;
    private String prepare_material_img;
    private String name_activity;
  //  private String state_activity;
    private OrderDetailBean.ResultBean result;
    private boolean isShowDialog = true;//用于判断是否已经显示了dialog，为了防止扫描时候的连续弹出
    private CaptureFragment captureFragment;
    private Printer printer;
    private DeviceManager deviceManager;
    private String order_name;
    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list = new ArrayList<>();
    private String wantNext = "";
    private int indexForList = -1;
    private ProgressDialog mProgressDialog;
    private File file1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    prinPra();
                    break;
                case 2:
                    state = "prepare_material_ing";
                    stateView("prepare_material_ing");
                    initView();
                    break;
                case DOWNLOAD_SUCCESS:
                    Intent intent = new Intent(OrderDetailActivity.this, PdfActivity.class);
                    intent.putExtra("path", file1.getAbsolutePath());
                    startActivity(intent);
                    break;
                case DOWNLOAD_ERROR:
                    ToastUtils.showCommonToast(OrderDetailActivity.this, "下载失败");
                    break;
                case 5:
                    changeShowAfterNfc();
                    break;
            }
        }
    };

    private RFCardModule rfCardModule;
    private Retrofit retrofit;
    private NFCdialog nfCdialog;
    private InsertNumDialog insertNumDialog;
    private OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean handlerBean;
    private int handlerPosition;
    private double handlerNum;
    private int employee_id;
    private int handlerType;
    private DoneAdapter doneAdapter;
    private int line_id;
    private int origin_sale_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.inject(this);
        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(new OKHttpFactory(OrderDetailActivity.this).getOkHttpClient())
                .baseUrl(Url + "/linkloving_user_auth/")
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        Url = RetrofitClient.Url;
        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 1);
        order_name = intent.getStringExtra("order_name");
        setTitle(order_name);
        state = intent.getStringExtra("state");
        line_id = intent.getIntExtra("line_id", -1000);
        process_id = intent.getIntExtra("process_id", 1);
        name_activity = intent.getStringExtra("name_activity");
        origin_sale_id = intent.getIntExtra("origin_sale_id", 0);
        // initFragment();
        stateView(state);
        recyclerOrderDetail.setLayoutManager(new FullyLinearLayoutManager(OrderDetailActivity.this));
        recyclerOrderDetail.addItemDecoration(new DividerItemDecoration(OrderDetailActivity.this,
                DividerItemDecoration.VERTICAL));
        recycler2OrderDetail.setLayoutManager(new FullyLinearLayoutManager(OrderDetailActivity.this));
        recycler2OrderDetail.addItemDecoration(new DividerItemDecoration(OrderDetailActivity.this,
                DividerItemDecoration.VERTICAL));
        recycler3OrderDetail.setLayoutManager(new FullyLinearLayoutManager(OrderDetailActivity.this));
        recycler3OrderDetail.addItemDecoration(new DividerItemDecoration(OrderDetailActivity.this,
                DividerItemDecoration.VERTICAL));
        recyclerDone.setLayoutManager(new FullyLinearLayoutManager(OrderDetailActivity.this));
        recyclerDone.addItemDecoration(new DividerItemDecoration(OrderDetailActivity.this,
                DividerItemDecoration.VERTICAL));
        recyclerOrderDetail.setNestedScrollingEnabled(false);
        recycler2OrderDetail.setNestedScrollingEnabled(false);
        recycler3OrderDetail.setNestedScrollingEnabled(false);
        recyclerDone.setNestedScrollingEnabled(false);
        initDevice();
        showDefultProgressDialog();
        getDetail();
        // TODO: 2017/12/18 判断是否上次请求网络异常
        int httpKey = SharePreferenceUtils.getInt("httpKey", 1000, OrderDetailActivity.this);
        if (httpKey == 1024){
            new TipDialog(OrderDetailActivity.this, R.style.MyDialogStyle, "在本pos机的上次网络请求操作出现异常或者正在队列中，\n请核实备料数量或等待请求结束")
                    .setTrue(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharePreferenceUtils.putInt("httpKey", 1000, OrderDetailActivity.this);
                        }
                    })
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (state.equals("waiting_material") || state.equals("prepare_material_ing")) {
            menu.getItem(2).setTitle("备料反馈");
        } else {
            menu.getItem(2).setTitle("生产反馈");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            HashMap<Object, Object> hashMap = new HashMap<>();
            hashMap.put("order_id", order_id);
            Call<GetFactroyRemarkBean> factroyRemark = inventoryApi.getFactroyRemark(hashMap);
            factroyRemark.enqueue(new MyCallback<GetFactroyRemarkBean>() {
                @Override
                public void onResponse(Call<GetFactroyRemarkBean> call, Response<GetFactroyRemarkBean> response) {
                    if (response.body() == null) return;
                    if (response.body().getError() != null) {
                        new TipDialog(OrderDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                .show();
                        return;
                    }
                    if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                        String remark = response.body().getResult().getRes_data().getFactory_mark();
                        new InsertFeedbackDial(OrderDetailActivity.this, R.style.MyDialogStyle, new InsertFeedbackDial.OnSendCommonClickListener() {
                            @Override
                            public void OnSendCommonClick(String num) {
                                HashMap<Object, Object> hashMap = new HashMap<>();
                                hashMap.put("factory_remark", num);
                                hashMap.put("order_id", order_id);
                                Call<CommonBean> objectCall = inventoryApi.updateFactroyRemark(hashMap);
                                objectCall.enqueue(new MyCallback<CommonBean>() {
                                    @Override
                                    public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                                        if (response == null) return;
                                        try {
                                            if (response.body().getResult().getRes_code() == 1) {
                                                ToastUtils.showCommonToast(OrderDetailActivity.this, "反馈成功");
                                            }
                                        } catch (Exception e) {
                                            MyLog.e(TAG, e.toString());
                                        }
                                    }
                                });
                            }
                        }, remark).show();
                    }
                }

                @Override
                public void onFailure(Call<GetFactroyRemarkBean> call, Throwable t) {
                    ToastUtils.showCommonToast(OrderDetailActivity.this, t.toString());
                }
            });
        } else if (item.getItemId() == R.id.action_print) {
            AlertAialogUtils.getCommonDialog(OrderDetailActivity.this, "是否确认打印？\n(请尽量避免订单重复打印)")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            prinPra();
                        }
                    })
                    .show();
        } else if (item.getItemId() == R.id.action_feedback) {
            Intent intent = new Intent(OrderDetailActivity.this, FeedbackActivity.class);
            intent.putExtra("state", state);
            intent.putExtra("order_id", order_id);
            startActivity(intent);
        } else if (item.getItemId() == R.id.action_download) {
            if (resDataBean == null){
                ToastUtils.showCommonToast(OrderDetailActivity.this, "未找到sop文件地址，请返回重试");
                return true;
            }
            if (StringUtils.isNullOrEmpty(resDataBean.getSop_file_url())) {
                ToastUtils.showCommonToast(OrderDetailActivity.this, "本单暂时没有SOP文件");
                return true;
            }
            final String strname = resDataBean.getSop_file_url();
            mProgressDialog = new ProgressDialog(OrderDetailActivity.this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            //文件存储
            file1 = new File(Environment.getExternalStorageDirectory(), resDataBean.getDisplay_name() + ".pdf");
            new Thread() {
                public void run() {
                //    Log.e("zws", "走了这里下载");
//              本地没有此文件 则从网上下载打开
                    File downloadfile = FileUtil.downLoad(strname, file1.getAbsolutePath(), mProgressDialog);
                //    Log.e("zws", file1.getAbsolutePath() + "url=" + strname);
                    Message msg = Message.obtain();
                    if (downloadfile != null) {
                        // 下载成功,安装....
                        msg.obj = downloadfile;
                        msg.what = DOWNLOAD_SUCCESS;
                    } else {
                        // 提示用户下载失败.
                        msg.what = DOWNLOAD_ERROR;
                    }
                    mHandler.sendMessage(msg);
                    mProgressDialog.dismiss();
                }
            }.start();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 根据state显示不同布局
     */
    private void stateView(String state) {
        switch (state) {
            case "draft":
                tvStateOrder.setText("草稿");
                break;
            case "confirmed":
                tvStateOrder.setText("已排产");
                break;
            case "waiting_material":
                //initDevice();
                tvStateOrder.setText("等待备料");
                // tvStartProduce.setText("打开NFC,请仓库打卡备料");
                tvStartProduce.setText("开始备料");
                click_check = STATE_WAIT_WATERIAL;
                tvShowCode.setVisibility(View.GONE);
                showLinThreePro();
                break;
            case "prepare_material_ing":
                tvFeedMaterial.setVisibility(View.VISIBLE);
                tvStateOrder.setText("备料中");
                tvStartProduce.setText("备料完成");
                click_check = STATE_START_PRODUCT;
                relativeOrderShow.setVisibility(View.GONE);
                tvCheckState.setText("展开");
                imgUpDown.setImageResource(R.mipmap.down);
                up_or_down = false;
                /*tvAreaLook.setVisibility(View.VISIBLE);
                tvAreaLook.setText("结束本次备料");*/
                showLinThreePro();
                break;
            case "finish_prepare_material":
                tvStateOrder.setText("备料完成");
                click_check = STATE_REQUSIT_RIGISTER;
                tvStartProduce.setText("领料登记");
                tvAreaLook.setVisibility(View.VISIBLE);
                relativeOrderShow.setVisibility(View.GONE);
                tvCheckState.setText("展开");
                imgUpDown.setImageResource(R.mipmap.down);
                up_or_down = false;
                showLinThreePro();
                break;
            case "already_picking":
                tvFeedMaterial.setVisibility(View.VISIBLE);
                tvStateOrder.setText("已领料");
                click_check = STATE_ALREADY_PICKING;
                tvShowCode.setVisibility(View.GONE);
                relativeOrderShow.setVisibility(View.VISIBLE);
                tvCheckState.setText("收起");
                imgUpDown.setImageResource(R.mipmap.up);
                up_or_down = true;
                tvStartProduce.setText("开始生产");
                tvAddProduct.setVisibility(View.VISIBLE);
                tvAreaLook.setVisibility(View.VISIBLE);
                tvAreaLook.setText("补拍物料位置信息");
                showLinThreePro();
                break;
            case "planned":
                tvStateOrder.setText("安排");
                break;
            case "progress":
                tvStateOrder.setText("进行中");
                tvShowCode.setVisibility(View.GONE);
                break;
            case "waiting_inspection_finish":
                tvStateOrder.setText("等待品检完成");
                break;
            case "waiting_rework":
                tvStateOrder.setText("等待返工");
                break;
            case "rework_ing":
                tvStateOrder.setText("返工中");
                break;
            case "force_cancel_waiting_return":
                tvStateOrder.setText("取消MO待清点退料");
                tvShowCode.setVisibility(View.GONE);
                tvStartProduce.setText("填写退料");
                click_check = WRITE_WATERIAL_OUT_FORCE;
                showLinThreePro();
                break;
            case "waiting_inventory_material":
                tvStateOrder.setText("等待清点退料");
                tvShowCode.setVisibility(View.GONE);
                tvStartProduce.setText("填写退料");
                click_check = WRITE_WATERIAL_OUT;
                showLinThreePro();
                break;
            case "force_cancel_waiting_warehouse_inspection":
                tvStateOrder.setText("取消MO待检验退料");
                tvShowCode.setVisibility(View.GONE);
                tvStartProduce.setText("查看退料信息");
                click_check = LOOK_FORCE_FEEDBACK;
                showLinThreePro();
                break;
            case "waiting_warehouse_inspection":
                tvStateOrder.setText("等待检验退料");
                tvShowCode.setVisibility(View.GONE);
                tvStartProduce.setText("仓库查看退料信息");
                click_check = LOOK_MESSAGE_FEEDBACK;
                showLinThreePro();
                break;
            case "waiting_post_inventory":
                tvStateOrder.setText("等待入库");
                break;
            case "done":
                tvStateOrder.setText("完成");
                tvShowCode.setVisibility(View.GONE);
                tvStartProduce.setText("清点退料");
                click_check = CHECK_MATERIAL_RETURN;
                break;
        }
    }

    /**
     * 是否显示底部（生产）
     */
    public void showLinThreePro() {
        try {
            if (!UserManager.getSingleton().getGrops().contains("group_charge_produce")) {
                linearThree.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            ToastUtils.showCommonToast(OrderDetailActivity.this, e.toString());
        }
    }


    /**
     * 订单详情
     */
    private void getDetail() {
        inventoryApi = RetrofitClient.getInstance(OrderDetailActivity.this).create(InventoryApi.class);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("order_id", order_id);
        Call<OrderDetailBean> orderDetail = inventoryApi.getOrderDetail(hashMap);
        orderDetail.enqueue(new MyCallback<OrderDetailBean>() {
            @Override
            public void onResponse(Call<OrderDetailBean> call, Response<OrderDetailBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError() != null) {
                    new TipDialog(OrderDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                    result = response.body().getResult();
                    resDataBean = response.body().getResult().getRes_data();
                    prepare_material_area_id = response.body().getResult().getRes_data().getPrepare_material_area_id();
                    prepare_material_img = response.body().getResult().getRes_data().getPrepare_material_img();
                    initView();
                }
            }

            @Override
            public void onFailure(Call<OrderDetailBean> call, Throwable t) {
                dismissDefultProgressDialog();
                Log.e("zws", t.toString());
                ToastUtils.showCommonToast(OrderDetailActivity.this, t.toString());
            }
        });
    }

    /**
     * 补领料
     * */
    @OnClick(R.id.tv_add_product)
    void setTvAddProduct(View view) {
        try {
            Intent intent = new Intent(OrderDetailActivity.this, BuGetLiaoActivity.class);
            intent.putExtra("value", resDataBean);
            intent.putExtra("state", resDataBean.getState());
            intent.putExtra("order_id", order_id);
            startActivity(intent);
        } catch (Exception e) {
            ToastUtils.showCommonToast(OrderDetailActivity.this, e.toString());
        }
    }

    /**
     * 扫NFC之后刷新试图
     */
    private void changeShowAfterNfc() {
        SharePreferenceUtils.putInt("httpKey", 1024, OrderDetailActivity.this);
        showDefultProgressDialog();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("order_id", order_id);
        hashMap.put("employee_id", employee_id);
        Map<Object, Object> mapSmall = new HashMap<>();
        mapSmall.put("stock_move_lines_id", handlerBean.getId());
        mapSmall.put("quantity_ready", handlerNum);
        mapSmall.put("order_id", handlerBean.getOrder_id());
        hashMap.put("stock_move", mapSmall);
        Call<OrderDetailBean> objectCall = inventoryApi.newPrepareMater(hashMap);
        objectCall.enqueue(new Callback<OrderDetailBean>() {
            @Override
            public void onResponse(final Call<OrderDetailBean> call, final Response<OrderDetailBean> response) {
                SharePreferenceUtils.putInt("httpKey", 1000, OrderDetailActivity.this);
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError() != null) {
                    new TipDialog(OrderDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            result = response.body().getResult();
                            resDataBean = response.body().getResult().getRes_data();
                            list_one = new ArrayList<>();
                            list_two = new ArrayList<>();
                            for (int i = 0; i < resDataBean.getStock_move_lines().size(); i++) {
                                if (resDataBean.getStock_move_lines().get(i).getProduct_type().equals("material")) {
                                    list_one.add(resDataBean.getStock_move_lines().get(i));
                                } else if (resDataBean.getStock_move_lines().get(i).getProduct_type().equals("real_semi_finished")) {
                                    list_two.add(resDataBean.getStock_move_lines().get(i));
                                }
                            }
                            OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean stockMoveLinesBean = new OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean();
                            switch (handlerType) {
                                case 1:
                                    stockMoveLinesBean = list_one.get(handlerPosition);
                                    handlerBean.setQuantity_ready(stockMoveLinesBean.getQuantity_ready());
                                    handlerBean.setQuantity_done(stockMoveLinesBean.getQuantity_done());
                                    handlerBean.setQty_available(stockMoveLinesBean.getQty_available());
                                    handlerBean.setBlue(true);
                                    adapter.notifyDataSetChanged();
                                    break;
                                case 2:
                                    stockMoveLinesBean = list_two.get(handlerPosition);
                                    handlerBean.setQuantity_ready(stockMoveLinesBean.getQuantity_ready());
                                    handlerBean.setQuantity_done(stockMoveLinesBean.getQuantity_done());
                                    handlerBean.setQty_available(stockMoveLinesBean.getQty_available());
                                    handlerBean.setBlue(true);
                                    adapter_two.notifyDataSetChanged();
                                    break;
                            }
                            printer = (Printer) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_PRINTER);
                            printer.init();
                            printer.setLineSpace(1);
                            printer.print("MO单号：" + order_name + "\n产品名称：" + stockMoveLinesBean.getProduct_id() + "\n需求数量：" +
                                    stockMoveLinesBean.getProduct_uom_qty()
                                    + "\n备料数量：" + (stockMoveLinesBean.getQuantity_ready() + stockMoveLinesBean.getQuantity_done()), 30, TimeUnit.SECONDS);
                            printer.print("\n\n\n\n\n\n\n", 30, TimeUnit.SECONDS);
//                            Message message = new Message();
//                            message.what = 1;
//                            mHandler.sendMessage(message);
                        }
                    });
                } else if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == -1) {
                    SharePreferenceUtils.putInt("httpKey", 1000, OrderDetailActivity.this);
                    new TipDialog(OrderDetailActivity.this, R.style.MyDialogStyle, response.body().getResult().getRes_data().getError())
                            .show();
                }
            }

            @Override
            public void onFailure(Call<OrderDetailBean> call, Throwable t) {
                dismissDefultProgressDialog();
                new TipDialog(OrderDetailActivity.this, R.style.MyDialogStyle, t.toString()+"\n返回重试")
                        .setTrue(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        })
                        .show();
                //threadDismiss(nfCdialog);
            }
        });
    }

    /**
     * 初始化dialog并进行相关后续操作
     *
     * @param linesBean
     */
    private void initDialog(final OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean linesBean, final int position, final int type) {

//        if (linesBean.getProduct_uom_qty() == 0 && !resDataBean.getProcess_id().is_multi_output()
//                && !resDataBean.getProcess_id().is_random_output()) {
//            ToastUtils.showCommonToast(OrderDetailActivity.this, "需求数量为0，不可备料");
//            return;
//        }
        if (isShowDialog) {
            isShowDialog = false;
            dialogForOrder = new DialogForOrder(OrderDetailActivity.this, new DialogForOrder.OnSendCommonClickListener() {
                @Override
                public void OnSendCommonClick(final double num) {
                    if (num == 0) {
                        return;
                    }
                    final double i = linesBean.getQty_available();
                    //   int i1 = StringUtils.doubleToInt(linesBean.getQuantity_ready());
                    if (num > i) {
                        ToastUtils.showCommonToast(OrderDetailActivity.this, "该产品库存不足");
                    } else {
                        if (type != 3) {
                            handlerBean = linesBean;
                            handlerPosition = position;
                            handlerNum = num;
                            handlerType = type;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    processingLock();
                                    showNfcDialog();
                                    try {
                                        final RFResult qPResult = rfCardModule.powerOn(null, 8, TimeUnit.SECONDS);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (qPResult.getCardSerialNo() == null) {
                                                    ToastUtils.showCommonToast(OrderDetailActivity.this, "不能识别序列号：" + Const.MessageTag.DATA);
                                                } else {
                                                    // showDefultProgressDialog();
                                                    String NFC_Number = ISOUtils.hexString(qPResult.getCardSerialNo());
                                                    InventoryApi inventory = retrofit.create(InventoryApi.class);
                                                    HashMap<Object, Object> hashMap = new HashMap<>();
                                                    hashMap.put("card_num", NFC_Number);
                                                    Call<NfcOrderBean> objectCall = inventory.authWarehouse(hashMap);
                                                    objectCall.enqueue(new Callback<NfcOrderBean>() {
                                                        @Override
                                                        public void onResponse(Call<NfcOrderBean> call, Response<NfcOrderBean> response) {
                                                            // dismissDefultProgressDialog();
                                                            if (response.body() == null) return;
                                                            if (response.body().getError() != null) {
                                                                nfCdialog.setHeaderImage(R.drawable.warning)
                                                                        .setTip(response.body().getError().getData().getMessage())
                                                                        .setCancelVisi().show();
                                                                threadDismiss(nfCdialog);
                                                                return;
                                                            }
                                                            if (response.body().getResult() != null && response.body().getResult().getRes_code() == -1) {
                                                                nfCdialog.setHeaderImage(R.drawable.warning)
                                                                        .setTip(response.body().getResult().getRes_data().getErrorX())
                                                                        .setCancelVisi().show();
                                                                threadDismiss(nfCdialog);
                                                            } else if (response.body().getResult() != null && response.body().getResult().getRes_code() == 1) {
                                                                final NfcOrderBean.ResultBean.ResDataBean res_data = response.body().getResult().getRes_data();
                                                                nfCdialog.setHeaderImage(R.drawable.defaultimage)
                                                                        .setTip(res_data.getName() + res_data.getEmployee_id() + "\n" + res_data.getWork_email()
                                                                                + "\n\n" + "打卡成功")
                                                                        .setCancelVisi().show();
                                                                new Handler().postDelayed(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        nfCdialog.dismiss();
                                                                    }
                                                                }, 500);
                                                                employee_id = res_data.getEmployee_id();
                                                                Message message = new Message();
                                                                message.what = 5;
                                                                mHandler.sendMessage(message);
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<NfcOrderBean> call, Throwable t) {
                                                            // dismissDefultProgressDialog();
                                                            Log.e("zws", t.toString());
                                                        }
                                                    });
                                                }
                                                processingUnLock();
                                            }
                                        });
                                    } catch (final Exception e) {
                                        e.fillInStackTrace();
                                        if (e.getMessage().equals("device invoke timeout!7")) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        Thread.sleep(1000);
                                                        // ToastUtils.showCommonToast(OrderDetailActivity.this, e.getMessage() + "  " + Const.MessageTag.ERROR);
                                                        nfCdialog.dismiss();
                                                        finish();
                                                    } catch (InterruptedException e1) {
                                                        e1.printStackTrace();
                                                    }
                                                }
                                            });
                                        }
                                        processingUnLock();
                                    }
                                }
                            }).start();
                        } else if (type == 3) {
                            showDefultProgressDialog();
                            HashMap<Object, Object> hashMap = new HashMap<>();
                            hashMap.put("order_id", order_id);
                            Map<Object, Object> mapSmall = new HashMap<>();
                            mapSmall.put("stock_move_lines_id", linesBean.getId());
                            mapSmall.put("quantity_ready", num);
                            mapSmall.put("order_id", linesBean.getOrder_id());
                            hashMap.put("stock_move", mapSmall);
                            Call<OrderDetailBean> objectCall = inventoryApi.newPrepareMater(hashMap);
                            objectCall.enqueue(new Callback<OrderDetailBean>() {
                                @Override
                                public void onResponse(final Call<OrderDetailBean> call, final Response<OrderDetailBean> response) {
                                    dismissDefultProgressDialog();
                                    if (response.body() == null)
                                        return;
                                    if (response.body().getError() != null) {
                                        new TipDialog(OrderDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                                .show();
                                        return;
                                    }
                                    if (response.body().getResult().getRes_code() == 1) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                result = response.body().getResult();
                                                resDataBean = response.body().getResult().getRes_data();
                                                list_three = new ArrayList<>();
                                                for (int i = 0; i < resDataBean.getStock_move_lines().size(); i++) {
                                                    String product_type = resDataBean.getStock_move_lines().get(i).getProduct_type();
                                                    if (product_type.equals("semi-finished")) {
                                                        list_three.add(resDataBean.getStock_move_lines().get(i));
                                                    }
                                                }
                                                linesBean.setQuantity_ready(list_three.get(position).getQuantity_ready());
                                                linesBean.setQuantity_done(list_three.get(position).getQuantity_done());
                                                linesBean.setQty_available(list_three.get(position).getQty_available());
                                                linesBean.setBlue(true);
                                                adapter_three.notifyDataSetChanged();
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (deviceManager == null) {
                                                            initDevice();
                                                        }
                                                        printer = (Printer) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_PRINTER);
                                                        printer.init();
                                                        printer.setLineSpace(1);
                                                        printer.print("MO单号：" + order_name + "\n产品名称：" + list_three.get(position).getProduct_id() + "\n需求数量：" +
                                                                list_three.get(position).getProduct_uom_qty()
                                                                + "\n备料数量：" + (list_three.get(position).getQuantity_ready() + list_three.get(position).getQuantity_done()), 30, TimeUnit.SECONDS);
                                                        printer.print("\n\n\n\n\n\n\n", 30, TimeUnit.SECONDS);
                                                    }
                                                }).start();
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onFailure(Call<OrderDetailBean> call, Throwable t) {
                                    dismissDefultProgressDialog();
                                    ToastUtils.showCommonToast(OrderDetailActivity.this, "请求出现错误，请重试");
                                    finish();
                                }
                            });
                        }
                    }
                }
            }, linesBean)
                    //.setWeight(2);
                    .setWeight(linesBean.getWeight());
            dialogForOrder.show();
        }
        if (!dialogForOrder.isShowing()) {//为防止扫描多次弹出多个对话框
            isShowDialog = true;
        }
    }

    /**
     * 根据数据赋值显示view
     */
    private void initView() {
        if (resDataBean.is_secondary_produce()) {
            tvSecondProduct.setText("二次生产");
        } else {
            tvSecondProduct.setVisibility(View.GONE);
        }
        if (resDataBean.getProduction_line_id() != null) {
            produceLineId.setText("产线：" + resDataBean.getProduction_line_id().getName());
        } else {
            produceLineId.setText("产线暂无");
        }
        tvNameProduct.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvNameProduct.setText(resDataBean.getProduct_name());
        tvNumProduct.setText(resDataBean.getQty_produced() + "");
        tvNeedNum.setText(resDataBean.getProduct_qty() + "");
        tvTimeProduct.setText(TimeUtils.utc2Local(resDataBean.getDate_planned_start()));
        tvReworkProduct.setText(resDataBean.getIn_charge_name());
        tvStringGuige.setText(String.valueOf(resDataBean.getProduct_id().getProduct_specs()));
        tvStringGuige.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        switch (String.valueOf(resDataBean.getProduction_order_type())) {
            case "stockup":
                tvTypeProduct.setText("备货制");
                break;
            case "ordering":
                tvTypeProduct.setText("订单制");
                break;
        }
        eidtMoNote.setText(resDataBean.getRemark());
        editSaleNote.setText(resDataBean.getSale_remark());
        tvGongxuProduct.setText(resDataBean.getProcess_id().getName());
        list_one = new ArrayList<>();
        list_two = new ArrayList<>();
        list_three = new ArrayList<>();
        for (int i = 0; i < resDataBean.getStock_move_lines().size(); i++) {
            if (resDataBean.getStock_move_lines().get(i).getProduct_type().equals("material")) {
                list_one.add(resDataBean.getStock_move_lines().get(i));
            } else if (resDataBean.getStock_move_lines().get(i).getProduct_type().equals("real_semi_finished")) {
                list_two.add(resDataBean.getStock_move_lines().get(i));
            } else {
                list_three.add(resDataBean.getStock_move_lines().get(i));
            }
        }
        adapter = new OrderDetailAdapter(OrderDetailActivity.this, list_one, "原材料", result);
        adapter_two = new OrderDetailAdapter(OrderDetailActivity.this, list_two, "半成品", result);
        adapter_three = new OrderDetailAdapter(OrderDetailActivity.this, list_three, "流转品", result);
        recyclerOrderDetail.setAdapter(adapter);
        recycler2OrderDetail.setAdapter(adapter_two);
        recycler3OrderDetail.setAdapter(adapter_three);
//        if (resDataBean.getProcess_id().is_multi_output() || resDataBean.getProcess_id().is_random_output()) {
//            doneAdapter = new DoneAdapter(R.layout.item_done_adapter, resDataBean.getDone_stock_moves(), false);
//            recyclerDone.setAdapter(doneAdapter);
//            tvProductFinish.setVisibility(View.VISIBLE);
//            productDetail.setText("消耗的物料");
//            tvShowCode.setText("查看物料关系");
//            tvShowCode.setVisibility(View.VISIBLE);
//        }
        canClick();
    }

    //设置adapter点击事件什么情况下可触发
    private void canClick() {
        if (state.equals("prepare_material_ing")) {
            adapter.setOnRecyclerViewItemClickListener(new OrderDetailAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean linesBean, int position) {
                    isShowDialog = true;
                    initDialog(linesBean, position, 1);
                }
            });
            adapter_two.setOnRecyclerViewItemClickListener(new OrderDetailAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean linesBean, int position) {
                    isShowDialog = true;
                    initDialog(linesBean, position, 2);
                }
            });
            adapter_three.setOnRecyclerViewItemClickListener(new OrderDetailAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean linesBean, int position) {
                    isShowDialog = true;
                    initDialog(linesBean, position, 3);
                }
            });
        }
        if (state.equals("finish_prepare_material")) {
            adapter_three.setOnRecyclerViewItemClickListener(new OrderDetailAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean linesBean, int position) {
                    isShowDialog = true;
                    initDialog(linesBean, position, 3);
                }
            });
        }
        if (state.equals("waiting_inventory_material") || state.equals("waiting_warehouse_inspection")
                || state.equals("done")) {
            adapter.setGray_bac(true);
            adapter.notifyDataSetChanged();
            adapter_two.setGray_bac(true);
            adapter_two.notifyDataSetChanged();
            adapter_three.setGray_bac(true);
            adapter_three.notifyDataSetChanged();
        }
    }

    /**
     * 点击规格显示更详细内容
     */
    @OnClick(R.id.tv_string_guige)
    void showDetail(View view) {
        new TipDialog(OrderDetailActivity.this, R.style.MyDialogStyle, String.valueOf(resDataBean.getProduct_id().getProduct_specs()))
                .show();
    }

    //将listone和listto合并为一个list
    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> linkOneTwo() {
        list.addAll(adapter.getList());
        list.addAll(adapter_two.getList());
        return list;
    }

    /**
     * 新增的退料按钮点击时间
     * */
    @OnClick(R.id.tv_feed_material)
    void clickFeedMaterial(View view){
        switch (click_check){
            case STATE_START_PRODUCT:
                try {
                    Intent intent3 = new Intent(OrderDetailActivity.this, WriteFeedMateriActivity.class);
                    intent3.putExtra("recycler_data", resDataBean);
                    intent3.putExtra("order_id", order_id);
                    intent3.putExtra("from", "anytime");
                    intent3.putExtra("process_id", process_id);
                    intent3.putExtra("line_id", line_id);
                    intent3.putExtra("origin_sale_id", origin_sale_id);
                    startActivity(intent3);
                } catch (Exception e) {
                    ToastUtils.showCommonToast(OrderDetailActivity.this, e.toString());
                }
                break;
            case STATE_ALREADY_PICKING:
                try {
                    Intent intent3 = new Intent(OrderDetailActivity.this, WriteFeedMateriActivity.class);
                    intent3.putExtra("recycler_data", resDataBean);
                    intent3.putExtra("order_id", order_id);
                    intent3.putExtra("from", "anytime");
                    intent3.putExtra("process_id", process_id);
                    intent3.putExtra("line_id", line_id);
                    intent3.putExtra("origin_sale_id", origin_sale_id);
                    startActivity(intent3);
                } catch (Exception e) {
                    ToastUtils.showCommonToast(OrderDetailActivity.this, e.toString());
                }
                break;
        }
    }
    /**
     * 底部按键点击时间
     * */
    @OnClick(R.id.tv_start_produce)
    void clickBottom(View view) {
        switch (click_check) {
            case STATE_WAIT_WATERIAL:
                AlertAialogUtils.getCommonDialog(OrderDetailActivity.this, "是否确定开始备料")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                showDefultProgressDialog();
                                HashMap hashMap = new HashMap();
                                hashMap.put("order_id", order_id);
                                Call<OrderDetailBean> objectCall = inventoryApi.checkPrepare(hashMap);
                                objectCall.enqueue(new MyCallback<OrderDetailBean>() {
                                    @Override
                                    public void onResponse(Call<OrderDetailBean> call, Response<OrderDetailBean> response) {
                                        dismissDefultProgressDialog();
                                        if (response.body() == null)
                                            return;
                                        if (response.body().getError() != null) {
                                            new TipDialog(OrderDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getMessage())
                                                    .show();
                                            return;
                                        }
                                        if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                                            resDataBean = response.body().getResult().getRes_data();
                                            Message message = mHandler.obtainMessage();
                                            message.what = 2;
                                            mHandler.sendMessage(message);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<OrderDetailBean> call, Throwable t) {
                                        dismissDefultProgressDialog();
                                        ToastUtils.showCommonToast(OrderDetailActivity.this, t.toString());
                                    }
                                });
                            }
                        }).show();
                break;
            case STATE_START_PRODUCT://备料完成
                boolean nextone = false;
                int indexone = -1;
                linkOneTwo();
                try {
                    for (int i = 0; i < list.size(); i++) {
                        if (StringUtils.doubleToInt(list.get(i).getQuantity_ready() + list.get(i).getQuantity_done()) > 0) {
                            nextone = true;
                            indexone = i;
                            break;
                        }
                    }
                    for (int i = 0; i < list_three.size(); i++) {
                        if (StringUtils.doubleToInt(list_three.get(i).getQuantity_ready() + list_three.get(i).getQuantity_done()) > 0) {
                            nextone = true;
                            break;
                        }
                    }
                } catch (Exception e) {
                    ToastUtils.showCommonToast(OrderDetailActivity.this, e.toString());
                }
                if (nextone) {
                    AlertAialogUtils.getCommonDialog(OrderDetailActivity.this, "确定备料完成？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    showNext();
                                }
                            }).show();
                } else {
                    ToastUtils.showCommonToast(OrderDetailActivity.this, "产品均未备料");
                    /*AlertAialogUtils.getCommonDialog(OrderDetailActivity.this, "").setMessage(list.get(indexone).getProduct_id() + " 未备料")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();*/
                }
                break;
            case STATE_REQUSIT_RIGISTER:
                /**
                 * 判断流转品列表，逻辑为：备料数量至少要大于等于1，不然不能领料登记，而是提醒去修改备料数量，这时候 ，原材料和半成品是不能点击的
                 * */
                boolean next = true;
                int index = -1;
                try {
                    for (int i = 0; i < list_three.size(); i++) {
                        if (StringUtils.doubleToInt(list_three.get(i).getQuantity_ready() + list_three.get(i).getQuantity_done()) < 1) {
                            next = false;
                            index = i;
                            break;
                        }
                    }
                } catch (Exception e) {
                    ToastUtils.showCommonToast(OrderDetailActivity.this, e.toString());
                }
                if (next) {
                    AlertAialogUtils.getCommonDialog(OrderDetailActivity.this, "是否确定领料登记")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    showDefultProgressDialog();
                                    HashMap<Object, Object> hashMap = new HashMap();
                                    hashMap.put("order_id", order_id);
                                    Map[] maps = new Map[list_three.size()];
                                    for (int i = 0; i < list_three.size(); i++) {
                                        Map<Object, Object> mapSmall = new HashMap<>();
                                        mapSmall.put("stock_move_lines_id", list_three.get(i).getId());
                                        mapSmall.put("quantity_ready", list_three.get(i).getQuantity_ready());
                                        mapSmall.put("order_id", list_three.get(i).getOrder_id());
                                        maps[i] = mapSmall;
                                    }
                                    hashMap.put("stock_moves", maps);
                                    Call<OrderDetailBean> objectCall = inventoryApi.checkPickRegister(hashMap);
                                    objectCall.enqueue(new MyCallback<OrderDetailBean>() {
                                        @Override
                                        public void onResponse(Call<OrderDetailBean> call, Response<OrderDetailBean> response) {
                                            dismissDefultProgressDialog();
                                            if (response.body() == null)
                                                return;
                                            if (response.body().getError() != null) {
                                                new TipDialog(OrderDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                                        .show();
                                                return;
                                            }
                                            if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                                                resDataBean = response.body().getResult().getRes_data();
                                                initView();
                                                stateView("already_picking");
                                                AlertAialogUtils.getCommonDialog(OrderDetailActivity.this, "").setMessage("领料完成")
                                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Intent intent = new Intent(OrderDetailActivity.this, ProductLlActivity.class);
                                                                intent.putExtra("name_activity", name_activity);
                                                                intent.putExtra("state_activity", state);
                                                                intent.putExtra("process_id", process_id);
                                                                intent.putExtra("line_id", line_id);
                                                                startActivity(intent);
                                                                dialog.dismiss();
                                                            }
                                                        }).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<OrderDetailBean> call, Throwable t) {
                                            dismissDefultProgressDialog();
                                            ToastUtils.showCommonToast(OrderDetailActivity.this, t.toString());
                                        }
                                    });
                                }
                            }).show();
                } else {
                    AlertAialogUtils.getCommonDialog(OrderDetailActivity.this, "").setMessage("请确认： " + list_three.get(index).getProduct_id() + " 的领料数量")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
                break;
            case STATE_ALREADY_PICKING:
                try {
                    Intent intent = new Intent(OrderDetailActivity.this, AddPersonActivity.class);
                    intent.putExtra("order_id", order_id);
                    intent.putExtra("state_activity", state);
                    intent.putExtra("name_activity", name_activity);
                    intent.putExtra("close", false);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    ToastUtils.showCommonToast(OrderDetailActivity.this, e.toString());
                }
                break;
            case WRITE_WATERIAL_OUT_FORCE:
                try {
                    Intent intent1 = new Intent(OrderDetailActivity.this, WriteFeedMateriActivity.class);
                    intent1.putExtra("recycler_data", resDataBean);
                    intent1.putExtra("order_id", order_id);
                    intent1.putExtra("from", "force_cancel_waiting_return");
                    intent1.putExtra("process_id", process_id);
                    intent1.putExtra("line_id", line_id);
                    intent1.putExtra("origin_sale_id", origin_sale_id);
                    startActivity(intent1);
                } catch (Exception e) {
                    ToastUtils.showCommonToast(OrderDetailActivity.this, e.toString());
                }
                break;
            case WRITE_WATERIAL_OUT://填写退料
                try {
                    Intent intent1 = new Intent(OrderDetailActivity.this, WriteFeedMateriActivity.class);
                    intent1.putExtra("recycler_data", resDataBean);
                    intent1.putExtra("order_id", order_id);
                    intent1.putExtra("from", "write");
                    intent1.putExtra("process_id", process_id);
                    intent1.putExtra("line_id", line_id);
                    intent1.putExtra("origin_sale_id", origin_sale_id);
                    startActivity(intent1);
                } catch (Exception e) {
                    ToastUtils.showCommonToast(OrderDetailActivity.this, e.toString());
                }
                break;
            case LOOK_MESSAGE_FEEDBACK:
                try {
                    Intent intent2 = new Intent(OrderDetailActivity.this, WriteFeedMateriActivity.class);
                    intent2.putExtra("recycler_data", resDataBean);
                    intent2.putExtra("order_id", order_id);
                    intent2.putExtra("from", "look");
                    intent2.putExtra("process_id", process_id);
                    intent2.putExtra("line_id", line_id);
                    intent2.putExtra("origin_sale_id", origin_sale_id);
                    startActivity(intent2);
                } catch (Exception e) {
                    ToastUtils.showCommonToast(OrderDetailActivity.this, e.toString());
                }
                break;
            case LOOK_FORCE_FEEDBACK://强制退料 待检验退料
                try {
                    Intent intent2 = new Intent(OrderDetailActivity.this, WriteFeedMateriActivity.class);
                    intent2.putExtra("recycler_data", resDataBean);
                    intent2.putExtra("order_id", order_id);
                    intent2.putExtra("from", "force_cancel_waiting_warehouse_inspection");
                    intent2.putExtra("process_id", process_id);
                    intent2.putExtra("line_id", line_id);
                    intent2.putExtra("origin_sale_id", origin_sale_id);
                    startActivity(intent2);
                } catch (Exception e) {
                    ToastUtils.showCommonToast(OrderDetailActivity.this, e.toString());
                }
                break;
            case CHECK_MATERIAL_RETURN:
                try {
                    Intent intent3 = new Intent(OrderDetailActivity.this, WriteFeedMateriActivity.class);
                    intent3.putExtra("recycler_data", resDataBean);
                    intent3.putExtra("order_id", order_id);
                    intent3.putExtra("from", "check");
                    intent3.putExtra("process_id", process_id);
                    intent3.putExtra("line_id", line_id);
                    intent3.putExtra("origin_sale_id", origin_sale_id);
                    startActivity(intent3);
                } catch (Exception e) {
                    ToastUtils.showCommonToast(OrderDetailActivity.this, e.toString());
                }
                break;
        }
    }

    //显示nfc的dialog
    private void showNfcDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nfCdialog = new NFCdialog(OrderDetailActivity.this);
                nfCdialog.setCancel(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        processingUnLock();
                        nfCdialog.dismiss();
                        isShowDialog = true;
                        return;
                    }
                }).show();
            }
        });
    }

    //关闭dialog
    private void threadDismiss(final NFCdialog dialog) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1000);
    }

    /**
     * 收起，展开
     */
    @OnClick(R.id.img_up_down)
    void onClickImage(View v) {
        if (up_or_down) {
            relativeOrderShow.setVisibility(View.GONE);
            tvCheckState.setText("展开");
            imgUpDown.setImageResource(R.mipmap.down);
            up_or_down = false;
        } else {
            relativeOrderShow.setVisibility(View.VISIBLE);
            tvCheckState.setText("收起");
            imgUpDown.setImageResource(R.mipmap.up);
            up_or_down = true;
        }
    }

    /**
     * 显示二维码
     */
    @OnClick(R.id.tv_show_code)
    void useCode(View view) {
        switch (tvShowCode.getText().toString()) {
            case "打开扫描":
            case "关闭扫描":
                if (camera_or_relative) {
                    initFragment();
                    tvShowCode.setText("关闭扫描");
                    camera_or_relative = false;
                } else {
                    //  captureFragment.onDestroyView();
                    CameraManager.get().stopPreview();
                    framelayoutProduct.setVisibility(View.GONE);
                    tvShowCode.setText("打开扫描");
                    camera_or_relative = true;
                }
                break;
            case "查看物料关系":
                Intent intent = new Intent(OrderDetailActivity.this, MaterialRelationActivity.class);
                intent.putExtra("rule_id", resDataBean.getRule_id());
                startActivity(intent);
                break;
        }
    }

    /**
     * 查看位置信息
     */
    @OnClick(R.id.tv_area_look)
    void lookArea(View view) {
        if (click_check == STATE_REQUSIT_RIGISTER) {
            ToastUtils.showCommonToast(OrderDetailActivity.this, "正在跳转");
            Intent intent = new Intent(OrderDetailActivity.this, AreaMessageActivity.class);
            intent.putExtra("img_area", prepare_material_img);
            intent.putExtra("string_area", (String) prepare_material_area_id.getArea_name());
            startActivity(intent);
        } else if (click_check == STATE_START_PRODUCT) {
            linkOneTwo();
            for (int i = 0; i < list.size(); i++) {
                if (StringUtils.doubleToInt(list.get(i).getQuantity_ready() + list.get(i).getQuantity_done())
                        < StringUtils.doubleToInt(list.get(i).getProduct_uom_qty())) {
                    wantNext = "pass";
                    break;
                } else {
                    wantNext = "true";
                }
            }
            if (!StringUtils.isNullOrEmpty(wantNext)) {
                if ("true".equals(wantNext)) {
                    showNext();
                } else if ("pass".equals(wantNext)) {
                    AlertAialogUtils.getCommonDialog(OrderDetailActivity.this, "确定保存备料数量?")
                            .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    showDefultProgressDialog();
                                    HashMap<Object, Object> hashMap = new HashMap<>();
                                    Map[] maps = new Map[resDataBean.getStock_move_lines().size()];
                                    for (int i = 0; i < resDataBean.getStock_move_lines().size(); i++) {
                                        Map<Object, Object> mapSmall = new HashMap<>();
                                        mapSmall.put("stock_move_lines_id", resDataBean.getStock_move_lines().get(i).getId());
                                        mapSmall.put("quantity_ready", resDataBean.getStock_move_lines().get(i).getQuantity_ready());
                                        mapSmall.put("order_id", resDataBean.getStock_move_lines().get(i).getOrder_id());
                                        maps[i] = mapSmall;
                                    }
                                    hashMap.put("stock_moves", maps);
                                    Call<UpdateMessageBean> objectCall = inventoryApi.saveMaterialData(hashMap);
                                    objectCall.enqueue(new MyCallback<UpdateMessageBean>() {
                                        @Override
                                        public void onResponse(Call<UpdateMessageBean> call, Response<UpdateMessageBean> response) {
                                            dismissDefultProgressDialog();
                                            if (response.body() == null) return;
                                            if (response.body().getResult().getRes_code() == 1) {
                                                Intent intent = new Intent(OrderDetailActivity.this, NewDateActivity.class);
                                              //  Intent intent = new Intent(OrderDetailActivity.this, MaterialDetailActivity.class);
                                            //    intent.putExtra("limit", limit);
                                                intent.putExtra("process_id", process_id);
                                                intent.putExtra("state", state);
                                                intent.putExtra("line_id", line_id);
                                                intent.putExtra("origin_sale_id", origin_sale_id);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<UpdateMessageBean> call, Throwable t) {
                                            dismissDefultProgressDialog();
                                            Log.e("OrderDetailActivity", t.toString());
                                        }
                                    });
                                }
                            }).show();
                }
            }
        } else if (STATE_ALREADY_PICKING == click_check) {
            ToastUtils.showCommonToast(OrderDetailActivity.this, "正在跳转。。。");
            Intent intent = new Intent(OrderDetailActivity.this, PhotoAreaActivity.class);
            intent.putExtra("type", state);
            intent.putExtra("order_id", order_id);
       //     intent.putExtra("delay_state", delay_state);
        //    intent.putExtra("limit", limit);
            intent.putExtra("process_id", process_id);
            intent.putExtra("change", false);
            intent.putExtra("state", state);
            intent.putExtra("bean", resDataBean);
            intent.putExtra("line_id", line_id);
            startActivity(intent);
        }
    }

    /**
     * 点击查看BOM结构
     */
    @OnClick(R.id.tv_name_product)
    void bomDetail(View view) {
        Intent intent = new Intent(OrderDetailActivity.this, BomFramworkActivity.class);
        intent.putExtra("order_id", order_id);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        if (resDataBean == null && result == null) {
            getDetail();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        resDataBean = null;
        result = null;
        dismissDefultProgressDialog();
        super.onPause();
    }

    /**
     * 连接设备打印机
     */
    private void initDevice() {
        deviceManager = ConnUtils.getDeviceManager();
        try {
            deviceManager.init(OrderDetailActivity.this, K21_DRIVER_NAME, new NSConnV100ConnParams(), new DeviceEventListener<ConnectionCloseEvent>() {
                @Override
                public void onEvent(ConnectionCloseEvent connectionCloseEvent, Handler handler) {
                    if (connectionCloseEvent.isSuccess()) {
                        ToastUtils.showCommonToast(OrderDetailActivity.this, "设备被客户主动断开！");
                    }
                    if (connectionCloseEvent.isFailed()) {
                        ToastUtils.showCommonToast(OrderDetailActivity.this, "设备链接异常断开！");
                    }
                }

                @Override
                public Handler getUIHandler() {
                    return null;
                }
            });
            deviceManager.connect();
            MyLog.e("OrderDetailActivity", "连接成功");
        } catch (Exception e) {
            e.printStackTrace();
            // ToastUtils.showCommonToast(OrderDetailActivity.this, "链接异常,请检查设备或重新连接.." + e);
        }
        try {
            rfCardModule = (RFCardModule) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_RFCARDREADER);
        } catch (Exception e) {
            Log.e("zws", "error");
        }
    }

    /**
     * 备料完成
     * 展示dialog  后续改的  用于等待生产
     */
    private void showNext() {
        showDefultProgressDialog();
        HashMap<Object, Object> hashMap = new HashMap();
        hashMap.put("order_id", order_id);
        Call<FinishPrepareMaBean> objectCall = inventoryApi.newfinishPrepareMa(hashMap);
        objectCall.enqueue(new MyCallback<FinishPrepareMaBean>() {
            @Override
            public void onResponse(Call<FinishPrepareMaBean> call, Response<FinishPrepareMaBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError() != null) {
                    new TipDialog(OrderDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                    new TipDialog(OrderDetailActivity.this, R.style.MyDialogStyle, "备料成功,点击确定将打印MO单，请等待")
                            .setTrue(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Message message = new Message();
                                    message.what = 1;
                                    mHandler.sendMessage(message);

                                    Intent intent = new Intent(OrderDetailActivity.this, NewDateActivity.class);
                                   // Intent intent = new Intent(OrderDetailActivity.this, MaterialDetailActivity.class);
                                 //   intent.putExtra("limit", limit);
                                    intent.putExtra("process_id", process_id);
                                    intent.putExtra("state", state);
                                    intent.putExtra("line_id", line_id);
                                    intent.putExtra("origin_sale_id", origin_sale_id);
                                    startActivity(intent);
                                    finish();
                                }
                            }).show();
                }
            }

            @Override
            public void onFailure(Call<FinishPrepareMaBean> call, Throwable t) {
                dismissDefultProgressDialog();
                Log.e("zws", t.toString());
                ToastUtils.showCommonToast(OrderDetailActivity.this, t.toString());
            }
        });
        /*AlertAialogUtils.getCommonDialog(OrderDetailActivity.this, "是否确定完成备料，下一步确认物料位置")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            ToastUtils.showCommonToast(OrderDetailActivity.this, "正在跳转。。。");
                            Intent intent = new Intent(OrderDetailActivity.this, PhotoAreaActivity.class);
                            intent.putExtra("type", state);
                            intent.putExtra("order_id", order_id);
                            intent.putExtra("delay_state", delay_state);
                            intent.putExtra("limit", limit);
                            intent.putExtra("process_id", process_id);
                            intent.putExtra("change", false);
                            intent.putExtra("bean", resDataBean);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            ToastUtils.showCommonToast(OrderDetailActivity.this, "It is exception for this activity,please connect manager");
                        }
                    }
                }).show();*/
    }

    @Override
    protected void onDestroy() {
        if (dialogForOrder != null) {
            dialogForOrder = null;
        }
        super.onDestroy();
    }

    /**
     * 初始化扫描模块
     */
    private void initFragment() {
        framelayoutProduct.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        captureFragment = new CaptureFragment();
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        // TODO: 2017/5/24 扫描的回调
        captureFragment.setAnalyzeCallback(new CodeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                Log.e(TAG, "result = " + result);
                if (!isShowDialog) return;
                HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("default_code", result);
                HashMap<Object, Object> objectObjectHashMap1 = new HashMap<>();
                objectObjectHashMap1.put("condition", objectObjectHashMap);
                Call<FindProductByConditionResponse> productByCondition = inventoryApi.findProductByCondition(objectObjectHashMap1);
                showDefultProgressDialog();
                productByCondition.enqueue(new MyCallback<FindProductByConditionResponse>() {
                    @Override
                    public void onResponse(Call<FindProductByConditionResponse> call, Response<FindProductByConditionResponse> response) {
                        dismissDefultProgressDialog();
                        if (response.body() == null) return;
                        if (response.body().getResult().getRes_code() == -1) {
                            ToastUtils.showCommonToast(OrderDetailActivity.this, "产品未找到或者类型不对");
                        } else {
                            String product_name = response.body().getResult().getRes_data().getProduct().getProduct_name();
                            boolean isHave = false;
                            int index = -1;
                            for (int i = 0; i < resDataBean.getStock_move_lines().size(); i++) {
                                if (resDataBean.getStock_move_lines().get(i).getProduct_id().equals(product_name)) {
                                    for (int j = 0; j < list_three.size(); j++) {
                                        if (!product_name.equals(list_three.get(j).getProduct_id())) {
                                            isHave = true;
                                            index = i;
                                        }
                                    }
                                }
                            }
                            if (isHave) {
                                OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean stockMoveLinesBean = resDataBean.getStock_move_lines().get(index);
                                initDialog(stockMoveLinesBean, index, 4);
                            } else {
                                ToastUtils.showCommonToast(OrderDetailActivity.this, "该产品不在单据中或者类型不对");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FindProductByConditionResponse> call, Throwable t) {
                        ToastUtils.showCommonToast(OrderDetailActivity.this, t.toString());
                    }
                });
            }

            @Override
            public void onAnalyzeFailed() {

            }
        });
        fragmentTransaction.replace(R.id.framelayout_product, captureFragment);
        fragmentTransaction.commit();
    }

    //打印
    private void prinPra() {
        initDevice();
        printer = (Printer) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_PRINTER);
        printer.init();
        printer.setLineSpace(1);
        printer.print("\nMO单号：" + order_name + "\n" + "产品: " + tvNameProduct.getText() + "\n" + "时间： " + tvTimeProduct.getText() + "\n" +
                "负责人: " + tvReworkProduct.getText() + "\n" + "生产数量：" + tvNumProduct.getText() + "\n" + "需求数量：" + tvNeedNum.getText()
                + "\n" + "规格：" + tvStringGuige.getText() + "\n" + "工序：" + tvGongxuProduct.getText() + "\n" + "类型：" + tvTypeProduct.getText()
                + "\n" + "MO单备注：" + eidtMoNote.getText() + "\n" + "销售单备注：" + editSaleNote.getText() + "\n", 30, TimeUnit.SECONDS);
        for (int i = 0; i < resDataBean.getStock_move_lines().size(); i++) {
            printer.print("\n产品名称：" + resDataBean.getStock_move_lines().get(i).getProduct_id() + "\n需求数量：" +
                    resDataBean.getStock_move_lines().get(i).getProduct_uom_qty()
                    + "\n备料数量：" + (resDataBean.getStock_move_lines().get(i).getQuantity_ready() + resDataBean.getStock_move_lines().get(i).getQuantity_done()) + "\n", 30, TimeUnit.SECONDS);
        }
        Bitmap mBitmap = CodeUtils.createImage(order_name, 150, 150, null);
        printer.print(0, mBitmap, 30, TimeUnit.SECONDS);
        printer.print("\n\n\n\n\n\n\n", 30, TimeUnit.SECONDS);
    }

}
