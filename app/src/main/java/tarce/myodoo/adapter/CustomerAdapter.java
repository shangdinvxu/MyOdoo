package tarce.myodoo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.greendaoBean.ContactsBean;
import tarce.myodoo.R;

/**
 * Created by Daniel.Xu on 2017/4/20.
 */

public class CustomerAdapter extends BaseQuickAdapter<ContactsBean,BaseViewHolder> {
    public CustomerAdapter(int layoutResId, List<ContactsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactsBean item) {
            helper.setText(R.id.customer_name,item.getName());
    }
}
