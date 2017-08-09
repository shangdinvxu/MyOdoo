package tarce.myodoo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.NewSaleBean;
import tarce.myodoo.R;
import tarce.myodoo.bean.AvailabilityBean;
import tarce.support.MyLog;

/**
 * Created by zws on 2017/8/9.
 */

public class SalesStatesAdapter extends BaseQuickAdapter<NewSaleBean.ResultBean.ResDataBean,BaseViewHolder> {

    public SalesStatesAdapter(int layoutResId, List<NewSaleBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, NewSaleBean.ResultBean.ResDataBean item) {
        if (item.getName()!=null)
        helper.setText(R.id.title_sale_team, item.getName());
    }
}
