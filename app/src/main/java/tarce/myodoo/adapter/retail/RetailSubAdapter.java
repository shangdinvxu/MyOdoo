package tarce.myodoo.adapter.retail;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.RetailSubBean;
import tarce.myodoo.R;

/**
 * Created by zouzou on 2017/7/4.
 */

public class RetailSubAdapter extends BaseQuickAdapter<RetailSubBean.ResultBean.ResDataBean, BaseViewHolder>{
    public RetailSubAdapter(int layoutResId, List<RetailSubBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RetailSubBean.ResultBean.ResDataBean item) {
        helper.setText(R.id.tv_name_retail, item.getName());
    }
}
