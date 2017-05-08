package tarce.myodoo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.GetGroupByListresponse;
import tarce.myodoo.R;
import tarce.myodoo.bean.AvailabilityBean;
import tarce.support.MyLog;

/**
 * Created by Daniel.Xu on 2017/4/21.
 */

public class SalesStatesAdapter extends BaseQuickAdapter<AvailabilityBean,BaseViewHolder> {
    private int i = 0 ;
    public SalesStatesAdapter(int layoutResId, List<AvailabilityBean> data) {
        super(layoutResId, data);
        MyLog.e(TAG,"data.size()"+data.size());
    }



    @Override
    protected void convert(BaseViewHolder helper, AvailabilityBean item) {
        switch (item.getPer()){
            case 100:
                helper.setText(R.id.text,"可用率100%")
                        .setText(R.id.number,item.getNumber()+"");
                break;
            case 99:
                helper.setText(R.id.text,"可用率1% ~ 99%")
                        .setText(R.id.number,item.getNumber()+"");
                break;
            case 0:
                helper.setText(R.id.text,"可用率0%")
                        .setText(R.id.number,item.getNumber()+"");
                break;
            case 1000:
                helper.setText(R.id.text,"完成")
                        .setText(R.id.number,item.getNumber()+"");
                break;

        }

    }
}
