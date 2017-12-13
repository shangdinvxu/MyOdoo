package tarce.myodoo.adapter.product;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.GetProcessBean;
import tarce.myodoo.R;

/**
 * Created by zouwansheng on 2017/11/16.
 */

public class NewProcessAdapter extends BaseQuickAdapter<GetProcessBean.TestRSubBean.ListSubBean, BaseViewHolder> {
    public NewProcessAdapter(int layoutResId, List<GetProcessBean.TestRSubBean.ListSubBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetProcessBean.TestRSubBean.ListSubBean item) {
        helper.setText(R.id.tv_gongxu_grid, item.getName()+"");
    }
}
