package tarce.myodoo.adapter.product;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.OrderDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by zouzou on 2017/6/28.
 * 填写退料
 */

public class WriteFeedAdapter extends BaseQuickAdapter<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean, BaseViewHolder>{

    public WriteFeedAdapter(int layoutResId, List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean item) {
        helper.setText(R.id.name_product_feedback, item.getProduct_id());
        helper.setText(R.id.num_feedback_write, item.getReturn_qty()+"");
        if (item.isNfc()){
            helper.setTextColor(R.id.name_product_feedback, Color.BLUE);
            helper.setTextColor(R.id.num_feedback_write, Color.BLUE);
        }else {
            helper.setTextColor(R.id.name_product_feedback, Color.BLACK);
            helper.setTextColor(R.id.num_feedback_write, Color.BLACK);
        }
    }
}
