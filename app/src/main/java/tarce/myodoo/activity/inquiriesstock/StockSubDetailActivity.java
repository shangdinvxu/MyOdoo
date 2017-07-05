package tarce.myodoo.activity.inquiriesstock;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tarce.model.inventory.StockMoveListBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by zouzou on 2017/7/5.
 * 库存查询移动 详细页面
 */

public class StockSubDetailActivity extends BaseActivity {
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_state)
    TextView tvState;
    @InjectView(R.id.tv_num)
    TextView tvNum;
    @InjectView(R.id.tv_prisciption)
    TextView tvPrisciption;
    @InjectView(R.id.from_area)
    TextView fromArea;
    @InjectView(R.id.aim_area)
    TextView aimArea;
    private StockMoveListBean.ResultBean.ResDataBean resDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_subdetail);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        resDataBean = (StockMoveListBean.ResultBean.ResDataBean) intent.getSerializableExtra("bean");
        if (resDataBean!=null){
            initView();
        }
    }

    private void initView() {
        tvName.setText(resDataBean.getProduct_id().getProduct_name());
        switch (resDataBean.getState()){
            case "draft":
                tvState.setText("新建");
                break;
            case "cancel":
                tvState.setText("取消");
                break;
            case "waiting":
                tvState.setText("等待其他移动");
                break;
            case "confirmed":
                tvState.setText("等待可用");
                break;
            case "assigned":
                tvState.setText("可用");
                break;
            case "done":
                tvState.setText("完成");
                break;
        }
        tvNum.setText(StringUtils.doubleToString(resDataBean.getProduct_uom_qty()));
        tvPrisciption.setText(resDataBean.getName());
        fromArea.setText(resDataBean.getLocation());
        aimArea.setText(resDataBean.getLocation_dest());
    }
}
