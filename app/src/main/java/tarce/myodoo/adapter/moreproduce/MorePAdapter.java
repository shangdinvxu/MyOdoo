package tarce.myodoo.adapter.moreproduce;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newland.mtype.module.common.lcd.Color;

import java.util.List;

import tarce.model.inventory.QcFeedbaskBean;
import tarce.myodoo.R;

/**
 * Created by zouwansheng on 2017/9/26.
 */

public class MorePAdapter extends BaseQuickAdapter<QcFeedbaskBean.ResultBean.ResDataBean.LinesBean, BaseViewHolder> {

    public MorePAdapter(int layoutResId, List<QcFeedbaskBean.ResultBean.ResDataBean.LinesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QcFeedbaskBean.ResultBean.ResDataBean.LinesBean item) {
        helper.setText(R.id.tv_product_id, item.getProduct_name()+"")
                .setText(R.id.tv_position_id, (helper.getPosition()+1)+".");
        if (item.getSuggest_qty() == 0){
            helper.setText(R.id.tv_need_num, "需求数量：");
        }else {
            helper.setText(R.id.tv_need_num, "需求数量："+item.getSuggest_qty());
        }
        if (item.getQty_produced() == 0){
            helper.setText(R.id.tv_produce_num, "生产数量：多产出");
        }else {
            helper.setText(R.id.tv_produce_num, "生产数量："+item.getQty_produced());
        }
        if (item.isBlue()){
            helper.setTextColor(R.id.tv_product_id, android.graphics.Color.BLUE)
                    .setTextColor(R.id.tv_position_id, android.graphics.Color.BLUE)
                    .setTextColor(R.id.tv_produce_num, android.graphics.Color.BLUE)
                    .setTextColor(R.id.tv_need_num, android.graphics.Color.BLUE);
        }else {
            helper.setTextColor(R.id.tv_product_id, android.graphics.Color.BLACK)
                    .setTextColor(R.id.tv_position_id, android.graphics.Color.BLACK)
                    .setTextColor(R.id.tv_produce_num, android.graphics.Color.BLACK)
                    .setTextColor(R.id.tv_need_num, android.graphics.Color.BLACK);
        }
    }
}
