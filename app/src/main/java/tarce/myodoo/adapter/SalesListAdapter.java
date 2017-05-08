package tarce.myodoo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Vector;

import tarce.model.GetSaleListResponse;
import tarce.model.inventory.SalesOutListResponse;
import tarce.myodoo.R;

/**
 * Created by Daniel.Xu on 2017/4/20.
 */

public class SalesListAdapter extends BaseQuickAdapter<SalesOutListResponse.TResult.TRes_data,BaseViewHolder> {
    public SalesListAdapter(int layoutResId, List<SalesOutListResponse.TResult.TRes_data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SalesOutListResponse.TResult.TRes_data item) {
        helper.setText(R.id.sale_number,item.getOrigin())
                .setText(R.id.userable,item.getComplete_rate()+"%");
        if (item.getParnter_id().contains(",")){
            String parnter_id = item.getParnter_id();
            String[] split = parnter_id.split(",");
            helper.setText(R.id.parnter_name,split[0]);
        }else {
            helper.setText(R.id.parnter_name,item.getParnter_id());
        }
        if (item.getHas_attachment()){
            helper.setVisible(R.id.imageview, true)
                    .setImageResource(R.id.imageview,R.mipmap.fujian);
        }
        if (item.getState().equals("assigned")||item.getState().equals("partially_available")){
            helper.setText(R.id.reserved,"是");
        }else {
            helper.setText(R.id.reserved,"否");
        }

    }
}
