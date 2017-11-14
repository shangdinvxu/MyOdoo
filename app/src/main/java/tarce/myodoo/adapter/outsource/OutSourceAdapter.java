package tarce.myodoo.adapter.outsource;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.OutsourceBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by zouwansheng on 2017/11/11.
 */

public class OutSourceAdapter extends BaseQuickAdapter<OutsourceBean.ResultBean.ResDataBean, BaseViewHolder> {
    private List<OutsourceBean.ResultBean.ResDataBean> data;

    @Override
    public List<OutsourceBean.ResultBean.ResDataBean> getData() {
        return data;
    }

    public void setData(List<OutsourceBean.ResultBean.ResDataBean> data) {
        this.data = data;
    }

    public OutSourceAdapter(int layoutResId, List<OutsourceBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, OutsourceBean.ResultBean.ResDataBean item) {
        helper.setText(R.id.id_product_in, item.getName())
                .setText(R.id.from_id, item.getProduction_id().get(1)+"")
        .setText(R.id.gongxu_product, item.getProcess_id().get(1)+"")
        .setText(R.id.num_outsourcing, item.getQty_produced()+"")
        .setText(R.id.people_buy, item.getEmployee_id().get(1)+"");
    }
}
