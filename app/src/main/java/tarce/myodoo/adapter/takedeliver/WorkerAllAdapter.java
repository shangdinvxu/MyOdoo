package tarce.myodoo.adapter.takedeliver;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.NFCWorkerBean;
import tarce.myodoo.R;

/**
 * Created by zouzou on 2017/6/28.
 */

public class WorkerAllAdapter extends BaseQuickAdapter<NFCWorkerBean.ResultBean.ResDataBean, BaseViewHolder>{
    public WorkerAllAdapter(int layoutResId, List<NFCWorkerBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NFCWorkerBean.ResultBean.ResDataBean item) {
        for (int i = 0; i < item.getEmployees().size(); i++) {
            helper.setText(R.id.tv_name, item.getEmployees().get(i).getName());
            helper.setText(R.id.tv_email,item.getEmployees().get(i).getWork_email());
        }
        helper.setText(R.id.tv_zhiwei, item.getName());
    }
}
