package tarce.myodoo.adapter.projectpick;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.ProjectBean;
import tarce.model.inventory.NewSaleListBean;
import tarce.myodoo.R;

/**
 * Created by zouwansheng on 2017/9/6.
 */

public class ProjectPickAdapter extends BaseQuickAdapter<ProjectBean.ResultBean.ResDataBean.WaitingDataBean, BaseViewHolder> {
    public ProjectPickAdapter(int layoutResId, List<ProjectBean.ResultBean.ResDataBean.WaitingDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectBean.ResultBean.ResDataBean.WaitingDataBean item) {
        helper.setText(R.id.tv_product_name, item.getName())
        .setText(R.id.tv_name_customer, item.getCreate_uid())
        .setText(R.id.note_date, "单据日期："+item.getCreate_date())
        .setText(R.id.put_date, "交货日期: "+item.getDelivery_date());
    }
}
