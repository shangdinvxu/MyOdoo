package tarce.myodoo.adapter.stock;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.StockMoveListBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by zouzou on 2017/7/5.
 */

public class StockMoveListAdapter extends BaseQuickAdapter<StockMoveListBean.ResultBean.ResDataBean, BaseViewHolder>{
    @Override
    public List<StockMoveListBean.ResultBean.ResDataBean> getData() {
        return data;
    }

    public void setData(List<StockMoveListBean.ResultBean.ResDataBean> data) {
        this.data = data;
    }

    private List<StockMoveListBean.ResultBean.ResDataBean> data;
    public StockMoveListAdapter(int layoutResId, List<StockMoveListBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, StockMoveListBean.ResultBean.ResDataBean item) {
        helper.setText(R.id.name_tv, item.getName())
                .setText(R.id.num_tv, StringUtils.doubleToString(item.getProduct_uom_qty()))
                .setText(R.id.from_area, item.getLocation())
                .setText(R.id.goal_area, item.getLocation_dest());
    }
}
