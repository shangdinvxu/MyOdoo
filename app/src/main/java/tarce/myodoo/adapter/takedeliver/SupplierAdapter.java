package tarce.myodoo.adapter.takedeliver;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.greendaoBean.SupplierBean;
import tarce.myodoo.R;

/**
 * Created by zouzou on 2017/6/26.
 * 供应商适配器
 */

public class SupplierAdapter extends BaseQuickAdapter<SupplierBean, BaseViewHolder>{

    public SupplierAdapter(int layoutResId, List<SupplierBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SupplierBean item) {
        helper.setText(R.id.customer_name,item.getName());
    }
}
