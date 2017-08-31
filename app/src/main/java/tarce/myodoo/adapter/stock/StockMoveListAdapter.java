package tarce.myodoo.adapter.stock;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.StockMoveListBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.DateTool;
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
        helper.setText(R.id.position_tv, (helper.getPosition()+1)+".")
        .setText(R.id.tv_type, StringUtils.typeSwitch(item.getMove_order_type()))
        .setText(R.id.name_and_time, item.getWrite_uid()+" "+ DateTool.getGMTBeijing(item.getWrite_date()))
        .setText(R.id.state_tv, StringUtils.switchString(item.getState()))
        .setText(R.id.tv_argument, "说明："+item.getProduct_id().getProduct_name())
        .setText(R.id.origin_tv, "源单据: "+item.getName())
        .setText(R.id.tv_out_in, "出入库数量: "+item.getProduct_uom_qty())
        .setText(R.id.tv_reference, "参考："+item.getPicking_id())
        .setText(R.id.tv_num_now, "当前库存："+item.getQuantity_adjusted_qty());
//        helper.setText(R.id.name_tv, item.getName())
//                .setText(R.id.num_tv, StringUtils.doubleToString(item.getProduct_uom_qty()))
//                .setText(R.id.from_area, item.getLocation())
//                .setText(R.id.goal_area, item.getLocation_dest());
    }
}
