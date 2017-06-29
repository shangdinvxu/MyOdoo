package tarce.myodoo.activity.ComponyOne;

import android.view.View;
import android.widget.TextView;

import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractAdapterItem;

import tarce.model.inventory.BomSubBean;
import tarce.model.inventory.NFCWorkerBean;
import tarce.myodoo.R;

/**
 * Created by zouzou on 2017/6/29.
 */

public class BottomThree extends AbstractAdapterItem {
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
        if (model instanceof NFCWorkerBean.ResultBean.ResDataBean.EmployeesBean) {
            NFCWorkerBean.ResultBean.ResDataBean.EmployeesBean sixBomBottomBean = (NFCWorkerBean.ResultBean.ResDataBean.EmployeesBean) model;
            mTv_name.setText(sixBomBottomBean.name);
            mTv_gongxu.setText(sixBomBottomBean.work_email);
            /*if (null != sixBomBottomBean.employee_id){
                mTv_processid.setText(sixBomBottomBean.employee_id);
            }*/
        }
    }
}
