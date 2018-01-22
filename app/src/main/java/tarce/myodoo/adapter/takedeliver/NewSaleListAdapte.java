package tarce.myodoo.adapter.takedeliver;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.NewSaleListBean;
import tarce.model.inventory.SalesOutListResponse;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by zws on 2017/8/9.
 */

public class NewSaleListAdapte extends BaseQuickAdapter<NewSaleListBean.ResultBean.ResDataBean.AbleToDataBean, BaseViewHolder>{

    public List<NewSaleListBean.ResultBean.ResDataBean.AbleToDataBean> getDataAll() {
        return dataAll;
    }

    public void setDataAll(List<NewSaleListBean.ResultBean.ResDataBean.AbleToDataBean> dataAll) {
        this.dataAll = dataAll;
    }

    private List<NewSaleListBean.ResultBean.ResDataBean.AbleToDataBean> dataAll;
    public NewSaleListAdapte(int layoutResId, List<NewSaleListBean.ResultBean.ResDataBean.AbleToDataBean> data) {
        super(layoutResId, data);
        this.dataAll = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, NewSaleListBean.ResultBean.ResDataBean.AbleToDataBean item) {
        if (item.getName()!= null && !StringUtils.isNullOrEmpty(item.getName())){
            helper.setText(R.id.name_note, item.getName());
        }else {
            helper.setText(R.id.name_note, "");
        }
        if (item.getOrigin()!= null && !StringUtils.isNullOrEmpty(item.getOrigin())){
            helper.setText(R.id.name_origin_note, "源单据:"+item.getOrigin());
        }else {
            helper.setText(R.id.name_origin_note, "");
        }
        if (!StringUtils.isNullOrEmpty(item.getState())){
            if ("assigned".equals(item.getState())
                    || "partially_available".equals(item.getState())){
                helper.getView(R.id.baoliu).setVisibility(View.VISIBLE);
            }else {
                helper.getView(R.id.baoliu).setVisibility(View.GONE);
            }
        }else {
            helper.getView(R.id.baoliu).setVisibility(View.GONE);
        }
        if (!StringUtils.isNullOrEmpty(item.getBack_order_id())){
            helper.getView(R.id.qiandan).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.qiandan).setVisibility(View.GONE);
        }
        if (!StringUtils.isNullOrEmpty(item.getState())){
            if ("done".equals(item.getState())){
                helper.getView(R.id.alldone).setVisibility(View.VISIBLE);
            }else {
                helper.getView(R.id.alldone).setVisibility(View.GONE);
            }
        }
        if (!StringUtils.isNullOrEmpty(item.getState())){
            if (!"secondary_operation_done".equals(item.getState()) && !"done".equals(item.getState()) && !"assigned".equals(item.getState())
                    && !"partially_available".equals(item.getState()) && StringUtils.isNullOrEmpty(item.getBack_order_id())){
                helper.getView(R.id.quanxin).setVisibility(View.VISIBLE);
            }else {
                helper.getView(R.id.quanxin).setVisibility(View.GONE);
            }
        }
    }
}
