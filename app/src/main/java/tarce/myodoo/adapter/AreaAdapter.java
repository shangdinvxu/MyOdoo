package tarce.myodoo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.AreaMessageBean;
import tarce.myodoo.R;

/**
 * Created by zouwansheng on 2017/8/29.
 */

public class AreaAdapter extends BaseQuickAdapter<AreaMessageBean.ResultBean.ResDataBean, BaseViewHolder> {
    public AreaAdapter(int layoutResId, List<AreaMessageBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AreaMessageBean.ResultBean.ResDataBean item) {
        helper.setText(R.id.china_tv, item.getArea_name());
    }
}
