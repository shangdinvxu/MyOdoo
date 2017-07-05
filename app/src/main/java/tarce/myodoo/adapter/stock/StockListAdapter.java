package tarce.myodoo.adapter.stock;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.StockListBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by zouzou on 2017/7/5.
 */

public class StockListAdapter extends BaseQuickAdapter<StockListBean.ResultBean.ResDataBean, BaseViewHolder>{
    @Override
    public List<StockListBean.ResultBean.ResDataBean> getData() {
        return data;
    }

    public void setData(List<StockListBean.ResultBean.ResDataBean> data) {
        this.data = data;
    }

    private List<StockListBean.ResultBean.ResDataBean> data;
    public StockListAdapter(int layoutResId, List<StockListBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, StockListBean.ResultBean.ResDataBean item) {
        helper.setText(R.id.product, "["+item.getDefault_code()+"]"+item.getProduct_name())
                .setText(R.id.need_in, item.getCateg_id())
                .setText(R.id.need_out, StringUtils.doubleToString(item.getQty_available()))
                .setText(R.id.done, StringUtils.doubleToString(item.getVirtual_available()));
    }
}
