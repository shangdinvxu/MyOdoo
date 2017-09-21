package tarce.myodoo.adapter.device;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.GetProcessBean;
import tarce.myodoo.R;

/**
 * Created by zouwansheng on 2017/9/18.
 */

public class DeviceSelecAdapter extends BaseQuickAdapter<GetProcessBean.TestRSubBean.ListSubBean, BaseViewHolder>{
    public DeviceSelecAdapter(int layoutResId, List<GetProcessBean.TestRSubBean.ListSubBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetProcessBean.TestRSubBean.ListSubBean item) {
        helper.setText(R.id.tv_process_name, item.getName());
    }
}
