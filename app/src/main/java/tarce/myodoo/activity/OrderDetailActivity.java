package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.inventory.OrderDetailBean;
import tarce.myodoo.R;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/5/22.
 * 生产订单详情页面
 */

public class OrderDetailActivity extends ToolBarActivity {
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
    @InjectView(R.id.linear_two)
    LinearLayout linearTwo;
    @InjectView(R.id.tv_gongxu_product)
    TextView tvGongxuProduct;
    @InjectView(R.id.tv_type_product)
    TextView tvTypeProduct;
    @InjectView(R.id.relative_order_show)
    RelativeLayout relativeOrderShow;
    @InjectView(R.id.recycler_order_detail)
    RecyclerView recyclerOrderDetail;
    private InventoryApi inventoryApi;
    private int order_id;
    private boolean up_or_down = true;
    private OrderDetailBean.ResultBean.ResDataBean resDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 1);
        setTitle(intent.getStringExtra("order_name"));
        setRecyclerview(recyclerOrderDetail);
        getDetail();
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
                if (response.body() == null) return;
                // TODO: 2017/5/22  分组显示数据
                if (response.body().getResult().getRes_code() == 1){
                    resDataBean = response.body().getResult().getRes_data();
                    initView();
                }
                Log.i("OrderDetailActivity", "==========");
            }

            @Override
            public void onFailure(Call<OrderDetailBean> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    /**
     * 根据数据赋值显示view
     * */
    private void initView() {
        tvNameProduct.setText(resDataBean.getDisplay_name());
        tvNumProduct.setText(String.valueOf(new Double(resDataBean.getQty_produced()).intValue()));
        tvNeedNum.setText(String.valueOf(new Double(resDataBean.getProduct_qty()).intValue()));
        tvTimeProduct.setText(resDataBean.getDate_planned_start());
        switch (resDataBean.getState()){
            case "draft":
                tvGongxuProduct.setText("草稿");
                break;
            case "confirmed":
                tvGongxuProduct.setText("已排产");
                break;
            case "waiting_material":
                tvGongxuProduct.setText("等待备料");
                break;
            case "prepare_material_ing":
                tvGongxuProduct.setText("备料中...");
                break;
            case "finish_prepare_material":
                tvGongxuProduct.setText("备料完成");
                break;
            case "already_picking":
                tvGongxuProduct.setText("已领料");
                break;
            case "planned":
                tvGongxuProduct.setText("安排");
                break;
            case "progress":
                tvGongxuProduct.setText("生产中");
                break;
            case "waiting_inspection_finish":
                tvGongxuProduct.setText("等待品检完成");
                break;
            case "waiting_rework":
                tvGongxuProduct.setText("等待返工");
                break;
            case "rework_ing":
                tvGongxuProduct.setText("返工中");
                break;
            case "waiting_inventory_material":
                tvGongxuProduct.setText("等待清点退料");
                break;
            case "waiting_warehouse_inspection":
                tvGongxuProduct.setText("等待检验退料");
                break;
            case "waiting_post_inventory":
                tvGongxuProduct.setText("等待入库");
                break;
            case "done":
                tvGongxuProduct.setText("完成");
                break;
        }
    }

    @OnClick(R.id.img_up_down)
    void onClickImage(View v) {
        if (up_or_down) {
            imgUpDown.setImageResource(R.mipmap.down);
            relativeOrderShow.setVisibility(View.GONE);
            up_or_down = false;
        } else {
            imgUpDown.setImageResource(R.mipmap.up);
            relativeOrderShow.setVisibility(View.VISIBLE);
            up_or_down = true;
        }
    }
}
