package tarce.myodoo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

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
import tarce.model.FindProductByConditionResponse;
import tarce.model.inventory.OrderDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.product.OrderDetailAdapter;
import tarce.myodoo.uiutil.DialogForOrder;
import tarce.myodoo.uiutil.FullyLinearLayoutManager;
import tarce.myodoo.utils.StringUtils;
import tarce.support.AlertAialogUtils;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/5/22.
 * 生产订单详情页面
 */

public class OrderDetailActivity extends ToolBarActivity {
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
    private static final int STATE_WAIT_WATERIAL = 1;
    private static final int STATE_START_PRODUCT = 2;
    private int click_check;//用于底部的点击事件  根据状态加载不同的点击事件后续
    private InventoryApi inventoryApi;
    private int order_id;
    private boolean up_or_down = true;//判断是收起还是展开,默认展开
    private boolean camera_or_relative;//判断是否显示camera,true为显示
    private OrderDetailBean.ResultBean.ResDataBean resDataBean;
    private OrderDetailAdapter adapter;
    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list_one = new ArrayList<>();
    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list_two = new ArrayList<>();
    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list_three = new ArrayList<>();
    private OrderDetailAdapter adapter_two;
    private OrderDetailAdapter adapter_three;
    private FullyLinearLayoutManager fullyLinearLayoutManager;
    private String state;//用于区分加载不同布局
    private DialogForOrder dialogForOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 1);
        setTitle(intent.getStringExtra("order_name"));
        state = intent.getStringExtra("state");
        initFragment();
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
                camera_or_relative = false;
                framelayoutProduct.setVisibility(View.GONE);
                break;
            case "prepare_material_ing":
                tvStateOrder.setText("备料中");
                tvStartProduce.setText("备料完成");
                click_check = STATE_START_PRODUCT;
                camera_or_relative = true;
                framelayoutProduct.setVisibility(View.VISIBLE);
                break;
            case "finish_prepare_material":
                tvStateOrder.setText("备料完成");
                framelayoutProduct.setVisibility(View.VISIBLE);
                break;
            case "already_picking":
                tvStateOrder.setText("已领料");
                break;
            case "planned":
                tvStateOrder.setText("安排");
                break;
            case "progress":
                tvStateOrder.setText("生产中");
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
                break;
            case "waiting_warehouse_inspection":
                tvStateOrder.setText("等待检验退料");
                break;
            case "waiting_post_inventory":
                tvStateOrder.setText("等待入库");
                break;
            case "done":
                tvStateOrder.setText("完成");
                break;
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
                if (response.body() == null) return;
                if (response.body().getResult().getRes_code() == 1) {
                    resDataBean = response.body().getResult().getRes_data();
                    initView();
                }
            }

            @Override
            public void onFailure(Call<OrderDetailBean> call, Throwable t) {
                super.onFailure(call, t);
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
        CaptureFragment captureFragment = new CaptureFragment();
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        // TODO: 2017/5/24 扫描的回调
        captureFragment.setAnalyzeCallback(new CodeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
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
                        super.onFailure(call, t);
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
                }
            }
        }, linesBean);
        dialogForOrder.show();
    }

    /**
     * 根据数据赋值显示view
     */
    private void initView() {
        tvNameProduct.setText(resDataBean.getProduct_name());
        tvNumProduct.setText(String.valueOf(new Double(resDataBean.getQty_produced()).intValue()));
        tvNeedNum.setText(String.valueOf(new Double(resDataBean.getProduct_qty()).intValue()));
        tvTimeProduct.setText(resDataBean.getDate_planned_start());
        tvReworkProduct.setText(resDataBean.getDisplay_name());
        switch (resDataBean.getProduction_order_type()) {
            case "stockup":
                tvTypeProduct.setText("备货制");
                break;
            case "ordering":
                tvTypeProduct.setText("订单制");
                break;
        }
        tvGongxuProduct.setText(resDataBean.getProcess_id().getName());
        for (int i = 0; i < resDataBean.getStock_move_lines().size(); i++) {
            if (resDataBean.getStock_move_lines().get(i).getProduct_type().equals("material")) {
                list_one.add(resDataBean.getStock_move_lines().get(i));
            } else if (resDataBean.getStock_move_lines().get(i).getProduct_type().equals("real_semi_finished")) {
                list_two.add(resDataBean.getStock_move_lines().get(i));
            } else {
                list_three.add(resDataBean.getStock_move_lines().get(i));
            }
        }
        adapter = new OrderDetailAdapter(OrderDetailActivity.this, list_one, "原材料");
        adapter_two = new OrderDetailAdapter(OrderDetailActivity.this, list_two, "半成品");
        adapter_three = new OrderDetailAdapter(OrderDetailActivity.this, list_three, "流转品");
        recyclerOrderDetail.setAdapter(adapter);
        recycler2OrderDetail.setAdapter(adapter_two);
        recycler3OrderDetail.setAdapter(adapter_three);
        adapter.setOnRecyclerViewItemClickListener(new OrderDetailAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean linesBean) {
                initDialog(linesBean);
            }
        });
        adapter_two.setOnRecyclerViewItemClickListener(new OrderDetailAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean linesBean) {
                initDialog(linesBean);
            }
        });
        adapter_three.setOnRecyclerViewItemClickListener(new OrderDetailAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean linesBean) {
                initDialog(linesBean);
            }
        });
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
                                        resDataBean = response.body().getResult().getRes_data();
                                        initView();
                                        stateView("prepare_material_ing");
                                        // framelayoutProduct.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onFailure(Call<OrderDetailBean> call, Throwable t) {
                                        super.onFailure(call, t);
                                    }
                                });
                            }
                        }).show();
                break;
            case STATE_START_PRODUCT:
                AlertAialogUtils.getCommonDialog(OrderDetailActivity.this,"是否确定完成备料，下一步确认物料位置")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(OrderDetailActivity.this, PhotoAreaActivity.class);
                                //intent.putExtra()
                                startActivity(intent);
                            }
                        }).show();
                break;
        }
    }

    /**
     * 收起，展开
     */
    @OnClick(R.id.img_up_down)
    void onClickImage(View v) {
        if (up_or_down) {
            if (camera_or_relative) {
                framelayoutProduct.setVisibility(View.GONE);
            } else {
                relativeOrderShow.setVisibility(View.GONE);
            }
            tvCheckState.setText("展开");
            imgUpDown.setImageResource(R.mipmap.down);
            up_or_down = false;
        } else {
            if (camera_or_relative) {
                framelayoutProduct.setVisibility(View.VISIBLE);
            } else {
                relativeOrderShow.setVisibility(View.VISIBLE);
            }
            tvCheckState.setText("收起");
            imgUpDown.setImageResource(R.mipmap.up);
            up_or_down = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialogForOrder != null) {
            dialogForOrder = null;
        }
    }
}
