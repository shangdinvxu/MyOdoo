package tarce.myodoo.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.ComponyQueryBean;
import tarce.myodoo.R;


/**
 * Created by zws on 2017/8/3.
 */

public class QueryComponyAdapter extends BaseQuickAdapter<ComponyQueryBean.ResultBean.ResDataBean, BaseViewHolder> {
    public QueryComponyAdapter(@LayoutRes int layoutResId, @Nullable List<ComponyQueryBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComponyQueryBean.ResultBean.ResDataBean item) {
        helper.setText(R.id.china_tv, item.getName());
    }
}
