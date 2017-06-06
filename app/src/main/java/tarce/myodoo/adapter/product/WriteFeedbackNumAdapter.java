package tarce.myodoo.adapter.product;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.OrderDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by rose.zou on 2017/6/5.
 * 填写退料数量
 */

public class WriteFeedbackNumAdapter extends BaseQuickAdapter<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean, BaseViewHolder>{

    public WriteFeedbackNumAdapter(int layoutResId, List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean item) {
        helper.setText(R.id.name_product_feedback, item.getProduct_id());
        helper.setText(R.id.num_feedback_write, StringUtils.doubleToString(item.getReturn_qty()));
    }
}
