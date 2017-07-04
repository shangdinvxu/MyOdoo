package tarce.myodoo.adapter.inventroy;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.InventroyResultBean;
import tarce.myodoo.R;

/**
 * Created by zouzou on 2017/7/4.
 */

public class InventroyAdapter extends BaseQuickAdapter<InventroyResultBean.ResultBean.ResDataBean, BaseViewHolder>{

    public InventroyAdapter(int layoutResId, List<InventroyResultBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InventroyResultBean.ResultBean.ResDataBean item) {
            helper.setText(R.id.tv_name_inv, item.getName())
                    .setText(R.id.tv_time_inv, item.getDate());
            if (item.getState().equals("done")){
               helper.setText(R.id.tv_already_check, "已验证");
            }
    }
}
