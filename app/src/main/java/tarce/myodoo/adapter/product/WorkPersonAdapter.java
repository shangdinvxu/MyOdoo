package tarce.myodoo.adapter.product;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.FreeWorkBean;
import tarce.myodoo.R;

/**
 * Created by rose.zou on 2017/5/27.
 * 用于发现待工的员工和工作中的员工
 */

public class WorkPersonAdapter extends BaseQuickAdapter<FreeWorkBean.ResultBean.ResDataBean, BaseViewHolder>{
    public WorkPersonAdapter(int layoutResId, List<FreeWorkBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FreeWorkBean.ResultBean.ResDataBean item) {
        helper.setText(R.id.tv_area_message, item.getName());
    }
}
