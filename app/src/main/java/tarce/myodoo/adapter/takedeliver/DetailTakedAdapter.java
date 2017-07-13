package tarce.myodoo.adapter.takedeliver;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.TakeDelListBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by zouzou on 2017/6/23.
 * 收货详情页的recycler列表
 */

public class DetailTakedAdapter extends BaseQuickAdapter<TakeDelListBean.ResultBean.ResDataBean.PackOperationProductIdsBean, BaseViewHolder>{

    public DetailTakedAdapter(int layoutResId, List<TakeDelListBean.ResultBean.ResDataBean.PackOperationProductIdsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TakeDelListBean.ResultBean.ResDataBean.PackOperationProductIdsBean item) {
        helper.setText(R.id.product,item.getProduct_id().getName())
                .setText(R.id.need_out, StringUtils.doubleToString(item.getProduct_qty()))
                .setText(R.id.done, StringUtils.doubleToString(item.getQty_done()));
        if (item.getProduct_id().getArea_id().getArea_name() == null){
            helper.setText(R.id.need_in,"");
        }else {
            helper.setText(R.id.need_in,item.getProduct_id().getArea_id().getArea_name()+"");
        }
        if (item.getPack_id() == -1){
            helper.setTextColor(R.id.product, Color.GRAY)
                    .setTextColor(R.id.need_out, Color.GRAY)
                    .setTextColor(R.id.done, Color.GRAY)
                    .setTextColor(R.id.need_in, Color.GRAY);
        }else {
            helper.setTextColor(R.id.product, Color.BLACK)
                    .setTextColor(R.id.need_out, Color.BLACK)
                    .setTextColor(R.id.done, Color.BLACK)
                    .setTextColor(R.id.need_in, Color.BLACK);
        }
    }
}
