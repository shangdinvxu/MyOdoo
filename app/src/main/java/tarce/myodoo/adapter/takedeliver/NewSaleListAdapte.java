package tarce.myodoo.adapter.takedeliver;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.SalesOutListResponse;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by zws on 2017/8/9.
 */

public class NewSaleListAdapte extends BaseQuickAdapter<SalesOutListResponse.TResult.TRes_data, BaseViewHolder>{
    public NewSaleListAdapte(int layoutResId, List<SalesOutListResponse.TResult.TRes_data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SalesOutListResponse.TResult.TRes_data item) {
        if (item.getName()!= null && !StringUtils.isNullOrEmpty(item.getName())){
            helper.setText(R.id.name_note, item.getName());
        }else {
            helper.setText(R.id.name_note, "");
        }
        if (item.getOrigin()!= null && !StringUtils.isNullOrEmpty(item.getOrigin())){
            helper.setText(R.id.name_origin_note, "源单据:"+item.getOrigin());
        }else {
            helper.setText(R.id.name_origin_note, "");
        }
        /*if ()
        confirmed
                partially_available
        assigned*/
        if (!StringUtils.isNullOrEmpty(item.getBack_order_id())){
            helper.getView(R.id.qiandan).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.qiandan).setVisibility(View.GONE);
        }
        if (!StringUtils.isNullOrEmpty(item.getState())){
            helper.setText(R.id.keyong, StringUtils.switchString(item.getState()));
        }else {
            helper.setText(R.id.keyong, "");
        }
    }
}
