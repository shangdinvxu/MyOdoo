package tarce.myodoo.adapter.processproduct;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.model.inventory.MainMdBean;
import tarce.model.inventory.MaterialDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.SectionAdapter;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by rose.zou on 2017/5/24.
 * 生产备料—》延误详细
 */

public class PrepareMdAdapter extends BaseSectionQuickAdapter<MainMdBean, BaseViewHolder>{
    private int itemType;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public PrepareMdAdapter(int layoutResId, int sectionHeadResId, List<MainMdBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainMdBean item) {
        helper.setText(R.id.tv_name_dan, item.t.getDisplay_name());
        helper.setText(R.id.tv_name_prod, item.t.getProduct_name());
        helper.setText(R.id.tv_num_state, StringUtils.doubleToString(item.t.getProduct_qty()));
        helper.setText(R.id.tv_person_need, item.t.getIn_charge_name());
        switch (item.t.getState()){
            case "draft":
                helper.setText(R.id.tv_state_name, "草稿");
                break;
            case "confirmed":
                helper.setText(R.id.tv_state_name, "已排产");
                break;
            case "waiting_material":
                helper.setText(R.id.tv_state_name, "等待备料");
                break;
            case "prepare_material_ing":
                helper.setText(R.id.tv_state_name, "备料中...");
                break;
            case "finish_prepare_material":
                helper.setText(R.id.tv_state_name, "备料完成");
                break;
            case "already_picking":
                helper.setText(R.id.tv_state_name, "已领料");
                break;
            case "planned":
                helper.setText(R.id.tv_state_name, "安排");
                break;
            case "progress":
                helper.setText(R.id.tv_state_name, "生产中");
                break;
            case "waiting_inspection_finish":
                helper.setText(R.id.tv_state_name, "等待品检完成");
                break;
            case "waiting_rework":
                helper.setText(R.id.tv_state_name, "等待返工");
                break;
            case "rework_ing":
                helper.setText(R.id.tv_state_name, "返工中");
                break;
            case "waiting_inventory_material":
                helper.setText(R.id.tv_state_name, "等待清点退料");
                break;
            case "waiting_warehouse_inspection":
                helper.setText(R.id.tv_state_name, "等待检验退料");
                break;
            case "waiting_post_inventory":
                helper.setText(R.id.tv_state_name, "等待入库");
                break;
            case "done":
                helper.setText(R.id.tv_state_name, "完成");
                break;
        }
    }

    @Override
    protected void convertHead(BaseViewHolder helper, MainMdBean item) {

    }
}
