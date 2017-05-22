package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import tarce.myodoo.R;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/5/22.
 * 生产订单详情页面
 */

public class OrderDetailActivity extends ToolBarActivity {
    @InjectView(R.id.img_up_down)
    ImageView imgUpDown;
    @InjectView(R.id.tv_test)
    TextView tvTest;
    private InventoryApi inventoryApi;
    private int order_id;
    private boolean up_or_down = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 1);
        setTitle(intent.getStringExtra("order_name"));
        getDetail();
    }


    /**
     * 订单详情
     */
    private void getDetail() {
        inventoryApi = RetrofitClient.getInstance(OrderDetailActivity.this).create(InventoryApi.class);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("order_id", order_id);
        Call<Object> orderDetail = inventoryApi.getOrderDetail(hashMap);
        orderDetail.enqueue(new MyCallback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.body() == null) return;
                // TODO: 2017/5/22  分组显示数据

                Log.i("OrderDetailActivity", "==========");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    @OnClick(R.id.img_up_down)
    void onClickImage(View v){
        if (up_or_down){
            imgUpDown.setImageResource(R.mipmap.down);
            tvTest.setVisibility(View.GONE);
            up_or_down = false;
        }else {
            imgUpDown.setImageResource(R.mipmap.up);
            tvTest.setVisibility(View.VISIBLE);
            up_or_down = true;
        }
    }
}
