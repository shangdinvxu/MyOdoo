package tarce.myodoo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.ByteArrayOutputStream;
import java.util.List;

import tarce.model.inventory.DiyListBean;
import tarce.myodoo.R;

/**
 * Created by zouwansheng on 2017/8/30.
 */

public class AddCuAdapter extends BaseQuickAdapter<DiyListBean, BaseViewHolder> {
    public AddCuAdapter(int layoutResId, List<DiyListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiyListBean item) {
        helper.setText(R.id.tv_num, (helper.getPosition()+1)+".")
                .setText(R.id.product, item.getProduct().getProduct_name())
                .setText(R.id.need_in, item.getProduct().getArea().getArea_name())
                .setText(R.id.need_out, item.getTheoretical_qty()+"")
                .setText(R.id.done, item.getProduct_qty()+"");
    }
}
