package tarce.myodoo.adapter.product;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.AllEmployeeBean;
import tarce.myodoo.R;

/**
 * Created by zouwansheng on 2018/1/16.
 */

public class AllEmployeeAdapter extends BaseQuickAdapter<AllEmployeeBean.ResultBean.ResDataBean, BaseViewHolder> {
    public AllEmployeeAdapter(int layoutResId, List<AllEmployeeBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllEmployeeBean.ResultBean.ResDataBean item) {
        helper.setText(R.id.textview_name, item.getName());
        int adapterPosition = helper.getAdapterPosition();
        if (item.getPosition() == adapterPosition){
            helper.setBackgroundColor(R.id.linear_list_emploeey, Color.GRAY);
            item.setPosition(-1);
        }else {
            helper.setBackgroundColor(R.id.linear_list_emploeey, Color.WHITE);
        }
    }
}
