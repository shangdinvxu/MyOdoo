package tarce.myodoo.adapter.expand;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractAdapterItem;

import tarce.model.inventory.BomSubBean;
import tarce.myodoo.R;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by rose.zou on 2017/6/19.
 */

public class SixItem extends AbstractAdapterItem {
    private TextView mTv_name;
    private TextView mTv_gongxu;
    private TextView mTv_processid;
    private TextView mNum;

    public SixItem(Context context) {
        this.context = context;
    }

    private Context context;
    @Override
    public int getLayoutResId() {
        return R.layout.item_six;
    }

    @Override
    public void onBindViews(View root) {
        mTv_name = (TextView) root.findViewById(R.id.tv_name);
        mTv_gongxu = (TextView) root.findViewById(R.id.tv_gongxu);
        mTv_processid = (TextView) root.findViewById(R.id.tv_process_id);
        mNum = (TextView) root.findViewById(R.id.tv_num);
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
            final BomSubBean.BomBottomBean.SixBomBottomBean sixBomBottomBean = (BomSubBean.BomBottomBean.SixBomBottomBean) model;
            mTv_name.setText("["+sixBomBottomBean.code+"]"+sixBomBottomBean.name);
            mTv_gongxu.setText(StringUtils.stringFilter(sixBomBottomBean.product_specs));
            mTv_gongxu.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            mTv_gongxu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TipDialog(context, R.style.MyDialogStyle, sixBomBottomBean.product_specs).show();
                }
            });
            mTv_processid.setText(String.valueOf(sixBomBottomBean.getProcess_id()));
            mNum.setText(StringUtils.doubleToString(sixBomBottomBean.qty));
        }
    }
}
