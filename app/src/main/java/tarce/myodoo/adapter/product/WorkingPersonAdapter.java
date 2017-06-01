package tarce.myodoo.adapter.product;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;

import tarce.model.inventory.FreeWorkBean;
import tarce.model.inventory.WorkingWorkerBean;
import tarce.myodoo.R;
import tarce.myodoo.bean.WorkingStateBean;

/**
 * Created by rose.zou on 2017/5/31.
 */

public class WorkingPersonAdapter extends RecyclerView.Adapter<WorkingPersonAdapter.WorkingViewHold>{
   private List<WorkingStateBean> list;
    private boolean close;

    public WorkingPersonAdapter(List<WorkingStateBean> list, Context context, boolean close) {
        this.list = list;
        this.context = context;
        this.close = close;
    }

    private Context context;

    @Override
    public WorkingViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        WorkingViewHold viewHold = null;
        View view = LayoutInflater.from(context).inflate(R.layout.adapte_working_person, parent, false);
        viewHold = new WorkingViewHold(view);
        return viewHold;
    }

    @Override
    public void onBindViewHolder(final WorkingViewHold holder, final int position) {
        holder.tv_show_name.setText(list.get(position).getName());
        ((SwipeMenuLayout)holder.itemView).setSwipeEnable(close);
        if (!close){
            holder.tv_state.setVisibility(View.GONE);
        }
        holder.tv_state.setText(list.get(position).getState());
        holder.mBtn_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swipeListener!=null){
                    swipeListener.onOffline(position);
                    ((SwipeMenuLayout) holder.itemView).quickClose();
                }
            }
        });
        holder.mBtn_outline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swipeListener!=null){
                    swipeListener.onOutline(position);
                    ((SwipeMenuLayout) holder.itemView).quickClose();
                }
            }
        });
        holder.mBtn_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swipeListener!=null){
                    swipeListener.onOnline(position);
                    ((SwipeMenuLayout) holder.itemView).quickClose();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size()==0?0:list.size();
    }

    public class WorkingViewHold extends RecyclerView.ViewHolder{

        private TextView tv_show_name;
        private Button mBtn_offline;
        private Button mBtn_outline;
        private Button mBtn_online;
        private TextView tv_state;
        public WorkingViewHold(View itemView) {
            super(itemView);
            tv_show_name = (TextView) itemView.findViewById(R.id.tv_show_woring);
            mBtn_offline = (Button) itemView.findViewById(R.id.but_state_offline);
            mBtn_online = (Button) itemView.findViewById(R.id.but_state_online);
            mBtn_outline = (Button) itemView.findViewById(R.id.but_state_outline);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state_worker);
        }
    }

    public interface onSwipeListener{
        void onOffline(int position);
        void onOnline(int position);
        void onOutline(int position);
    }

    private onSwipeListener swipeListener;

    public void setOnSwipeListener(onSwipeListener swipeListener){
        this.swipeListener = swipeListener;
    }
}
