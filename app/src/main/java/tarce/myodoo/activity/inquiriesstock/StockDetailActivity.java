package tarce.myodoo.activity.inquiriesstock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tarce.model.inventory.StockListBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by zouzou on 2017/7/5.
 */

public class StockDetailActivity extends BaseActivity {
    @InjectView(R.id.image_stockdetail)
    ImageView imageStockdetail;
    @InjectView(R.id.tv_product_type)
    TextView tvProductType;
    @InjectView(R.id.tv_consult)
    TextView tvConsult;
    @InjectView(R.id.tv_product_name)
    TextView tvProductName;
    @InjectView(R.id.tv_num)
    TextView tvNum;
    @InjectView(R.id.tv_area)
    TextView tvArea;
    @InjectView(R.id.tv_shortform)
    TextView tvShortform;
    @InjectView(R.id.tv_type_in)
    TextView tvTypeIn;
    @InjectView(R.id.tv_guige)
    TextView tvGuige;
    @InjectView(R.id.tv_in_channel)
    TextView tvInChannel;
    @InjectView(R.id.tv_look_move)
    TextView tvLookMove;
    private StockListBean.ResultBean.ResDataBean resDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockdetail);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        resDataBean = (StockListBean.ResultBean.ResDataBean) intent.getSerializableExtra("bean");
        if (resDataBean != null) {
            initView();
        }
    }

    //初始化试图
    private void initView() {
        if (!StringUtils.isNullOrEmpty(resDataBean.getImage_medium())) {
            Glide.with(StockDetailActivity.this).load(resDataBean.getImage_medium()).into(imageStockdetail);
        }
        switch (resDataBean.getType()) {
            case "product":
                tvProductType.setText("可库存产品");
                break;
            case "consu":
                tvProductType.setText("可消耗");
                break;
            case "service":
                tvProductType.setText("服务");
                break;
        }
        tvConsult.setText(resDataBean.getDefault_code());
        tvProductName.setText(resDataBean.getProduct_name());
        tvNum.setText(resDataBean.getQty_available() + "/" + resDataBean.getVirtual_available());
        tvArea.setText(String.valueOf(resDataBean.getArea_id().getArea_name()));
        tvShortform.setText(String.valueOf(resDataBean.getInner_code()));
        tvTypeIn.setText(String.valueOf(resDataBean.getInner_spec()));
        tvGuige.setText(String.valueOf(resDataBean.getProduct_spec()));
        tvInChannel.setText(resDataBean.getCateg_id());
    }

    @OnClick(R.id.tv_look_move)
    void moveLin(View view) {
        Intent intent = new Intent(StockDetailActivity.this, StockMoveListActivity.class);
        intent.putExtra("product_id", resDataBean.getProduct_id());
        intent.putExtra("name", "[" + resDataBean.getDefault_code() + "] " + resDataBean.getProduct_name());
        startActivity(intent);
    }
}
