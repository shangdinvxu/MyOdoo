package tarce.myodoo.adapter.product;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.GetFeedbackBean;
import tarce.myodoo.R;

/**
 * Created by zouzou on 2017/6/22.
 * 反馈适配器
 */

public class FeedbackAdapter extends BaseQuickAdapter<GetFeedbackBean.ResultBean.ResDataBean, BaseViewHolder>{


    public FeedbackAdapter(int layoutResId, List<GetFeedbackBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetFeedbackBean.ResultBean.ResDataBean item) {
        helper.setText(R.id.string_feedback, item.getContent());
    }
}
