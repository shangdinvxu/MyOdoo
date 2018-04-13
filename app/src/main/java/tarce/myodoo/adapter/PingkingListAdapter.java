package tarce.myodoo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.GetSaleResponse;
import tarce.myodoo.R;

/**
 * Created by zouwansheng on 2018/4/12.
 */

public class PingkingListAdapter extends BaseQuickAdapter<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids.ReservedPingking, BaseViewHolder> {
    public PingkingListAdapter(int layoutResId, List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids.ReservedPingking> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids.ReservedPingking item) {
        helper.setText(R.id.tv_partner_name, item.getPartner_name())
                .setText(R.id.tv_origin, "源单据: "+item.getOrigin()+"  "+item.getPick_name())
                .setText(R.id.tv_qty_done, "完成: "+ item.getQty_done());
    }
}
