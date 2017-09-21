package tarce.myodoo.adapter.takedeliver;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.TakeDelListBean;
import tarce.myodoo.R;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by zouzou on 2017/6/23.
 * 收货详情页的recycler列表
 */

public class DetailTakedAdapter extends BaseQuickAdapter<TakeDelListBean.ResultBean.ResDataBean.PackOperationProductIdsBean, BaseViewHolder>{

    private Context context;

    public String getShowNotgood() {
        return showNotgood;
    }

    public void setShowNotgood(String showNotgood) {
        this.showNotgood = showNotgood;
    }

    private String showNotgood;
    public DetailTakedAdapter(int layoutResId, List<TakeDelListBean.ResultBean.ResDataBean.PackOperationProductIdsBean> data, Context context, String showNotgood) {
        super(layoutResId, data);
        this.context = context;
        this.showNotgood = showNotgood;
    }

    @Override
    protected void convert(BaseViewHolder helper, final TakeDelListBean.ResultBean.ResDataBean.PackOperationProductIdsBean item) {
        switch (showNotgood){
            case "qc_check":
                helper.setVisible(R.id.nongood, true);
                helper.setBackgroundColor(R.id.nongood, context.getResources().getColor(R.color.grgray));
                helper.setText(R.id.nongood, item.getRejects_qty()+"");
                break;
            case "validate":
                helper.setVisible(R.id.nongood, true);
                helper.setText(R.id.nongood, item.getRejects_qty()+"");
                break;
        }
        helper.setText(R.id.product,item.getProduct_id().getName())
                .setText(R.id.need_out, item.getProduct_qty()+"")
                .setText(R.id.done, item.getQty_done()+"")
                .setText(R.id.tv_num, helper.getPosition()+1+".");
        if (item.getProduct_id().getArea_id().getArea_name() == null){
            helper.setText(R.id.need_in,"");
        }else {
            helper.setText(R.id.need_in,item.getProduct_id().getArea_id().getArea_name()+"");
        }
        if (item.getPack_id() == -1){
            helper.setTextColor(R.id.product, Color.GRAY)
                    .setTextColor(R.id.need_out, Color.GRAY)
                    .setTextColor(R.id.done, Color.GRAY)
                    .setTextColor(R.id.need_in, Color.GRAY)
                    .setTextColor(R.id.tv_num, Color.GRAY)
                    .setTextColor(R.id.nongood, Color.GRAY);
        }else {
            helper.setTextColor(R.id.product, Color.BLACK)
                    .setTextColor(R.id.need_out, Color.BLACK)
                    .setTextColor(R.id.done, Color.BLACK)
                    .setTextColor(R.id.need_in, Color.BLACK)
                    .setTextColor(R.id.tv_num, Color.BLACK)
                    .setTextColor(R.id.nongood, Color.BLACK);
        }

        helper.addOnClickListener(R.id.tv_guige);
        helper.getView(R.id.tv_guige).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TipDialog(context, R.style.MyDialogStyle, item.getProduct_id().getProduct_specs()).show();
            }
        });
    }
}
