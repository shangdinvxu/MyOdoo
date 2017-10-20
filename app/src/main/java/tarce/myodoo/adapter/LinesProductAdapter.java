package tarce.myodoo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.ProductLinesBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by zouwansheng on 2017/10/18.
 */

public class LinesProductAdapter extends BaseQuickAdapter<ProductLinesBean.ResultBean.ResDataBean, BaseViewHolder> {
    public LinesProductAdapter(int layoutResId, List<ProductLinesBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductLinesBean.ResultBean.ResDataBean item) {
        List production_line_id = (List) item.getProduction_line_id();
        Double o = (Double) production_line_id.get(0);
        int a =  StringUtils.doubleToInt(o);
        if (a == -1000){
            helper.setText(R.id.line_name, "暂无产线");
        }else {
            helper.setText(R.id.line_name, "产线："+production_line_id.get(1));
        }
        helper.setText(R.id.line_num, "生产单："+item.getProduction_line_id_count());
    }
}
