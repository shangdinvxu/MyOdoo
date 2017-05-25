package tarce.myodoo.adapter.processproduct;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.AreaMessageBean;
import tarce.myodoo.R;

/**
 * Created by rose.zou on 2017/5/25.
 * 位置信息适配器
 */

public class AreaMessageAdapter extends BaseQuickAdapter<AreaMessageBean.ResultBean.ResDataBean, BaseViewHolder>{
    public AreaMessageAdapter(int layoutResId, List<AreaMessageBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AreaMessageBean.ResultBean.ResDataBean item) {
                helper.setText(R.id.tv_area_message, item.getArea_name());
    }
}
