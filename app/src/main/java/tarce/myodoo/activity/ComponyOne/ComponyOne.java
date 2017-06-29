package tarce.myodoo.activity.ComponyOne;

import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractExpandableAdapterItem;

import tarce.model.inventory.BomFramworkBean;
import tarce.model.inventory.NFCWorkerBean;
import tarce.myodoo.R;

/**
 * Created by zouzou on 2017/6/29.
 */

public class ComponyOne extends AbstractExpandableAdapterItem {
    private ImageView mArrow;
    private NFCWorkerBean.ResultBean mCompany;
    private boolean isHaveHeader;

    public ComponyOne(boolean isHaveHeader) {
        this.isHaveHeader = isHaveHeader;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.componyone;
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
        mArrow = (ImageView) root.findViewById(R.id.iv_arrow);
        if (isHaveHeader){
            root.setVisibility(View.GONE);
        }
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
    }
}
