package tarce.myodoo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.myodoo.R;
import tarce.myodoo.bean.MenuBean;

/**
 * Created by zou.zou on 2017/6/22.
 * 收货初始化的适配器
 */

public class TakeDelievAdapter extends BaseQuickAdapter<MenuBean, BaseViewHolder>{
    public TakeDelievAdapter(int layoutResId, List<MenuBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuBean item) {
        helper.setText(R.id.text, item.getName());
        helper.setText(R.id.number, "("+item.getNumber()+")");
    }
}
