package tarce.myodoo.adapter.product;

import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.PickingDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.DateTool;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by rose.zou on 2017/5/22.
 * 领料详情适配器
 */

public class PickingDetailAdapter extends BaseQuickAdapter<PickingDetailBean.ResultBean.ResDataBean, BaseViewHolder>{
    private List<PickingDetailBean.ResultBean.ResDataBean> data;
    public PickingDetailAdapter(int layoutResId, List<PickingDetailBean.ResultBean.ResDataBean> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, PickingDetailBean.ResultBean.ResDataBean item) {
        helper.setText(R.id.id_colum, helper.getPosition()+1+".");
        helper.setText(R.id.tv_display_name, item.getDisplay_name());
        helper.setText(R.id.tv_product_name, item.getProduct_name());
        helper.setText(R.id.tv_name_process, "工序:"+item.getProcess_id().getName());
        helper.setText(R.id.tv_product_qty, StringUtils.doubleToString(item.getProduct_qty()));
        helper.setText(R.id.tv_date_planned_start, item.getDate_planned_start());
       // helper.setText(R.id.tv_date_planned_start, DateTool.getGMTBeijing(item.getDate_planned_start()));
        switch (item.getState()){
            case "draft":
                helper.setText(R.id.tv_state, "草稿");
                break;
            case "confirmed":
                helper.setText(R.id.tv_state, "已排产");
                break;
            case "waiting_material":
                helper.setText(R.id.tv_state, "等待备料");
                break;
            case "prepare_material_ing":
                helper.setText(R.id.tv_state, "备料中...");
                break;
            case "finish_prepare_material":
                helper.setText(R.id.tv_state, "备料完成");
                break;
            case "already_picking":
                helper.setText(R.id.tv_state, "已领料");
                break;
            case "planned":
                helper.setText(R.id.tv_state, "安排");
                break;
            case "progress":
                helper.setText(R.id.tv_state, "进行中");
                break;
            case "waiting_inspection_finish":
                helper.setText(R.id.tv_state, "等待品检完成");
                break;
            case "waiting_rework":
                helper.setText(R.id.tv_state, "等待返工");
                break;
            case "rework_ing":
                helper.setText(R.id.tv_state, "返工中");
                break;
            case "waiting_inventory_material":
                helper.setText(R.id.tv_state, "等待清点退料");
                break;
            case "waiting_warehouse_inspection":
                helper.setText(R.id.tv_state, "等待检验退料");
                break;
            case "waiting_post_inventory":
                helper.setText(R.id.tv_state, "等待入库");
                break;
            case "done":
                helper.setText(R.id.tv_state, "完成");
                break;
        }
    }
}
