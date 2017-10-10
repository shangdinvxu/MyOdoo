package tarce.myodoo.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.OrderDetailBean;
import tarce.myodoo.R;

/**
 * Created by zouwansheng on 2017/9/22.
 */

public class DoneAdapter extends BaseQuickAdapter<OrderDetailBean.ResultBean.ResDataBean.DoneStockMovBean, BaseViewHolder> {
    private boolean isShow = false;
    public DoneAdapter(int layoutResId, List<OrderDetailBean.ResultBean.ResDataBean.DoneStockMovBean> data, boolean isShow) {
        super(layoutResId, data);
        this.isShow = isShow;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBean.ResultBean.ResDataBean.DoneStockMovBean item) {
        helper.setText(R.id.tv_product_id, item.getProduct_id())
                .setText(R.id.tv_position_id, (helper.getPosition()+1)+".");
        if (item.getQuantity_done_finished() == 0){
            helper.setText(R.id.tv_produce_num, "生产数量：");
        }else {
            helper.setText(R.id.tv_produce_num, "生产数量："+item.getQuantity_done_finished());
        }
        if (item.getSuggest_qty() == null){
            helper.setText(R.id.tv_need_num, "需求数量：多产出");
        }else {
            helper.setText(R.id.tv_need_num, "需求数量："+item.getSuggest_qty());
        }
        if (isShow) {
            helper.setText(R.id.tv_now_num, "本次产出数量："+item.getNow_num());
            helper.getView(R.id.tv_now_num).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.tv_now_num).setVisibility(View.GONE);
        }
    }
}
