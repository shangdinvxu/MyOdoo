package tarce.myodoo.adapter.product;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;


import java.util.ArrayList;
import java.util.List;

import tarce.model.inventory.FreeWorkBean;
import tarce.myodoo.R;

/**
 * Created by rose.zou on 2017/5/27.
 * 用于发现待工的员工和工作中的员工
 */

public class WorkPersonAdapter extends RecyclerView.Adapter<WorkPersonAdapter.WorkViewHold> {

    public WorkPersonAdapter(List<FreeWorkBean.ResultBean.ResDataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    private List<FreeWorkBean.ResultBean.ResDataBean> list;
    private Context context;
    private List<FreeWorkBean.ResultBean.ResDataBean> selectList = new ArrayList<>();

    @Override
    public WorkViewHold onCreateViewHolder(ViewGroup parent, int viewType){
        WorkViewHold viewHold = null;
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_work_person, parent, false);
        viewHold = new WorkViewHold(view);
        return viewHold;
    }

    @Override
    public void onBindViewHolder(final WorkViewHold holder, final int position) {
        holder.mTv_show_name.setText(list.get(position).getName());
       final FreeWorkBean.ResultBean.ResDataBean resDataBean = list.get(position);

        holder.mTv_show_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (!selectList.contains(holder.itemView.getTag())) {
                        selectList.add(resDataBean);
                    }
                }else {
                    if (selectList.contains(holder.itemView.getTag())) {
                        selectList.remove(resDataBean);
                    }
                }
            }
        });
        if (selectList.contains(resDataBean)) {
            holder.mTv_show_name.setChecked(true);
        } else {
            holder.mTv_show_name.setChecked(false);
        }
        holder.itemView.setTag(resDataBean);
    }

    /**
     * 返回选中的list数据
     * */
    public List<FreeWorkBean.ResultBean.ResDataBean> getSelected(){
        return selectList;
    }

    @Override
    public int getItemCount() {
        return list.size()==0?0:list.size();
    }

    public class WorkViewHold extends RecyclerView.ViewHolder{
        private CheckBox mTv_show_name;
        public WorkViewHold(View itemView) {
            super(itemView);
            mTv_show_name = (CheckBox) itemView.findViewById(R.id.adapter_bac_radiobu);
        }
    }
}
