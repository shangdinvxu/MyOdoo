package tarce.myodoo.adapter.expand;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zaihuishou.expandablerecycleradapter.model.ExpandableListItem;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractAdapterItem;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractExpandableAdapterItem;

import java.util.List;

import tarce.model.inventory.BomSubBean;
import tarce.myodoo.R;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by rose.zou on 2017/6/6.
 */

public class LastItem extends AbstractExpandableAdapterItem{

    private ImageView mArrow;
    private TextView mTv_name;
    private TextView mTv_gongxu;
    private TextView mTv_processid;
    private TextView mNum;

    public LastItem(Context context) {
        this.context = context;
    }

    private Context context;
    @Override
    public void onExpansionToggled(boolean expanded){
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
    public int getLayoutResId() {
        return R.layout.item_kast;
    }

    @Override
    public void onBindViews(View root) {
        mArrow = (ImageView) root.findViewById(R.id.iv_arrow);
        mTv_name = (TextView) root.findViewById(R.id.tv_name);
        mTv_gongxu = (TextView) root.findViewById(R.id.tv_gongxu);
        mTv_processid = (TextView) root.findViewById(R.id.tv_process_id);
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
    public void onUpdateViews(Object model, int position) {
        super.onUpdateViews(model, position);
        onSetViews();
        final BomSubBean.BomBottomBean employee = (BomSubBean.BomBottomBean) model;
        mTv_name.setText("["+employee.code+"]"+employee.name);
        mTv_gongxu.setText(StringUtils.stringFilter(employee.product_specs));
        mTv_gongxu.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mTv_gongxu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TipDialog(context, R.style.MyDialogStyle, employee.product_specs).show();
            }
        });
        mTv_processid.setText(String.valueOf(employee.process_id));
        mNum.setText(StringUtils.doubleToString(employee.qty));
        ExpandableListItem parentListItem = (ExpandableListItem) model;
        List<?> childItemList = parentListItem.getChildItemList();
        if (childItemList != null && !childItemList.isEmpty()){
            mArrow.setVisibility(View.VISIBLE);
        }else {
            mArrow.setVisibility(View.GONE);
        }
    }}
