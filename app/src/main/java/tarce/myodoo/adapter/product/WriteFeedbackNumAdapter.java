package tarce.myodoo.adapter.product;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.GetReturnMaterBean;
import tarce.model.inventory.OrderDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by rose.zou on 2017/6/5.
 * 填写退料数量
 */

public class WriteFeedbackNumAdapter extends BaseQuickAdapter<GetReturnMaterBean.ResultBean.ResDataBean, BaseViewHolder>{

    public WriteFeedbackNumAdapter(int layoutResId, List<GetReturnMaterBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetReturnMaterBean.ResultBean.ResDataBean item) {
        helper.setText(R.id.name_product_feedback, item.getProduct_id());
        helper.setText(R.id.num_feedback_write, StringUtils.doubleToString(item.getReturn_qty()));
        if (item.isNfc()){
            helper.setTextColor(R.id.name_product_feedback, Color.BLUE);
            helper.setTextColor(R.id.num_feedback_write, Color.BLUE);
        }else {
            helper.setTextColor(R.id.name_product_feedback, Color.BLACK);
            helper.setTextColor(R.id.num_feedback_write, Color.BLACK);
        }
    }
}
