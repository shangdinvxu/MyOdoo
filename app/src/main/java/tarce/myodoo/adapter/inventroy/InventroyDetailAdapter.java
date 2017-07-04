package tarce.myodoo.adapter.inventroy;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.InventroyDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by zouzou on 2017/7/4.
 */

public class InventroyDetailAdapter extends BaseQuickAdapter<InventroyDetailBean.ResultBean.ResDataBean.LineIdsBean, BaseViewHolder>{
    public InventroyDetailAdapter(int layoutResId, List<InventroyDetailBean.ResultBean.ResDataBean.LineIdsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InventroyDetailBean.ResultBean.ResDataBean.LineIdsBean item) {
            helper.setText(R.id.product, item.getProduct().getProduct_name())
            .setText(R.id.need_in, String.valueOf(item.getProduct().getArea().getArea_name()))
            .setText(R.id.need_out, StringUtils.doubleToString(item.getTheoretical_qty()))
            .setText(R.id.done, StringUtils.doubleToString(item.getProduct_qty()));
    }
}
