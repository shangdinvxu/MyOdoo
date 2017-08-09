package tarce.myodoo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.CustomerSaleBean;
import tarce.myodoo.R;

/**
 * Created by zws on 2017/8/9.
 */

public class NewSaleCustomAdapter extends BaseQuickAdapter<CustomerSaleBean.ResultBean.ResDataBean, BaseViewHolder>{
    public NewSaleCustomAdapter(int layoutResId, List<CustomerSaleBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomerSaleBean.ResultBean.ResDataBean item) {
        if (item.getName()!=null)
        helper.setText(R.id.name_cutt_sale, item.getName());
    }
}
