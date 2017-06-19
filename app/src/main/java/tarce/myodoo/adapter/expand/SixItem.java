package tarce.myodoo.adapter.expand;

import android.view.View;
import android.widget.TextView;

import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractAdapterItem;

import tarce.model.inventory.BomSubBean;
import tarce.myodoo.R;

/**
 * Created by rose.zou on 2017/6/19.
 */

public class SixItem extends AbstractAdapterItem {
    private TextView mTv_name;
    private TextView mTv_gongxu;
    private TextView mTv_processid;
    @Override
    public int getLayoutResId() {
        return R.layout.item_six;
    }

    @Override
    public void onBindViews(View root) {
        mTv_name = (TextView) root.findViewById(R.id.tv_name);
        mTv_gongxu = (TextView) root.findViewById(R.id.tv_gongxu);
        mTv_processid = (TextView) root.findViewById(R.id.tv_process_id);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onSetViews() {

    }

    @Override
    public void onUpdateViews(Object model, int position) {
        if (model instanceof BomSubBean.BomBottomBean.SixBomBottomBean) {
            BomSubBean.BomBottomBean.SixBomBottomBean sixBomBottomBean = (BomSubBean.BomBottomBean.SixBomBottomBean) model;
            mTv_name.setText("["+sixBomBottomBean.code+"]"+sixBomBottomBean.name);
            mTv_gongxu.setText(sixBomBottomBean.product_specs);
            mTv_processid.setText(String.valueOf(sixBomBottomBean.getProcess_id()));
        }
    }
}
