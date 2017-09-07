package tarce.myodoo.adapter.projectpick;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.NewSaleListBean;
import tarce.myodoo.R;

/**
 * Created by zouwansheng on 2017/9/6.
 */

public class ProjectPickAdapter extends BaseQuickAdapter<NewSaleListBean.ResultBean.ResDataBean.AbleToDataBean, BaseViewHolder> {
    public ProjectPickAdapter(int layoutResId, List<NewSaleListBean.ResultBean.ResDataBean.AbleToDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewSaleListBean.ResultBean.ResDataBean.AbleToDataBean item) {
        helper.setText(R.id.tv_product_name, item.getName())
        .setText(R.id.tv_name_customer, item.getPartner_id());
    }
}
