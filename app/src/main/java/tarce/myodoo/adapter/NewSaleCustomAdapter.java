package tarce.myodoo.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.CustomerSaleBean;
import tarce.myodoo.R;

/**
 * Created by zws on 2017/8/9.
 */

public class NewSaleCustomAdapter extends BaseQuickAdapter<CustomerSaleBean.ResultBean.ResDataBean, BaseViewHolder>{
    private List<CustomerSaleBean.ResultBean.ResDataBean> data;

    @Override
    public List<CustomerSaleBean.ResultBean.ResDataBean> getData() {
        return data;
    }

    public void setData(List<CustomerSaleBean.ResultBean.ResDataBean> data) {
        this.data = data;
    }

    public NewSaleCustomAdapter(int layoutResId, List<CustomerSaleBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomerSaleBean.ResultBean.ResDataBean item) {
        if (item.getName()!=null)
        helper.setText(R.id.name_cutt_sale, item.getName());
        helper.setText(R.id.wait_num, "待处理："+item.getWaiting_data());
        helper.setText(R.id.able_num, "可处理："+item.getAble_to_data());
        if (item.getAble_to_data()>0){
            helper.getView(R.id.red_tv).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.red_tv).setVisibility(View.INVISIBLE);
        }
    }
}
