package tarce.myodoo.adapter.expand;

import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractExpandableAdapterItem;

import tarce.model.inventory.BomFramworkBean;
import tarce.myodoo.R;


public class CompanyItem extends AbstractExpandableAdapterItem {

    private TextView mName;
    private TextView mProcess;
    private ImageView mArrow;
    private BomFramworkBean.ResultBean.ResDataBean mCompany;

    @Override
    public int getLayoutResId() {
        return R.layout.item_company;
    }

    @Override
    public void onBindViews(final View root) {
        /**
         * control item expand and unexpand
         */
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doExpandOrUnexpand();
           //     Toast.makeText(root.getContext(), "click company：" +mCompany.name, Toast.LENGTH_SHORT).show();
            }
        });
        mName = (TextView) root.findViewById(R.id.tv_name);
        mProcess = (TextView) root.findViewById(R.id.tv_process_id);
        mArrow = (ImageView) root.findViewById(R.id.iv_arrow);
    }

    @Override
    public void onExpansionToggled(boolean expanded) {
        float start, target;
        if (expanded) {
            start = 0f;
            target = 90f;
        } else {
            start = 90f;
            target = 0f;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mArrow, View.ROTATION, start, target);
        objectAnimator.setDuration(300);
        objectAnimator.start();
    }

    @Override
    public void onSetViews() {
        mArrow.setImageResource(0);
        mArrow.setImageResource(R.mipmap.arrow_down);
    }

    @Override
    public void onUpdateViews(Object model, int position) {
        super.onUpdateViews(model, position);
        onSetViews();
        onExpansionToggled(getExpandableListItem().isExpanded());
        mCompany = (BomFramworkBean.ResultBean.ResDataBean) model;
        mName.setText("["+mCompany.code+"]"+mCompany.name);
        if (mCompany.process_id.size()!=0){
            if (mCompany.process_id.get(1) instanceof Boolean){
                mProcess.setText("");
            }else {
                mProcess.setText((String)mCompany.process_id.get(1));
            }
        }
    }
}
