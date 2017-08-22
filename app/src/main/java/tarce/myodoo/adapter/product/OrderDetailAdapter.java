package tarce.myodoo.adapter.product;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

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

    private OrderDetailBean.ResultBean result;
    private boolean gray_bac;

    public boolean isGray_bac() {
        return gray_bac;
    }

    public void setGray_bac(boolean gray_bac) {
        this.gray_bac = gray_bac;
    }

    public OrderDetailAdapter(Context context, List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list, String head_name, OrderDetailBean.ResultBean result) {
        this.context = context;
        this.list = list;
        this.head_name = head_name;
        this.result = result;
    }

    public OrderDetailAdapter(Context context, List<OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean> list) {
        this.context = context;
        this.list = list;
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
                holder.tv_advice_order.setText("建议:"+list.get(position-1).getSuggest_qty());
                if (list.get(position-1).getArea_id() != null){
                    holder.tv_area_order.setText("位置:"+list.get(position-1).getArea_id().getArea_name());
                }else {
                    holder.tv_area_order.setText("位置:null");
                }
                holder.tv_kucun_order.setText("库存:"+StringUtils.changeDouble(list.get(position-1).getQty_available()));
                holder.tv_need_order.setText("需求:"+list.get(position-1).getProduct_uom_qty());
                if (result.getRes_data().getState().equals("waiting_material")
                        || result.getRes_data().getState().equals("prepare_material_ing")
                        || result.getRes_data().getState().equals("finish_prepare_material")){
                    holder.tv_prepare_order.setText("备料:"+(list.get(position-1).getQuantity_ready()+list.get(position-1).getQuantity_done()));
                }else {
                    holder.tv_prepare_order.setText("备料:"+list.get(position-1).getQuantity_done());
                }
                if (isGray_bac()){
                    holder.tv_kucun_order.setTextColor(Color.GRAY);
                    holder.tv_prepare_order.setTextColor(Color.GRAY);
                    holder.tv_need_order.setTextColor(Color.GRAY);
                    holder.tv_area_order.setTextColor(Color.GRAY);
                    holder.tv_id_order.setTextColor(Color.GRAY);
                    holder.tv_id_product.setTextColor(Color.GRAY);
                    holder.tv_advice_order.setTextColor(Color.GRAY);
                }else {
                    holder.tv_kucun_order.setTextColor(Color.BLACK);
                    holder.tv_prepare_order.setTextColor(Color.BLACK);
                    holder.tv_need_order.setTextColor(Color.BLACK);
                    holder.tv_area_order.setTextColor(Color.BLACK);
                    holder.tv_id_order.setTextColor(Color.BLACK);
                    holder.tv_id_product.setTextColor(Color.BLACK);
                    holder.tv_advice_order.setTextColor(Color.BLACK);
                }
              //  holder.itemView.setTag(list.get(position-1));
                holder.itemView.setTag(R.id.tag_first,list.get(position-1));
                holder.itemView.setTag(R.id.tag_second,position-1);
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
                listener.onItemClick(v, (OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean) v.getTag(R.id.tag_first), (int) v.getTag(R.id.tag_second));
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
        public TextView tv_feedback;
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
                tv_feedback = (TextView) itemView.findViewById(R.id.tv_feedback);
            }
        }
    }

    public static interface OnRecyclerViewItemClickListener{
        void onItemClick(View view,OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean linesBean, int position);
    }

    private OnRecyclerViewItemClickListener listener;


    public void  setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener){
        this.listener = listener;
    }

}
