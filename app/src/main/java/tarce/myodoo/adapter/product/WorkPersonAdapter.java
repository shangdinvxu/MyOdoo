package tarce.myodoo.adapter.product;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import tarce.model.inventory.FreeWorkBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.SectionAdapter;
import tarce.myodoo.bean.MainItemBean;

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
    public WorkViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        WorkViewHold viewHold = null;
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_work_person, parent, false);
        viewHold = new WorkViewHold(view);
        return viewHold;
    }

    @Override
    public void onBindViewHolder(final WorkViewHold holder, final int position) {
        holder.mTv_show_name.setText(list.get(position).getName());
       final FreeWorkBean.ResultBean.ResDataBean resDataBean = list.get(position);

        /*if (selectList.contains(resDataBean)) {
            holder.mTv_show_name.setChecked(true);
            Log.i("WorkPersonAdapter","1");
        } else {
            holder.mTv_show_name.setChecked(false);
            Log.i("WorkPersonAdapter","2");
        }*/
        holder.mTv_show_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (!selectList.contains(holder.itemView.getTag())) {
                        selectList.add(resDataBean);
                        Log.i("WorkPersonAdapter","3");
                    }
                }else {
                    if (selectList.contains(holder.itemView.getTag())) {
                        selectList.remove(resDataBean);
                        Log.i("WorkPersonAdapter","4");
                    }
                }
            }
        });
        if (selectList.contains(resDataBean)) {
            holder.mTv_show_name.setChecked(true);
            Log.i("WorkPersonAdapter","1");
        } else {
            holder.mTv_show_name.setChecked(false);
            Log.i("WorkPersonAdapter","2");
        }
        holder.itemView.setTag(resDataBean);
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
