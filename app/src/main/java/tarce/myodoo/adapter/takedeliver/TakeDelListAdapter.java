package tarce.myodoo.adapter.takedeliver;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.TakeDelListBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by zouzou on 2017/6/23.
 * 收货列表的适配器
 */

public class TakeDelListAdapter extends BaseQuickAdapter<TakeDelListBean.ResultBean.ResDataBean, BaseViewHolder>{
    @Override
    public List<TakeDelListBean.ResultBean.ResDataBean> getData() {
        return data;
    }

    public void setData(List<TakeDelListBean.ResultBean.ResDataBean> data) {
        this.data = data;
    }

    private List<TakeDelListBean.ResultBean.ResDataBean> data;
    public TakeDelListAdapter(int layoutResId, List<TakeDelListBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, TakeDelListBean.ResultBean.ResDataBean item) {
        helper.setText(R.id.sale_number,  item.getOrigin());
        helper.setText(R.id.parnter_name, item.getParnter_id());
        helper.setText(R.id.state_deliever, StringUtils.switchString(item.getState()))
                .setText(R.id.tv_num, helper.getPosition()+1+".");
    }

}
