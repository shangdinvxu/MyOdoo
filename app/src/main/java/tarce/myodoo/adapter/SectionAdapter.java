package tarce.myodoo.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.myodoo.R;
import tarce.myodoo.bean.MainItemBean;

/**
 * Created by Daniel.Xu on 2017/5/2.
 */

public class SectionAdapter extends BaseSectionQuickAdapter<MainItemBean,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SectionAdapter(int layoutResId, int sectionHeadResId, List<MainItemBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, MainItemBean item) {

    }

    @Override
    protected void convert(BaseViewHolder helper, MainItemBean item) {
        helper.setText(R.id.text,item.t.getName());
        if (item.t.getNumber()>0){
            if (item.t.getNumber()>99){
                helper.setText(R.id.number,"99+");
                helper.setVisible(R.id.number,true);
            }else {
                helper.setText(R.id.number,item.t.getNumber()+"");
                helper.setVisible(R.id.number,true);
            }
        }else {
            helper.setVisible(R.id.number,false);
        }
    }
}

