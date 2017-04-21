package tarce.myodoo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.GetSaleListResponse;
import tarce.myodoo.R;

/**
 * Created by Daniel.Xu on 2017/4/20.
 */

public class SalesListAdapter extends BaseQuickAdapter<GetSaleListResponse.TResult.TRes_data,BaseViewHolder> {
    public SalesListAdapter(int layoutResId, List<GetSaleListResponse.TResult.TRes_data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetSaleListResponse.TResult.TRes_data item) {
        helper.setText(R.id.name,item.getName())
                .setText(R.id.parnter_id,item.getParnter_id())
                .setText(R.id.origin,item.getOrigin());
    }
}
