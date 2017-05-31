package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.inventory.OrderDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.product.OrderDetailAdapter;
import tarce.myodoo.uiutil.DialogForOrder;
import tarce.myodoo.uiutil.FullyLinearLayoutManager;
import tarce.myodoo.utils.StringUtils;
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
        showDefultProgressDialog();
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
                super.onFailure(call, t);
            }
        });
    }

    /**
     * 根据数据赋值显示view
     */
    private void initView() {
        tvNameProduct.setText(resDataBean.getProduct_name());
        tvNumProduct.setText(String.valueOf(new Double(resDataBean.getQty_produced()).intValue()));
        tvNeedNum.setText(String.valueOf(new Double(resDataBean.getProduct_qty()).intValue()));
        tvTimeProduct.setText(resDataBean.getDate_planned_start());
        tvReworkProduct.setText(resDataBean.getIn_charge_name());
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
        adapter = new OrderDetailAdapter(ProductingActivity.this, list_one, "原材料", result);
        adapter_two = new OrderDetailAdapter(ProductingActivity.this, list_two, "半成品", result);
        adapter_three = new OrderDetailAdapter(ProductingActivity.this, list_three, "流转品", result);
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

    /**
     * 初始化dialog并进行相关后续操作
     *
     * @param linesBean
     */
    private void initDialog(final OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean linesBean) {

        if (isShowDialog){
            isShowDialog = false;
            dialogForOrder = new DialogForOrder(ProductingActivity.this, new DialogForOrder.OnSendCommonClickListener() {
                @Override
                public void OnSendCommonClick(int num) {
                    int i = StringUtils.doubleToInt(linesBean.getQty_available());
                    //   int i1 = StringUtils.doubleToInt(linesBean.getQuantity_ready());
                    if (num > i) {
                        ToastUtils.showCommonToast(ProductingActivity.this, "该产品库存不足");
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
        if (!dialogForOrder.isShowing()){//为防止扫描多次弹出多个对话框
            isShowDialog = true;
        }
    }
}
