package tarce.myodoo.adapter.processproduct;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.ProcessShowBean;
import tarce.myodoo.R;

/**
 * Created by rose.zou on 2017/5/19.
 * 生产工序详细情况适配器
 */

public class ProcessDetailAdapter extends BaseQuickAdapter<ProcessShowBean, BaseViewHolder>{

    public ProcessDetailAdapter(int layoutResId, List<ProcessShowBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProcessShowBean item) {
                helper.setText(R.id.tv_delay_process, item.getProcess_name());
                helper.setText(R.id.tv_today_process, "("+item.getProcess_num()+")");

    }
}
