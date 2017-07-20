package tarce.myodoo.adapter.expand;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zaihuishou.expandablerecycleradapter.model.ExpandableListItem;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractExpandableAdapterItem;

import java.util.List;

import tarce.model.inventory.BomFramworkBean;
import tarce.myodoo.R;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.StringUtils;


public class EmployeeItem extends AbstractExpandableAdapterItem {

    private TextView mName;
    private ImageView mArrow;
    private TextView mExpand;
    private TextView mProcess_id;
    private TextView mNum;

    public EmployeeItem(Context context) {
        this.context = context;
    }

    private Context context;

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
        mNum = (TextView) root.findViewById(R.id.tv_num);
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
        final BomFramworkBean.ResultBean.ResDataBean.BomIdsBeanX.BomIdsBean employee = (BomFramworkBean.ResultBean.ResDataBean.BomIdsBeanX.BomIdsBean) model;
        mName.setText("["+employee.code+"]"+employee.name);
        mExpand.setText(StringUtils.stringFilter(employee.product_specs));
        mExpand.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TipDialog(context, R.style.MyDialogStyle, employee.product_specs).show();
            }
        });
        mProcess_id.setText(String.valueOf(employee.process_id));
        mNum.setText(StringUtils.doubleToString(employee.qty));
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
