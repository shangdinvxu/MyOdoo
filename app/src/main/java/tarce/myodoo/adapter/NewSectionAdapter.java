package tarce.myodoo.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.myodoo.R;
import tarce.myodoo.bean.MainItemBean;
import tarce.myodoo.bean.MainProcessBean;

/**
 * Created by zouwansheng on 2017/12/18.
 */

public class NewSectionAdapter extends BaseSectionQuickAdapter<MainProcessBean,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public NewSectionAdapter(int layoutResId, int sectionHeadResId, List<MainProcessBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, MainProcessBean item) {

    }

    @Override
    protected void convert(BaseViewHolder helper, MainProcessBean item) {
        helper.setText(R.id.text,item.t.getName());
    }
}
