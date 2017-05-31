package tarce.myodoo.adapter.product;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.OrderDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by rose.zou on 2017/5/31.
 * 补领料适配器
 */

public class BuGetLiaoAdapter extends BaseQuickAdapter<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean, BaseViewHolder>{
    private String state;
    public BuGetLiaoAdapter(int layoutResId, List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> data) {
        super(layoutResId, data);
    }
    public BuGetLiaoAdapter(int layoutResId, List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> data,String state) {
        super(layoutResId, data);
        this.state = state;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean item) {
        helper.setText(R.id.tv_id_product, item.getProduct_id());
        if (item.getArea_id()!=null){
            helper.setText(R.id.tv_area_order, item.getArea_id().getArea_name()+"");
        }
        helper.setText(R.id.tv_advice_order, StringUtils.doubleToString(item.getSuggest_qty()));
        helper.setText(R.id.tv_kucun_order, StringUtils.doubleToString(item.getQty_available()));
        helper.setText(R.id.tv_need_order, StringUtils.doubleToString(item.getProduct_uom_qty()));
        if (state.equals("waiting_material") || state.equals("prepare_material_ing") || state.equals("finish_prepare_material")){
            helper.setText(R.id.tv_prepare_order, StringUtils.doubleToString(item.getQuantity_ready()+item.getQuantity_done()));
        }else {
            helper.setText(R.id.tv_prepare_order, StringUtils.doubleToString(item.getQuantity_done()));
        }
    }
}
