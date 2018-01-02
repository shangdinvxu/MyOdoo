package tarce.myodoo.adapter.newproduct;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.SoQcBean;
import tarce.myodoo.R;

/**
 * Created by zouwansheng on 2017/12/21.
 */

public class ForInspecSoAdapter extends BaseQuickAdapter<SoQcBean.ResultBean.ResDataBean, BaseViewHolder> {
    public ForInspecSoAdapter(int layoutResId, List<SoQcBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SoQcBean.ResultBean.ResDataBean item) {
        helper.setText(R.id.tv_so_origin, item.getSoname());
        helper.setText(R.id.tv_so_count, item.getFeedback().size()+"");
    }
}
