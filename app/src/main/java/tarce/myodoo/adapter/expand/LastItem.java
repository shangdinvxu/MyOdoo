package tarce.myodoo.adapter.expand;

import android.view.View;
import android.widget.TextView;

import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractAdapterItem;

import tarce.model.inventory.BomSubBean;
import tarce.myodoo.R;

/**
 * Created by rose.zou on 2017/6/6.
 */

public class LastItem extends AbstractAdapterItem {
    private TextView mTv_name;
    private TextView mTv_gongxu;
    @Override
    public int getLayoutResId() {
        return R.layout.item_kast;
    }

    @Override
    public void onBindViews(View root) {
        mTv_name = (TextView) root.findViewById(R.id.tv_name);
        mTv_gongxu = (TextView) root.findViewById(R.id.tv_gongxu);
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
        if (model instanceof BomSubBean) {
            BomSubBean employee = (BomSubBean) model;
            mTv_name.setText("["+employee.code+"]"+employee.name);
         //   mTv_name.setText(employee.name);
            mTv_gongxu.setText(employee.product_specs);
        }
    }
}
