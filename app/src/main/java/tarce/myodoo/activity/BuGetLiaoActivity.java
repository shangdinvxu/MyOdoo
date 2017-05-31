package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tarce.model.inventory.OrderDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.product.BuGetLiaoAdapter;
import tarce.myodoo.adapter.product.OrderDetailAdapter;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/5/31.
 * 补领料页面
 */

public class BuGetLiaoActivity extends ToolBarActivity {
    @InjectView(R.id.recycler_bu_getliao)
    RecyclerView recyclerBuGetliao;

    private OrderDetailBean.ResultBean.ResDataBean resDataBean;
    private BuGetLiaoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bu_getliao);
        ButterKnife.inject(this);

        setTitle("补领料");
        setRecyclerview(recyclerBuGetliao);
        Intent intent = getIntent();
        resDataBean = (OrderDetailBean.ResultBean.ResDataBean) intent.getSerializableExtra("value");
        String state = intent.getStringExtra("state");
        adapter = new BuGetLiaoAdapter(R.layout.order_detail_body, resDataBean.getStock_move_lines(), state);
        recyclerBuGetliao.setAdapter(adapter);
    }
}
