package tarce.myodoo.activity.ComponyOne;

import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zaihuishou.expandablerecycleradapter.model.ExpandableListItem;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractExpandableAdapterItem;

import java.util.List;

import tarce.model.inventory.BomFramworkBean;
import tarce.model.inventory.NFCWorkerBean;
import tarce.myodoo.R;

/**
 * Created by zouzou on 2017/6/29.
 */

public class EmployeeTwo extends AbstractExpandableAdapterItem {
    private TextView mName;
    private ImageView mArrow;
    private TextView mExpand;
    private TextView mProcess_id;

    @Override
    public int getLayoutResId() {
        return R.layout.item_employee;
    }

    @Override
    public void onBindViews(View root) {
        mName = (TextView) root.findViewById(R.id.tv_name);
        mArrow = (ImageView) root.findViewById(R.id.iv_arrow);
        mExpand = (TextView) root.findViewById(R.id.tv_gongxu);
        mProcess_id = (TextView) root.findViewById(R.id.tv_process_id);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (getExpandableListItem() != null && getExpandableListItem().getChildItemList() != null) {
                    if (getExpandableListItem().isExpanded()){
                        collapseView();
                    } else {
                        expandView();
                    }
                }
            }
        });
    }

    @Override
    public void onSetViews() {
        mArrow.setVisibility(View.GONE);
    }

    @Override
    public void onUpdateViews(Object model, int position){
        super.onUpdateViews(model, position);
        onSetViews();
        NFCWorkerBean.ResultBean.ResDataBean employee = (NFCWorkerBean.ResultBean.ResDataBean) model;
        mName.setText(employee.name);
       // mExpand.setText(employee.product_specs);
        mProcess_id.setText(employee.department_id+"");
        ExpandableListItem parentListItem = (ExpandableListItem) model;
        List<?> childItemList = parentListItem.getChildItemList();
        if (childItemList != null && !childItemList.isEmpty()){
            mArrow.setVisibility(View.VISIBLE);
        }else {
            mArrow.setVisibility(View.GONE);
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
}
