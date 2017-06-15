package tarce.myodoo.adapter.processproduct;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.ProcessShowBean;
import tarce.myodoo.R;

/**
 * Created by rose.zou on 2017/5/18.
 */

public class ProcessListAdapter extends BaseQuickAdapter<ProcessShowBean,BaseViewHolder>{


    public ProcessListAdapter(int layoutResId, List<ProcessShowBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProcessShowBean item) {
        helper.setText(R.id.tv_process_name,item.getProcess_name());
        helper.setText(R.id.tv_process_num, "("+item.getProcess_num()+")");

    }
}
