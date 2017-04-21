package tarce.myodoo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.GetSaleResponse;
import tarce.myodoo.R;

/**
 * Created by Daniel.Xu on 2017/4/21.
 */

public class SalesDetailAdapter extends BaseQuickAdapter<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids,BaseViewHolder> {
    public SalesDetailAdapter(int layoutResId, List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids item) {
            helper.setText(R.id.product,item.getProduct_id().getName())
                    .setText(R.id.need_in,item.getProduct_id().getArea_id().getArea_name())
                    .setText(R.id.inventory,item.getProduct_id().getQty_available()+"")
                    .setText(R.id.need_out,item.getProduct_qty()+"")
                    .setText(R.id.done,item.getQty_done()+"");
    }
}
