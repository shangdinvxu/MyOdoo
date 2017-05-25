package tarce.myodoo.adapter.product;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;
import java.util.zip.Inflater;

import tarce.model.inventory.OrderDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by rose.zou on 2017/5/23.
 */

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderViewhold> implements View.OnClickListener{

    private static final int header = 1;
    private static final int body = 2;

    private Context context;
    private String head_name;

    public OrderDetailAdapter(Context context, List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list, String head_name) {
        this.context = context;
        this.list = list;
        this.head_name = head_name;
    }

    private List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list;
    @Override
    public OrderViewhold onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderViewhold viewhold = null;
        switch (viewType){
            case header:
                viewhold = new OrderViewhold(LayoutInflater.from(context).inflate(R.layout.order_detail_header, parent, false), header);
                break;
            case body:
                viewhold = new OrderViewhold(LayoutInflater.from(context).inflate(R.layout.order_detail_body, parent, false), body);
                break;
        }
        return viewhold;
    }

    @Override
    public void onBindViewHolder(OrderViewhold holder, int position) {
        switch(holder.getItemViewType()){
            case header:
                holder.tv_header_name.setText(head_name);
                break;
            case body:
                holder.tv_id_order.setText(position+".");
                holder.tv_id_product.setText(list.get(position-1).getProduct_id());
                holder.tv_advice_order.setText(StringUtils.doubleToString(list.get(position-1).getSuggest_qty()));
                if (list.get(position-1).getArea_id() != null){
                    holder.tv_area_order.setText(list.get(position-1).getArea_id().getArea_name()+"");
                }
                holder.tv_kucun_order.setText(StringUtils.doubleToString(list.get(position-1).getQty_available()));
                holder.tv_need_order.setText(StringUtils.doubleToString(list.get(position-1).getProduct_uom_qty()));
                holder.tv_prepare_order.setText(StringUtils.doubleToString(list.get(position-1).getQuantity_ready()));
                holder.itemView.setTag(list.get(position-1));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;
        if (position == 0){
            viewType = header;
        }else {
            viewType = body;
        }
        return viewType;
    }

    @Override
    public int getItemCount() {
        return 1+list.size();
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            if (v.getTag()!=null){
                listener.onItemClick(v, (OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean) v.getTag());
            }
        }
    }

    public class OrderViewhold extends RecyclerView.ViewHolder{
        public TextView tv_header_name;
        public TextView tv_id_order;
        public TextView tv_id_product;
        public TextView tv_area_order;
        public TextView tv_kucun_order;
        public TextView tv_need_order;
        public TextView tv_advice_order;
        public TextView tv_prepare_order;
        public OrderViewhold(View itemView, int type) {
            super(itemView);
            if (type == header){
                tv_header_name = (TextView) itemView.findViewById(R.id.name_type);
            }else {
                itemView.setOnClickListener(OrderDetailAdapter.this);
                tv_id_order = (TextView) itemView.findViewById(R.id.tv_id_order);
                tv_id_product = (TextView) itemView.findViewById(R.id.tv_id_product);
                tv_area_order = (TextView) itemView.findViewById(R.id.tv_area_order);
                tv_kucun_order = (TextView) itemView.findViewById(R.id.tv_kucun_order);
                tv_need_order = (TextView) itemView.findViewById(R.id.tv_need_order);
                tv_advice_order = (TextView) itemView.findViewById(R.id.tv_advice_order);
                tv_prepare_order = (TextView) itemView.findViewById(R.id.tv_prepare_order);
            }
        }
    }

    public static interface OnRecyclerViewItemClickListener{
        void onItemClick(View view,OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean linesBean);
    }

    private OnRecyclerViewItemClickListener listener;


    public void  setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener){
        this.listener = listener;
    }

}
