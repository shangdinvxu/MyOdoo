package tarce.myodoo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.NFCUserBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by Daniel.Xu on 2017/6/30.
 */

public class NFCUseradapter extends BaseQuickAdapter<NFCUserBean.ResultBean.ResDataBean,BaseViewHolder> {
    public NFCUseradapter(int layoutResId, List<NFCUserBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NFCUserBean.ResultBean.ResDataBean item) {
            helper.setText(R.id.textview_name,item.getName())
                    .setText(R.id.textview_email,item.getWork_email());
        if (StringUtils.isNullOrEmpty(item.getCard_num())){
            helper.setText(R.id.textview_card_num, "未绑定");
        }else {
            helper.setText(R.id.textview_card_num, item.getCard_num());
        }

    }
}
