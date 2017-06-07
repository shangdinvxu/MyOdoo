package tarce.myodoo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.inventory.CheckOutProductBean;
import tarce.model.inventory.FinishProductBean;
import tarce.model.inventory.OrderDetailBean;
import tarce.model.inventory.StopProductlineBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.product.OrderDetailAdapter;
import tarce.myodoo.uiutil.DialogForOrder;
import tarce.myodoo.uiutil.FullyLinearLayoutManager;
import tarce.myodoo.uiutil.InsertNumDialog;
import tarce.myodoo.utils.DateTool;
import tarce.myodoo.utils.StringUtils;
import tarce.support.AlertAialogUtils;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/5/31.
 * 生产中详情页面
 */

public class ProductingActivity extends ToolBarActivity {
    @InjectView(R.id.tv_state_order)
    TextView tvStateOrder;
    @InjectView(R.id.img_up_down)
    ImageView imgUpDown;
    @InjectView(R.id.tv_check_state)
    TextView tvCheckState;
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
    @InjectView(R.id.recycler_order_detail)
    RecyclerView recyclerOrderDetail;
    @InjectView(R.id.recycler2_order_detail)
    RecyclerView recycler2OrderDetail;
    @InjectView(R.id.recycler3_order_detail)
    RecyclerView recycler3OrderDetail;
    @InjectView(R.id.tv_start_produce)
    TextView tvStartProduce;
    @InjectView(R.id.tv_add_ll)
    TextView tvAddLl;
    @InjectView(R.id.tv_product_out)
    TextView tvProductOut;
    @InjectView(R.id.tv_person_manage)
    TextView tvPersonManage;
    @InjectView(R.id.tv_line_stop)
    TextView tvLineStop;
    @InjectView(R.id.tv_string_guige)
    TextView tvStringGuige;
    @InjectView(R.id.relative_order_show)
    RelativeLayout relativeOrderShow;
    private int order_id;
    private String state;
    private int limit;
    private String delay_state;
    private int process_id;
    private String name_activity;
    private String state_activity;
    private InventoryApi inventoryApi;
    private OrderDetailBean.ResultBean.ResDataBean.PrepareMaterialAreaIdBean prepare_material_area_id;
    private String prepare_material_img;
    private OrderDetailBean.ResultBean.ResDataBean resDataBean;
    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list_one;
    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list_two;
    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list_three;
    private OrderDetailBean.ResultBean result;
    private OrderDetailAdapter adapter;
    private OrderDetailAdapter adapter_two;
    private OrderDetailAdapter adapter_three;
    private DialogForOrder dialogForOrder;
    private boolean isShowDialog = true;
    private InsertNumDialog insertNumDialog;
    private boolean product_line = true;
    private boolean up_or_down = true;//判断是收起还是展开,默认展开

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producting);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 1);
        setTitle(intent.getStringExtra("order_name"));
        state = intent.getStringExtra("state");
        limit = intent.getIntExtra("limit", 1);
        delay_state = intent.getStringExtra("delay_state");
        process_id = intent.getIntExtra("process_id", 1);
        name_activity = intent.getStringExtra("name_activity");
        state_activity = intent.getStringExtra("state_activity");

        stateView(state);
        recyclerOrderDetail.setLayoutManager(new FullyLinearLayoutManager(ProductingActivity.this));
        recyclerOrderDetail.addItemDecoration(new DividerItemDecoration(ProductingActivity.this,
                DividerItemDecoration.VERTICAL));
        recycler2OrderDetail.setLayoutManager(new FullyLinearLayoutManager(ProductingActivity.this));
        recycler2OrderDetail.addItemDecoration(new DividerItemDecoration(ProductingActivity.this,
                DividerItemDecoration.VERTICAL));
        recycler3OrderDetail.setLayoutManager(new FullyLinearLayoutManager(ProductingActivity.this));
        recycler3OrderDetail.addItemDecoration(new DividerItemDecoration(ProductingActivity.this,
                DividerItemDecoration.VERTICAL));
        recyclerOrderDetail.setNestedScrollingEnabled(false);
        recycler2OrderDetail.setNestedScrollingEnabled(false);
        recycler3OrderDetail.setNestedScrollingEnabled(false);
        showDefultProgressDialog();
        getDetail();
    }

    @Override
    protected void onResume() {
        if (resDataBean == null && result == null){
            getDetail();
        }
        super.onResume();
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
                break;
            case "prepare_material_ing":
                tvStateOrder.setText("备料中");
                tvStartProduce.setText("备料完成");
                break;
            case "finish_prepare_material":
                tvStateOrder.setText("备料完成");
                break;
            case "already_picking":
                tvStateOrder.setText("已领料");
                break;
            case "planned":
                tvStateOrder.setText("安排");
                break;
            case "progress":
                tvStateOrder.setText("进行中");
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
        inventoryApi = RetrofitClient.getInstance(ProductingActivity.this).create(InventoryApi.class);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("order_id", order_id);
        Call<OrderDetailBean> orderDetail = inventoryApi.getOrderDetail(hashMap);
        orderDetail.enqueue(new MyCallback<OrderDetailBean>() {
            @Override
            public void onResponse(Call<OrderDetailBean> call, Response<OrderDetailBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getResult().getRes_code() == 1) {
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
            }
        });
    }

    /**
     * 根据数据赋值显示view
     */
    private void initView(){
        tvNameProduct.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        tvNameProduct.setText(resDataBean.getProduct_name());
        int num_product = new Double(resDataBean.getQty_produced()).intValue();
        tvNumProduct.setText(String.valueOf(num_product));
        if (num_product > 0) {
            tvStartProduce.setVisibility(View.VISIBLE);
        }
        tvNeedNum.setText(String.valueOf(new Double(resDataBean.getProduct_qty()).intValue()));
        tvTimeProduct.setText(DateTool.getGMTBeijing(resDataBean.getDate_planned_start()));
        tvReworkProduct.setText(resDataBean.getIn_charge_name());
        tvStringGuige.setText(String.valueOf(resDataBean.getProduct_id().getProduct_specs()));
        switch (resDataBean.getProduction_order_type()){
            case "stockup":
                tvTypeProduct.setText("备货制");
                break;
            case "ordering":
                tvTypeProduct.setText("订单制");
                break;
        }
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
        adapter = new OrderDetailAdapter(ProductingActivity.this, list_one, "原材料", result);
        adapter_two = new OrderDetailAdapter(ProductingActivity.this, list_two, "半成品", result);
        adapter_three = new OrderDetailAdapter(ProductingActivity.this, list_three, "流转品", result);
        recyclerOrderDetail.setAdapter(adapter);
        recycler2OrderDetail.setAdapter(adapter_two);
        recycler3OrderDetail.setAdapter(adapter_three);
       /* adapter.setOnRecyclerViewItemClickListener(new OrderDetailAdapter.OnRecyclerViewItemClickListener() {
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
        });*/
    }

    /**
     * 点击产出
     */
    @OnClick(R.id.tv_product_out)
    void outProduct(View view) {
        insertNumDialog = new InsertNumDialog(ProductingActivity.this, R.style.MyDialogStyle,
                new InsertNumDialog.OnSendCommonClickListener(){
                    @Override
                    public void OnSendCommonClick(final int num) {
                        for (int i = 0; i < resDataBean.getStock_move_lines().size(); i++) {
                            if ((num + resDataBean.getQty_produced()) / resDataBean.getProduct_qty() * resDataBean.getStock_move_lines().get(i)
                                    .getProduct_uom_qty() <= resDataBean.getStock_move_lines().get(i).getQuantity_done()) {
                                HashMap<Object, Object> hashMap = new HashMap<>();
                                hashMap.put("order_id", order_id);
                                hashMap.put("produce_qty", num);
                                Call<CheckOutProductBean> objectCall = inventoryApi.checkOut(hashMap);
                                objectCall.enqueue(new MyCallback<CheckOutProductBean>() {
                                    @Override
                                    public void onResponse(Call<CheckOutProductBean> call, Response<CheckOutProductBean> response) {
                                        if (response.body() == null) return;
                                        if (response.body().getResult().getRes_code() == 1) {
                                            tvStartProduce.setVisibility(View.VISIBLE);
                                            tvNumProduct.setText(StringUtils.doubleToString(response.body().getResult().getRes_data()
                                                    .getQty_produced()));
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<CheckOutProductBean> call, Throwable t) {
                                        super.onFailure(call, t);
                                    }
                                });
                                /*AlertAialogUtils.getCommonDialog(ProductingActivity.this, "提示")
                                        .setMessage("提交产品成功，等待生产品检")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                HashMap<Object, Object> hashMap = new HashMap<>();
                                                hashMap.put("order_id", order_id);
                                                hashMap.put("produce_qty", num);
                                                Call<CheckOutProductBean> objectCall = inventoryApi.checkOut(hashMap);
                                                objectCall.enqueue(new MyCallback<CheckOutProductBean>() {
                                                    @Override
                                                    public void onResponse(Call<CheckOutProductBean> call, Response<CheckOutProductBean> response) {
                                                        if (response.body() == null) return;
                                                        if (response.body().getResult().getRes_code() == 1) {
                                                            tvStartProduce.setVisibility(View.VISIBLE);
                                                            tvNumProduct.setText(String.valueOf(new Double(response.body().getResult().getRes_data()
                                                                    .getQty_produced()).intValue()));
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<CheckOutProductBean> call, Throwable t) {
                                                        super.onFailure(call, t);
                                                    }
                                                });
                                            }
                                        }).show();*/
                   //             break;
                            } else {
                                AlertAialogUtils.getCommonDialog(ProductingActivity.this, "")
                                        .setMessage(resDataBean.getStock_move_lines().get(i).getProduct_id() + "备料数量不足，请补料")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                                break;
                            }
                        }
                    }
                }, resDataBean.getProduct_name());
        insertNumDialog.show();
    }

    /**
     * 点击补领料
     */
    @OnClick(R.id.tv_add_ll)
    void addLl(View view) {
        Intent intent = new Intent(ProductingActivity.this, BuGetLiaoActivity.class);
        intent.putExtra("value", resDataBean);
        intent.putExtra("state", resDataBean.getState());
        intent.putExtra("order_id", order_id);
        startActivity(intent);
    }

    /**
     * 点击人员管理
     */
    @OnClick(R.id.tv_person_manage)
    void managePerson(View view) {

        Intent intent = new Intent(ProductingActivity.this, AddPersonActivity.class);
        intent.putExtra("order_id", order_id);
        intent.putExtra("state_activity", state_activity);
        intent.putExtra("name_activity", name_activity);
        intent.putExtra("close", true);
        startActivity(intent);
    }

    /**
     * 点击产线暂停
     */
    @OnClick(R.id.tv_line_stop)
    void stopLine(View view) {
        if (product_line){
            AlertAialogUtils.getCommonDialog(ProductingActivity.this, "")
                    .setMessage("是否确定暂停产线")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            stopProductLine("outline", 1);
                        }
                    }).show();
        }else {
            AlertAialogUtils.getCommonDialog(ProductingActivity.this, "")
                    .setMessage("是否确定恢复产线")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            stopProductLine("online", 0);
                        }
                    }).show();
        }
    }

    /**
     * 暂停产线  恢复产线
     * */
    private void stopProductLine(String state, int is_all_pending){
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("order_id",order_id);
        hashMap.put("state",state);
        hashMap.put("is_all_pending", is_all_pending);
        Call<StopProductlineBean> objectCall = inventoryApi.stopProductLine(hashMap);
        objectCall.enqueue(new MyCallback<StopProductlineBean>() {
            @Override
            public void onResponse(Call<StopProductlineBean> call, Response<StopProductlineBean> response) {
                if (response.body() == null)return;
                if (response.body().getResult().getRes_code() == 1){
                        if (product_line){
                            tvLineStop.setText("恢复产线");
                            product_line = false;
                        }else {
                            tvLineStop.setText("产线暂停");
                            product_line = true;
                        }
                }
            }

            @Override
            public void onFailure(Call<StopProductlineBean> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    /**
     * 生产完成
     * */
    @OnClick(R.id.tv_start_produce)
    void finishProduct(View view){
        AlertAialogUtils.getCommonDialog(ProductingActivity.this, "")
                .setMessage("本单共产出"+tvNumProduct.getText().toString()+",请确认")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap<Object,Object> hashMap = new HashMap<>();
                        hashMap.put("order_id", order_id);
                        Call<FinishProductBean> objectCall = inventoryApi.finishProduct(hashMap);
                        objectCall.enqueue(new MyCallback<FinishProductBean>() {
                            @Override
                            public void onResponse(Call<FinishProductBean> call, final Response<FinishProductBean> response) {
                                if (response.body() == null)return;
                                if (response.body().getResult().getRes_code() == 1){
                                    AlertAialogUtils.getCommonDialog(ProductingActivity.this, "")
                                            .setMessage("生产完成，是否拍摄产品位置信息")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(ProductingActivity.this, PhotoAreaActivity.class);
                                                    intent.putExtra("type", state);
                                                    intent.putExtra("order_id", order_id);
                                                    intent.putExtra("delay_state", delay_state);
                                                    intent.putExtra("limit", limit);
                                                    intent.putExtra("process_id", process_id);
                                                    intent.putExtra("change", true);
                                                    startActivity(intent);
                                                }
                                            })
                                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(ProductingActivity.this, ProductLlActivity.class);
                                                    intent.putExtra("name_activity","生产中");
                                                    intent.putExtra("state_product",state);
                                                    startActivity(intent);
                                                }
                                            }).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<FinishProductBean> call, Throwable t) {
                                super.onFailure(call, t);
                            }
                        });
                    }
                }).show();
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

    @OnClick(R.id.tv_name_product)
    void bomDetail(View view){
        Intent intent = new Intent(ProductingActivity.this, BomFramworkActivity.class);
        intent.putExtra("order_id", order_id);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        resDataBean = null;
        result = null;
        super.onPause();
    }
}
