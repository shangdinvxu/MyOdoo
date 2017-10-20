package tarce.myodoo.activity.incentroy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tarce.model.inventory.InventroyDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by zouzou on 2017/7/4.
 */

public class InventroyDetailActivity extends BaseActivity {
    @InjectView(R.id.image_invent_detail)
    ImageView imageInventDetail;
    @InjectView(R.id.liaohao)
    TextView liaohao;
    @InjectView(R.id.area)
    TextView area;
    @InjectView(R.id.lilun_num)
    TextView lilunNum;
    @InjectView(R.id.actrul_num)
    TextView actrulNum;
    @InjectView(R.id.product_guige)
    TextView productGuige;
    @InjectView(R.id.product_weight)
    TextView productWeight;
    private InventroyDetailBean.ResultBean.ResDataBean.LineIdsBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventroy_detail);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        bean = (InventroyDetailBean.ResultBean.ResDataBean.LineIdsBean) intent.getSerializableExtra("bean");
        initView();
    }

    private void initView() {
        if (!StringUtils.isNullOrEmpty(bean.getProduct().getImage_medium())) {
            Glide.with(InventroyDetailActivity.this).load(bean.getProduct().getImage_medium());
        }
        liaohao.setText(bean.getProduct().getProduct_name());
        area.setText(String.valueOf(bean.getProduct().getArea().getArea_name()));
        lilunNum.setText(StringUtils.doubleToString(bean.getTheoretical_qty()));
        actrulNum.setText(StringUtils.doubleToString(bean.getProduct_qty()));
        productGuige.setText(bean.getProduct().getProduct_spec());
        productWeight.setText(bean.getProduct().getWeight()+"");
    }
}
