package tarce.myodoo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.QcFeedbaskBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by rose.zou on 2017/6/2.
 * 等待品检的列表页面
 */

public class InspectionSubAdapter extends BaseQuickAdapter<QcFeedbaskBean.ResultBean.ResDataBean, BaseViewHolder>{
    private List<QcFeedbaskBean.ResultBean.ResDataBean> data;

    @Override
    public List<QcFeedbaskBean.ResultBean.ResDataBean> getData() {
        return data;
    }

    public void setData(List<QcFeedbaskBean.ResultBean.ResDataBean> data) {
        this.data = data;
    }

    public InspectionSubAdapter(int layoutResId, List<QcFeedbaskBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, QcFeedbaskBean.ResultBean.ResDataBean item) {
        helper.setText(R.id.tv_receipts_origin, String.valueOf(item.getProduction_id().getDisplay_name()));
        helper.setText(R.id.tv_nameproduct_inspec, String.valueOf(item.getProduction_id().getProduct_id().getProduct_name()));
        if (item.is_random_output() || item.is_multi_output()){
            if (StringUtils.doubleToString(item.getQty_produced()).equals("0")){
                helper.setText(R.id.tv_num_inspection, "多产出");
            }else {
                helper.setText(R.id.tv_num_inspection, item.getQty_produced()+"");
            }
        }else {
            helper.setText(R.id.tv_num_inspection, item.getQty_produced()+"");
        }
        helper.setText(R.id.tv_num, helper.getPosition()+1+".");
        switch (item.getState()){
            case "draft":
                helper.setText(R.id.tv_state_inspection, "等待品检");
                break;
            case "qc_ing":
                helper.setText(R.id.tv_state_inspection, "品检中");
                break;
            case "qc_success":
                helper.setText(R.id.tv_state_inspection, "等待入库");
                break;
            case "qc_fail":
                helper.setText(R.id.tv_state_inspection, "品检失败");
                break;
        }
    }
}
