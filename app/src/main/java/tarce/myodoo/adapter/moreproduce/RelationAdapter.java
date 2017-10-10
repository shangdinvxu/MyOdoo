package tarce.myodoo.adapter.moreproduce;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.MaterialRelationBean;
import tarce.myodoo.R;

/**
 * Created by zouwansheng on 2017/9/27.
 */

public class RelationAdapter extends BaseQuickAdapter<MaterialRelationBean.ResultBean.ResDataBean.InputBean, BaseViewHolder> {
    public RelationAdapter(int layoutResId, List<MaterialRelationBean.ResultBean.ResDataBean.InputBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MaterialRelationBean.ResultBean.ResDataBean.InputBean item) {
        helper.setText(R.id.tv_position, (helper.getPosition()+1)+".")
                .setText(R.id.tv_name_relation, item.getProduct_name());
    }
}
