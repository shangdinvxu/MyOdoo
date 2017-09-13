package tarce.myodoo.adapter.projectpick;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.ProjectDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by zouwansheng on 2017/9/8.
 */

public class ProjectDetailAdapter extends BaseQuickAdapter<ProjectDetailBean.ResultBean.ResDataBean.LineIdsBean, BaseViewHolder> {
    public ProjectDetailAdapter(int layoutResId, List<ProjectDetailBean.ResultBean.ResDataBean.LineIdsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectDetailBean.ResultBean.ResDataBean.LineIdsBean item) {
        {
            helper.setText(R.id.product, "产品："+item.getName())
                    .setText(R.id.line_num, (helper.getPosition()+1)+". ")
                    .setText(R.id.need_in, "位置: " + item.getLocation())
                    .setText(R.id.origin_qty, "领料数量: " + item.getQuantity_done());
            helper.setText(R.id.done, "需求：" + item.getProduct_qty());
            helper.setText(R.id.inventory, "可用: " + item.getQuantity_available());
            helper.setText(R.id.baoliu, "保留: " + item.getReserve());
        }
    }
}
