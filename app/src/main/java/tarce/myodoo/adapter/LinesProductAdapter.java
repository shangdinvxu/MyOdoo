package tarce.myodoo.adapter;

import android.graphics.Color;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.ProductLinesBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;
import tarce.support.ToastUtils;

/**
 * Created by zouwansheng on 2017/10/18.
 */

public class LinesProductAdapter extends BaseQuickAdapter<ProductLinesBean.ResultBean.ResDataBean, BaseViewHolder> {
    public LinesProductAdapter(int layoutResId, List<ProductLinesBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductLinesBean.ResultBean.ResDataBean item) {
        int adapterPosition = helper.getAdapterPosition();
        if (item.getPosition() == adapterPosition){
            helper.setTextColor(R.id.line_name, Color.BLUE);
            item.setPosition(-1);
        }else {
            helper.setTextColor(R.id.line_name, Color.BLACK);
        }
        List production_line_id = (List) item.getProduction_line_id();
        Double o = (Double) production_line_id.get(0);
        int a =  StringUtils.doubleToInt(o);
        if (a == -1000){
            helper.setText(R.id.line_name, "暂无产线");
        }else {
            helper.setText(R.id.line_name, "产线："+production_line_id.get(1));
        }
    }
}
