package tarce.myodoo.adapter.product;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.ReworkRukuBean;

/**
 * Created by rose.zou on 2017/5/22.
 * 等待返工，生产入库适配器
 */

public class ReworkRukuAdapter extends BaseQuickAdapter<ReworkRukuBean.ResultBean.ResDataBean, BaseViewHolder>{
    public ReworkRukuAdapter(int layoutResId, List<ReworkRukuBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReworkRukuBean.ResultBean.ResDataBean item) {

    }
}
