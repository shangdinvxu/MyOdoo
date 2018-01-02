package tarce.myodoo.adapter.newproduct;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.LineBean;
import tarce.myodoo.R;

/**
 * Created by zouwansheng on 2017/12/28.
 */

public class LineAdapter extends BaseQuickAdapter<LineBean.ResultBean.ResDataBean, BaseViewHolder> {

    public LineAdapter(int layoutResId, List<LineBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LineBean.ResultBean.ResDataBean item) {
        int adapterPosition = helper.getAdapterPosition();
        if (item.getPosition() == adapterPosition){
            helper.setTextColor(R.id.line_name, Color.BLUE);
            item.setPosition(-1);
        }else {
            helper.setTextColor(R.id.line_name, Color.BLACK);
        }
            helper.setText(R.id.line_name, item.getLine_name());

    }
}
