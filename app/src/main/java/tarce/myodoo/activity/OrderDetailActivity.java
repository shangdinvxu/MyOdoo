package tarce.myodoo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.newland.mtypex.nseries.NSConnV100ConnParams;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.camera.CameraManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.FindProductByConditionResponse;
import tarce.model.inventory.CommonBean;
import tarce.model.inventory.GetFactroyRemarkBean;
import tarce.model.inventory.OrderDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.product.OrderDetailAdapter;
import tarce.myodoo.device.AbstractDevice;
import tarce.myodoo.uiutil.DialogForOrder;
import tarce.myodoo.uiutil.FullyLinearLayoutManager;
import tarce.myodoo.uiutil.InsertFeedbackDial;
import tarce.myodoo.utils.StringUtils;
import tarce.myodoo.utils.UserManager;
import tarce.support.AlertAialogUtils;
import tarce.support.MyLog;
import tarce.support.TimeUtils;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/5/22.
 * 生产订单详情页面
 */

public class OrderDetailActivity extends ToolBarActivity {
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
    private String state_activity;
    private OrderDetailBean.ResultBean result;
    private boolean isShowDialog = true;//用于判断是否已经显示了dialog，为了防止扫描时候的连续弹出
    private String scanResult = "";
    private CaptureFragment captureFragment;
    private long mExitTime;
    private Printer printer;
    private DeviceManager deviceManager;
    private String order_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 1);
        order_name = intent.getStringExtra("order_name");
        setTitle(order_name);
        state = intent.getStringExtra("state");
        limit = intent.getIntExtra("limit", 1);
        delay_state = intent.getStringExtra("delay_state");
        process_id = intent.getIntExtra("process_id", 1);
        name_activity = intent.getStringExtra("name_activity");
        state_activity = intent.getStringExtra("state_activity");
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
        recyclerOrderDetail.setNestedScrollingEnabled(false);
        recycler2OrderDetail.setNestedScrollingEnabled(false);
        recycler3OrderDetail.setNestedScrollingEnabled(false);
        getDetail();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (state.equals("waiting_material") || state.equals("prepare_material_ing")){
            menu.getItem(2).setTitle("备料反馈");
        }else {
            menu.getItem(2).setTitle("生产反馈");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings){
            HashMap<Object, Object> hashMap = new HashMap<>();
            hashMap.put("order_id", order_id);
            Call<GetFactroyRemarkBean> factroyRemark = inventoryApi.getFactroyRemark(hashMap);
            factroyRemark.enqueue(new MyCallback<GetFactroyRemarkBean>() {
                @Override
                public void onResponse(Call<GetFactroyRemarkBean> call, Response<GetFactroyRemarkBean> response) {
                    if (response == null || response.body().getResult() == null)return;
                    if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data()!=null){
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
                                        if (response == null)return;
                                        try {
                                            if (response.body().getResult().getRes_code() == 1){
                                                ToastUtils.showCommonToast(OrderDetailActivity.this, "反馈成功");
                                            }
                                        }catch (Exception e){
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
        }else if (item.getItemId() == R.id.action_print){
            AlertAialogUtils.getCommonDialog(OrderDetailActivity.this, "是否确认打印？\n(请尽量避免订单重复打印)")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            prinPra();
                        }
                    })
                    .show();
        }else if (item.getItemId() == R.id.action_feedback){
            Intent intent = new Intent(OrderDetailActivity.this, FeedbackActivity.class);
            intent.putExtra("state", state);
            intent.putExtra("order_id", order_id);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    //打印
    private void prinPra() {
        initDevice();
        printer = (Printer) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_PRINTER);
        printer.init();
        printer.print("\n\nMO单号："+order_name+"\n\n"+"产品: " + tvNameProduct.getText() + "\n\n" + "时间： " + tvTimeProduct.getText() + "\n\n" +
                "负责人: " + tvReworkProduct.getText() + "\n\n" + "生产数量：" + tvNumProduct.getText() + "\n\n" + "需求数量：" + tvNeedNum.getText()
                + "\n\n" + "规格：" + tvStringGuige.getText() + "\n\n" + "工序：" + tvGongxuProduct.getText() + "\n\n" + "类型：" + tvTypeProduct.getText()
                + "\n\n" + "MO单备注："+eidtMoNote.getText()+"\n\n"+"销售单备注："+editSaleNote.getText()+"\n\n", 30, TimeUnit.SECONDS);
        Bitmap mBitmap = CodeUtils.createImage(order_name, 300, 300, null);
        printer.print(0, mBitmap, 30, TimeUnit.SECONDS);
        printer.print("\n\n\n\n\n\n\n\n\n\n", 30, TimeUnit.SECONDS);
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
                tvStateOrder.setText("等待备料");
                tvStartProduce.setText("开始备料");
                click_check = STATE_WAIT_WATERIAL;
                tvShowCode.setVisibility(View.GONE);
                showLinThreeCang();
                break;
            case "prepare_material_ing":
                tvStateOrder.setText("备料中");
                tvStartProduce.setText("备料完成");
                click_check = STATE_START_PRODUCT;
                relativeOrderShow.setVisibility(View.GONE);
                tvCheckState.setText("展开");
                imgUpDown.setImageResource(R.mipmap.down);
                up_or_down = false;
                showLinThreeCang();
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
                tvStateOrder.setText("已领料");
                click_check = STATE_ALREADY_PICKING;
                tvShowCode.setVisibility(View.GONE);
                relativeOrderShow.setVisibility(View.VISIBLE);
                tvCheckState.setText("收起");
                imgUpDown.setImageResource(R.mipmap.up);
                up_or_down = true;
                tvStartProduce.setText("开始生产");
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
            case "waiting_inventory_material":
                tvStateOrder.setText("等待清点退料");
                tvShowCode.setVisibility(View.GONE);
                tvStartProduce.setText("填写退料");
                click_check = WRITE_WATERIAL_OUT;
                showLinThreePro();
                break;
            case "waiting_warehouse_inspection":
                tvStateOrder.setText("等待检验退料");
                tvShowCode.setVisibility(View.GONE);
                tvStartProduce.setText("仓库查看退料信息");
                click_check = LOOK_MESSAGE_FEEDBACK;
                showLinThreeCang();
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
        }catch (Exception e){
            ToastUtils.showCommonToast(OrderDetailActivity.this, e.toString());
        }
    }

    /**
     * 是否显示底部(仓库)
     */
    public void showLinThreeCang() {
        try {
            if (!UserManager.getSingleton().getGrops().contains("group_charge_warehouse")) {
                linearThree.setVisibility(View.GONE);
            }
        }catch (Exception e){
            ToastUtils.showCommonToast(OrderDetailActivity.this, e.toString());
        }
    }

    /**
     * 订单详情
     */
    private void getDetail() {
        showDefultProgressDialog();
        inventoryApi = RetrofitClient.getInstance(OrderDetailActivity.this).create(InventoryApi.class);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("order_id", order_id);
        Call<OrderDetailBean> orderDetail = inventoryApi.getOrderDetail(hashMap);
        orderDetail.enqueue(new MyCallback<OrderDetailBean>() {
            @Override
            public void onResponse(Call<OrderDetailBean> call, Response<OrderDetailBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null || response.body().getResult() == null) return;
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data()!=null) {
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
                ToastUtils.showCommonToast(OrderDetailActivity.this, t.toString());
            }
        });
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
                                initDialog(stockMoveLinesBean);
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

    /**
     * 初始化dialog并进行相关后续操作
     *
     * @param linesBean
     */
    private void initDialog(final OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean linesBean) {

        if (isShowDialog) {
            isShowDialog = false;
            dialogForOrder = new DialogForOrder(OrderDetailActivity.this, new DialogForOrder.OnSendCommonClickListener() {
                @Override
                public void OnSendCommonClick(int num) {
                    int i = StringUtils.doubleToInt(linesBean.getQty_available());
                    //   int i1 = StringUtils.doubleToInt(linesBean.getQuantity_ready());
                    if (num > i) {
                        ToastUtils.showCommonToast(OrderDetailActivity.this, "该产品库存不足");
                    } else {
                        linesBean.setQuantity_ready((double) num);
                        adapter.notifyDataSetChanged();
                        adapter_two.notifyDataSetChanged();
                        adapter_three.notifyDataSetChanged();
                    }
                }
            }, linesBean);
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
        tvNameProduct.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvNameProduct.setText(resDataBean.getProduct_name());
        tvNumProduct.setText(StringUtils.doubleToString(resDataBean.getQty_produced()));
        tvNeedNum.setText(StringUtils.doubleToString(resDataBean.getProduct_qty()));
        tvTimeProduct.setText(TimeUtils.utc2Local(resDataBean.getDate_planned_start()));
        tvReworkProduct.setText(resDataBean.getIn_charge_name());
        tvStringGuige.setText(String.valueOf(resDataBean.getProduct_id().getProduct_specs()));
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
        if (state.equals("prepare_material_ing")) {
            adapter.setOnRecyclerViewItemClickListener(new OrderDetailAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean linesBean) {
                    isShowDialog = true;
                    initDialog(linesBean);
                }
            });
            adapter_two.setOnRecyclerViewItemClickListener(new OrderDetailAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean linesBean) {
                    isShowDialog = true;
                    initDialog(linesBean);
                }
            });
        }
        if (state.equals("finish_prepare_material")) {
            adapter_three.setOnRecyclerViewItemClickListener(new OrderDetailAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean linesBean) {
                    isShowDialog = true;
                    initDialog(linesBean);
                }
            });
        }
    }

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
                                        if (response.body() == null) return;
                                        if (response.body().getError() != null){
                                            ToastUtils.showCommonToast(OrderDetailActivity.this, response.body().getError().getMessage());
                                            return;
                                        }
                                        if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data()!=null){
                                            resDataBean = response.body().getResult().getRes_data();
                                            initView();
                                            state = "prepare_material_ing";
                                            stateView("prepare_material_ing");
                                            adapter.setOnRecyclerViewItemClickListener(new OrderDetailAdapter.OnRecyclerViewItemClickListener() {
                                                @Override
                                                public void onItemClick(View view, OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean linesBean) {
                                                    isShowDialog = true;
                                                    initDialog(linesBean);
                                                }
                                            });
                                            adapter_two.setOnRecyclerViewItemClickListener(new OrderDetailAdapter.OnRecyclerViewItemClickListener() {
                                                @Override
                                                public void onItemClick(View view, OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean linesBean) {
                                                    isShowDialog = true;
                                                    initDialog(linesBean);
                                                }
                                            });
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
            case STATE_START_PRODUCT:
                showNext();
                break;
            case STATE_REQUSIT_RIGISTER:
                /**
                 * 判断流转品列表，逻辑为：备料数量至少要大于等于1，不然不能领料登记，而是提醒去修改备料数量，这时候 ，原材料和半成品是不能点击的
                 * */
                boolean next = true;
                int index = -1;
                try {
                    for (int i = 0; i < list_three.size(); i++) {
                        if (StringUtils.doubleToInt(list_three.get(i).getQuantity_ready()) < 1) {
                            next = false;
                            index = i;
                        }
                    }
                }catch (Exception e){
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
                                            if (response.body() == null || response.body().getResult() == null) return;
                                            if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data()!=null) {
                                                resDataBean = response.body().getResult().getRes_data();
                                                initView();
                                                stateView("already_picking");
                                                AlertAialogUtils.getCommonDialog(OrderDetailActivity.this, "").setMessage("领料完成")
                                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Intent intent = new Intent(OrderDetailActivity.this, ProductLlActivity.class);
                                                                intent.putExtra("name_activity", name_activity);
                                                                intent.putExtra("state_activity", state_activity);
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
                    intent.putExtra("state_activity", state_activity);
                    intent.putExtra("name_activity", name_activity);
                    intent.putExtra("close", false);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    ToastUtils.showCommonToast(OrderDetailActivity.this, e.toString());
            }
                break;
            case WRITE_WATERIAL_OUT://填写退料
                try {
                    Intent intent1 = new Intent(OrderDetailActivity.this, WriteFeedMateriActivity.class);
                    intent1.putExtra("recycler_data", resDataBean);
                    intent1.putExtra("order_id", order_id);
                    intent1.putExtra("from", "write");
                    startActivity(intent1);
                }catch (Exception e){
                    ToastUtils.showCommonToast(OrderDetailActivity.this, e.toString());
                }
                break;
            case LOOK_MESSAGE_FEEDBACK:
                try {
                    Intent intent2 = new Intent(OrderDetailActivity.this, WriteFeedMateriActivity.class);
                    intent2.putExtra("recycler_data", resDataBean);
                    intent2.putExtra("order_id", order_id);
                    intent2.putExtra("from", "look");
                    startActivity(intent2);
                }catch (Exception e){
                    ToastUtils.showCommonToast(OrderDetailActivity.this, e.toString());
                }
                break;
            case CHECK_MATERIAL_RETURN:
                try {
                    Intent intent3 = new Intent(OrderDetailActivity.this, WriteFeedMateriActivity.class);
                    intent3.putExtra("recycler_data", resDataBean);
                    intent3.putExtra("order_id", order_id);
                    intent3.putExtra("from", "check");
                    startActivity(intent3);
                }catch (Exception e){
                    ToastUtils.showCommonToast(OrderDetailActivity.this, e.toString());
                }
                break;
        }
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
    }

    /**
     * 查看位置信息
     */
    @OnClick(R.id.tv_area_look)
    void lookArea(View view) {
        ToastUtils.showCommonToast(OrderDetailActivity.this, "正在跳转");
        Intent intent = new Intent(OrderDetailActivity.this, AreaMessageActivity.class);
        intent.putExtra("img_area", prepare_material_img);
        intent.putExtra("string_area", (String) prepare_material_area_id.getArea_name());
        startActivity(intent);
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
    protected void onPause() {
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
            ToastUtils.showCommonToast(OrderDetailActivity.this, "链接异常,请检查设备或重新连接.." + e);
        }
    }

    /**
     * 展示dialog  后续改的  用于等待生产
     * */
    private void showNext(){
        AlertAialogUtils.getCommonDialog(OrderDetailActivity.this, "是否确定完成备料，下一步确认物料位置")
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
                        }catch (Exception e){
                            ToastUtils.showCommonToast(OrderDetailActivity.this, "It is exception for this activity,please connect manager");
                        }
                    }
                }).show();
    }
    @Override
    protected void onDestroy() {
        if (dialogForOrder != null) {
            dialogForOrder = null;
        }
        super.onDestroy();
    }
}
