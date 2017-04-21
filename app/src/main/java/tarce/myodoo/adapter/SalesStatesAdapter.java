package tarce.myodoo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.GetGroupByListresponse;
import tarce.myodoo.R;
import tarce.support.MyLog;

/**
 * Created by Daniel.Xu on 2017/4/21.
 */

public class SalesStatesAdapter extends BaseQuickAdapter<GetGroupByListresponse.ResultBean.ResDataBean.StatesBean ,BaseViewHolder> {
    private int i = 0 ;
    public SalesStatesAdapter(int layoutResId, List<GetGroupByListresponse.ResultBean.ResDataBean.StatesBean> data) {
        super(layoutResId, data);
        MyLog.e(TAG,"data.size()"+data.size());
    }



    @Override
    protected void convert(BaseViewHolder helper, GetGroupByListresponse.ResultBean.ResDataBean.StatesBean item) {
        if (item.getState().equals("assigned")){
            helper.setText(R.id.text,"可用");
            helper.setText(R.id.number,item.getState_count()+"");
        }else if (item.getState().equals("confirmed")){
            helper.setText(R.id.text,"等待可用");
            helper.setText(R.id.number,item.getState_count()+"");
        }else if (item.getState().equals("done")){
            helper.setText(R.id.text,"完成");
            helper.setText(R.id.number,item.getState_count()+"");
        }else if (item.getState().equals("partially_available")){
            helper.setText(R.id.text,"部分可用");
            helper.setText(R.id.number,item.getState_count()+"");
        }
    }
}
