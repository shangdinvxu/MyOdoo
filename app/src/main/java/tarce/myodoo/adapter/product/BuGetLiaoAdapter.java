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
        helper.setText(R.id.num_position, (helper.getPosition()+1)+".");
        helper.setText(R.id.tv_id_product, item.getProduct_id());
        /*if (item.getArea_id()!=null){
            helper.setText(R.id.tv_area_order, item.getArea_id().getArea_name()+"");
        }*/
        if (item.getArea_id()!=null){
            helper.setText(R.id.tv_area_order, "位置："+item.getArea_id().getArea_name());
        }else {
            helper.setText(R.id.tv_area_order, "位置：null");
        }
        helper.setText(R.id.tv_advice_order, "建议："+item.getSuggest_qty());
        helper.setText(R.id.tv_kucun_order, "库存："+item.getQty_available());
        helper.setText(R.id.tv_need_order, "需求："+item.getProduct_uom_qty());
        if (state.equals("waiting_material") || state.equals("prepare_material_ing") || state.equals("finish_prepare_material")){
            helper.setText(R.id.tv_prepare_order, "备料:"+(item.getQuantity_ready()+item.getQuantity_done()));
        }else {
            helper.setText(R.id.tv_prepare_order, "备料:"+item.getQuantity_done());
        }
        helper.setText(R.id.tv_out_num, "补料数量："+item.getOver_picking_qty());
    }
}
