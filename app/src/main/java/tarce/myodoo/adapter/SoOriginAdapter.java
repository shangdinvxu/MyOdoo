package tarce.myodoo.adapter;

import android.opengl.Visibility;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.SoOriginBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by zouwansheng on 2017/12/19.
 */

public class SoOriginAdapter extends BaseQuickAdapter<SoOriginBean.ResultBean.ResDataBean, BaseViewHolder> {
    public SoOriginAdapter(int layoutResId, List<SoOriginBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SoOriginBean.ResultBean.ResDataBean item) {
        helper.setText(R.id.tv_so_origin, item.getOrigin_name());
        helper.setText(R.id.tv_so_count, item.getOrigin_count()+"");
        if (item.isHave()){
            helper.setVisible(R.id.view_have, false);
        }else {
            helper.setVisible(R.id.view_have, true);
        }
    }
}
