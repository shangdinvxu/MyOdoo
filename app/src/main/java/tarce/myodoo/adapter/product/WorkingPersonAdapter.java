package tarce.myodoo.adapter.product;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.FreeWorkBean;
import tarce.model.inventory.WorkingWorkerBean;
import tarce.myodoo.R;

/**
 * Created by rose.zou on 2017/5/31.
 */

public class WorkingPersonAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
    public WorkingPersonAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_show_woring, item);
    }
}
